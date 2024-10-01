package org.cen.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Blueprints an abstract sensor, providing basic functionalities, options, and objects common to
 * all sensors. Cannot be instantiated, only extended by actual sensor classes (see {@link Touch}
 * and {@link Distance}).
 * <p>
 * 
 * @Methods {@link #state()} (used by subclasses)
 */
abstract class Sensor<V> {
    protected HardwareMap hardwareMap;
    protected boolean reverseState;
    protected double calibrationValue;

    /**
     * Constructor
     * 
     * @defaults calibrationValue = 0
     */
    public Sensor(HardwareMap hardwareMap) {
        this(hardwareMap, 0);
    }

    /**
     * Constructor
     */
    public Sensor(HardwareMap hardwareMap, double calibrationValue) {
        this.hardwareMap = hardwareMap;
        this.calibrationValue = calibrationValue;
        hardwareInit();
    }

    protected abstract void hardwareInit();

    public abstract V state();
}
