package org.cen.easy_ftc.lift;

import org.cen.easy_ftc.mechanism.MotorMechanismUtil;

/**
 * Provides static utility methods for moving a one-motor lift by extending the functionality of
 * {@link MotorMechanismUtil}.
 * 
 * @Methods {@link #controlToDirection(double deadZone, float lt, float rt)}
 *          <li>{@link #languageToDirection(String direction)}
 *          <li>{@link #map(double controllerValue, double deadZone)} (inherited from
 *          {@link MotorMechanismUtil})
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (inherited from
 *          {@link MotorMechanismUtil})
 */
class SoloLiftUtil extends MotorMechanismUtil {
    /**
     * Set lift motor movements based on triggers
     */
    protected static double[] controlToDirection(double deadZone, float lt, float rt) {
        double lift = map(rt, deadZone) - map(lt, deadZone);
        double[] movements = {lift};
        return movements;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double[] languageToDirection(double power, String direction) {
        double[] motorDirections = {0};
        switch (direction) {
            case "up":
                motorDirections[0] = 1;
                break;
            case "down":
                motorDirections[0] = -1;
                break;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction
                        + ", passed to SoloLift.move(). Valid directions are: up, down");
        }
        double[] movements = scaleDirections(power, motorDirections);
        return movements;
    }
}
