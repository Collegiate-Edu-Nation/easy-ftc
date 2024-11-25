// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Blueprints an abstract Motor Mechanism, providing basic functionalities, options, and objects
 * common to all Motor Mechanisms. Cannot be instantiated, only extended by other classes.
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
 */
abstract class MotorMechanism<E> extends Mechanism {
    protected DcMotor[] motors;
    protected DcMotorEx[] motorsEx;
    protected IMU imu;
    protected boolean encoder;
    protected DcMotor.ZeroPowerBehavior behavior;
    protected double up;
    protected double down;
    protected double velocityMultiplier;
    protected double distanceMultiplier;
    protected double diameter;
    protected double length;
    protected double gearing;
    protected double deadzone;
    protected Drive.Layout layout;

    /**
     * Constructor
     */
    protected MotorMechanism(Builder<?> builder) {
        super(builder);
        this.encoder = builder.encoder;
        if (!this.encoder
                && (builder.diameter != 0 || builder.length != 0 || builder.diameter != 0)) {
            throw new IllegalStateException(
                    "One of: MotorMechanism.Builder().diameter(), MotorMechanism.Builder().length(), or MotorMechanism.Builder().gearing() has been set without enabling MotorMechanism.Builder().encoder(). Enable MotorMechanism.Builder().encoder()");
        }
        this.diameter = builder.diameter;
        this.length = builder.length;
        this.gearing = builder.gearing;
        this.deadzone = builder.deadzone;
    }

    public abstract static class Builder<T extends Builder<T>> extends Mechanism.Builder<T> {
        private boolean encoder = false;
        private double diameter = 0.0;
        private double length = 0.0;
        private double gearing = 0.0;
        private double deadzone = 0.0;

        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Whether to enable encoders (time-based)
         */
        public T encoder() {
            this.encoder = true;
            return self();
        }

        /**
         * Specify the diameter of the wheels/axel for encoder control (distance-based)
         */
        public T diameter(double diameter) {
            if (diameter <= 0) {
                throw new IllegalArgumentException("Unexpected diameter value: " + diameter
                        + ", passed to MotorMechanism.Builder().diameter(). Valid values are numbers > 0");
            }
            this.diameter = diameter;
            return self();
        }

        /**
         * Specify the length of the arm for encoder control (distance-based)
         */
        public T length(double length) {
            if (length <= 0) {
                throw new IllegalArgumentException("Unexpected length value: " + length
                        + ", passed to MotorMechanism.Builder().length(). Valid values are numbers > 0");
            }
            this.length = length;
            // length is the radius of arm's ROM, so double it for arc length = distance
            this.diameter = 2.0 * length;
            return self();
        }

        /**
         * Specify the gearing of the motors (increases accuracy of distance-based movement)
         */
        public T gearing(double gearing) {
            if (gearing <= 0) {
                throw new IllegalArgumentException("Unexpected gearing value: " + gearing
                        + ", passed to MotorMechanism.Builder().gearing(). Valid values are numbers > 0");
            }
            this.gearing = gearing;
            return self();
        }

        /**
         * Specify the joystick deadzone (minimum value registered as input)
         */
        public T deadzone(double deadzone) {
            if (deadzone < 0) {
                throw new IllegalArgumentException("Unexpected deadzone value: " + deadzone
                        + ", passed to MotorMechanism.Builder().deadzone(). Valid values are numbers >= 0");
            }
            this.deadzone = deadzone;
            return self();
        }

        public abstract T names(String[] names);

        public abstract MotorMechanism<?> build();

        protected abstract T self();
    }

    public abstract void control(double multiplier);

    public abstract void command(E direction, double measurement, double power);

    /**
     * Ensure multiplier is in (0, 1]
     */
    protected void validate(double multiplier) {
        if (multiplier <= 0 || multiplier > 1) {
            throw new IllegalArgumentException(
                    "Unexpected multiplier value: " + multiplier + ", passed to " + mechanismName
                            + ".control(). Valid values are numbers in the interval (0, 1]");
        }
    }

    /**
     * Ensure power is in (0, 1], measurement is positive
     */
    protected void validate(double measurement, double power) {
        if (measurement < 0) {
            throw new IllegalArgumentException("Unexpected measurement value: " + measurement
                    + ", passed to " + mechanismName + ".command(). Valid values are numbers >= 0");
        }
        if (power <= 0 || power > 1) {
            throw new IllegalArgumentException(
                    "Unexpected power value: " + power + ", passed to " + mechanismName
                            + ".command(). Valid values are numbers in the interval (0, 1]");
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    @Override
    protected void reverse(String deviceName) {
        boolean found = false;

        // reverse the device
        for (int i = 0; i < count; i++) {
            if (deviceName == names[i]) {
                found = true;
                if (encoder) {
                    DcMotorEx.Direction direction = (i % 2 == 0) ? DcMotorEx.Direction.FORWARD
                            : DcMotorEx.Direction.REVERSE;
                    motorsEx[i].setDirection(direction);
                } else {
                    DcMotor.Direction direction =
                            (i % 2 == 0) ? DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE;
                    motors[i].setDirection(direction);
                }
            }
        }

        // throw exception if device not found
        if (!found) {
            String validNames = "";
            for (String name : names) {
                validNames += name + ", ";
            }
            validNames = validNames.substring(0, validNames.length() - 2);
            throw new IllegalArgumentException(
                    "Unexpected deviceName: " + deviceName + ", passed to " + mechanismName
                            + ".Builder().reverse(). Valid names are: " + validNames);
        }
    }

    /**
     * Initializes motors based on constructor args (e.g. using encoders or not)
     */
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
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (diameter == 0.0) {
                velocityMultiplier = getAchieveableMaxTicksPerSecond(motorTypes);
            } else {
                distanceMultiplier = getTicksPerRev(motorTypes);
                if (gearing != 0.0) {
                    setGearing(gearing);
                }
            }
        } else {
            // Instantiate motors
            motors = new DcMotor[count];
            for (int i = 0; i < count; i++) {
                motors[i] = hardwareMap.get(DcMotor.class, names[i]);
            }

            // Set motors to run without the encoders (power, not velocity or position)
            setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // Initializes imu for field-centric layout. Adjust "UP" and "FORWARD" if orientation is
        // reversed
        if (mechanismName == "Drive" && layout == Drive.Layout.FIELD) {
            imu = hardwareMap.get(IMU.class, "imu");
            IMU.Parameters parameters = new IMU.Parameters(
                    new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
                            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
            imu.initialize(parameters);
            imu.resetYaw();
        }

        // specify zeroPowerBehavior of motors
        setBehaviors(behavior);

        // Reverse direction of left motors for convenience (switch if robot drives backwards)
        setDirections(reverse);

        // reverse direction of specified motors
        for (String device : reverseDevices) {
            reverse(device);
        }
    }

    /**
     * Moves the mechanism for the given measurement at power
     */
    protected void moveForMeasurement(double[] unscaledMovements, double measurement, double power,
            boolean limit) {
        double[] movements = scaleDirections(unscaledMovements, power);

        if (diameter == 0.0) {
            // move the motors at power for the specified time (or until limits are reached)
            setPowers(movements);
            if (!limit) {
                wait(measurement);
            } else {
                this.timer.reset();
                while (opMode.opModeIsActive() && (this.timer.time() < measurement)
                        && limitsNotReached(unscaledMovements[0], unscaledMovements)) {
                }
            }
            setPowers();
        } else {
            int[] positions = calculatePositions(measurement, diameter, distanceMultiplier,
                    unscaledMovements);
            int[] currentPositions = getCurrentPositions();

            // move the motors at power until they've reached the position (or the limit)
            setPositions(positions, currentPositions);
            setPowers(movements);
            if (!limit) {
                while (motorsAreBusy(movements)) {
                }
            } else {
                while (motorsAreBusy(movements)
                        && limitsNotReached(unscaledMovements[0], unscaledMovements)) {
                }
            }
            setPowers();

            // Reset motors to run using velocity (allows for using command() w/ length along w/
            // control())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Determines whether positional limits have not yet been reached
     */
    protected boolean limitsNotReached(double direction, double[] movements) {
        int[] currentPositions = getCurrentPositions();
        boolean move = true;

        if (diameter == 0.0) {
            if (direction > 0) {
                for (int position : currentPositions) {
                    move = (position < up) ? true : false;
                    if (!move) {
                        break;
                    }
                }
            } else {
                for (int position : currentPositions) {
                    move = (position > down) ? true : false;
                    if (!move) {
                        break;
                    }
                }
            }
        } else {
            if (direction > 0) {
                int[] positions = calculatePositions(up, diameter, distanceMultiplier, movements);
                for (int i = 0; i < count; i++) {
                    move = (currentPositions[i] < positions[i]) ? true : false;
                    if (!move) {
                        break;
                    }
                }
            } else {
                int[] positions = calculatePositions(down, diameter, distanceMultiplier, movements);
                if (down < 0) {
                    // calculatePositions is absolute, so reverse values if negative value for down
                    // is used
                    for (int i = 0; i < count; i++) {
                        positions[i] *= -1;
                    }
                }
                for (int i = 0; i < count; i++) {
                    move = (currentPositions[i] > positions[i]) ? true : false;
                    if (!move) {
                        break;
                    }
                }
            }
        }

        return move;
    }

    /**
     * Sets the target position for each motor before setting the mode to RUN_TO_POSITION
     */
    protected void setPositions(int[] positions, int[] currentPositions) {
        // set target-position (relative + current = desired)
        for (int i = 0; i < count; i++) {
            motorsEx[i].setTargetPosition(positions[i] + currentPositions[i]);
        }

        // Set motors to run using the encoder (position, not velocity)
        setModesEx(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets all extended motors to the specified mode
     */
    protected void setModesEx(DcMotorEx.RunMode runMode) {
        for (DcMotorEx motorEx : motorsEx) {
            motorEx.setMode(runMode);
        }
    }

    /**
     * Sets all basic motors to the specified mode
     */
    protected void setModes(DcMotor.RunMode runMode) {
        for (DcMotor motor : motors) {
            motor.setMode(runMode);
        }
    }

    /**
     * Helper function to set all motor powers to received values (defaults to 0 if no args
     * provided).
     */
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

    /**
     * Helper function to set all motor powers to zero (this is the default case).
     */
    protected void setPowers() {
        double[] zeros = new double[count];
        setPowers(zeros);
    }

    /**
     * Scales movements by multiplier if applicable
     */
    protected void setPowers(double[] movements, double multiplier) {
        if (multiplier == 1.0) {
            setPowers(movements);
        } else {
            double[] scaledMovements =
                    scaleDirections(movements, Math.min(Math.abs(multiplier), 1));
            setPowers(scaledMovements);
        }
    }

    /**
     * Wrapper around setDirection for all motors
     */
    protected void setDirections(boolean reverse) {
        if (!reverse) {
            if (encoder) {
                for (int i = 0; i < count; i++) {
                    DcMotorEx.Direction direction = (i % 2 == 0) ? DcMotorEx.Direction.REVERSE
                            : DcMotorEx.Direction.FORWARD;
                    motorsEx[i].setDirection(direction);
                }
            } else {
                for (int i = 0; i < count; i++) {
                    DcMotor.Direction direction =
                            (i % 2 == 0) ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD;
                    motors[i].setDirection(direction);
                }
            }
        } else {
            if (encoder) {
                for (int i = 0; i < count; i++) {
                    DcMotorEx.Direction direction = (i % 2 == 0) ? DcMotorEx.Direction.FORWARD
                            : DcMotorEx.Direction.REVERSE;
                    motorsEx[i].setDirection(direction);
                }
            } else {
                for (int i = 0; i < count; i++) {
                    DcMotor.Direction direction =
                            (i % 2 == 0) ? DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE;
                    motors[i].setDirection(direction);
                }
            }
        }
    }

    /**
     * Wrapper around setZeroPowerBehavior for all motors
     */
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

    /**
     * Wrapper around getMotorType for all motors
     */
    protected MotorConfigurationType[] getMotorTypes() {
        MotorConfigurationType[] motorTypes = new MotorConfigurationType[count];
        for (int i = 0; i < count; i++) {
            motorTypes[i] = motorsEx[i].getMotorType();
        }
        return motorTypes;
    }

    /**
     * Wrapper around getGearing to get the minimum of all motors
     */
    protected double getGearing(MotorConfigurationType[] motorTypes) {
        double[] gearings = new double[count];
        for (int i = 0; i < count; i++) {
            gearings[i] = motorTypes[i].getGearing();
        }
        double gearing = min(gearings);
        return gearing;
    }

    /**
     * Correct the gear-ratio of all motors using encoders. Automatically updates distanceMultiplier
     */
    protected void setGearing(double gearing) {
        MotorConfigurationType[] motorTypes = getMotorTypes();
        double currentGearing = getGearing(motorTypes);
        distanceMultiplier *= gearing / currentGearing;
    }

    /**
     * Wrapper around isBusy to see if any motors are busy (when they're supposed to be)
     */
    protected boolean motorsAreBusy(double[] movements) {
        boolean isBusy = false;
        for (int i = 0; i < count; i++) {
            if (motorsEx[i].isBusy() && Math.abs(movements[i]) > 0.01) {
                isBusy = true;
            }
        }
        return isBusy;
    }

    /**
     * Wrapper around getAchieveableMaxTicksPerSecond to return minimum of all motors
     */
    protected double getAchieveableMaxTicksPerSecond(MotorConfigurationType[] motorTypes) {
        double[] achieveableMaxTicksPerSecondArr = new double[count];
        for (int i = 0; i < count; i++) {
            achieveableMaxTicksPerSecondArr[i] = motorTypes[i].getAchieveableMaxTicksPerSecond();
        }
        double achieveableMaxTicksPerSecond = min(achieveableMaxTicksPerSecondArr);
        return achieveableMaxTicksPerSecond;
    }

    /**
     * Wrapper around getTicksPerRev to return minimum of all motors
     */
    protected double getTicksPerRev(MotorConfigurationType[] motorTypes) {
        double[] ticksPerRevArr = new double[count];
        for (int i = 0; i < count; i++) {
            ticksPerRevArr[i] = motorTypes[i].getTicksPerRev();
        }
        double ticksPerRev = min(ticksPerRevArr);
        return ticksPerRev;
    }

    /**
     * Wrapper around getCurrentPosition to return it for all motors
     */
    protected int[] getCurrentPositions() {
        int[] currentPositions = new int[count];
        for (int i = 0; i < count; i++) {
            currentPositions[i] = motorsEx[i].getCurrentPosition();
        }
        return currentPositions;
    }

    /**
     * Maps controller value from [-1,-deadzone] U [deadzone,1] -> [-1,1], enabling controller
     * deadzone
     * 
     * @Defaults deadzone = 0.0
     */
    protected static double map(double controllerValue, double deadzone) {
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

    /**
     * Scale directions by a factor of power to derive actual, intended motor movements
     */
    protected static double[] scaleDirections(double[] motorDirections, double power) {
        int arrLength = motorDirections.length;
        double[] movements = new double[arrLength];
        for (int i = 0; i < arrLength; i++) {
            movements[i] = power * motorDirections[i];
        }
        return movements;
    }

    /**
     * Calculate posiitons based on distance, diameter, distanceMultiplier, movements
     */
    protected static int[] calculatePositions(double distance, double diameter,
            double distanceMultiplier, double[] movements) {
        double circumference = Math.PI * diameter;
        double revolutions = distance / circumference;
        double positionRaw = revolutions * distanceMultiplier;
        int position = (int) Math.round(positionRaw);

        int arrLength = movements.length;
        int[] positions = new int[arrLength];
        for (int i = 0; i < arrLength; i++) {
            positions[i] = (int) (movements[i] * position);
        }
        return positions;
    }

    /**
     * Helper for calculating minimum value in array
     */
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

    /**
     * Helper for calculating minimum of 4 values
     */
    private double min(double a, double b, double c, double d) {
        return Math.min(Math.min(a, b), Math.min(c, d));
    }
}
