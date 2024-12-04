// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Implements a color sensor by extending the functionality of {@link Sensor}
 * 
 * @see Builder
 * @see RGB
 */
public class Color extends Sensor<ColorSensor, Color.RGB> {
    private int[] rgbOffsets;

    /** Constructor */
    private Color(Builder builder) {
        super(builder);
        this.name = builder.name;
        this.threshold = builder.threshold;
        this.rgbOffsets = builder.rgbOffsets;
        init();
    }

    /**
     * Construct a Color sensor object using the Builder design pattern
     * <p>
     * <b>Basic Usage:</b>
     * 
     * <pre>
     * {@code
     * Color color = new Color.Builder(hardwareMap).build();
     * }
     * </pre>
     * 
     * <b>Defaults:</b>
     * <ul>
     * <li>reverse = false
     * <li>name = "color"
     * <li>threshold = 85.0
     * <li>rgbOffsets = {10, -25, 0}
     * </ul>
     */
    public static class Builder extends Sensor.Builder<Builder> {
        private String name = "color";
        private double threshold = 85.0;
        private int[] rgbOffsets = {10, -25, 0};

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

        // color-specific methods
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
                throw new NullPointerException("Null name passed to Color.Builder().name()");
            }
            this.name = name;
            return this;
        }

        /**
         * Specify the calibration value
         * 
         * @param threshold cutoff threshold for what's considered a meaningful reading
         * @return builder instance
         * @throws IllegalArgumentException if calibration value not in the interval [0, 255]
         */
        public Builder threshold(double threshold) {
            if (threshold < 0 || threshold > 255) {
                throw new IllegalArgumentException("Unexpected threshold: "
                        + threshold
                        + ", passed to Color.Builder().threshold(). Valid values are numbers in the interval [0, 255]");
            }
            this.threshold = threshold;
            return this;
        }

        /**
         * Specify the rgbOffsets (array of 3 integers, -255-255)
         * 
         * @param rgbOffsets array of 3 integer offsets that are added to the RGB values
         * @return builder instance
         * @throws NullPointerException if rgbOffsets is null
         * @throws IllegalArgumentException if any integers are not in the interval [-255, 255]
         */
        public Builder rgbOffsets(int[] rgbOffsets) {
            if (rgbOffsets == null) {
                throw new NullPointerException(
                        "Null rgbOffsets passed to Color.Builder().rgbOffsets()");
            }
            for (int rgbOffset : rgbOffsets) {
                if (rgbOffset < -255 || rgbOffset > 255) {
                    throw new IllegalArgumentException("Unexpected rgbOffsets value: " + rgbOffset
                            + ", passed to Color.Builder().rgbOffsets(). Valid values are arrays of integers in the interval [-255, 255]");
                }
            }
            this.rgbOffsets = rgbOffsets;
            return this;
        }

        /**
         * Build the sensor
         * 
         * @return color instance
         */
        @Override
        public Color build() {
            return new Color(this);
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

    /** RGB values that can be returned by {@link Color#state()} */
    public enum RGB {
        RED, GREEN, BLUE
    }

    /** Initialize color sensor */
    @Override
    protected void init() {
        sensor = hardwareMap.get(ColorSensor.class, name);
    }

    /**
     * Return color sensor state
     * 
     * @return color of detection, one of {@link RGB} or null
     */
    @Override
    public RGB state() {
        int[] rgbRaw = {sensor.red(), sensor.green(), sensor.blue()};
        RGB color;
        if (reverse) {
            color = weakColor(rgbRaw);
        } else {
            color = dominantColor(rgbRaw);
        }
        return color;
    }

    /** Convert the maximum, normalized rgb value to the corresponding color as a String */
    protected RGB dominantColor(int[] rgbRaw) {
        int[] rgbNormalized = normalize(rgbRaw);
        int max = max(rgbNormalized);

        RGB color;
        if (max > threshold) {
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
                throw new IllegalArgumentException(
                        "Unexpected color passed to Color.dominantColor(). Valid colors are: Color.RGB.RED, Color.RGB.GREEN, Color.RGB.BLUE");
            }
        } else {
            color = null;
        }
        return color;
    }

    /** Convert the minimum, normalized rgb value to the corresponding RGB color */
    protected RGB weakColor(int[] rgbRaw) {
        int[] rgbNormalized = normalize(rgbRaw);
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
            throw new IllegalArgumentException(
                    "Unexpected color passed to Color.weakColor(). Valid colors are: Color.RGB.RED, Color.RGB.GREEN, Color.RGB.BLUE");
        }
        return color;
    }

    /** Normalize color readings by applying offsets */
    private int[] normalize(int[] rgbRaw) {
        int[] rgbNormalized = {0, 0, 0};
        for (int i = 0; i < 3; i++) {
            rgbNormalized[i] = rgbRaw[i] + rgbOffsets[i];
        }
        return rgbNormalized;
    }

    /** Return maximum of normalized rgb values */
    private int max(int[] rgbNormalized) {
        return Math.max(Math.max(rgbNormalized[0], rgbNormalized[1]),
                Math.max(rgbNormalized[1], rgbNormalized[2]));
    }

    /** Return minimum of normalized rgb values */
    private int min(int[] rgbNormalized) {
        return Math.min(Math.min(rgbNormalized[0], rgbNormalized[1]),
                Math.min(rgbNormalized[1], rgbNormalized[2]));
    }
}
