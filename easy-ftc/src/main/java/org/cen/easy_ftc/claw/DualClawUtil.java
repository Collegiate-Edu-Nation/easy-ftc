package org.cen.easy_ftc.claw;

/**
 * Provides static utility methods for moving a two-servo claw
 * 
 * @Methods {@link #controlToDirection(double open, double close, double currentRight, double currentLeft, boolean a, boolean b)}
 *          <li>{@link #languageToDirection(String direction, double open, double close)}
 */
class DualClawUtil {
    /**
     * Set claw servo movements based on open, close values as well as current position
     */
    protected static double[] controlToDirection(double open, double close, double currentLeft,
            double currentRight, boolean a, boolean b) {
        double left, right;
        if (a && !b) {
            left = open;
            right = open;
        } else if (b && !a) {
            left = close;
            right = close;
        } else { // do nothing otherwise
            left = currentLeft;
            right = currentRight;
        }

        double[] movements = {left, right};
        return movements;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double[] languageToDirection(String direction, double open, double close) {
        double[] servoDirections = {0, 0};
        switch (direction) {
            case "open":
                servoDirections[0] = open;
                servoDirections[1] = open;
                break;
            case "close":
                servoDirections[0] = close;
                servoDirections[1] = close;
                break;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction
                        + ", passed to DualClaw.move(). Valid directions are: open, close");
        }
        return servoDirections;
    }
}
