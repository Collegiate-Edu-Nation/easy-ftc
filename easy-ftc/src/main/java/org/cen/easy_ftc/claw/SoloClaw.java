package org.cen.easy_ftc.claw;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a solo-servo claw by extending the functionality of {@link Claw}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverseState (true or false)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(String direction)}
 */
public class SoloClaw extends Claw {
    private Servo claw;

    /**
     * Constructor
     * 
     * @Defaults reverseState = false
     *           <li>gamepad = null
     */
    public SoloClaw(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public SoloClaw(HardwareMap hardwareMap, boolean reverseState) {
        super(hardwareMap, reverseState);
    }

    /**
     * Constructor
     * 
     * @Defaults reverseState = false
     */
    public SoloClaw(HardwareMap hardwareMap, Gamepad gamepad) {
        super(hardwareMap, gamepad);
    }

    /**
     * Constructor
     */
    public SoloClaw(HardwareMap hardwareMap, boolean reverseState, Gamepad gamepad) {
        super(hardwareMap, reverseState, gamepad);
    }

    /**
     * Initializes claw servo
     */
    @Override
    protected void hardwareInit() {
        // Instantiate servo
        claw = hardwareMap.get(Servo.class, "claw");

        // Set direction of servo (switch to BACKWARD if claw is backwards)
        claw.setDirection(Servo.Direction.FORWARD);
    }

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        if (gamepad.a) {
            claw.setPosition(open);
        } else if (gamepad.b) {
            claw.setPosition(close);
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
        // Translate natural-language direction to numeric values
        switch (direction) {
            case "open":
                claw.setPosition(open);
                break;
            case "close":
                claw.setPosition(close);
                break;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction
                        + ", passed to DualClaw.move(). Valid directions are: open, close");
        }
    }
}
