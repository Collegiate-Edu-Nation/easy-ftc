package org.cen.easy_ftc.lift;

/**
 * Blueprints an abstract lift utility class, providing methods for mapping controls and scaling
 * directions, which are common to all lifts. Cannot be instantiated, only extended by other classes
 * providing static methods (see {@link DualLiftUtil} and {@link SoloLiftUtil}).
 * <p>
 * 
 * @Methods {@link #map(double controllerValue, double deadZone)} (used by subclasses)
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (used by
 *          subclasses)
 */
abstract class LiftUtil {
    /**
     * Maps controller value from [-1,-deadZone] U [deadZone,1] -> [-1,1], enabling controller
     * deadZone
     * 
     * @Defaults deadZone = 0.1
     */
    protected static double map(double controllerValue, double deadZone) {
        double mappedValue;
        if (Math.abs(controllerValue) < Math.abs(deadZone)) {
            mappedValue = 0;
        } else {
            mappedValue = ((Math.abs(controllerValue) - deadZone) / (1.0 - deadZone));
            if (controllerValue < 0) {
                mappedValue *= -1;
            }
        }
        return mappedValue;
    }

    /**
     * Scale directions by a factor of power to derive actual, intended motor movements
     */
    protected static double[] scaleDirections(double power, double[] motorDirections) {
        double[] movements = new double[motorDirections.length];
        for (int i = 0; i < motorDirections.length; i++) {
            movements[i] = power * motorDirections[i];
        }
        return movements;
    }
}
