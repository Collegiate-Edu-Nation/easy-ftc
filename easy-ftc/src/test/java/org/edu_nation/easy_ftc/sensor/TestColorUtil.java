// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestColorUtil {
    @Test
    public void dominantColor_isCorrect() {
        int[][] rgbRawArrays =
                {{76, 110, 85}, {75, 111, 85}, {75, 110, 86}, {75, 110, 85}, {76, 111, 86}};
        int[] rgbOffsets = {10, -25, 0};
        double calibrationValue = 85.0;

        String[] colorsExpected = {"red", "green", "blue", "", ""};
        String[] colorsActual =
                {ColorUtil.dominantColor(rgbRawArrays[0], rgbOffsets, calibrationValue),
                        ColorUtil.dominantColor(rgbRawArrays[1], rgbOffsets, calibrationValue),
                        ColorUtil.dominantColor(rgbRawArrays[2], rgbOffsets, calibrationValue),
                        ColorUtil.dominantColor(rgbRawArrays[3], rgbOffsets, calibrationValue),
                        ColorUtil.dominantColor(rgbRawArrays[4], rgbOffsets, calibrationValue)};

        for (int i = 0; i < colorsActual.length; i++) {
            assertEquals(colorsExpected[i], colorsActual[i]);
        }
    }

    @Test
    public void weakColor_isCorrect() {
        int[][] rgbRawArrays = {{74, 110, 85}, {75, 109, 85}, {75, 110, 84}, {75, 110, 85}};
        int[] rgbOffsets = {10, -25, 0};

        String[] colorsExpected = {"red", "green", "blue", ""};
        String[] colorsActual = {ColorUtil.weakColor(rgbRawArrays[0], rgbOffsets),
                ColorUtil.weakColor(rgbRawArrays[1], rgbOffsets),
                ColorUtil.weakColor(rgbRawArrays[2], rgbOffsets),
                ColorUtil.weakColor(rgbRawArrays[3], rgbOffsets)};

        for (int i = 0; i < colorsActual.length; i++) {
            assertEquals(colorsExpected[i], colorsActual[i]);
        }
    }
}
