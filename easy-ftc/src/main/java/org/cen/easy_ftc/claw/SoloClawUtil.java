package org.cen.easy_ftc.claw;

/**
 * Provides static utility methods for moving a one-servo claw
 * 
 * @Methods {@link #controlToDirection(double open, double close, double current, boolean a, boolean b)}
 *          <li>{@link #languageToDirection(String direction, double open, double close)}
 */
class SoloClawUtil {
    /**
     * Set claw servo movement based on open, close values as well as current position
     */
    protected static double[] controlToDirection(double open, double close, double current,
            boolean a, boolean b) {
        double direction;
        if (a && !b) {
            direction = open;
        } else if (b && !a) {
            direction = close;
        } else { // do nothing otherwise
            direction = current;
        }

        double[] movements = {direction};
        return movements;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double[] languageToDirection(String direction, double open, double close) {
        double[] servoDirections = {0};
        switch (direction) {
            case "open":
                servoDirections[0] = open;
                break;
            case "close":
                servoDirections[0] = close;
                break;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction
                        + ", passed to SoloClaw.move(). Valid directions are: open, close");
        }
        return servoDirections;
    }
}
