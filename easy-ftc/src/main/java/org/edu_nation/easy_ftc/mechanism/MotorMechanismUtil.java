package org.edu_nation.easy_ftc.mechanism;

import java.lang.Math;

/**
 * Blueprints an abstract motor mechanism utility class, providing methods for mapping controls and
 * scaling directions, which are common to all Mechanisms. Cannot be instantiated, only extended by
 * other classes providing static methods.
 * <p>
 * 
 * @Methods {@link #map(double controllerValue, double deadZone)} (used by subclasses)
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (used by
 *          subclasses)
 *          <li>{@link #calculatePositions(double distance, double diameter, double distanceMultiplier, double[] movements)}
 *          (used by subclasses)
 */
public abstract class MotorMechanismUtil {
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

    /**
     * Calculate posiitons based on distance, diameter, distanceMultiplier, movements
     */
    public static int[] calculatePositions(double distance, double diameter,
            double distanceMultiplier, double[] movements) {
        double circumference = Math.PI * diameter;
        double revolutions = distance / circumference;
        double positionRaw = revolutions * distanceMultiplier;
        int position = (int) Math.round(positionRaw);

        int[] positions = new int[movements.length];
        for (int i = 0; i < movements.length; i++) {
            positions[i] = (int) movements[i] * position;
        }
        return positions;
    }
}
