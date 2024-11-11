// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

/**
 * Provides static utility methods for moving an arm by extending the functionality of
 * {@link MotorMechanismUtil}.
 * 
 * @Methods {@link #controlToDirection(double power, boolean lb, boolean rb)}
 *          <li>{@link #languageToDirection(String direction)}
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (inherited from
 *          {@link MotorMechanismUtil})
 *          <li>{@link #calculatePositions(double, double, double, double[])} (inherited from
 *          {@link MotorMechanismUtil})
 */
class ArmUtil extends MotorMechanismUtil {
    /**
     * Sets arm motor movements based on bumpers
     */
    protected static double controlToDirection(boolean lb, boolean rb) {
        int down = lb ? 1 : 0;
        int up = rb ? 1 : 0;
        double arm = up - down;
        return arm;
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
                        + ", passed to Arm.move(). Valid directions are: up, down");
        }
        return motorDirection;
    }
}
