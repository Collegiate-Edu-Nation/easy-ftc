package org.cen.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Implements a touch sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 *        <p>
 * @Methods {@link #state()}
 */
public class Touch extends Sensor<Boolean> {
    private TouchSensor sensor;

    /**
     * Constructor
     */
    public Touch(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    /**
     * Initializes touch sensor
     */
    @Override
    protected void hardwareInit() {
        sensor = hardwareMap.get(TouchSensor.class, "touchSensor");
    }

    /**
     * Returns touch sensor state (whether the sensor has been pressed or not)
     */
    @Override
    public Boolean state() {
        if (reverseState) {
            return !(sensor.isPressed());
        } else {
            return sensor.isPressed();
        }
    }
}
