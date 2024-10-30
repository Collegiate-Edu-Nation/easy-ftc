package org.edu_nation.easy_ftc.mechanism;

import java.lang.Math;

/**
 * Provides static utility methods for driving a mecanum drivetrain by extending the functionality
 * of {@link DriveUtil}.
 * 
 * @Methods {@link #controlToDirection(String layout, double deadzone, double heading, float leftY, float leftX, float rightX)}
 *          <li>{@link #languageToDirection(String direction)}
 *          <li>{@link #map(double controllerValue, double deadzone)} (inherited from
 *          {@link MotorMechanismUtil})
 *          <li>{@link #scaleDirections(double power, double [] motorDirections)} (inherited from
 *          {@link MotorMechanismUtil})
 *          <li>{@link #calculatePositions(double, double, double, double[])} (inherited from
 *          {@link MotorMechanismUtil})
 */
class DriveUtil extends MotorMechanismUtil {
    /**
     * Set drivetrain motor movements based on layout: robot(default) or field
     */
    protected static double[] controlToDirection(int count, String type, String layout,
            double deadzone, double heading, float leftY, float leftX, float rightY, float rightX) {
        if (type == "differential" || type == "") {
            double left, right;
            if (layout == "tank" || layout == "") {
                left = map(-leftY, deadzone);
                right = map(-rightY, deadzone);
            } else if (layout == "arcade") {
                left = map(-leftY, deadzone) + map(rightX, deadzone);
                right = map(-leftY, deadzone) - map(rightX, deadzone);
            } else {
                throw new IllegalArgumentException("Unexpected layout: " + layout
                        + ", passed to Differential.tele(). Valid layouts are: tank, arcade");
            }

            double[] movements = new double[count];
            for (int i = 0; i < count; i++) {
                movements[i] = (i % 2 == 0) ? left : right;
            }
            return movements;
        } else {
            double frontLeft, frontRight, backLeft, backRight;
            // Axes (used for both robot-centric and field-centric)
            double axial = map(-leftY, deadzone);
            double lateral = map(leftX, deadzone);
            double yaw = map(rightX, deadzone);

            // Calculate desired individual motor values (orientation factored in for else-if
            // statement
            // i.e. field-centric driving)
            if (layout == "robot" || layout == "") {
                // Scaled individual motor movements derived from axes (left to right, front to
                // back)
                // Scaling is needed to ensure intended ratios of motor powers
                // (otherwise, for example, 1.1 would become 1, while 0.9 would be unaffected)
                double max = Math.max(Math.abs(axial) + Math.abs(lateral) + Math.abs(yaw), 1);
                frontLeft = ((axial + lateral + yaw) / max);
                frontRight = ((axial - lateral - yaw) / max);
                backLeft = ((axial - lateral + yaw) / max);
                backRight = ((axial + lateral - yaw) / max);
            } else if (layout == "field") {
                // Scaled individual motor movements derived from axes and orientation (left to
                // right,
                // front to back)
                // Heading is the current yaw of the robot, which is used to calculate relative axes
                double axial_relative = lateral * Math.sin(-heading) + axial * Math.cos(-heading);
                double lateral_relative = lateral * Math.cos(-heading) - axial * Math.sin(-heading);
                double max = Math.max(
                        Math.abs(axial_relative) + Math.abs(lateral_relative) + Math.abs(yaw), 1);
                frontLeft = ((axial_relative + lateral_relative + yaw) / max);
                frontRight = ((axial_relative - lateral_relative - yaw) / max);
                backLeft = ((axial_relative - lateral_relative + yaw) / max);
                backRight = ((axial_relative + lateral_relative - yaw) / max);
            } else {
                throw new IllegalArgumentException("Unexpected layout: " + layout
                        + ", passed to Mecanum.tele(). Valid layouts are: robot, field");
            }

            double[] movements = {frontLeft, frontRight, backLeft, backRight};
            return movements;
        }
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double[] languageToDirection(int count, String type, double power,
            String direction) {
        if (type == "differential" || type == "") {
            double[] motorDirections = new double[count];
            switch (direction) {
                case "forward":
                    for (int i = 0; i < count; i++) {
                        motorDirections[i] = 1;
                    }
                    break;
                case "backward":
                    for (int i = 0; i < count; i++) {
                        motorDirections[i] = -1;
                    }
                    break;
                case "rotateLeft":
                    for (int i = 0; i < count; i++) {
                        motorDirections[i] = (i % 2 == 0) ? -1 : 1;
                    }
                    break;
                case "rotateRight":
                    for (int i = 0; i < count; i++) {
                        motorDirections[i] = (i % 2 == 0) ? 1 : -1;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected direction: " + direction
                            + ", passed to Differential.move(). Valid directions are: forward, backward, rotateLeft, rotateRight");
            }

            double[] movements = scaleDirections(power, motorDirections);
            return movements;
        } else {
            double[] motorDirections = {0, 0, 0, 0};
            switch (direction) {
                case "forward":
                    motorDirections[0] = 1;
                    motorDirections[1] = 1;
                    motorDirections[2] = 1;
                    motorDirections[3] = 1;
                    break;
                case "backward":
                    motorDirections[0] = -1;
                    motorDirections[1] = -1;
                    motorDirections[2] = -1;
                    motorDirections[3] = -1;
                    break;
                case "left":
                    motorDirections[0] = -1;
                    motorDirections[1] = 1;
                    motorDirections[2] = 1;
                    motorDirections[3] = -1;
                    break;
                case "right":
                    motorDirections[0] = 1;
                    motorDirections[1] = -1;
                    motorDirections[2] = -1;
                    motorDirections[3] = 1;
                    break;
                case "rotateLeft":
                    motorDirections[0] = -1;
                    motorDirections[1] = 1;
                    motorDirections[2] = -1;
                    motorDirections[3] = 1;
                    break;
                case "rotateRight":
                    motorDirections[0] = 1;
                    motorDirections[1] = -1;
                    motorDirections[2] = 1;
                    motorDirections[3] = -1;
                    break;
                case "forwardLeft":
                    motorDirections[0] = 0;
                    motorDirections[1] = 1;
                    motorDirections[2] = 1;
                    motorDirections[3] = 0;
                    break;
                case "forwardRight":
                    motorDirections[0] = 1;
                    motorDirections[1] = 0;
                    motorDirections[2] = 0;
                    motorDirections[3] = 1;
                    break;
                case "backwardLeft":
                    motorDirections[0] = -1;
                    motorDirections[1] = 0;
                    motorDirections[2] = 0;
                    motorDirections[3] = -1;
                    break;
                case "backwardRight":
                    motorDirections[0] = 0;
                    motorDirections[1] = -1;
                    motorDirections[2] = -1;
                    motorDirections[3] = 0;
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected direction: " + direction
                            + ", passed to Mecanum.move(). Valid directions are: forward, backward, left, right, rotateLeft, rotateRight, forwaredLeft, forwardRight, backwardLeft, backwardRight");
            }

            double[] movements = scaleDirections(power, motorDirections);
            return movements;
        }
    }
}
