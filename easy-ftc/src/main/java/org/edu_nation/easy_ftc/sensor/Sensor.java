// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Blueprints an abstract sensor, providing basic functionalities, options, and objects common to
 * all Sensors. Cannot be instantiated; only extended by actual sensor classes.
 */
abstract class Sensor<S, V> {
    protected HardwareMap hardwareMap;

    @SuppressWarnings("java:S1700")
    protected S sensor;

    protected String name;
    protected boolean reverse;
    protected double threshold;

    /** Constructor */
    protected Sensor(Builder<?> builder) {
        this.hardwareMap = builder.hardwareMap;
        this.reverse = builder.reverse;
    }

    public abstract static class Builder<T extends Builder<T>> {
        private final HardwareMap hardwareMap;
        private boolean reverse = false;

        /**
         * Builder constructor
         *
         * @param hardwareMap instance of the calling opMode's hardwareMap
         * @throws NullPointerException if hardwareMap is null
         */
        protected Builder(HardwareMap hardwareMap) {
            if (hardwareMap == null) {
                throw new NullPointerException("Null hardwareMap passed to Sensor.Builder()");
            }
            this.hardwareMap = hardwareMap;
        }

        /**
         * Reverse the sensor's state
         *
         * @return builder instance
         */
        public T reverse() {
            this.reverse = true;
            return self();
        }

        public abstract T name(String name);

        @SuppressWarnings("java:S1452")
        abstract Sensor<?, ?> build();

        protected abstract T self();
    }

    protected abstract void init();

    public abstract V state();
}
