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
        this.mechanismName = "Claw";
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
        } else {
            servos[0] = hardwareMap.get(Servo.class, "claw");
        }
        setDirections();
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
                            + ", passed to Claw.reverse(). Valid names are: clawLeft, clawRight");
            }
        } else {
            switch (servoName) {
                case "claw":
                    reverse();
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected servoName: " + servoName
                            + ", passed to Claw.reverse(). Valid names are: claw");
            }
        }
    }
}
