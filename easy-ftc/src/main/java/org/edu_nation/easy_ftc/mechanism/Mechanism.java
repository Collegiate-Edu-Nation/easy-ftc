package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract Mechanism, providing basic functionalities, options, and objects common to
 * all claws. Cannot be instantiated, only extended by other abstract classes.
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
 */
abstract class Mechanism {
    protected LinearOpMode opMode;
    protected HardwareMap hardwareMap;
    protected Gamepad gamepad;
    protected ElapsedTime timer = new ElapsedTime();
    protected boolean reverse;
    protected String[] reverseDevices;
    protected String mechanismName;

    protected abstract void init();

    public abstract void tele();

    protected abstract void reverse(String deviceName);

    /**
     * Helper function to wait (but not suspend) for specified time in s.
     */
    protected void wait(double time) {
        this.timer.reset();
        while (opMode.opModeIsActive() && (this.timer.time() < time)) {
        }
    }
}
