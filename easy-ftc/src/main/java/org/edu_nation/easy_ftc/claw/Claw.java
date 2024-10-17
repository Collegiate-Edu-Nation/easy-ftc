package org.edu_nation.easy_ftc.claw;

import org.edu_nation.easy_ftc.mechanism.ServoMechanism;
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
abstract class Claw extends ServoMechanism {
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

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    public void tele() {
        double current = servos[0].getPosition();
        double movement =
                ClawUtil.controlToDirection(open, close, current, gamepad.b, gamepad.a);
        if (smoothServo) {
            double position = current;
            setPositionByIncrement(position, movement);
        } else {
            for (Servo claw : servos) {
                claw.setPosition(movement);
            }
        }
    }

    /**
     * Intermediate function that assigns individual servo positions based on direction specified in
     * runOpMode() calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: open, close
     */
    public void move(String direction) {
        double servoDirection = ClawUtil.languageToDirection(direction, open, close);
        if (smoothServo) {
            double position = servos[0].getPosition();
            setPositionByIncrement(position, servoDirection);
        } else {
            for (Servo claw : servos) {
                claw.setPosition(servoDirection);
            }
            wait(delay);
        }
    }
}
