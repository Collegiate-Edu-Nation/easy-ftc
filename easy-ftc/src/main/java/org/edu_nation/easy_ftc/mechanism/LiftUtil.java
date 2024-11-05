// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

/**
 * Provides static utility methods for moving a lift by extending the functionality of
 * {@link LiftUtil}.
 * 
 * @Methods {@link #controlToDirection(double deadzone, float lt, float rt)}
 *          <li>{@link #languageToDirection(String direction)}
 *          <li>{@link #map(double controllerValue, double deadzone)} (inherited from
 *          {@link MotorMechanismUtil})
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (inherited from
 *          {@link MotorMechanismUtil})
 *          <li>{@link #calculatePositions(double, double, double, double[])} (inherited from
 *          {@link MotorMechanismUtil})
 */
class LiftUtil extends MotorMechanismUtil {
    /**
     * Set lift motor movements based on triggers
     */
    protected static double controlToDirection(double deadzone, float lt, float rt) {
        double lift = map(rt, deadzone) - map(lt, deadzone);
        return lift;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double languageToDirection(String direction) {
        double motorDirection;
        switch (direction) {
            case "up":
                motorDirection = 1;
                break;
            case "down":
                motorDirection = -1;
                break;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction
                        + ", passed to Lift.move(). Valid directions are: up, down");
        }
        return motorDirection;
    }
}
