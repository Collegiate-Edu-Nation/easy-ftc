// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Implements a touch sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverse
 *        <p>
 * @Methods {@link #state()}
 */
public class Touch extends Sensor<TouchSensor, Boolean> {

    /**
     * Constructor
     */
    private Touch(Builder builder) {
        super(builder);
        init();
    }

    public static class Builder extends Sensor.Builder<Builder> {

        /**
         * Touch Builder
         * 
         * @Defaults reverse = false
         */
        public Builder(HardwareMap hardwareMap) {
            super(hardwareMap);
        }

        /**
         * Build the sensor
         */
        @Override
        public Touch build() {
            return new Touch(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    /**
     * Initializes touch sensor
     */
    @Override
    protected void init() {
        sensor = hardwareMap.get(TouchSensor.class, "touchSensor");
    }

    /**
     * Returns touch sensor state (whether the sensor has been pressed or not)
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
