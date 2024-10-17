package org.edu_nation.easy_ftc.claw;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a solo-servo claw by extending the functionality of {@link Claw}.
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
 *          <li>{@link #wait(double time)} (inherited from {@link Claw})
 */
public class SoloClaw extends Claw {
    /**
     * Constructor
     * 
     * @Defaults smoothServo = true
     *           <li>gamepad = null
     */
    public SoloClaw(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public SoloClaw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo) {
        super(opMode, hardwareMap, smoothServo);
    }

    /**
     * Constructor
     * 
     * @Defaults smoothServo = true
     */
    public SoloClaw(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     */
    public SoloClaw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo,
            Gamepad gamepad) {
        super(opMode, hardwareMap, smoothServo, gamepad);
    }

    /**
     * Initializes claw servo
     */
    @Override
    protected void hardwareInit() {
        // Instantiate servo
        servos = new Servo[1];
        servos[0] = hardwareMap.get(Servo.class, "claw");

        // Set direction of servo (switch to FORWARD if claw is backwards)
        servos[0].setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double current = servos[0].getPosition();
        double movement =
                SoloClawUtil.controlToDirection(open, close, current, gamepad.b, gamepad.a);
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
     * Intermediate function that assigns servo position based on direction specified in runOpMode()
     * calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: open, close
     */
    @Override
    public void move(String direction) {
        double servoDirection = SoloClawUtil.languageToDirection(direction, open, close);
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

    /**
     * Reverse the direction of the claw servo
     */
    @Override
    public void reverse() {
        servos[0].setDirection(Servo.Direction.FORWARD);
    }
}
