package org.cen.easy_ftc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract mecanism, providing basic functionalities, options, and objects common to
 * all claws. Cannot be instantiated, only extended by other abstract classes.
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
 */
public abstract class Mecanism {
    protected LinearOpMode opMode;
    protected HardwareMap hardwareMap;
    protected Gamepad gamepad;
    protected ElapsedTime timer = new ElapsedTime();

    protected abstract void hardwareInit();

    public abstract void tele();

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
