package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Implements a touch sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 *        <p>
 * @Methods {@link #state()}
 *          <li>{@link #reverse()}
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

    /**
     * Reverses sensor state
     */
    @Override
    public void reverse() {
        this.reverseState = true;
    }
}
