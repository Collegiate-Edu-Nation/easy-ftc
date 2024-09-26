package org.cen.easy_ftc.lift;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Blueprints an abstract lift, providing basic functionalities, options, and objects common to all lifts.
 * Cannot be instantiated, only extended by actual lift classes (see {@link SoloLift} and {@link DualLift}).
 * <p>
 * @Methods
 * {@link #wait(double time)} (used by subclasses)
 */
abstract class Lift {
    protected LinearOpMode opMode;
    protected HardwareMap hardwareMap;
    protected boolean useEncoder;
    protected Gamepad gamepad;
    protected double velocityMultiplier; // scales user-provided power (-1 to 1) to useable unit for setVelocity()
    protected double deadZone = 0.1;
    protected ElapsedTime timer = new ElapsedTime();

    /**
     * Constructor
     * @Defaults
     * useEncoder = false
     * <li>gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap) {this(opMode, hardwareMap, false);}
    /**
     * Constructor
     * @Defaults 
     * gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {this(opMode, hardwareMap, useEncoder, null);}
    /**
     * Constructor
     * @Defaults
     * useEncoder = false
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {this(opMode, hardwareMap, false, gamepad);}
    /**
     * Constructor
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.useEncoder = useEncoder;
        this.gamepad = gamepad;
        hardwareInit();
    }

    protected abstract void hardwareInit();
    public abstract void tele();
    public abstract void move(double power, String direction, double time);
    public abstract void setAllPower(double [] movements);
    public abstract void setAllPower();

    /**
     * Helper function to wait (but not suspend) for specified time in s.
     * <p>
     * Public, so custom movements [] use-case can also be timed.
     */
    public void wait(double time) {
        this.timer.reset();
        while(opMode.opModeIsActive() && (this.timer.time() < time)) {}
    }

    /**
     * Set the deadZone from 0-1. Default is 0.1
     */
    public void setDeadZone(double deadZone) {
        this.deadZone = deadZone;
    }
}
