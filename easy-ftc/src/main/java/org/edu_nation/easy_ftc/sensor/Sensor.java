// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Blueprints an abstract sensor, providing basic functionalities, options, and objects common to
 * all sensors. Cannot be instantiated, only extended by actual sensor classes (see {@link Touch} ,
 * {@link Distance}, {@link Color}).
 * <p>
 * 
 * @Methods {@link #state()} (used by subclasses)
 */
abstract class Sensor<S, V> {
    protected HardwareMap hardwareMap;
    protected S sensor;
    protected String name;
    protected boolean reverse;
    protected double calibrationValue;

    protected Sensor(Builder<?> builder) {
        this.hardwareMap = builder.hardwareMap;
        this.reverse = builder.reverse;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected HardwareMap hardwareMap;
        protected boolean reverse = false;

        public Builder(HardwareMap hardwareMap) {
            this.hardwareMap = hardwareMap;
        }

        /**
         * Reverse the sensor's state
         */
        public T reverse() {
            this.reverse = true;
            return self();
        }

        public abstract T name(String name);

        public abstract Sensor<?, ?> build();

        public abstract T self();
    }

    protected abstract void init();

    public abstract V state();
}
