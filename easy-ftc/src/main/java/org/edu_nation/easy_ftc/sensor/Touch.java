package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Implements a touch sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverseState
 *        <p>
 * @Methods {@link #state()}
 */
public class Touch extends Sensor<Boolean> {
    private TouchSensor sensor;

    /**
     * Constructor
     */
    private Touch(Builder builder) {
        this.hardwareMap = builder.hardwareMap;
        this.reverseState = builder.reverseState;
        hardwareInit();
    }

    public static class Builder {
        private HardwareMap hardwareMap;
        private boolean reverseState = false;

        /**
         * Distance Builder
         * 
         * @Defaults reverseState = false
         */
        public Builder(HardwareMap hardwareMap) {
            this.hardwareMap = hardwareMap;
        }

        /**
         * Reverse the sensor's state
         */
        public Builder reverse() {
            this.reverseState = true;
            return this;
        }

        /**
         * Build the sensor
         */
        public Touch build() {
            return new Touch(this);
        }
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
