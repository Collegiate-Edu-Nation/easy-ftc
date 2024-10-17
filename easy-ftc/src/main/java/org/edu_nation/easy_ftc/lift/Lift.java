package org.edu_nation.easy_ftc.lift;

import org.edu_nation.easy_ftc.mechanism.Mechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract lift, providing basic functionalities, options, and objects common to all
 * lifts. Cannot be instantiated, only extended by actual lift classes (see {@link SoloLift} and
 * {@link DualLift}).
 * <p>
 * 
 * @Methods {@link #wait(double time)} (inherited from {@link Mechanism})
 */
abstract class Lift extends Mechanism {
    protected DcMotor[] motors;
    protected DcMotorEx[] motorsEx;
    protected boolean useEncoder;
    protected double velocityMultiplier;
    protected double distanceMultiplier;
    protected double diameter;
    protected double deadZone = 0.1;

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, false);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        this(opMode, hardwareMap, useEncoder, null);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, false, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double diameter) {
        this(opMode, hardwareMap, useEncoder, diameter, null);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad) {
        this(opMode, hardwareMap, useEncoder, 0.0, gamepad);
    }

    /**
     * Constructor
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double diameter,
            Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.useEncoder = useEncoder;
        this.diameter = diameter;
        this.gamepad = gamepad;
        hardwareInit();
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
        for (DcMotorEx liftMotorEx : motorsEx) {
            liftMotorEx.setMode(runMode);
        }
    }

    /**
     * Sets all basic motors to the specified mode
     */
    protected void setModes(DcMotor.RunMode runMode) {
        for (DcMotor liftMotor : motors) {
            liftMotor.setMode(runMode);
        }
    }

    /**
     * Helper function to set all motor powers to received values (defaults to 0 if no args
     * provided).
     * <p>
     * Public, so custom movements [] can be passed directly if needed (tele() is an example of
     * this).
     */
    public void setAllPower(double[] movements) {
        if (useEncoder && diameter != 0.0) {
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
     * <p>
     * Public, so motors can be stopped if needed (tele() is an example of this).
     */
    public void setAllPower() {
        double[] zeros;
        if (useEncoder) {
            zeros = new double[motorsEx.length];
        } else {
            zeros = new double[motors.length];
        }
        setAllPower(zeros);
    }

    /**
     * Set the deadZone from 0-1. Default is 0.1
     */
    public void setDeadZone(double deadZone) {
        this.deadZone = deadZone;
    }
}
