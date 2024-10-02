package org.edu_nation.easy_ftc.arm;

import org.edu_nation.easy_ftc.mechanism.MotorMechanismUtil;

/**
 * Provides static utility methods for moving a two-motor arm by extending the functionality of
 * {@link ArmUtil}.
 * 
 * @Methods {@link #controlToDirection(double power, boolean lb, boolean rb)}
 *          <li>{@link #languageToDirection(String direction)}
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (inherited from
 *          {@link MotorMechanismUtil})
 */
class DualArmUtil extends MotorMechanismUtil {
    /**
     * Sets arm motor movements based on bumpers
     */
    protected static double[] controlToDirection(double power, boolean lb, boolean rb) {
        int down = lb ? 1 : 0;
        int up = rb ? 1 : 0;
        double arm = power * (up - down);
        double[] movements = {arm, arm};
        return movements;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double[] languageToDirection(double power, String direction) {
        double[] motorDirections = {0, 0};
        switch (direction) {
            case "up":
                motorDirections[0] = 1;
                motorDirections[1] = 1;
                break;
            case "down":
                motorDirections[0] = -1;
                motorDirections[1] = -1;
                break;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction
                        + ", passed to DualArm.move(). Valid directions are: up, down");
        }
        double movements[] = scaleDirections(power, motorDirections);
        return movements;
    }
}
