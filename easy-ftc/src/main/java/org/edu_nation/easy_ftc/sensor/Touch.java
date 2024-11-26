// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Implements a touch sensor by extending the functionality of {@link Sensor}.
 * <ul>
 * <li>See {@link Builder} for Builder methods and defaults.
 * </ul>
 */
public class Touch extends Sensor<TouchSensor, Boolean> {

    /** Constructor */
    private Touch(Builder builder) {
        super(builder);
        this.name = builder.name;
        init();
    }

    public static class Builder extends Sensor.Builder<Builder> {
        private String name = "touchSensor";

        /**
         * Touch Builder
         * 
         * <ul>
         * <li>name = "touchSensor"
         * <li>reverse = false
         * </ul>
         */
        public Builder(HardwareMap hardwareMap) {
            super(hardwareMap);
        }

        // methods inherited from Sensor.Builder
        @Override
        public Builder reverse() {
            return super.reverse();
        }

        // touch-specific methods
        /**
         * Change the name of the hardware device
         * 
         * @param name name of the hardware devices
         * @return builder instance
         * @throws NullPointerException if name is null
         */
        @Override
        public Builder name(String name) {
            if (name == null) {
                throw new NullPointerException("Null name passed to Touch.Builder().name()");
            }
            this.name = name;
            return this;
        }

        /**
         * Build the sensor
         * 
         * @return touch instance
         */
        @Override
        public Touch build() {
            return new Touch(this);
        }

        /**
         * Return builder instance
         * 
         * @return builder instance
         */
        @Override
        protected Builder self() {
            return this;
        }
    }

    /** Initialize touch sensor */
    @Override
    protected void init() {
        sensor = hardwareMap.get(TouchSensor.class, name);
    }

    /**
     * Returns touch sensor state
     * 
     * @return whether the sensor has been pressed or not
     */
    @Override
    public Boolean state() {
        if (reverse) {
            return !(sensor.isPressed());
        } else {
            return sensor.isPressed();
        }
    }
}
