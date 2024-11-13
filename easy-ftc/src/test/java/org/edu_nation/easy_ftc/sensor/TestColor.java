// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class TestColor {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    ColorSensor mockedColorSensor = mock(ColorSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(ColorSensor.class, "colorSensor")).thenReturn(mockedColorSensor);
    }

    private void mockRGB(int red, int green, int blue) {
        when(mockedColorSensor.red()).thenReturn(red);
        when(mockedColorSensor.green()).thenReturn(green);
        when(mockedColorSensor.blue()).thenReturn(blue);
    }

    @Test
    public void Color_initializes() {
        mockInit();

        try {
            new Color.Builder(mockedHardwareMap).build();
            new Color.Builder(mockedHardwareMap).name("color").build();
            new Color.Builder(mockedHardwareMap).calibrationValue(1).build();
            int[] offsets = {9, -26, -1};
            new Color.Builder(mockedHardwareMap).rgbOffsets(offsets).build();
            new Color.Builder(mockedHardwareMap).reverse().build();
            new Color.Builder(mockedHardwareMap).calibrationValue(1).rgbOffsets(offsets).build();
            new Color.Builder(mockedHardwareMap).calibrationValue(1).reverse().build();
            new Color.Builder(mockedHardwareMap).rgbOffsets(offsets).reverse().build();
            new Color.Builder(mockedHardwareMap).calibrationValue(1).rgbOffsets(offsets).reverse()
                    .build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void state_isCorrect() {
        mockInit();

        Color mockedColor = new Color.Builder(mockedHardwareMap).build();

        // red is the max after offset
        mockRGB(76, 110, 85);
        String result = mockedColor.state();
        assertEquals("red", result);

        // green is the max after offset
        mockRGB(75, 111, 85);
        result = mockedColor.state();
        assertEquals("green", result);

        // blue is the max after offset
        mockRGB(75, 110, 86);
        result = mockedColor.state();
        assertEquals("blue", result);

        // reversed-state

        Color mockedColorReverse = new Color.Builder(mockedHardwareMap).reverse().build();

        // red is the max after offset
        mockRGB(74, 110, 85);
        result = mockedColorReverse.state();
        assertEquals("red", result);

        // green is the max after offset
        mockRGB(75, 109, 85);
        result = mockedColorReverse.state();
        assertEquals("green", result);

        // blue is the max after offset
        mockRGB(75, 110, 84);
        result = mockedColorReverse.state();
        assertEquals("blue", result);
    }

    @Test
    public void dominantColor_isCorrect() {
        int[][] rgbRawArrays =
                {{76, 110, 85}, {75, 111, 85}, {75, 110, 86}, {75, 110, 85}, {76, 111, 86}};
        int[] rgbOffsets = {10, -25, 0};
        double calibrationValue = 85.0;

        String[] colorsExpected = {"red", "green", "blue", "", ""};
        String[] colorsActual = {Color.dominantColor(rgbRawArrays[0], rgbOffsets, calibrationValue),
                Color.dominantColor(rgbRawArrays[1], rgbOffsets, calibrationValue),
                Color.dominantColor(rgbRawArrays[2], rgbOffsets, calibrationValue),
                Color.dominantColor(rgbRawArrays[3], rgbOffsets, calibrationValue),
                Color.dominantColor(rgbRawArrays[4], rgbOffsets, calibrationValue)};

        for (int i = 0; i < colorsActual.length; i++) {
            assertEquals(colorsExpected[i], colorsActual[i]);
        }
    }

    @Test
    public void weakColor_isCorrect() {
        int[][] rgbRawArrays = {{74, 110, 85}, {75, 109, 85}, {75, 110, 84}, {75, 110, 85}};
        int[] rgbOffsets = {10, -25, 0};

        String[] colorsExpected = {"red", "green", "blue", ""};
        String[] colorsActual = {Color.weakColor(rgbRawArrays[0], rgbOffsets),
                Color.weakColor(rgbRawArrays[1], rgbOffsets),
                Color.weakColor(rgbRawArrays[2], rgbOffsets),
                Color.weakColor(rgbRawArrays[3], rgbOffsets)};

        for (int i = 0; i < colorsActual.length; i++) {
            assertEquals(colorsExpected[i], colorsActual[i]);
        }
    }
}
