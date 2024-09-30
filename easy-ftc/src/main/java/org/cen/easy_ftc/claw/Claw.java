package org.cen.easy_ftc.claw;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract claw, providing basic functionalities, options, and objects common to all
 * claws. Cannot be instantiated, only extended by actual lift classes (see {@link SoloClaw} and
 * {@link DualClaw}).
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
 */
abstract class Claw {
    protected LinearOpMode opMode;
    protected HardwareMap hardwareMap;
    protected double open, close;
    protected double increment;
    protected Gamepad gamepad;
    protected double delay = 0.02;
    protected ElapsedTime timer = new ElapsedTime();

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, null);
    }

    /**
     * Constructor
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.open = 1.0;
        this.close = 0.0;
        this.increment = 0.02;
        this.gamepad = gamepad;
        hardwareInit();
    }

    protected abstract void hardwareInit();

    public abstract void tele();

    public abstract void move(String direction);

    protected abstract void setPositionByIncrement(double position, double movement);

    /**
     * Helper function to wait (but not suspend) for specified time in s.
     * <p>
     * Public, so custom movements [] use-case can also be timed.
     */
    public void wait(double time) {
        this.timer.reset();
        while (opMode.opModeIsActive() && (this.timer.time() < time)) {
        }
    }
}
