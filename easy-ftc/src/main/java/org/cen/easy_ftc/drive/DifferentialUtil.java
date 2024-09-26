package org.cen.easy_ftc.drive;

/**
 * Provides static utility methods for driving a differential drivetrain by extending the functionality of {@link DriveUtil}.
 * @Methods
 * {@link #controlToDirection(String layout, double deadZone, float leftY, float rightY, float rightX)}
 * <li>{@link #languageToDirection(String direction)}
 * <li>{@link #map(double controllerValue, double deadZone)} (inherited from {@link DriveUtil})
 * <li>{@link #scaleDirections(double power, double [] motorDirections)} (inherited from {@link DriveUtil})
 */
class DifferentialUtil extends DriveUtil {
    /**
     * Set drivetrain motor movements based on layout: tank(default) or arcade
     */
    protected static double [] controlToDirection(String layout, double deadZone, float leftY, float rightY, float rightX) {
        double left, right;
        if(layout == "tank" || layout == "") {
            left = map(-leftY, deadZone);
            right = map(-rightY, deadZone);
        }
        else if(layout == "arcade") {
            left = map(-leftY, deadZone) + map(rightX, deadZone);
            right = map(-leftY, deadZone) - map(rightX, deadZone);
        }
        else {
            throw new IllegalArgumentException(
                "Unexpected layout: "
                + layout
                + ", passed to Differential.tele(). Valid layouts are: tank, arcade"
            );
        }

        double [] movements = {left, right};
        return movements;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double [] languageToDirection(String direction) {
        double [] motorDirections = {0,0};
        switch (direction) {
            case "forward":
                motorDirections[0] = 1;
                motorDirections[1] = 1;
                break;
            case "backward":
                motorDirections[0] = -1;
                motorDirections[1] = -1;
                break;
            case "rotateLeft":
                motorDirections[0] = -1;
                motorDirections[1] = 1;
                break;
            case "rotateRight":
                motorDirections[0] = 1;
                motorDirections[1] = -1;
                break;
            default:
                throw new IllegalArgumentException(
                    "Unexpected direction: "
                    + direction
                    + ", passed to Differential.move(). Valid directions are: forward, backward, rotateLeft, rotateRight"
                );
        }
        return motorDirections;
    }
}
