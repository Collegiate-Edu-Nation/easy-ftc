package org.edu_nation.easy_ftc.sensor;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestColorUtil {
    @Test
    public void dominantColor_isCorrect() {
        int[][] rgbRawArrays = {{76, 110, 85}, {75, 111, 85}, {75, 110, 86}, {75, 110, 85}};
        int[] rgbOffsets = {10, -25, 0};
        double calibrationValue = 85.0;

        String[] colorsExpected = {"red", "green", "blue", ""};
        String[] colorsActual =
                {ColorUtil.dominantColor(rgbRawArrays[0], rgbOffsets, calibrationValue),
                        ColorUtil.dominantColor(rgbRawArrays[1], rgbOffsets, calibrationValue),
                        ColorUtil.dominantColor(rgbRawArrays[2], rgbOffsets, calibrationValue), 
                        ColorUtil.dominantColor(rgbRawArrays[3], rgbOffsets, calibrationValue)};

        for (int i = 0; i < colorsActual.length; i++) {
            assertEquals(colorsExpected[i], colorsActual[i]);
        }

    }
}
