package org.edu_nation.easy_ftc.claw;

import org.edu_nation.easy_ftc.mechanism.ServoMechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a claw by extending the functionality of {@link ServoMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer numServos (1 or 2)
 * @param Boolean smoothServo (true or false)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(String direction)}
 *          <li>{@link #reverse()}
 *          <li>{@link #reverse(String servoName)}
 *          <li>{@link #wait(double time)} (inherited from {@link Claw})
 */
public class Claw extends ServoMechanism {
    /**
     * Constructor
     * 
     * @Defaults numServos = 1
     *           <li>smoothServo = false
     *           <li>gamepad = null
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, 1);
    }

    /**
     * Constructor
     * 
     * @Defaults smoothServo = false
     *           <li>gamepad = null
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, int numServos) {
        this(opMode, hardwareMap, numServos, false);
    }

    /**
     * Constructor
     * 
     * @Defaults numServos = 1
     *           <li>gamepad = null
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo) {
        this(opMode, hardwareMap, 1, smoothServo);
    }

    /**
     * Constructor
     * 
     * @Defaults numServos = 1
     *           <li>smoothServo = false
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, 1, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, int numServos,
            boolean smoothServo) {
        this(opMode, hardwareMap, numServos, smoothServo, null);
    }

    /**
     * Constructor
     * 
     * @Defaults smoothServo = false
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, int numServos,
            Gamepad gamepad) {
        this(opMode, hardwareMap, numServos, false, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults numServos = 1
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, boolean smoothServo,
            Gamepad gamepad) {
        this(opMode, hardwareMap, 1, smoothServo, gamepad);
    }

    /**
     * Constructor
     */
    public Claw(LinearOpMode opMode, HardwareMap hardwareMap, int numServos, boolean smoothServo, Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.numServos = numServos;
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
     * Initializes claw servos
     */
    @Override
    protected void hardwareInit() {
        // Instantiate servos
        servos = new Servo[numServos];

        if (numServos == 2) {
            servos[0] = hardwareMap.get(Servo.class, "clawLeft");
            servos[1] = hardwareMap.get(Servo.class, "clawRight");

            // Reverse direction of right servo for convenience (switch if claw is backwards)
            servos[0].setDirection(Servo.Direction.FORWARD);
            servos[1].setDirection(Servo.Direction.REVERSE);
        } else {
            servos[0] = hardwareMap.get(Servo.class, "claw");

            // Set direction of servo (switch to FORWARD if claw is backwards)
            servos[0].setDirection(Servo.Direction.REVERSE);
        }
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

    /**
     * Reverse the direction of the claw servos
     */
    @Override
    public void reverse() {
        if (numServos == 2) {
            servos[0].setDirection(Servo.Direction.REVERSE);
            servos[1].setDirection(Servo.Direction.FORWARD);
        } else {
            servos[0].setDirection(Servo.Direction.FORWARD);
        }
    }

    /**
     * Reverse the direction of the specified servo
     */
    public void reverse(String servoName) {
        if (numServos == 2) {
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
        } else {
            reverse();
        }
    }
}
