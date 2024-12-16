// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Implements a distance sensor by extending the functionality of {@link Sensor}
 *
 * @see Builder
 */
public class Distance extends Sensor<DistanceSensor, Boolean> {

    /** Constructor */
    private Distance(Builder builder) {
        super(builder);
        this.name = builder.name;
        this.threshold = builder.threshold;
        init();
    }

    /**
     * Construct a {@link Distance} sensor object using the builder design pattern
     *
     * <p><b>Basic Usage:</b>
     *
     * <pre>{@code
     * Distance distance = new Distance.Builder(hardwareMap).build();
     * }</pre>
     *
     * <b>Defaults:</b>
     *
     * <ul>
     *   <li>reverse = false
     *   <li>name = "distance"
     *   <li>threshold = 7.0
     * </ul>
     */
    @SuppressWarnings("java:S1185")
    public static class Builder extends Sensor.Builder<Builder> {
        private String name = "distance";
        private double threshold = 7.0;

        /**
         * Builder constructor
         *
         * @param hardwareMap instance of the calling opMode's hardwareMap
         * @throws NullPointerException if hardwareMap is null
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
         *
         * @param name name of the hardware devices
         * @return builder instance
         * @throws NullPointerException if name is null
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
         *
         * @param threshold cutoff threshold for whether an object is detected
         * @return builder instance
         * @throws IllegalArgumentException if threshold &lt; 0
         */
        public Builder threshold(double threshold) {
            if (threshold < 0) {
                throw new IllegalArgumentException(
                        "Unexpected threshold: "
                                + threshold
                                + ", passed to Distance.Builder().threshold(). Valid values are >0");
            }
            this.threshold = threshold;
            return this;
        }

        /**
         * Build the sensor
         *
         * @return distance instance
         */
        @Override
        public Distance build() {
            return new Distance(this);
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

    /** Initialize distance sensor */
    @Override
    protected void init() {
        sensor = hardwareMap.get(DistanceSensor.class, name);
    }

    /**
     * Return distance sensor state
     *
     * @return whether an object is within the distance cutoff
     */
    @Override
    public Boolean state() {
        if (reverse) {
            return (sensor.getDistance(DistanceUnit.CM) >= threshold);
        } else {
            return (sensor.getDistance(DistanceUnit.CM) < threshold);
        }
    }
}
