// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Implements a drivetrain by extending the functionality of {@link MotorMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer count (2 or 4)
 * @param String[] names
 * @param Boolean encoder
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double diameter (> 0.0)
 * @param Double gearing (> 0.0)
 * @param Double deadzone (>= 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * @param String type ("mecanum" or "differential")
 * @param String layout ("tank" or "arcade")
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double measurement)}
 */
public class Drive extends MotorMechanism {
    private String type;

    /**
     * Constructor
     */
    private Drive(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.names = builder.names;
        this.behavior = builder.behavior;
        this.type = builder.type;
        this.layout = builder.layout;
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 2;
        private String[] names = {"driveLeft", "driveRight"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;
        private String type = "";
        private String layout = "";
        private String mechanismName = "Drive";

        /**
         * Drive Builder
         * 
         * @Defaults count = 2
         *           <li>names = {"driveLeft", "driveRight"}
         *           <li>behavior = FLOAT
         *           <li>encoder = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>diameter = 0.0
         *           <li>gearing = 0.0
         *           <li>deadzone = 0.0
         *           <li>gamepad = null
         *           <li>type = "" (interpreted as "differential")
         *           <li>layout = "" (interpreted as "tank" for differential, "robot" for mecanum)
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Specify the number of motors (2, 4)
         */
        public Builder count(int count) {
            this.count = count;
            if (count == 4) {
                String[] names = {"frontLeft", "frontRight", "backLeft", "backRight"};
                this.names = names;
            }
            return this;
        }

        /**
         * Change the names of the hardware devices
         */
        public Builder names(String[] names) {
            this.names = names;
            return this;
        }

        /**
         * Specify the zero-power behavior of the motors (DcMotor.ZeroPowerBehavior.BRAKE or FLOAT)
         */
        public Builder behavior(DcMotor.ZeroPowerBehavior behavior) {
            this.behavior = behavior;
            return this;
        }

        /**
         * Specify the drivetrain type: "differential" (default) or "mecanum"
         */
        public Builder type(String type) {
            this.type = type;
            if (type == "mecanum" && this.count == 2) {
                this.count = 4;
                if (this.names.length == 2) {
                    String[] names = {"frontLeft", "frontRight", "backLeft", "backRight"};
                    this.names = names;
                }
            }
            return this;
        }

        /**
         * Specify the control layout. For Differential, "tank" (default) or "arcade". For Mecanum,
         * "robot" (default) or "field"
         */
        public Builder layout(String layout) {
            this.layout = layout;
            return this;
        }

        /**
         * Build the arm
         */
        @Override
        public Drive build() {
            return new Drive(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    /**
     * Enables teleoperated mecanum movement with gamepad (inherits layout), scaling by multiplier <
     * 1
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Basic mecanum algorithm largely taken from the BasicOmniOpMode block example.
     * <p>
     * Field-centric algorithm taken from:
     * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
     */
    @Override
    public void tele(double multiplier) {
        double heading = 0;
        // Press option to reset imu to combat drift, set heading if applicable
        if (layout == "field") {
            heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            if (gamepad.options) {
                imu.resetYaw();
            }
        }

        double[] movements =
                controlToDirection(count, type, layout, deadzone, heading, gamepad.left_stick_y,
                        gamepad.left_stick_x, gamepad.right_stick_y, gamepad.right_stick_x);

        setPowers(movements, multiplier);
    }

    /**
     * Enables teleoperated mecanum movement with gamepad (inherits layout) with multiplier = 1.0
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Basic mecanum algorithm largely taken from the BasicOmniOpMode block example.
     * <p>
     * Field-centric algorithm taken from:
     * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
     */
    @Override
    public void tele() {
        tele(1.0);
    }

    /**
     * Intermediate function that assigns individual motor powers based on direction specified in
     * runOpMode() calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: forward, backward, left, right, rotateLeft, rotateRight, forwaredLeft,
     * forwardRight, backwardLeft, backwardRight
     */
    @Override
    public void move(double power, String direction, double measurement) {
        double[] unscaledMovements = languageToDirection(count, type, direction);
        moveForMeasurement(unscaledMovements, power, measurement);
    }

    /**
     * Set drivetrain motor movements based on layout: robot(default) or field
     */
    protected static double[] controlToDirection(int count, String type, String layout,
            double deadzone, double heading, float leftY, float leftX, float rightY, float rightX) {
        if (type == "differential" || type == "") {
            double left, right;
            if (layout == "tank" || layout == "") {
                left = map(-leftY, deadzone);
                right = map(-rightY, deadzone);
            } else if (layout == "arcade") {
                left = map(-leftY, deadzone) + map(rightX, deadzone);
                right = map(-leftY, deadzone) - map(rightX, deadzone);
            } else {
                throw new IllegalArgumentException("Unexpected layout: " + layout
                        + ", passed to Differential.tele(). Valid layouts are: tank, arcade");
            }

            double[] movements = new double[count];
            for (int i = 0; i < count; i++) {
                movements[i] = (i % 2 == 0) ? left : right;
            }
            return movements;
        } else {
            double frontLeft, frontRight, backLeft, backRight;
            // Axes (used for both robot-centric and field-centric)
            double axial = map(-leftY, deadzone);
            double lateral = map(leftX, deadzone);
            double yaw = map(rightX, deadzone);

            // Calculate desired individual motor values (orientation factored in for else-if
            // statement
            // i.e. field-centric driving)
            if (layout == "robot" || layout == "") {
                // Scaled individual motor movements derived from axes (left to right, front to
                // back)
                // Scaling is needed to ensure intended ratios of motor powers
                // (otherwise, for example, 1.1 would become 1, while 0.9 would be unaffected)
                double max = Math.max(Math.abs(axial) + Math.abs(lateral) + Math.abs(yaw), 1);
                frontLeft = ((axial + lateral + yaw) / max);
                frontRight = ((axial - lateral - yaw) / max);
                backLeft = ((axial - lateral + yaw) / max);
                backRight = ((axial + lateral - yaw) / max);
            } else if (layout == "field") {
                // Scaled individual motor movements derived from axes and orientation (left to
                // right,
                // front to back)
                // Heading is the current yaw of the robot, which is used to calculate relative axes
                double axial_relative = lateral * Math.sin(-heading) + axial * Math.cos(-heading);
                double lateral_relative = lateral * Math.cos(-heading) - axial * Math.sin(-heading);
                double max = Math.max(
                        Math.abs(axial_relative) + Math.abs(lateral_relative) + Math.abs(yaw), 1);
                frontLeft = ((axial_relative + lateral_relative + yaw) / max);
                frontRight = ((axial_relative - lateral_relative - yaw) / max);
                backLeft = ((axial_relative - lateral_relative + yaw) / max);
                backRight = ((axial_relative + lateral_relative - yaw) / max);
            } else {
                throw new IllegalArgumentException("Unexpected layout: " + layout
                        + ", passed to Mecanum.tele(). Valid layouts are: robot, field");
            }

            double[] movements = {frontLeft, frontRight, backLeft, backRight};
            return movements;
        }
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double[] languageToDirection(int count, String type, String direction) {
        if (type == "differential" || type == "") {
            double[] motorDirections = new double[count];
            switch (direction) {
                case "forward":
                    for (int i = 0; i < count; i++) {
                        motorDirections[i] = 1;
                    }
                    break;
                case "backward":
                    for (int i = 0; i < count; i++) {
                        motorDirections[i] = -1;
                    }
                    break;
                case "rotateLeft":
                    for (int i = 0; i < count; i++) {
                        motorDirections[i] = (i % 2 == 0) ? -1 : 1;
                    }
                    break;
                case "rotateRight":
                    for (int i = 0; i < count; i++) {
                        motorDirections[i] = (i % 2 == 0) ? 1 : -1;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected direction: " + direction
                            + ", passed to Differential.move(). Valid directions are: forward, backward, rotateLeft, rotateRight");
            }

            return motorDirections;
        } else {
            double[] motorDirections = {0, 0, 0, 0};
            switch (direction) {
                case "forward":
                    motorDirections[0] = 1;
                    motorDirections[1] = 1;
                    motorDirections[2] = 1;
                    motorDirections[3] = 1;
                    break;
                case "backward":
                    motorDirections[0] = -1;
                    motorDirections[1] = -1;
                    motorDirections[2] = -1;
                    motorDirections[3] = -1;
                    break;
                case "left":
                    motorDirections[0] = -1;
                    motorDirections[1] = 1;
                    motorDirections[2] = 1;
                    motorDirections[3] = -1;
                    break;
                case "right":
                    motorDirections[0] = 1;
                    motorDirections[1] = -1;
                    motorDirections[2] = -1;
                    motorDirections[3] = 1;
                    break;
                case "rotateLeft":
                    motorDirections[0] = -1;
                    motorDirections[1] = 1;
                    motorDirections[2] = -1;
                    motorDirections[3] = 1;
                    break;
                case "rotateRight":
                    motorDirections[0] = 1;
                    motorDirections[1] = -1;
                    motorDirections[2] = 1;
                    motorDirections[3] = -1;
                    break;
                case "forwardLeft":
                    motorDirections[0] = 0;
                    motorDirections[1] = 1;
                    motorDirections[2] = 1;
                    motorDirections[3] = 0;
                    break;
                case "forwardRight":
                    motorDirections[0] = 1;
                    motorDirections[1] = 0;
                    motorDirections[2] = 0;
                    motorDirections[3] = 1;
                    break;
                case "backwardLeft":
                    motorDirections[0] = -1;
                    motorDirections[1] = 0;
                    motorDirections[2] = 0;
                    motorDirections[3] = -1;
                    break;
                case "backwardRight":
                    motorDirections[0] = 0;
                    motorDirections[1] = -1;
                    motorDirections[2] = -1;
                    motorDirections[3] = 0;
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected direction: " + direction
                            + ", passed to Mecanum.move(). Valid directions are: forward, backward, left, right, rotateLeft, rotateRight, forwaredLeft, forwardRight, backwardLeft, backwardRight");
            }

            return motorDirections;
        }
    }
}
