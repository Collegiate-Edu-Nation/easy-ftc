package org.cen.easy_ftc.claw;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a dual-servo claw by extending the functionality of {@link Claw}.
 * <p>
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverseState (true or false)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * <p>
 * @Methods
 * {@link #tele()}
 * <li>{@link #move(String direction)}
 */
public class DualClaw extends Claw {
    private Servo left_claw, right_claw;

    /**
     * Constructor
     * @Defaults 
     * reverseState = false
     * <li>gamepad = null
     */
    public DualClaw(HardwareMap hardwareMap) {super(hardwareMap);}
    /**
     * Constructor
     * @Defaults
     * gamepad = null
     */
    public DualClaw(HardwareMap hardwareMap, boolean reverseState) {super(hardwareMap, reverseState);}
    /**
     * Constructor
     * @Defaults
     * reverseState = false
     */
    public DualClaw(HardwareMap hardwareMap, Gamepad gamepad) {super(hardwareMap, gamepad);}
    /**
     * Constructor
     */
    public DualClaw(HardwareMap hardwareMap, boolean reverseState, Gamepad gamepad) {super(hardwareMap, reverseState, gamepad);}

    /**
     * Initializes claw servos based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        // Instantiate servos
        left_claw = hardwareMap.get(Servo.class, "left_claw");
        right_claw = hardwareMap.get(Servo.class, "right_claw");

        // Reverse direction of left servo for convenience (switch if claw is backwards)
        left_claw.setDirection(Servo.Direction.REVERSE);
        right_claw.setDirection(Servo.Direction.FORWARD);
    }

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        if(gamepad.a) {
            left_claw.setPosition(open);
            right_claw.setPosition(open);
        }
        else if(gamepad.b) {
            left_claw.setPosition(close);
            right_claw.setPosition(close);
        }
    }

    /**
     * Intermediate function that assigns individual servo positions based on direction specified in runOpMode() calls.
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
                left_claw.setPosition(open);
                right_claw.setPosition(open);
                break;
            case "close":
                left_claw.setPosition(close);
                right_claw.setPosition(close);
                break;
            default: 
                throw new IllegalArgumentException(
                    "Unexpected direction: " 
                    + direction
                    + ", passed to DualClaw.move(). Valid directions are: open, close"
                );
        }
    }
}
