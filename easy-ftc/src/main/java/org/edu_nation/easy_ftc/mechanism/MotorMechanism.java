package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract Motor Mechanism, providing basic functionalities, options, and objects
 * common to all Motor Mechanisms. Cannot be instantiated, only extended by other classes.
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
 */
abstract class MotorMechanism extends Mechanism {
    protected DcMotor[] motors;
    protected DcMotorEx[] motorsEx;
    protected boolean encoder;
    protected double velocityMultiplier;
    protected double distanceMultiplier;
    protected double diameter;
    protected double length;
    protected double gearing;
    protected double deadzone;

    /**
     * Constructor
     */
    protected MotorMechanism(Builder<?> builder) {
        this.opMode = builder.opMode;
        this.hardwareMap = builder.hardwareMap;
        this.encoder = builder.encoder;
        this.reverse = builder.reverse;
        this.reverseDevices = builder.reverseDevices;
        this.diameter = builder.diameter;
        this.length = builder.length;
        this.gearing = builder.gearing;
        this.deadzone = builder.deadzone;
        this.gamepad = builder.gamepad;
    }

    public abstract static class Builder<T extends Builder<T>>  {
        protected LinearOpMode opMode;
        protected HardwareMap hardwareMap;
        protected boolean encoder = false;
        protected boolean reverse = false;
        protected String[] reverseDevices = {};
        private double diameter = 0.0;
        private double length = 0.0;
        protected double gearing = 0.0;
        private double deadzone = 0.0;
        protected Gamepad gamepad = null;

        /**
         * MotorMechanism Builder
         * 
         * @Defaults encoder = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>gearing = 0.0
         *           <li>gamepad = null
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            this.opMode = opMode;
            this.hardwareMap = hardwareMap;
        }

        /**
         * Whether to enable encoders (time-based)
         */
        public T encoder() {
            this.encoder = true;
            return (T) this;
        }

        /**
         * Whether to reverse motors
         */
        public T reverse() {
            this.reverse = true;
            return (T) this;
        }

        /**
         * Reverse the specified motor
         */
        public T reverse(String deviceName) {
            int arrLength = reverseDevices.length;
            String[] reverseDevices = new String[arrLength + 1];
            for (int i = 0; i < arrLength; i++) {
                reverseDevices[i] = this.reverseDevices[i];
            }
            reverseDevices[arrLength] = deviceName;

            this.reverseDevices = reverseDevices;
            return (T) this;
        }

        /**
         * Specify the diameter of the wheels/axel for encoder control (distance-based)
         */
        public T diameter(double diameter) {
            this.diameter = diameter;
            return (T) this;
        }

        /**
         * Specify the length of the arm for encoder control (distance-based)
         */
        public T length(double length) {
            this.length = length;
            return (T) this;
        }

        /**
         * Specify the gearing of the motors (increases accuracy of distance-based movement)
         */
        public T gearing(double gearing) {
            if (gearing <= 0) {
                throw new IllegalArgumentException(
                        "Unexpected gearing value: " + gearing + ", passed to "
                                + ".gearing(). Valid values are numbers > 0");
            }
            this.gearing = gearing;
            return (T) this;
        }

        /**
         * Specify the joystick deadzone (minimum value registered as input)
         */
        public T deadzone(double deadzone) {
            if (deadzone < 0) {
                throw new IllegalArgumentException(
                        "Unexpected deadzone value: " + deadzone + ", passed to " 
                                + ".deadzone(). Valid values are numbers >= 0");
            }
            this.deadzone = deadzone;
            return (T) this;
        }

        /**
         * Pass the gamepad instance for teleop control
         */
        public T gamepad(Gamepad gamepad) {
            this.gamepad = gamepad;
            return (T) this;
        }
    }

    public abstract void move(double power, String direction, double measurement);

    /**
     * Sets the target position for each motor before setting the mode to RUN_TO_POSITION
     */
    protected void setPositions(int[] positions, int[] currentPositions) {
        // set target-position (relative + current = desired)
        for (int i = 0; i < motorsEx.length; i++) {
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
        if (encoder && (diameter != 0.0 || length != 0.0)) {
            for (int i = 0; i < motorsEx.length; i++) {
                motorsEx[i].setPower(movements[i]);
            }
        } else if (encoder) {
            for (int i = 0; i < motorsEx.length; i++) {
                motorsEx[i].setVelocity(movements[i] * velocityMultiplier);
            }
        } else {
            for (int i = 0; i < motors.length; i++) {
                motors[i].setPower(movements[i]);
            }
        }
    }

    /**
     * Helper function to set all motor powers to zero (this is the default case).
     */
    protected void setPowers() {
        double[] zeros;
        if (encoder) {
            zeros = new double[motorsEx.length];
        } else {
            zeros = new double[motors.length];
        }
        setPowers(zeros);
    }

    /**
     * Wrapper around setDirection for all motors
     */
    protected void setDirections(boolean reverse) {
        if (!reverse) {
            if (encoder) {
                for (int i = 0; i < motorsEx.length; i++) {
                    DcMotorEx.Direction direction = (i % 2 == 0) ? DcMotorEx.Direction.REVERSE
                            : DcMotorEx.Direction.FORWARD;
                    motorsEx[i].setDirection(direction);
                }
            } else {
                for (int i = 0; i < motors.length; i++) {
                    DcMotor.Direction direction =
                            (i % 2 == 0) ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD;
                    motors[i].setDirection(direction);
                }
            }
        } else {
            if (encoder) {
                for (int i = 0; i < motorsEx.length; i++) {
                    DcMotorEx.Direction direction = (i % 2 == 0) ? DcMotorEx.Direction.FORWARD
                            : DcMotorEx.Direction.REVERSE;
                    motorsEx[i].setDirection(direction);
                }
            } else {
                for (int i = 0; i < motors.length; i++) {
                    DcMotor.Direction direction =
                            (i % 2 == 0) ? DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE;
                    motors[i].setDirection(direction);
                }
            }
        }
    }

    /**
     * Wrapper around getMotorType for all motors
     */
    protected MotorConfigurationType[] getMotorTypes() {
        MotorConfigurationType[] motorTypes = new MotorConfigurationType[motorsEx.length];
        for (int i = 0; i < motorsEx.length; i++) {
            motorTypes[i] = motorsEx[i].getMotorType();
        }
        return motorTypes;
    }

    /**
     * Wrapper around getGearing to get the minimum of all motors
     */
    protected double getGearing(MotorConfigurationType[] motorTypes) {
        double[] gearings = new double[motorTypes.length];
        for (int i = 0; i < motorTypes.length; i++) {
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
     * Wrapper around isBusy to see if any motors are busy
     */
    protected boolean motorsAreBusy() {
        boolean isBusy = false;
        for (DcMotorEx motorEx : motorsEx) {
            if (motorEx.isBusy()) {
                isBusy = true;
            }
        }
        return isBusy;
    }

    /**
     * Wrapper around getAchieveableMaxTicksPerSecond to return minimum of all motors
     */
    protected double getAchieveableMaxTicksPerSecond(MotorConfigurationType[] motorTypes) {
        double[] achieveableMaxTicksPerSecondArr = new double[motorTypes.length];
        for (int i = 0; i < motorTypes.length; i++) {
            achieveableMaxTicksPerSecondArr[i] = motorTypes[i].getAchieveableMaxTicksPerSecond();
        }
        double achieveableMaxTicksPerSecond = min(achieveableMaxTicksPerSecondArr);
        return achieveableMaxTicksPerSecond;
    }

    /**
     * Wrapper around getTicksPerRev to return minimum of all motors
     */
    protected double getTicksPerRev(MotorConfigurationType[] motorTypes) {
        double[] ticksPerRevArr = new double[motorTypes.length];
        for (int i = 0; i < motorTypes.length; i++) {
            ticksPerRevArr[i] = motorTypes[i].getTicksPerRev();
        }
        double ticksPerRev = min(ticksPerRevArr);
        return ticksPerRev;
    }

    /**
     * Wrapper around getCurrentPosition to return it for all motors
     */
    protected int[] getCurrentPositions() {
        int[] currentPositions = new int[motorsEx.length];
        for (int i = 0; i < motorsEx.length; i++) {
            currentPositions[i] = motorsEx[i].getCurrentPosition();
        }
        return currentPositions;
    }

    /**
     * Helper for calculating minimum value in array
     */
    private double min(double[] arr) {
        if (arr.length == 1) {
            return arr[0];
        } else if (arr.length == 2) {
            return Math.min(arr[0], arr[1]);
        } else {
            return min(arr[0], arr[1], arr[2], arr[3]);
        }
    }

    /**
     * Helper for calculating minimum of 4 values
     */
    private double min(double a, double b, double c, double d) {
        return Math.min(Math.min(a, b), Math.min(c, d));
    }
}
