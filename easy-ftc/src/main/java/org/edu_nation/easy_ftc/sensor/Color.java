package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Implements a color sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverseState
 * @param Double calibrationValue (0-255, cutoff for what constitutes a significant color reading)
 * @param Integer[] rgbOffsets (array of three integers, 0-255, shifts raw rgb readings for determining dominant color)
 *        <p>
 * @Methods {@link #state()}
 */
public class Color extends Sensor<String> {
    private ColorSensor sensor;
    private int[] rgbOffsets;

    /**
     * Constructor
     */
    private Color(Builder builder) {
        this.hardwareMap = builder.hardwareMap;
        this.reverseState = builder.reverseState;
        this.calibrationValue = builder.calibrationValue;
        this.rgbOffsets = builder.rgbOffsets;
        hardwareInit();
    }

    public static class Builder {
        private HardwareMap hardwareMap;
        private boolean reverseState = false;
        private double calibrationValue = 85.0;
        private int[] rgbOffsets = {10, -25, 0};

        /**
         * Color Builder
         * 
         * @Defaults reverseState = false
         *           <li>calibrationValue = 85.0
         *           <li>rgbOffsets = {10, -25, 0}
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
        public Color build() {
            return new Color(this);
        }
    }

    /**
     * Initializes color sensor
     */
    @Override
    protected void hardwareInit() {
        sensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }

    /**
     * Returns color sensor state (color of detection, one of: "red", "green", "blue", or "")
     */
    @Override
    public String state() {
        int[] rgbRaw = {sensor.red(), sensor.green(), sensor.blue()};
        String color;
        if (reverseState) {
            color = ColorUtil.weakColor(rgbRaw, rgbOffsets);
        } else {
            color = ColorUtil.dominantColor(rgbRaw, rgbOffsets, calibrationValue);
        }
        return color;
    }
}
