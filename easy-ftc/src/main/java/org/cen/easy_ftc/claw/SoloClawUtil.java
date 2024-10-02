package org.cen.easy_ftc.claw;

import org.cen.easy_ftc.mechanism.ServoMechanismUtil;

/**
 * Provides static utility methods for moving a one-servo claw by extending the functionality of
 * {@link ServoMechanismUtil}.
 * 
 * @Methods {@link #languageToDirection(String direction, double open, double close)}
 *          <li>{@link #controlToDirection(double open, double close, double current, boolean openButton, boolean closeButton)}
 *          (inherited from {@link ServoMechanismUtil})
 */
class SoloClawUtil extends ServoMechanismUtil {
    /**
     * Translate natural-language direction to numeric values
     */
    protected static double languageToDirection(String direction, double open, double close) {
        double servoDirection;
        switch (direction) {
            case "open":
                servoDirection = open;
                break;
            case "close":
                servoDirection = close;
                break;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction
                        + ", passed to SoloClaw.move(). Valid directions are: open, close");
        }
        return servoDirection;
    }
}
