// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Implements a color sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param String name
 * @param Boolean reverse
 * @param Double calibrationValue (0-255, cutoff for what constitutes a significant color reading)
 * @param Integer[] rgbOffsets (array of three integers, 0-255, shifts raw rgb readings for
 *        determining dominant color)
 *        <p>
 * @Methods {@link #state()}
 */
public class Color extends Sensor<ColorSensor, Color.RGB> {
    private int[] rgbOffsets;

    /**
     * Constructor
     */
    private Color(Builder builder) {
        super(builder);
        this.name = builder.name;
        this.calibrationValue = builder.calibrationValue;
        this.rgbOffsets = builder.rgbOffsets;
        init();
    }

    public static class Builder extends Sensor.Builder<Builder> {
        private String name = "colorSensor";
        private double calibrationValue = 85.0;
        private int[] rgbOffsets = {10, -25, 0};

        /**
         * Color Builder
         * 
         * @Defaults name = "colorSensor"
         *           <li>reverse = false
         *           <li>calibrationValue = 85.0
         *           <li>rgbOffsets = {10, -25, 0}
         */
        public Builder(HardwareMap hardwareMap) {
            super(hardwareMap);
        }

        /**
         * Change the name of the hardware device
         */
        @Override
        public Builder name(String name) {
            this.name = name;
            return this;
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
        protected Builder self() {
            return this;
        }
    }

    /**
     * RGB values that can be returned by state
     */
    public enum RGB {
        RED, GREEN, BLUE
    }

    /**
     * Initializes color sensor
     */
    @Override
    protected void init() {
        sensor = hardwareMap.get(ColorSensor.class, name);
    }

    /**
     * Returns color sensor state (color of detection, one of: Color.RGB.RED, Color.RGB.GREEN,
     * Color.RGB.BLUE, or null)
     */
    @Override
    public RGB state() {
        int[] rgbRaw = {sensor.red(), sensor.green(), sensor.blue()};
        RGB color;
        if (reverse) {
            color = weakColor(rgbRaw, rgbOffsets);
        } else {
            color = dominantColor(rgbRaw, rgbOffsets, calibrationValue);
        }
        return color;
    }

    /**
     * Converts the maximum, normalized rgb value to the corresponding color as a String
     * 
     * @param rgbRaw
     * @param rgbOffsets
     * @param calibrationValue
     * @return <b>color</b>
     */
    protected static RGB dominantColor(int[] rgbRaw, int[] rgbOffsets, double calibrationValue) {
        int[] rgbNormalized = normalize(rgbRaw, rgbOffsets);
        int max = max(rgbNormalized);

        RGB color;
        if (max > calibrationValue) {
            if ((rgbNormalized[0] == rgbNormalized[1] && rgbNormalized[0] == max)
                    || (rgbNormalized[0] == rgbNormalized[2] && rgbNormalized[0] == max)
                    || (rgbNormalized[1] == rgbNormalized[2] && rgbNormalized[1] == max)) {
                color = null;
            } else if (max == rgbNormalized[0]) {
                color = RGB.RED;
            } else if (max == rgbNormalized[1]) {
                color = RGB.GREEN;
            } else if (max == rgbNormalized[2]) {
                color = RGB.BLUE;
            } else {
                color = null;
            }
        } else {
            color = null;
        }
        return color;
    }

    /**
     * Converts the minimum, normalized rgb value to the corresponding RGB color
     * 
     * @param rgbRaw
     * @param rgbOffsets
     * @return <b>color</b>
     */
    protected static RGB weakColor(int[] rgbRaw, int[] rgbOffsets) {
        int[] rgbNormalized = normalize(rgbRaw, rgbOffsets);
        int min = min(rgbNormalized);

        RGB color;

        if ((rgbNormalized[0] == rgbNormalized[1] && rgbNormalized[0] == min)
                || (rgbNormalized[0] == rgbNormalized[2] && rgbNormalized[0] == min)
                || (rgbNormalized[1] == rgbNormalized[2] && rgbNormalized[1] == min)) {
            color = null;
        } else if (min == rgbNormalized[0]) {
            color = RGB.RED;
        } else if (min == rgbNormalized[1]) {
            color = RGB.GREEN;
        } else if (min == rgbNormalized[2]) {
            color = RGB.BLUE;
        } else {
            color = null;
        }
        return color;
    }

    /**
     * Normalize color readings by applying offsets
     * 
     * @param rgbRaw
     * @param rgbOffsets
     * @return <b>rgbNormalized</b>
     */
    private static int[] normalize(int[] rgbRaw, int[] rgbOffsets) {
        int[] rgbNormalized = {0, 0, 0};
        for (int i = 0; i < 3; i++) {
            rgbNormalized[i] = rgbRaw[i] + rgbOffsets[i];
        }
        return rgbNormalized;
    }

    /**
     * Return maximum of normalized rgb values
     * 
     * @param rgbNormalized
     * @return <b>max</b>
     */
    private static int max(int[] rgbNormalized) {
        int max = Math.max(Math.max(rgbNormalized[0], rgbNormalized[1]),
                Math.max(rgbNormalized[1], rgbNormalized[2]));
        return max;
    }

    /**
     * Return minimum of normalized rgb values
     * 
     * @param rgbNormalized
     * @return <b>min</b>
     */
    private static int min(int[] rgbNormalized) {
        int min = Math.min(Math.min(rgbNormalized[0], rgbNormalized[1]),
                Math.min(rgbNormalized[1], rgbNormalized[2]));
        return min;
    }
}
