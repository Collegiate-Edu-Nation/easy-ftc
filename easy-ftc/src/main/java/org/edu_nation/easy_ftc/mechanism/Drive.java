// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
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
 * @Methods {@link #control()}
 *          <li>{@link #command(double power, String direction, double measurement)}
 */
public class Drive extends MotorMechanism<Drive.Direction> {
    private Type type;

    /**
     * Constructor
     */
    private Drive(Builder builder) {
        super(builder);
        this.count = builder.count;
        if (builder.count != builder.names.length) {
            throw new IllegalStateException(
                    "Unexpected array length for array passed to Drive.Builder().names(). The length of this array must be equal to count");
        }
        this.names = builder.names;
        this.behavior = builder.behavior;
        if (builder.type == Type.DIFFERENTIAL) {
            if (builder.layout == Layout.ROBOT || builder.layout == Layout.FIELD) {
                throw new IllegalStateException(
                        "Illegal layout passed to Drive.Builder().layout() for the specified type in Drive.Builder().type(). Permitted layouts are: Drive.Layout.ARCADE, Drive.Layout.TANK");
            }
        } else if (builder.type == Type.MECANUM) {
            if (count != 4) {
                throw new IllegalStateException(
                        "Illegal count passed to Drive.Builder().count() for the specified type in Drive.Builder().type(). Permitted count value is 4");
            }
            if (builder.layout == Layout.ARCADE || builder.layout == Layout.TANK) {
                throw new IllegalStateException(
                        "Illegal layout passed to Drive.Builder().layout() for the specified type in Drive.Builder().type(). Permitted layouts are: Drive.Layout.ROBOT, Drive.Layout.FIELD");
            }
        }
        this.type = builder.type;
        this.layout = builder.layout;
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 2;
        private String[] names = {"driveLeft", "driveRight"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;
        private Type type = Type.DIFFERENTIAL;
        private Layout layout = Layout.TANK;
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
            if (count != 2 && count != 4) {
                throw new IllegalArgumentException("Unexpected count value: " + count
                        + ", passed to Drive.Builder().count(). Valid values are 2 or 4");
            }
            this.count = count;

            // correct the default names based on count
            if (count == 4 && names.length == 2) {
                String[] names = {"frontLeft", "frontRight", "backLeft", "backRight"};
                this.names = names;
            } else if (count == 2 && names.length == 4) {
                String[] names = {"driveLeft", "driveRight"};
                this.names = names;
            }
            return this;
        }

        /**
         * Change the names of the hardware devices
         */
        public Builder names(String[] names) {
            if (names == null) {
                throw new NullPointerException("Null names passed to Drive.Builder().names()");
            }
            this.names = names;
            return this;
        }

        /**
         * Specify the zero-power behavior of the motors (DcMotor.ZeroPowerBehavior.BRAKE or FLOAT)
         */
        public Builder behavior(DcMotor.ZeroPowerBehavior behavior) {
            if (behavior == null) {
                throw new NullPointerException(
                        "Null behavior passed to Drive.Builder().behavior()");
            }
            this.behavior = behavior;
            return this;
        }

        /**
         * Specify the drivetrain type: "differential" (default) or "mecanum"
         */
        public Builder type(Type type) {
            if (type == null) {
                throw new NullPointerException("Null type passed to Drive.Builder().type()");
            }
            this.type = type;

            // correct deviceNames, count, Layout if Type is set to MECANUM
            if (type == Type.MECANUM) {
                if (this.count == 2) {
                    this.count = 4;
                }
                if (this.names.length == 2) {
                    String[] names = {"frontLeft", "frontRight", "backLeft", "backRight"};
                    this.names = names;
                }
                if (this.layout == Layout.TANK) {
                    this.layout = Layout.ROBOT;
                }
            }

            return this;
        }

        /**
         * Specify the control layout. For Differential, Drive.Layout.TANK (default) or
         * Drive.Layout.ARCADE. For Mecanum, Drive.Layout.ROBOT (default) or Drive.Layout.FIELD
         */
        public Builder layout(Layout layout) {
            if (layout == null) {
                throw new NullPointerException("Null layout passed to Drive.Builder().layout()");
            }
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
        protected Builder self() {
            return this;
        }
    }

    /**
     * Directions that can be passed to command
     */
    public enum Direction {
        FORWARD, BACKWARD, LEFT, RIGHT, ROTATE_LEFT, ROTATE_RIGHT, FORWARD_LEFT, FORWARD_RIGHT, BACKWARD_LEFT, BACKWARD_RIGHT
    }

    /**
     * Drivetrain types that can be passed to .type()
     */
    public enum Type {
        DIFFERENTIAL, MECANUM
    }

    /**
     * Drivetrain layouts that can be passed to .layout()
     */
    public enum Layout {
        ARCADE, TANK, FIELD, ROBOT
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
    public void control(double multiplier) {
        validate(multiplier);
        double heading = 0;
        // Press option to reset imu to combat drift, set heading if applicable
        if (layout == Layout.FIELD) {
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
    public void control() {
        control(1.0);
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
    public void command(Direction direction, double measurement, double power) {
        validate(measurement, power);
        double[] unscaledMovements = languageToDirection(count, type, direction);
        moveForMeasurement(unscaledMovements, measurement, power);
    }

    /**
     * Set drivetrain motor movements based on type: DIFFERENTIAL (default) or MECANUM
     */
    protected static double[] controlToDirection(int count, Type type, Layout layout,
            double deadzone, double heading, float leftY, float leftX, float rightY, float rightX) {
        switch (type) {
            case DIFFERENTIAL:
                return controlToDirectionDifferential(count, layout, deadzone, leftY, rightY,
                        rightX);
            case MECANUM:
                return controlToDirectionMecanum(count, layout, deadzone, heading, leftY, leftX,
                        rightX);
            default:
                throw new IllegalArgumentException(
                        "Unexpected type passed to Drive.Builder().type(). Valid types are: Drive.Type.DIFFERENTIAL, Drive.Type.MECANUM");
        }
    }

    /**
     * Set differential drivetrain movements based on layout: TANK (default) or ARCADE
     */
    private static double[] controlToDirectionDifferential(int count, Layout layout,
            double deadzone, double leftY, double rightY, double rightX) {
        double left, right;

        switch (layout) {
            case TANK:
                left = map(-leftY, deadzone);
                right = map(-rightY, deadzone);
                break;
            case ARCADE:
                left = map(-leftY, deadzone) + map(rightX, deadzone);
                right = map(-leftY, deadzone) - map(rightX, deadzone);
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected layout passed to Drive.Builder().layout(). Valid layouts are: Drive.Layout.TANK, Drive.Layout.ARCADE");
        }

        double[] movements = new double[count];
        for (int i = 0; i < count; i++) {
            movements[i] = (i % 2 == 0) ? left : right;
        }
        return movements;
    }

    /**
     * Set mecanum drivetrain motor movements based on layout: ROBOT (default) or FIELD
     */
    private static double[] controlToDirectionMecanum(int count, Layout layout, double deadzone,
            double heading, double leftY, double leftX, double rightX) {
        double frontLeft, frontRight, backLeft, backRight;

        // Axes (used for both robot-centric and field-centric)
        double axial = map(-leftY, deadzone);
        double lateral = map(leftX, deadzone);
        double yaw = map(rightX, deadzone);

        // Calculate desired individual motor values (orientation factored in for else-if
        // statement, i.e. field-centric driving)
        switch (layout) {
            case ROBOT:
                // Scaled individual motor movements derived from axes (left to right, front to
                // back). Scaling is needed to ensure intended ratios of motor powers
                // (otherwise, for example, 1.1 would become 1, while 0.9 would be unaffected)
                double max = Math.max(Math.abs(axial) + Math.abs(lateral) + Math.abs(yaw), 1);
                frontLeft = ((axial + lateral + yaw) / max);
                frontRight = ((axial - lateral - yaw) / max);
                backLeft = ((axial - lateral + yaw) / max);
                backRight = ((axial + lateral - yaw) / max);
                break;
            case FIELD:
                // Scaled individual motor movements derived from axes and orientation (left to
                // right, front to back)
                // Heading is the current yaw of the robot, which is used to calculate relative axes
                double axial_relative = lateral * Math.sin(-heading) + axial * Math.cos(-heading);
                double lateral_relative = lateral * Math.cos(-heading) - axial * Math.sin(-heading);
                max = Math.max(
                        Math.abs(axial_relative) + Math.abs(lateral_relative) + Math.abs(yaw), 1);
                frontLeft = ((axial_relative + lateral_relative + yaw) / max);
                frontRight = ((axial_relative - lateral_relative - yaw) / max);
                backLeft = ((axial_relative - lateral_relative + yaw) / max);
                backRight = ((axial_relative + lateral_relative - yaw) / max);
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected layout passed to Drive.Builder().layout(). Valid layouts are: Drive.Layout.ROBOT, Drive.Layout.FIELD");
        }

        double[] movements = {frontLeft, frontRight, backLeft, backRight};
        return movements;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double[] languageToDirection(int count, Type type, Direction direction) {
        if (direction == null) {
            throw new NullPointerException("Null direction passed to Drive.command()");
        }

        switch (type) {
            case DIFFERENTIAL:
                return languageToDirectionDifferential(count, direction);
            case MECANUM:
                return languageToDirectionMecanum(count, direction);
            default:
                throw new IllegalArgumentException(
                        "Unexpected type passed to Drive.Builder().type(). Valid types are: Drive.Type.DIFFERENTIAL, Drive.Type.MECANUM");
        }
    }

    /**
     * Translate natural-language direction for Differential to numeric values
     */
    private static double[] languageToDirectionDifferential(int count, Direction direction) {
        double[] motorDirections = new double[count];

        switch (direction) {
            case FORWARD:
                for (int i = 0; i < count; i++) {
                    motorDirections[i] = 1;
                }
                break;
            case BACKWARD:
                for (int i = 0; i < count; i++) {
                    motorDirections[i] = -1;
                }
                break;
            case ROTATE_LEFT:
                for (int i = 0; i < count; i++) {
                    motorDirections[i] = (i % 2 == 0) ? -1 : 1;
                }
                break;
            case ROTATE_RIGHT:
                for (int i = 0; i < count; i++) {
                    motorDirections[i] = (i % 2 == 0) ? 1 : -1;
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction passed to Drive.command(). Valid directions are: Drive.Direction.FORWARD, Drive.Direction.BACKWARD, Drive.Direction.ROTATE_LEFT, Drive.Direction.ROTATE_RIGHT");
        }

        return motorDirections;
    }

    /**
     * Translate natural-language direction for Mecanum to numeric values
     */
    private static double[] languageToDirectionMecanum(int count, Direction direction) {
        switch (direction) {
            case FORWARD:
                return new double[] {1, 1, 1, 1};
            case BACKWARD:
                return new double[] {-1, -1, -1, -1};
            case LEFT:
                return new double[] {-1, 1, 1, -1};
            case RIGHT:
                return new double[] {1, -1, -1, 1};
            case ROTATE_LEFT:
                return new double[] {-1, 1, -1, 1};
            case ROTATE_RIGHT:
                return new double[] {1, -1, 1, -1};
            case FORWARD_LEFT:
                return new double[] {0, 1, 1, 0};
            case FORWARD_RIGHT:
                return new double[] {1, 0, 0, 1};
            case BACKWARD_LEFT:
                return new double[] {-1, 0, 0, -1};
            case BACKWARD_RIGHT:
                return new double[] {0, -1, -1, 0};
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction passed to Drive.command(). Valid directions are: Drive.Direction.FORWARD, Drive.Direction.BACKWARD, Drive.Direction.LEFT, Drive.Direction.RIGHT, Drive.Direction.ROTATE_LEFT, Drive.Direction.ROTATE_RIGHT, Drive.Direction.FORWARD_LEFT, Drive.Direction.FORWARD_RIGHT, Drive.Direction.BACKWARD_LEFT, Drive.Direction.BACKWARD_RIGHT");
        }
    }
}
