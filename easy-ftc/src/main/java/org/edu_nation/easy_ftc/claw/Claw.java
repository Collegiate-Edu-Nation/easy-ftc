package org.edu_nation.easy_ftc.claw;

import org.edu_nation.easy_ftc.mechanism.Mechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract claw, providing basic functionalities, options, and objects common to all
 * claws. Cannot be instantiated, only extended by actual lift classes (see {@link SoloClaw} and
 * {@link DualClaw}).
 * 
 * @Methods {@link #wait(double time)} (inherited from {@link Mechanism})
 */
abstract class Claw extends Mechanism {
    protected Servo[] servos;
    protected boolean smoothServo;
    protected double open, close;
    protected double increment;
    protected double incrementDelay;
    protected double delay;

    /**
     * Constructor
     * 
     * @Defaults smoothServo = false
     *           <li>gamepad = null
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, false);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo) {
        this(opMode, hardwareMap, smoothServo, null);
    }

    /**
     * Constructor
     * 
     * @Defaults smoothServo = false
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, false, gamepad);
    }

    /**
     * Constructor
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo,
            Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.smoothServo = smoothServo;
        this.open = 1.0;
        this.close = 0.0;
        this.increment = 0.02;
        this.incrementDelay = 0.02;
        this.delay = 2;
        this.gamepad = gamepad;
        hardwareInit();
    }

    public abstract void move(String direction);

    /**
     * Wrapper around setPosition that enables smooth, synchronized servo control
     */
    protected void setPositionByIncrement(double position, double movement) {
        while (opMode.opModeIsActive() && position != movement) {
            position += (movement - position > 0) ? increment : -increment;
            position = Math.min(Math.max(position, 0), 1);
            for (Servo claw : servos) {
                claw.setPosition(position);
            }
            wait(incrementDelay);
        }
    }
}
