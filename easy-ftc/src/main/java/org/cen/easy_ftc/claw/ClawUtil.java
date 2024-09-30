package org.cen.easy_ftc.claw;

/**
 * Blueprints an abstract claw utility class, providing a method for controlling directions, which
 * is common to all claws. Cannot be instantiated, only extended by other classes providing static
 * methods (see {@link DualClawUtil} and {@link SoloClawUtil}).
 * <p>
 * 
 * @Methods {@link #controlToDirection(double open, double close, double current, boolean a, boolean b)}
 *          (used by subclasses)
 */
abstract class ClawUtil {
    /**
     * Set claw servo movement based on open, close values as well as current position
     */
    protected static double controlToDirection(double open, double close, double current, boolean a,
            boolean b) {
        double movement;
        if (a && !b) {
            movement = open;
        } else if (b && !a) {
            movement = close;
        } else { // do nothing otherwise
            movement = current;
        }
        return movement;
    }
}
