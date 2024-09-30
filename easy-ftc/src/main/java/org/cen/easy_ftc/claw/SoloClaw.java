package org.cen.easy_ftc.claw;

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
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(String direction)}
 *          <li>{@link #wait(double time)} (inherited from {@link Claw})
 */
public class SoloClaw extends Claw {
    private Servo claw;

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public SoloClaw(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap, null);
    }

    /**
     * Constructor
     */
    public SoloClaw(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
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
        double current = claw.getPosition();
        double movement =
                SoloClawUtil.controlToDirection(open, close, current, gamepad.a, gamepad.b);
        double position = current;
        setPositionByIncrement(position, movement);
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
        double position = claw.getPosition();
        setPositionByIncrement(position, servoDirection);
    }

    /**
     * Wrapper around setPosition that enables smooth, synchronized servo control
     */
    @Override
    protected void setPositionByIncrement(double position, double movement) {
        while (opMode.opModeIsActive() && position != movement) {
            position += (movement - position > 0) ? increment : -increment;
            position = Math.min(Math.max(position, 0), 1);
            claw.setPosition(position);
            wait(delay);
        }
    }
}
