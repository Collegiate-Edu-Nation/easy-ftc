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
    protected boolean smoothServo;
    protected double open, close;
    protected double increment;
    protected double incrementDelay;
    protected double delay;

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
}
