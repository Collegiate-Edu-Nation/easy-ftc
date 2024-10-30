package org.edu_nation.easy_ftc.mechanism;

/**
 * Blueprints an abstract servo utility class, providing a method for controlling directions, which
 * is common to all servo mechanisms. Cannot be instantiated, only extended by other classes
 * providing static methods.
 * <p>
 * 
 * @Methods {@link #controlToDirection(double open, double close, double current, boolean openButton, boolean closeButton)}
 *          (used by subclasses)
 */
abstract class ServoMechanismUtil {
    /**
     * Set claw servo movement based on open, close values as well as current position
     */
    protected static double controlToDirection(double open, double close, double current,
            boolean openButton, boolean closeButton) {
        double movement;
        if (openButton && !closeButton) {
            movement = open;
        } else if (closeButton && !openButton) {
            movement = close;
        } else { // do nothing otherwise
            movement = current;
        }
        return movement;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double languageToDirection(String direction, double open, double close,
            String mechanismName) {
        double servoDirection;
        switch (direction) {
            case "open":
                servoDirection = open;
                break;
            case "close":
                servoDirection = close;
                break;
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction: " + direction + ", passed to " + mechanismName
                                + ".move(). Valid directions are: open, close");
        }
        return servoDirection;
    }
}
