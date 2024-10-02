package org.cen.easy_ftc.claw;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.cen.easy_ftc.Mechanism;

/**
 * Blueprints an abstract claw, providing basic functionalities, options, and objects common to all
 * claws. Cannot be instantiated, only extended by actual lift classes (see {@link SoloClaw} and
 * {@link DualClaw}).
 * 
 * @Methods {@link #wait(double time)} (inherited from {@link Mechanism})
 */
abstract class Claw extends Mechanism {
    protected double open, close;
    protected double increment;
    protected double delay;

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
        this.delay = 0.02;
        this.gamepad = gamepad;
        hardwareInit();
    }

    public abstract void move(String direction);

    protected abstract void setPositionByIncrement(double position, double movement);
}
