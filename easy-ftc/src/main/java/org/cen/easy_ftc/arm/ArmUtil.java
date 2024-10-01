package org.cen.easy_ftc.arm;

/**
 * Blueprints an abstract arm utility class, providing a method for scaling directions, which is
 * common to all arms. Cannot be instantiated, only extended by other classes providing static
 * methods (see {@link DualArmUtil} and {@link SoloArmUtil}).
 * <p>
 * 
 * @Methods {@link #map(double controllerValue, double deadZone)} (used by subclasses)
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (used by
 *          subclasses)
 */
abstract class ArmUtil {
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
