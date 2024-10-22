package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Blueprints an abstract sensor, providing basic functionalities, options, and objects common to
 * all sensors. Cannot be instantiated, only extended by actual sensor classes (see {@link Touch} ,
 * {@link Distance}, {@link Apriltag}, {@link Color}).
 * <p>
 * 
 * @Methods {@link #state()} (used by subclasses)
 */
abstract class Sensor<V> {
    protected HardwareMap hardwareMap;
    protected boolean reverseState;
    protected double calibrationValue;

    protected abstract void hardwareInit();

    public abstract V state();
}
