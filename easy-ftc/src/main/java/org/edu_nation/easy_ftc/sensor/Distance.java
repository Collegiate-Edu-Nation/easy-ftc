// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Implements a distance sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param String name
 * @param Boolean reverse
 * @param Double calibrationValue (in CM)
 *        <p>
 * @Methods {@link #state()}
 */
public class Distance extends Sensor<DistanceSensor, Boolean> {

    /**
     * Constructor
     */
    private Distance(Builder builder) {
        super(builder);
        this.name = builder.name;
        this.calibrationValue = builder.calibrationValue;
        init();
    }

    public static class Builder extends Sensor.Builder<Builder> {
        private String name = "distanceSensor";
        private double calibrationValue = 7.0;

        /**
         * Distance Builder
         * 
         * @Defaults name = "distanceSensor"
         *           <li>reverse = false
         *           <li>calibrationValue = 7.0
         */
        public Builder(HardwareMap hardwareMap) {
            super(hardwareMap);
        }

        // methods inherited from Sensor.Builder
        @Override
        public Builder reverse() {
            return super.reverse();
        }

        // distance-specific methods
        /**
         * Change the name of the hardware device
         */
        @Override
        public Builder name(String name) {
            if (name == null) {
                throw new NullPointerException("Null name passed to Distance.Builder().name()");
            }
            this.name = name;
            return this;
        }

        /**
         * Specify the calibration value
         */
        public Builder calibrationValue(double calibrationValue) {
            if (calibrationValue < 0) {
                throw new IllegalArgumentException("Unexpected calibrationValue: "
                        + calibrationValue
                        + ", passed to Distance.Builder().calibrationValue(). Valid values are >0");
            }
            this.calibrationValue = calibrationValue;
            return this;
        }

        /**
         * Build the sensor
         */
        @Override
        public Distance build() {
            return new Distance(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    /**
     * Initializes distance sensor
     */
    @Override
    protected void init() {
        sensor = hardwareMap.get(DistanceSensor.class, name);
    }

    /**
     * Returns distance sensor state (whether an object is within the distance cutoff)
     */
    @Override
    public Boolean state() {
        if (reverse) {
            return !(sensor.getDistance(DistanceUnit.CM) < calibrationValue);
        } else {
            return (sensor.getDistance(DistanceUnit.CM) < calibrationValue);
        }
    }
}
