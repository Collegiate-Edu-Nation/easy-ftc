package org.edu_nation.easy_ftc.mechanism;

import java.lang.Math;

/**
 * Blueprints an abstract motor mechanism utility class, providing methods for mapping controls and
 * scaling directions, which are common to all Mechanisms. Cannot be instantiated, only extended by
 * other classes providing static methods.
 * <p>
 * 
 * @Methods {@link #map(double controllerValue, double deadzone)} (used by subclasses)
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (used by
 *          subclasses)
 *          <li>{@link #calculatePositions(double distance, double diameter, double distanceMultiplier, double[] movements)}
 *          (used by subclasses)
 */
abstract class MotorMechanismUtil {
    /**
     * Maps controller value from [-1,-deadzone] U [deadzone,1] -> [-1,1], enabling controller
     * deadzone
     * 
     * @Defaults deadzone = 0.0
     */
    protected static double map(double controllerValue, double deadzone) {
        if (deadzone == 0.0) {
            return controllerValue;
        }

        double mappedValue;
        if (Math.abs(controllerValue) < Math.abs(deadzone)) {
            mappedValue = 0;
        } else {
            mappedValue = ((Math.abs(controllerValue) - deadzone) / (1.0 - deadzone));
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
        int arrLength = motorDirections.length;
        double[] movements = new double[arrLength];
        for (int i = 0; i < arrLength; i++) {
            movements[i] = power * motorDirections[i];
        }
        return movements;
    }

    /**
     * Calculate posiitons based on distance, diameter, distanceMultiplier, movements
     */
    protected static int[] calculatePositions(double distance, double diameter,
            double distanceMultiplier, double[] movements) {
        double circumference = Math.PI * diameter;
        double revolutions = distance / circumference;
        double positionRaw = revolutions * distanceMultiplier;
        int position = (int) Math.round(positionRaw);

        int arrLength = movements.length;
        int[] positions = new int[arrLength];
        for (int i = 0; i < arrLength; i++) {
            positions[i] = (int) movements[i] * position;
        }
        return positions;
    }
}
