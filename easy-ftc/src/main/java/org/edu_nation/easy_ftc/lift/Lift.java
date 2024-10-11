package org.edu_nation.easy_ftc.lift;

import org.edu_nation.easy_ftc.mechanism.Mechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
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

    public abstract void setAllPower(double[] movements);

    public abstract void setAllPower();

    /**
     * Set the deadZone from 0-1. Default is 0.1
     */
    public void setDeadZone(double deadZone) {
        this.deadZone = deadZone;
    }
}
