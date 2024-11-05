// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Implements a color sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverse
 * @param Double calibrationValue (0-255, cutoff for what constitutes a significant color reading)
 * @param Integer[] rgbOffsets (array of three integers, 0-255, shifts raw rgb readings for
 *        determining dominant color)
 *        <p>
 * @Methods {@link #state()}
 */
public class Color extends Sensor<ColorSensor, String> {
    private int[] rgbOffsets;

    /**
     * Constructor
     */
    private Color(Builder builder) {
        super(builder);
        this.calibrationValue = builder.calibrationValue;
        this.rgbOffsets = builder.rgbOffsets;
        init();
    }

    public static class Builder extends Sensor.Builder<Builder> {
        private double calibrationValue = 85.0;
        private int[] rgbOffsets = {10, -25, 0};

        /**
         * Color Builder
         * 
         * @Defaults reverse = false
         *           <li>calibrationValue = 85.0
         *           <li>rgbOffsets = {10, -25, 0}
         */
        public Builder(HardwareMap hardwareMap) {
            super(hardwareMap);
        }

        /**
         * Specify the calibration value
         */
        public Builder calibrationValue(double calibrationValue) {
            this.calibrationValue = calibrationValue;
            return this;
        }

        /**
         * Specify the rgbOffsets (array of 3 integers, 0-255)
         */
        public Builder rgbOffsets(int[] rgbOffsets) {
            this.rgbOffsets = rgbOffsets;
            return this;
        }

        /**
         * Build the sensor
         */
        @Override
        public Color build() {
            return new Color(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    /**
     * Initializes color sensor
     */
    @Override
    protected void init() {
        sensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }

    /**
     * Returns color sensor state (color of detection, one of: "red", "green", "blue", or "")
     */
    @Override
    public String state() {
        int[] rgbRaw = {sensor.red(), sensor.green(), sensor.blue()};
        String color;
        if (reverse) {
            color = ColorUtil.weakColor(rgbRaw, rgbOffsets);
        } else {
            color = ColorUtil.dominantColor(rgbRaw, rgbOffsets, calibrationValue);
        }
        return color;
    }
}
