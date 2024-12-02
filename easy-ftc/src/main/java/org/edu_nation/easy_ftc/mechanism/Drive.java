// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;

/**
 * Implements a drivetrain by extending the functionality of {@link MotorMechanism}.
 * <ul>
 * <li>See {@link Builder} for Builder methods and defaults.
 * <li>See {@link Direction} for directions that can be passed to
 * {@link #command(direction, measurement, power)}.
 * <li>See {@link Type} for Types that can be passed to {@link Drive.Builder#type(type)}.
 * <li>See {@link Layout} for Layouts that can be passed to {@link Drive.Builder#layout(layout)}.
 * </ul>
 */
public class Drive extends MotorMechanism<Drive.Direction> {
    private Type type;

    /** Constructor */
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

    /**
     * Drive Builder
     * <p>
     * <b>Defaults:</b>
     * <ul>
     * <li>count = 2
     * <li>names = {"driveLeft", "driveRight"}
     * <li>behavior = FLOAT
     * <li>encoder = false
     * <li>reverse = false
     * <li>reverseDevices = {}
     * <li>diameter = 0.0
     * <li>gearing = 0.0
     * <li>deadzone = 0.0
     * <li>gamepad = null
     * <li>type = "" (interpreted as "differential")
     * <li>layout = "" (interpreted as "tank" for differential, "robot" for mecanum)
     * </ul>
     */
    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 2;
        private String[] names = {"driveLeft", "driveRight"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;
        private Type type = Type.DIFFERENTIAL;
        private Layout layout = Layout.TANK;
        private String mechanismName = "Drive";

        /**
         * Builder constructor
         * 
         * @param opMode instance of the calling opMode
         * @param hardwareMap instance of the calling opMode's hardwareMap
         * @throws NullPointerException if opMode or hardwareMap are null
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        // methods inherited from Mechanism.Builder
        @Override
        public Builder reverse() {
            return super.reverse();
        }

        @Override
        public Builder reverse(String deviceName) {
            return super.reverse(deviceName);
        }

        @Override
        public Builder reverse(String[] deviceNames) {
            return super.reverse(deviceNames);
        }

        @Override
        public Builder gamepad(Gamepad gamepad) {
            return super.gamepad(gamepad);
        }

        // methods inherited from MotorMechanism.Builder
        @Override
        public Builder encoder() {
            return super.encoder();
        }

        @Override
        public Builder diameter(double diameter) {
            return super.diameter(diameter);
        }

        @Override
        public Builder gearing(double gearing) {
            return super.gearing(gearing);
        }

        @Override
        public Builder deadzone(double deadzone) {
            return super.deadzone(deadzone);
        }

        @Override
        public Builder logo(LogoFacingDirection logo) {
            return super.logo(logo);
        }

        @Override
        public Builder usb(UsbFacingDirection usb) {
            return super.usb(usb);
        }

        // drive-specific methods
        /**
         * Specify the number of motors
         * 
         * @param count the number of motors in the drive mechanism
         * @return builder instance
         * @throws IllegalArgumentException if count isn't 2 or 4
         */
        public Builder count(int count) {
            if (count != 2 && count != 4) {
                throw new IllegalArgumentException("Unexpected count value: " + count
                        + ", passed to Drive.Builder().count(). Valid values are 2 or 4");
            }
            this.count = count;

            // correct the default names based on count
            if (count == 4 && names.length == 2) {
                String[] newNames = {"frontLeft", "frontRight", "backLeft", "backRight"};
                this.names = newNames;
            } else if (count == 2 && names.length == 4) {
                String[] newNames = {"driveLeft", "driveRight"};
                this.names = newNames;
            }
            return this;
        }

        /**
         * Change the names of the hardware devices
         * 
         * @param names an array of the names for the hardware devices
         * @return builder instance
         * @throws NullPointerException if names is null
         */
        public Builder names(String[] names) {
            if (names == null) {
                throw new NullPointerException("Null names passed to Drive.Builder().names()");
            }
            this.names = names;
            return this;
        }

        /**
         * Specify the zero-power behavior of the motors
         * 
         * @param behavior the zero-power behavior, one of ZeroPowerBehavior.BRAKE or FLOAT
         * @return builder instance
         * @throws NullPointerException if behavior is null
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
         * Specify the drivetrain type
         * 
         * @param type drivetrain type, one of Type.DIFFERENTIAL or MECANUM
         * @return builder instance
         * @throws NullPointerException if type is null
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
                    String[] newNames = {"frontLeft", "frontRight", "backLeft", "backRight"};
                    this.names = newNames;
                }
                if (this.layout == Layout.TANK) {
                    this.layout = Layout.ROBOT;
                }
            }

            return this;
        }

        /**
         * Specify the drivetrain layout
         * 
         * @param layout drivetrain layout
         *        <ul>
         *        <li>For DIFFERENTIAL, one of Layout.TANK or ARCADE
         *        <li>For MECANUM, one of Layout.ROBOT or FIELD
         *        </ul>
         * @return builder instance
         * @throws NullPointerException if layout is null
         */
        public Builder layout(Layout layout) {
            if (layout == null) {
                throw new NullPointerException("Null layout passed to Drive.Builder().layout()");
            }
            this.layout = layout;
            return this;
        }

        /**
         * Build the drivetrain
         * 
         * @return drive instance
         * @throws IllegalStateException if count != names.length
         * @throws IllegalStateException if type = DIFFERENTIAL and layout is one of: ROBOT, FIELD
         * @throws IllegalStateException if type = MECANUM and count != 4
         * @throws IllegalStateException if type = MECANUM and layout is one of: ARCADE, TANK
         */
        @Override
        public Drive build() {
            if (this.count != this.names.length) {
                throw new IllegalStateException(
                        "Unexpected array length for array passed to Drive.Builder().names(). The length of this array must be equal to count");
            }
            if (this.type == Type.DIFFERENTIAL) {
                if (this.layout == Layout.ROBOT || this.layout == Layout.FIELD) {
                    throw new IllegalStateException(
                            "Illegal layout passed to Drive.Builder().layout() for the specified type in Drive.Builder().type(). Permitted layouts are: Drive.Layout.ARCADE, Drive.Layout.TANK");
                }
            } else if (this.type == Type.MECANUM) {
                if (this.count != 4) {
                    throw new IllegalStateException(
                            "Illegal count passed to Drive.Builder().count() for the specified type in Drive.Builder().type(). Permitted count value is 4");
                }
                if (this.layout == Layout.ARCADE || this.layout == Layout.TANK) {
                    throw new IllegalStateException(
                            "Illegal layout passed to Drive.Builder().layout() for the specified type in Drive.Builder().type(). Permitted layouts are: Drive.Layout.ROBOT, Drive.Layout.FIELD");
                }
            }
            return new Drive(this);
        }

        /**
         * Return builder instance
         * 
         * @return builder instance
         */
        @Override
        protected Builder self() {
            return this;
        }
    }

    /** Directions that can be passed to command */
    public enum Direction {
        FORWARD, BACKWARD, LEFT, RIGHT, ROTATE_LEFT, ROTATE_RIGHT, FORWARD_LEFT, FORWARD_RIGHT, BACKWARD_LEFT, BACKWARD_RIGHT
    }

    /** Drivetrain types that can be passed to .type() */
    public enum Type {
        DIFFERENTIAL, MECANUM
    }

    /** Drivetrain layouts that can be passed to .layout() */
    public enum Layout {
        ARCADE, TANK, FIELD, ROBOT
    }

    /** Enables teleoperated mecanum movement with gamepad, scaling by multiplier &lt; 1 */
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

    /** Enables teleoperated mecanum movement with gamepad with multiplier = 1.0 */
    @Override
    public void control() {
        control(1.0);
    }

    /**
     * Intermediate function that assigns individual motor powers based on direction specified in
     * runOpMode() calls.
     * <p>
     * Valid directions are: forward, backward, left, right, rotateLeft, rotateRight, forwaredLeft,
     * forwardRight, backwardLeft, backwardRight
     */
    @Override
    public void command(Direction direction, double measurement, double power) {
        validate(measurement, power);
        double heading = 0;

        // set heading if applicable
        if (layout == Layout.FIELD) {
            heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        }

        double[] unscaledMovements = languageToDirection(count, type, layout, direction, heading);
        moveForMeasurement(unscaledMovements, measurement, power, false);
    }

    /** Set drivetrain motor movements based on type: DIFFERENTIAL or MECANUM */
    protected static double[] controlToDirection(int count, Type type, Layout layout,
            double deadzone, double heading, float leftY, float leftX, float rightY, float rightX) {
        switch (type) {
            case DIFFERENTIAL:
                return controlToDirectionDifferential(count, layout, deadzone, leftY, rightY,
                        rightX);
            case MECANUM:
                return controlToDirectionMecanum(layout, deadzone, heading, leftY, leftX, rightX);
            default:
                throw new IllegalArgumentException(
                        "Unexpected type passed to Drive.Builder().type(). Valid types are: Drive.Type.DIFFERENTIAL, Drive.Type.MECANUM");
        }
    }

    /** Set differential drivetrain movements based on layout: TANK or ARCADE */
    private static double[] controlToDirectionDifferential(int count, Layout layout,
            double deadzone, double leftY, double rightY, double rightX) {
        double left;
        double right;

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

    /** Set mecanum drivetrain motor movements based on layout: ROBOT or FIELD */
    private static double[] controlToDirectionMecanum(Layout layout, double deadzone,
            double heading, double leftY, double leftX, double rightX) {
        double[] axes = {map(-leftY, deadzone), map(leftX, deadzone), map(rightX, deadzone)};
        return axesToDirection(layout, axes, heading);
    }

    /**
     * Converts axial, lateral, yaw, and heading to motor directions using the formulas here:
     * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
     */
    private static double[] axesToDirection(Layout layout, double[] axes, double heading) {
        double axial;
        double lateral;
        double yaw = axes[2];

        // Assign axial, lateral based on layout
        switch (layout) {
            case ROBOT:
                axial = axes[0];
                lateral = axes[1];
                break;
            case FIELD:
                // Heading is the current orientation of the robot, which is used to calculate
                // relative axes
                axial = axes[1] * Math.sin(-heading) + axes[0] * Math.cos(-heading);
                lateral = axes[1] * Math.cos(-heading) - axes[0] * Math.sin(-heading);
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected layout passed to Drive.Builder().layout(). Valid layouts are: Drive.Layout.ROBOT, Drive.Layout.FIELD");
        }

        // Calculate individual motor values. Scaling is needed to ensure intended
        // ratios of motor
        // powers (otherwise, for example, 1.1 would become 1, while 0.9 would be
        // unaffected)
        double max = Math.max(Math.abs(axial) + Math.abs(lateral) + Math.abs(yaw), 1);
        double frontLeft = ((axial + lateral + yaw) / max);
        double frontRight = ((axial - lateral - yaw) / max);
        double backLeft = ((axial - lateral + yaw) / max);
        double backRight = ((axial + lateral - yaw) / max);

        return new double[] {frontLeft, frontRight, backLeft, backRight};
    }

    /** Translate natural-language direction to numeric values */
    protected static double[] languageToDirection(int count, Type type, Layout layout,
            Direction direction, double heading) {
        if (direction == null) {
            throw new NullPointerException("Null direction passed to Drive.command()");
        }

        switch (type) {
            case DIFFERENTIAL:
                return languageToDirectionDifferential(count, direction);
            case MECANUM:
                return axesToDirection(layout, languageToDirectionMecanum(direction), heading);
            default:
                throw new IllegalArgumentException(
                        "Unexpected type passed to Drive.Builder().type(). Valid types are: Drive.Type.DIFFERENTIAL, Drive.Type.MECANUM");
        }
    }

    /** Translate natural-language direction for Differential to numeric values */
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

    /** Translate natural-language direction for Mecanum to axial, lateral, yaw */
    private static double[] languageToDirectionMecanum(Direction direction) {
        switch (direction) {
            case FORWARD:
                return new double[] {1, 0, 0};
            case BACKWARD:
                return new double[] {-1, 0, 0};
            case LEFT:
                return new double[] {0, -1, 0};
            case RIGHT:
                return new double[] {0, 1, 0};
            case ROTATE_LEFT:
                return new double[] {0, 0, -1};
            case ROTATE_RIGHT:
                return new double[] {0, 0, 1};
            case FORWARD_LEFT:
                return new double[] {Math.sqrt(2) / 2, -Math.sqrt(2) / 2, 0};
            case FORWARD_RIGHT:
                return new double[] {Math.sqrt(2) / 2, Math.sqrt(2) / 2, 0};
            case BACKWARD_LEFT:
                return new double[] {-Math.sqrt(2) / 2, -Math.sqrt(2) / 2, 0};
            case BACKWARD_RIGHT:
                return new double[] {-Math.sqrt(2) / 2, Math.sqrt(2) / 2, 0};
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction passed to Drive.command(). Valid directions are: Drive.Direction.FORWARD, Drive.Direction.BACKWARD, Drive.Direction.LEFT, Drive.Direction.RIGHT, Drive.Direction.ROTATE_LEFT, Drive.Direction.ROTATE_RIGHT, Drive.Direction.FORWARD_LEFT, Drive.Direction.FORWARD_RIGHT, Drive.Direction.BACKWARD_LEFT, Drive.Direction.BACKWARD_RIGHT");
        }
    }
}
