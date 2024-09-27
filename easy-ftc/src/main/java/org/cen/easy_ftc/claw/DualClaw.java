package org.cen.easy_ftc.claw;

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
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(String direction)}
 *          <li>{@link #wait(double time)} (inherited from {@link Claw})
 */
public class DualClaw extends Claw {
    private Servo left_claw, right_claw;

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap, null);
    }

    /**
     * Constructor
     */
    public DualClaw(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Initializes claw servos
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
        double currentLeft = left_claw.getPosition();
        double currentRight = right_claw.getPosition();
        double[] movements = DualClawUtil.controlToDirection(open, close, currentLeft, currentRight,
                gamepad.a, gamepad.b);
        left_claw.setPosition(movements[0]);
        right_claw.setPosition(movements[1]);
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
        double[] servoDirections = DualClawUtil.languageToDirection(direction, open, close);
        left_claw.setPosition(servoDirections[0]);
        right_claw.setPosition(servoDirections[1]);
        wait(delay);
    }
}
