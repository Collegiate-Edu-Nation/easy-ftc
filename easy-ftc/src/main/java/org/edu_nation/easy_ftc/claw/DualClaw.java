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
    /**
     * Constructor
     * 
     * @Defaults smoothServo = true
     *           <li>gamepad = null
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo) {
        super(opMode, hardwareMap, smoothServo);
    }

    /**
     * Constructor
     * 
     * @Defaults smoothServo = true
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
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
        servos = new Servo[2];
        servos[0] = hardwareMap.get(Servo.class, "clawLeft");
        servos[1] = hardwareMap.get(Servo.class, "clawRight");

        // Reverse direction of right servo for convenience (switch if claw is backwards)
        servos[0].setDirection(Servo.Direction.FORWARD);
        servos[1].setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Reverse the direction of the claw servos
     */
    @Override
    public void reverse() {
        servos[0].setDirection(Servo.Direction.REVERSE);
        servos[1].setDirection(Servo.Direction.FORWARD);
    }

    /**
     * Reverse the direction of the specified servo
     */
    public void reverse(String servoName) {
        switch (servoName) {
            case "clawLeft":
                servos[0].setDirection(Servo.Direction.REVERSE);
                break;
            case "clawRight":
                servos[1].setDirection(Servo.Direction.FORWARD);
                break;
            default:
                throw new IllegalArgumentException("Unexpected servoName: " + servoName
                        + ", passed to DualClaw.reverse(). Valid names are: clawLeft, clawRight");
        }
    }
}
