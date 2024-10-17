package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Blueprints an abstract Servo Mechanism, providing basic functionalities, options, and objects common to
 * all Servo Mechanisms. Cannot be instantiated, only extended by other classes.
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
 */
public abstract class ServoMechanism extends Mechanism {
    protected Servo[] servos;
    protected int numServos;
    protected boolean smoothServo;
    protected double open, close;
    protected String mechanismName;
    protected double increment;
    protected double incrementDelay;
    protected double delay;

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    public void tele() {
        boolean openButton, closeButton;
        switch (mechanismName) {
            case "Claw":
                openButton = gamepad.b;
                closeButton = gamepad.a;
                break;
            default:
                openButton = gamepad.b;
                closeButton = gamepad.a;
        }
        double current = servos[0].getPosition();
        double movement =
                ServoMechanismUtil.controlToDirection(open, close, current, openButton, closeButton);
        if (smoothServo) {
            double position = current;
            setPositionByIncrement(position, movement);
        } else {
            for (Servo servo : servos) {
                servo.setPosition(movement);
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
        double servoDirection = ServoMechanismUtil.languageToDirection(direction, open, close, mechanismName);
        if (smoothServo) {
            double position = servos[0].getPosition();
            setPositionByIncrement(position, servoDirection);
        } else {
            for (Servo servo : servos) {
                servo.setPosition(servoDirection);
            }
            wait(delay);
        }
    }

    /**
     * Reverse the direction of the claw servos
     */
    public void reverse() {
        setDirections(true);
    }

    /**
     * Wrapper around setPosition that enables smooth, synchronized servo control
     */
    protected void setPositionByIncrement(double position, double movement) {
        while (opMode.opModeIsActive() && position != movement) {
            position += (movement - position > 0) ? increment : -increment;
            position = Math.min(Math.max(position, 0), 1);
            for (Servo claw : servos) {
                claw.setPosition(position);
            }
            wait(incrementDelay);
        }
    }

    /**
     * Wrapper around setDirection for all servos
     */
    protected void setDirections(boolean reverse) {
        if (!reverse) {
            for (int i = 0; i < servos.length; i++) {
                Servo.Direction direction = (i % 2 == 0) ? Servo.Direction.FORWARD : Servo.Direction.REVERSE;
                servos[i].setDirection(direction);
            }
        } else {
            for (int i = 0; i < servos.length; i++) {
                Servo.Direction direction = (i % 2 == 0) ? Servo.Direction.REVERSE : Servo.Direction.FORWARD;
                servos[i].setDirection(direction);
            }
        }
    }

    /**
     * Wrapper around setDirection for all servos, default case doesn't reverse
     */
    protected void setDirections() {
        setDirections(false);
    }
}
