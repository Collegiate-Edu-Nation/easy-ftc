// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import java.util.Objects;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Blueprints an abstract Motor Mechanism, providing basic functionalities, options, and objects
 * common to all Motor Mechanisms. Cannot be instantiated; only extended by other classes.
 */
abstract class MotorMechanism<E> extends Mechanism {
    protected DcMotor[] motors;
    protected DcMotorEx[] motorsEx;
    protected IMU imu;
    protected boolean encoder;
    protected DcMotor.ZeroPowerBehavior behavior;
    protected double dir1;
    protected double dir2;
    protected double velocityMultiplier;
    protected double distanceMultiplier;
    protected double diameter;
    protected double length;
    protected double gearing;
    protected double deadzone;
    protected LogoFacingDirection logo;
    protected UsbFacingDirection usb;
    protected Drive.Layout layout;
    private static final String CONNECT = ", passed to ";

    /** Constructor */
    protected MotorMechanism(Builder<?> builder) {
        super(builder);
        this.encoder = builder.encoder;
        this.diameter = builder.diameter;
        this.length = builder.length;
        this.gearing = builder.gearing;
        this.deadzone = builder.deadzone;
        this.logo = builder.logo;
        this.usb = builder.usb;
    }

    public abstract static class Builder<T extends Builder<T>> extends Mechanism.Builder<T> {
        protected boolean encoder = false;
        protected double diameter = 0.0;
        protected double length = 0.0;
        protected double gearing = 0.0;
        private double deadzone = 0.0;
        private LogoFacingDirection logo = LogoFacingDirection.UP;
        private UsbFacingDirection usb = UsbFacingDirection.FORWARD;

        protected Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Whether to enable encoders (time-based)
         *
         * @return builder instance
         */
        public T encoder() {
            this.encoder = true;
            return self();
        }

        /**
         * Specify the diameter for encoder control (distance-based)
         *
         * @param diameter measurement of the wheel or axel attached to the motor
         * @return builder instance
         * @throws IllegalArgumentException if diameter &lt;= 0
         */
        public T diameter(double diameter) {
            if (diameter <= 0) {
                throw new IllegalArgumentException(
                        "Unexpected diameter value: "
                                + diameter
                                + ", passed to MotorMechanism.Builder().diameter(). Valid values are numbers > 0");
            }
            this.diameter = diameter;
            return self();
        }

        /**
         * Specify the length for encoder control (distance-based)
         *
         * @param length measurement of the length of the arm attached to the motor
         * @return builder instance
         * @throws IllegalArgumentException if length &lt;= 0
         */
        public T length(double length) {
            if (length <= 0) {
                throw new IllegalArgumentException(
                        "Unexpected length value: "
                                + length
                                + ", passed to MotorMechanism.Builder().length(). Valid values are numbers > 0");
            }
            this.length = length;
            // length is the radius of arm's ROM, so double it for arc length = distance
            this.diameter = 2.0 * length;
            return self();
        }

        /**
         * Specify the gearing of the motors (increases accuracy of distance-based movement)
         *
         * @param gearing gearing of the motors in the mechanism
         * @return builder instance
         * @throws IllegalArgumentException if gearing &lt;= 0
         */
        public T gearing(double gearing) {
            if (gearing <= 0) {
                throw new IllegalArgumentException(
                        "Unexpected gearing value: "
                                + gearing
                                + ", passed to MotorMechanism.Builder().gearing(). Valid values are numbers > 0");
            }
            this.gearing = gearing;
            return self();
        }

        /**
         * Specify the joystick deadzone
         *
         * @param deadzone minimum joystick/trigger value registered as input
         * @return builder instance
         * @throws IllegalArgumentException if deadzone &lt; 0
         */
        public T deadzone(double deadzone) {
            if (deadzone < 0) {
                throw new IllegalArgumentException(
                        "Unexpected deadzone value: "
                                + deadzone
                                + ", passed to MotorMechanism.Builder().deadzone(). Valid values are numbers >= 0");
            }
            this.deadzone = deadzone;
            return self();
        }

        /**
         * Specify the logo direction of the IMU/gyro
         *
         * @param logo direction of the Hub's logo
         * @return builder instance
         * @throws NullPointerException if logo is null
         */
        public T logo(LogoFacingDirection logo) {
            if (logo == null) {
                throw new NullPointerException(
                        "Null IMU logo direction passed to MotorMechanism.Builder.logo()");
            }
            this.logo = logo;
            return self();
        }

        /**
         * Specify the logo direction of the IMU/gyro
         *
         * @param usb direction of the Hub's usb ports
         * @return builder instance
         * @throws NullPointerException if usb is null
         */
        public T usb(UsbFacingDirection usb) {
            if (usb == null) {
                throw new NullPointerException(
                        "Null IMU usb direction passed to MotorMechanism.Builder.usb()");
            }
            this.usb = usb;
            return self();
        }

        public abstract T names(String[] names);

        abstract MotorMechanism<?> build();

        protected abstract T self();
    }

    public abstract void control(double multiplier);

    public abstract void command(E direction, double measurement, double power);

    /** Ensure multiplier is in (0, 1] */
    protected void validate(double multiplier) {
        if (multiplier <= 0 || multiplier > 1) {
            throw new IllegalArgumentException(
                    "Unexpected multiplier value: "
                            + multiplier
                            + CONNECT
                            + MECHANISM_NAME
                            + ".control(). Valid values are numbers in the interval (0, 1]");
        }
    }

    /** Ensure power is in (0, 1], measurement is positive */
    protected void validate(double measurement, double power) {
        if (measurement < 0) {
            throw new IllegalArgumentException(
                    "Unexpected measurement value: "
                            + measurement
                            + CONNECT
                            + MECHANISM_NAME
                            + ".command(). Valid values are numbers >= 0");
        }
        if (power <= 0 || power > 1) {
            throw new IllegalArgumentException(
                    "Unexpected power value: "
                            + power
                            + CONNECT
                            + MECHANISM_NAME
                            + ".command(). Valid values are numbers in the interval (0, 1]");
        }
    }

    /** Ensure measurement is in (0, inf] */
    protected void validate_deg(double measurement) {
        if (measurement <= 0) {
            throw new IllegalArgumentException(
                    "Unexpected measurement value: "
                            + measurement
                            + " passed to Drive.command(). Valid values are numbers in the interval (0, inf]");
        }
    }

    /** Reverse the direction of the specified motor */
    @Override
    protected void reverse(String deviceName) {
        boolean found = false;

        // reverse the device
        for (int i = 0; i < count; i++) {
            if (Objects.equals(deviceName, names[i])) {
                found = true;
                DcMotorSimple.Direction direction =
                        (i % 2 == 0)
                                ? DcMotorSimple.Direction.FORWARD
                                : DcMotorSimple.Direction.REVERSE;
                if (encoder) {
                    motorsEx[i].setDirection(direction);
                } else {
                    motors[i].setDirection(direction);
                }
            }
        }

        // throw exception if device not found
        if (!found) {
            StringBuilder bld = new StringBuilder();
            for (String name : names) {
                bld.append(name).append(", ");
            }
            String validNames = bld.toString();
            validNames = validNames.substring(0, validNames.length() - 2);
            throw new IllegalArgumentException(
                    "Unexpected deviceName: "
                            + deviceName
                            + CONNECT
                            + MECHANISM_NAME
                            + ".Builder().reverse(). Valid names are: "
                            + validNames);
        }
    }

    /** Initializes motors based on builder args (e.g. using encoders or not) */
    @Override
    protected void init() {
        if (encoder) {
            // Instantiate motors
            motorsEx = new DcMotorEx[count];
            for (int i = 0; i < count; i++) {
                motorsEx[i] = hardwareMap.get(DcMotorEx.class, names[i]);
            }

            MotorConfigurationType[] motorTypes = getMotorTypes();

            // Reset encoders
            setModesEx(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotor.RunMode.RUN_USING_ENCODER);

            if (diameter == 0.0) {
                velocityMultiplier = getAchievableMaxTicksPerSecond(motorTypes);
            } else {
                distanceMultiplier = getTicksPerRev(motorTypes);
                if (gearing != 0.0) {
                    setGearing();
                }
            }
        } else {
            // Instantiate motors
            motors = new DcMotor[count];
            for (int i = 0; i < count; i++) {
                motors[i] = hardwareMap.get(DcMotor.class, names[i]);
            }

            // Set motors to run without the encoders (power, not velocity or position)
            setModes();
        }

        // Initializes imu for field-centric layout
        if ("Drive".equals(MECHANISM_NAME) && layout == Drive.Layout.FIELD) {
            imu = hardwareMap.get(IMU.class, "imu");
            IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(logo, usb));
            imu.initialize(parameters);
            imu.resetYaw();
        }

        // specify zeroPowerBehavior of motors
        setBehaviors(behavior);

        // Reverse direction of left motors for convenience
        setDirections(reverse);

        // reverse direction of specified motors
        for (String device : reverseDevices) {
            reverse(device);
        }
    }

    /** Moves the mechanism for the given measurement at power */
    @SuppressWarnings("java:S3776")
    protected void moveForMeasurement(
            double[] unscaledMovements, double measurement, double power, boolean limit) {
        double[] movements = scaleDirections(unscaledMovements, power);

        if (diameter == 0.0) {
            // move the motors at power for the specified time (or until limits are reached)
            setPowers(movements);
            if (!limit) {
                wait(measurement);
            } else {
                this.timer.reset();
                while (opMode.opModeIsActive()
                        && (this.timer.time() < measurement)
                        && limitsNotReached(unscaledMovements[0], unscaledMovements))
                    ;
            }
            setPowers();
        } else {
            int[] positions = calculatePositions(measurement, unscaledMovements);
            int[] currentPositions = getCurrentPositions();

            // move the motors at power until they've reached the position (or the limit)
            setPositions(positions, currentPositions);
            setPowers(movements);
            if (!limit) {
                while (motorsAreBusy(movements))
                    ;
            } else {
                while (motorsAreBusy(movements)
                        && limitsNotReached(unscaledMovements[0], unscaledMovements))
                    ;
            }
            setPowers();

            // Reset motors to run using velocity
            // Allows for using command() w/ length along w/ control()
            setModesEx(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /** Moves the mechanism for the given measurement angle at power */
    protected void moveForMeasurement(
            double[] unscaledMovements,
            double measurement,
            double power,
            AngleUnit unit,
            boolean limit) {
        double[] movements = scaleDirections(unscaledMovements, power);
        double measurementDeg = 0;
        if (unit == AngleUnit.RADIANS) {
            measurementDeg = unit.fromRadians(measurement);
        } else {
            measurementDeg = measurement;
        }
        validate_deg(measurementDeg);

        setPowers(movements);
        if (!limit) {
            while (gyroIsBusy(measurement))
                ;
        } else {
            while (gyroIsBusy(measurement)
                    && limitsNotReached(unscaledMovements[0], unscaledMovements))
                ;
        }
        setPowers();
    }

    /** Determines whether positional limits have not yet been reached */
    protected boolean limitsNotReached(double direction, double[] movements) {
        if (diameter == 0.0) {
            return limitsNotReachedTimeBased(direction);
        } else {
            return limitsNotReachedDistanceBased(direction, movements);
        }
    }

    /** Helper function to determine positional limits with time-based movement */
    private boolean limitsNotReachedTimeBased(double direction) {
        int[] currentPositions = getCurrentPositions();
        boolean move = true;

        if (direction > 0) {
            for (int position : currentPositions) {
                move = (position < dir1);
                if (!move) {
                    break;
                }
            }
        } else {
            for (int position : currentPositions) {
                move = (position > dir2);
                if (!move) {
                    break;
                }
            }
        }

        return move;
    }

    /** Helper function to determine positional limits with distance-based movement */
    @SuppressWarnings("java:S3776")
    private boolean limitsNotReachedDistanceBased(double direction, double[] movements) {
        int[] currentPositions = getCurrentPositions();
        boolean move = true;

        if (direction > 0) {
            int[] positions = calculatePositions(dir1, movements);
            for (int i = 0; i < count; i++) {
                move = (currentPositions[i] < positions[i]);
                if (!move) {
                    break;
                }
            }
        } else {
            int[] positions = calculatePositions(dir2, movements);
            if (dir2 < 0) {
                // calculatePositions is absolute, so reverse values if negative value for dir2
                // is used
                for (int i = 0; i < count; i++) {
                    positions[i] *= -1;
                }
            }
            for (int i = 0; i < count; i++) {
                move = (currentPositions[i] > positions[i]);
                if (!move) {
                    break;
                }
            }
        }

        return move;
    }

    /** Sets the target position for each motor before setting the mode to RUN_TO_POSITION */
    protected void setPositions(int[] positions, int[] currentPositions) {
        // set target-position (relative + current = desired)
        for (int i = 0; i < count; i++) {
            motorsEx[i].setTargetPosition(positions[i] + currentPositions[i]);
        }

        // Set motors to run using the encoder (position, not velocity)
        setModesEx(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /** Sets all extended motors to the specified mode */
    protected void setModesEx(DcMotor.RunMode runMode) {
        for (DcMotorEx motorEx : motorsEx) {
            motorEx.setMode(runMode);
        }
    }

    /** Sets all basic motors to the specified mode */
    protected void setModes() {
        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /** Helper function to set all motor powers to received values */
    protected void setPowers(double[] movements) {
        if (encoder && (diameter != 0.0)) {
            for (int i = 0; i < count; i++) {
                motorsEx[i].setPower(movements[i]);
            }
        } else if (encoder) {
            for (int i = 0; i < count; i++) {
                motorsEx[i].setVelocity(movements[i] * velocityMultiplier);
            }
        } else {
            for (int i = 0; i < count; i++) {
                motors[i].setPower(movements[i]);
            }
        }
    }

    /** Helper function to set all motor powers to zero */
    protected void setPowers() {
        double[] zeros = new double[count];
        setPowers(zeros);
    }

    /** Scales movements by multiplier if applicable */
    protected void setPowers(double[] movements, double multiplier) {
        if (multiplier == 1.0) {
            setPowers(movements);
        } else {
            double[] scaledMovements =
                    scaleDirections(movements, Math.min(Math.abs(multiplier), 1));
            setPowers(scaledMovements);
        }
    }

    /** Wrapper around setDirection for all motors */
    protected void setDirections(boolean reverse) {
        if (!reverse) {
            setDirectionsDefault();
        } else {
            setDirectionsReversed();
        }
    }

    /** Helper function to set directions normally */
    private void setDirectionsDefault() {
        if (encoder) {
            for (int i = 0; i < count; i++) {
                DcMotorSimple.Direction direction =
                        (i % 2 == 0)
                                ? DcMotorSimple.Direction.REVERSE
                                : DcMotorSimple.Direction.FORWARD;
                motorsEx[i].setDirection(direction);
            }
        } else {
            for (int i = 0; i < count; i++) {
                DcMotorSimple.Direction direction =
                        (i % 2 == 0)
                                ? DcMotorSimple.Direction.REVERSE
                                : DcMotorSimple.Direction.FORWARD;
                motors[i].setDirection(direction);
            }
        }
    }

    /** Helper function to reverse directions */
    private void setDirectionsReversed() {
        if (encoder) {
            for (int i = 0; i < count; i++) {
                DcMotorSimple.Direction direction =
                        (i % 2 == 0)
                                ? DcMotorSimple.Direction.FORWARD
                                : DcMotorSimple.Direction.REVERSE;
                motorsEx[i].setDirection(direction);
            }
        } else {
            for (int i = 0; i < count; i++) {
                DcMotorSimple.Direction direction =
                        (i % 2 == 0)
                                ? DcMotorSimple.Direction.FORWARD
                                : DcMotorSimple.Direction.REVERSE;
                motors[i].setDirection(direction);
            }
        }
    }

    /** Wrapper around setZeroPowerBehavior for all motors */
    protected void setBehaviors(DcMotor.ZeroPowerBehavior behavior) {
        if (encoder) {
            for (DcMotorEx motorEx : motorsEx) {
                motorEx.setZeroPowerBehavior(behavior);
            }
        } else {
            for (DcMotor motor : motors) {
                motor.setZeroPowerBehavior(behavior);
            }
        }
    }

    /** Wrapper around getMotorType for all motors */
    protected MotorConfigurationType[] getMotorTypes() {
        MotorConfigurationType[] motorTypes = new MotorConfigurationType[count];
        for (int i = 0; i < count; i++) {
            motorTypes[i] = motorsEx[i].getMotorType();
        }
        return motorTypes;
    }

    /** Wrapper around getGearing to get the minimum of all motors */
    protected double getGearing(MotorConfigurationType[] motorTypes) {
        double[] gearings = new double[count];
        for (int i = 0; i < count; i++) {
            gearings[i] = motorTypes[i].getGearing();
        }
        return min(gearings);
    }

    /** Correct the gear ratio of all motors using encoders. Updates distanceMultiplier */
    protected void setGearing() {
        MotorConfigurationType[] motorTypes = getMotorTypes();
        double currentGearing = getGearing(motorTypes);
        distanceMultiplier *= gearing / currentGearing;
    }

    /** Wrapper around isBusy to see if any motors are busy (when they're supposed to be) */
    protected boolean motorsAreBusy(double[] movements) {
        boolean isBusy = false;
        for (int i = 0; i < count; i++) {
            if (motorsEx[i].isBusy() && Math.abs(movements[i]) > 0.01) {
                isBusy = true;
            }
        }
        return isBusy;
    }

    /** Helper to identify whether the movement angle has been reached */
    private boolean gyroIsBusy(double measurement) {
        boolean isBusy = false;
        if (Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES))
                        - Math.abs(measurement)
                > 1) {
            isBusy = true;
        }
        return isBusy;
    }

    /** Wrapper around getAchievableMaxTicksPerSecond to return minimum of all motors */
    protected double getAchievableMaxTicksPerSecond(MotorConfigurationType[] motorTypes) {
        double[] achievableMaxTicksPerSecondArr = new double[count];
        for (int i = 0; i < count; i++) {
            achievableMaxTicksPerSecondArr[i] = motorTypes[i].getAchieveableMaxTicksPerSecond();
        }
        return min(achievableMaxTicksPerSecondArr);
    }

    /** Wrapper around getTicksPerRev to return minimum of all motors */
    protected double getTicksPerRev(MotorConfigurationType[] motorTypes) {
        double[] ticksPerRevArr = new double[count];
        for (int i = 0; i < count; i++) {
            ticksPerRevArr[i] = motorTypes[i].getTicksPerRev();
        }
        return min(ticksPerRevArr);
    }

    /** Wrapper around getCurrentPosition to return it for all motors */
    protected int[] getCurrentPositions() {
        int[] currentPositions = new int[count];
        for (int i = 0; i < count; i++) {
            currentPositions[i] = motorsEx[i].getCurrentPosition();
        }
        return currentPositions;
    }

    /** Maps controller value from [-1,-deadzone] U [deadzone,1] -> [-1,1] */
    protected double map(double controllerValue) {
        if (deadzone == 0.0) {
            return controllerValue;
        }

        double mappedValue;
        if (Math.abs(controllerValue) < Math.abs(deadzone)) {
            mappedValue = 0;
        } else {
            mappedValue = ((Math.abs(controllerValue) - deadzone) / (1.0 - deadzone));
            if (controllerValue < 0) {
                mappedValue *= -1;
            }
        }
        return mappedValue;
    }

    /** Scale directions by a factor of power to derive actual, intended motor movements */
    protected double[] scaleDirections(double[] motorDirections, double power) {
        double[] movements = new double[count];
        for (int i = 0; i < count; i++) {
            movements[i] = power * motorDirections[i];
        }
        return movements;
    }

    /** Calculate positions based on distance, diameter, distanceMultiplier, movements */
    protected int[] calculatePositions(double distance, double[] movements) {
        double circumference = Math.PI * diameter;
        double revolutions = distance / circumference;
        double positionRaw = revolutions * distanceMultiplier;
        int position = (int) Math.round(positionRaw);

        int[] positions = new int[count];
        for (int i = 0; i < count; i++) {
            positions[i] = (int) (movements[i] * position);
        }
        return positions;
    }

    /** Helper for calculating minimum value in array */
    private double min(double[] arr) {
        double min;
        switch (arr.length) {
            case 1:
                min = arr[0];
                break;
            case 2:
                min = Math.min(arr[0], arr[1]);
                break;
            default:
                min = min(arr[0], arr[1], arr[2], arr[3]);
        }
        return min;
    }

    /** Helper for calculating minimum of 4 values */
    private double min(double a, double b, double c, double d) {
        return Math.min(Math.min(a, b), Math.min(c, d));
    }
}
