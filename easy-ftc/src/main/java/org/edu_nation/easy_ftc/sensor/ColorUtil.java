package org.edu_nation.easy_ftc.sensor;

import java.lang.Math;

/**
 * Provides static utility methods to {@link Color} for identifying the dominant color from given
 * rgb and calibration values
 * 
 * @Methods {@link #dominantColor(int[] rgbRaw, int[] rgbOffsets, double calibrationValue)}
 *          <li>{@link #normalize(int[] rgbRaw, int[] rgbOffsets)}
 *          <li>{@link #max(int[] rgbNormalized)}
 */
class ColorUtil {
    /**
     * Converts the maximum, normalized rgb value to the corresponding color as a String
     * 
     * @param rgbRaw
     * @param rgbOffsets
     * @param calibrationValue
     * @return <b>color</b>
     */
    protected static String dominantColor(int[] rgbRaw, int[] rgbOffsets, double calibrationValue) {
        int[] rgbNormalized = normalize(rgbRaw, rgbOffsets);
        int max = max(rgbNormalized);

        String color;
        if (max > calibrationValue) {
            if (max == rgbNormalized[0]) {
                color = "red";
            } else if (max == rgbNormalized[1]) {
                color = "green";
            } else if (max == rgbNormalized[2]) {
                color = "blue";
            } else {
                color = "";
            }
        } else {
            color = "";
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
        for (int i = 0; i < rgbRaw.length; i++) {
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
}
