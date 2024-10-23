package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Blueprints an abstract Motor Mechanism, providing basic functionalities, options, and objects
 * common to all Motor Mechanisms. Cannot be instantiated, only extended by other classes.
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
 */
abstract class MotorMechanism extends Mechanism {
    protected DcMotor[] motors;
    protected DcMotorEx[] motorsEx;
    protected int numMotors;
    protected boolean useEncoder;
    protected double velocityMultiplier;
    protected double distanceMultiplier;
    protected double diameter;
    protected double length;
    protected double gearing;
    protected double deadzone;

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
        if (useEncoder && (diameter != 0.0 || length != 0.0)) {
            for (int i = 0; i < motorsEx.length; i++) {
                motorsEx[i].setPower(movements[i]);
            }
        } else if (useEncoder) {
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
        if (useEncoder) {
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
            if (useEncoder) {
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
            if (useEncoder) {
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
