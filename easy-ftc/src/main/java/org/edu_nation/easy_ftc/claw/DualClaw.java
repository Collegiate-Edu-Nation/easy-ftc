package org.edu_nation.easy_ftc.claw;

import java.lang.Math;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a dual-servo claw by extending the functionality of {@link Claw}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Boolean smoothServo (true or false)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(String direction)}
 *          <li>{@link #reverse()}
 *          <li>{@link #reverse(String servoName)}
 *          <li>{@link #wait(double time)} (inherited from {@link Claw})
 */
public class DualClaw extends Claw {
    private Servo clawLeft, clawRight;

    /**
     * Constructor
     * 
     * @Defaults smoothServo = true
     *           <li>gamepad = null
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap, true);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo) {
        super(opMode, hardwareMap, smoothServo, null);
    }

    /**
     * Constructor
     * 
     * @Defaults smoothServo = true
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, true, gamepad);
    }

    /**
     * Constructor
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo,
            Gamepad gamepad) {
        super(opMode, hardwareMap, smoothServo, gamepad);
    }

    /**
     * Initializes claw servos
     */
    @Override
    protected void hardwareInit() {
        // Instantiate servos
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");

        // Reverse direction of right servo for convenience (switch if claw is backwards)
        clawLeft.setDirection(Servo.Direction.FORWARD);
        clawRight.setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double current = clawLeft.getPosition();
        double movement =
                DualClawUtil.controlToDirection(open, close, current, gamepad.b, gamepad.a);
        if (smoothServo) {
            double position = current;
            setPositionByIncrement(position, movement);
        } else {
            clawLeft.setPosition(movement);
            clawRight.setPosition(movement);
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
    @Override
    public void move(String direction) {
        double servoDirection = DualClawUtil.languageToDirection(direction, open, close);
        if (smoothServo) {
            double position = clawLeft.getPosition();
            setPositionByIncrement(position, servoDirection);
        } else {
            clawLeft.setPosition(servoDirection);
            clawRight.setPosition(servoDirection);
            wait(delay);
        }
    }

    /**
     * Reverse the direction of the claw servos
     */
    @Override
    public void reverse() {
        clawLeft.setDirection(Servo.Direction.REVERSE);
        clawRight.setDirection(Servo.Direction.FORWARD);
    }

    /**
     * Reverse the direction of the specified servo
     */
    public void reverse(String servoName) {
        switch (servoName) {
            case "clawLeft":
                clawLeft.setDirection(Servo.Direction.REVERSE);
                break;
            case "clawRight":
                clawRight.setDirection(Servo.Direction.FORWARD);
                break;
            default:
                throw new IllegalArgumentException("Unexpected servoName: " + servoName
                        + ", passed to DualClaw.reverse(). Valid names are: clawLeft, clawRight");
        }
    }

    /**
     * Wrapper around setPosition that enables smooth, synchronized servo control
     */
    @Override
    protected void setPositionByIncrement(double position, double movement) {
        while (opMode.opModeIsActive() && position != movement) {
            position += (movement - position > 0) ? increment : -increment;
            position = Math.min(Math.max(position, 0), 1);
            clawLeft.setPosition(position);
            clawRight.setPosition(position);
            wait(incrementDelay);
        }
    }
}
