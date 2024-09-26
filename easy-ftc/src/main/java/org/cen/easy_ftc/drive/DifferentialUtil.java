package org.cen.easy_ftc.drive;

public class DifferentialUtil extends DriveUtil {
    /**
     * Set axes based on layout: tank(default) or arcade
     */
    protected static double [] ControlToDirection(String layout, double deadZone, float leftY, float rightY, float rightX) {
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
    protected static double [] LanguageToDirection(String direction) {
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
