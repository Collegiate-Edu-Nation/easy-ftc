package org.edu_nation.easy_ftc.arm;

import org.edu_nation.easy_ftc.mechanism.Mechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract arm, providing basic functionalities, options, and objects common to all
 * arms. Cannot be instantiated, only extended by actual arm classes (see {@link SoloArm} and
 * {@link DualArm}).
 * <p>
 * 
 * @Methods {@link #wait(double time)} (inherited from {@link Mechanism})
 */
abstract class Arm extends Mechanism {
    protected DcMotor[] armMotors;
    protected DcMotorEx[] armMotorsEx;
    protected boolean useEncoder;
    protected double velocityMultiplier;
    protected double distanceMultiplier;
    protected double length;

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, false);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        this(opMode, hardwareMap, useEncoder, null);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, false, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double length) {
        this(opMode, hardwareMap, useEncoder, length, null);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad) {
        this(opMode, hardwareMap, useEncoder, 0.0, gamepad);
    }

    /**
     * Constructor
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double length,
            Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.useEncoder = useEncoder;
        this.length = length;
        this.gamepad = gamepad;
        hardwareInit();
    }

    public abstract void tele(double power);

    public abstract void move(double power, String direction, double measurement);

    /**
     * Sets the target position for each motor before setting the mode to RUN_TO_POSITION
     */
    protected void setPositions(int[] positions, int[] currentPositions) {
        // set target-position (relative + current = desired)
        for (int i = 0; i < armMotorsEx.length; i++) {
            armMotorsEx[i].setTargetPosition(positions[i] + currentPositions[i]);
        }

        // Set motors to run using the encoder (position, not velocity)
        setModesEx(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets all extended motors to the specified mode
     */
    protected void setModesEx(DcMotorEx.RunMode runMode) {
        for (DcMotorEx armMotorEx : armMotorsEx) {
            armMotorEx.setMode(runMode);
        }
    }

    /**
     * Sets all basic motors to the specified mode
     */
    protected void setModes(DcMotor.RunMode runMode) {
        for (DcMotor armMotor : armMotors) {
            armMotor.setMode(runMode);
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
        if (useEncoder && length != 0.0) {
            for (int i = 0; i < armMotorsEx.length; i++) {
                armMotorsEx[i].setPower(movements[i]);
            }
        } else if (useEncoder) {
            for (int i = 0; i < armMotorsEx.length; i++) {
                armMotorsEx[i].setVelocity(movements[i] * velocityMultiplier);
            }
        } else {
            for (int i = 0; i < armMotors.length; i++) {
                armMotors[i].setPower(movements[i]);
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
            zeros = new double[armMotorsEx.length];
        } else {
            zeros = new double[armMotors.length];
        }
        setAllPower(zeros);
    }
}
