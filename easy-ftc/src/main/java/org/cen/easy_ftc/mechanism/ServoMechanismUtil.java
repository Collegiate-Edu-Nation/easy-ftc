package org.cen.easy_ftc.mechanism;

/**
 * Blueprints an abstract servo utility class, providing a method for controlling directions, which
 * is common to all servo mechanisms. Cannot be instantiated, only extended by other classes
 * providing static methods.
 * <p>
 * 
 * @Methods {@link #controlToDirection(double open, double close, double current, boolean openButton, boolean closeButton)}
 *          (used by subclasses)
 */
public abstract class ServoMechanismUtil {
    /**
     * Set claw servo movement based on open, close values as well as current position
     */
    public static double controlToDirection(double open, double close, double current,
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
}
