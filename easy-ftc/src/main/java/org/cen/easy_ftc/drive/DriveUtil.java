package org.cen.easy_ftc.drive;

import java.lang.Math;

public abstract class DriveUtil {
    /**
     * Maps controller value from [-1,-deadZone] U [deadZone,1] -> [-1,1], enabling controller deadZone
     * @Defaults
     * deadZone = 0.1
     */
    protected static double map(double controllerValue, double deadZone) {
        double mappedValue;
        if(Math.abs(controllerValue) < Math.abs(deadZone)) {
            mappedValue = 0;
        }
        else {
            mappedValue = ((Math.abs(controllerValue) - deadZone) / (1.0 - deadZone));
            if(controllerValue < 0) {
                mappedValue *= -1;
            }
        }
        return mappedValue;
    }
}
