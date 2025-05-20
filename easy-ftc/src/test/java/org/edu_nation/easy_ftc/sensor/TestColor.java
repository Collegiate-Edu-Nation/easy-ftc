// SPDX-FileCopyrightText: 2024-2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.edu_nation.easy_ftc.sensor.Color.RGB;
import org.junit.Test;

public class TestColor {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    ColorSensor mockedColorSensor = mock(ColorSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(ColorSensor.class, "color")).thenReturn(mockedColorSensor);
    }

    private void mockRGB(int red, int green, int blue) {
        when(mockedColorSensor.red()).thenReturn(red);
        when(mockedColorSensor.green()).thenReturn(green);
        when(mockedColorSensor.blue()).thenReturn(blue);
    }

    @Test(expected = NullPointerException.class)
    public void hardwareMapThrowsException() {
        mockInit();

        new Color.Builder(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void rgbOffsetsNullThrowsException() {
        mockInit();

        new Color.Builder(mockedHardwareMap).rgbOffsets(null).build();
    }

    @Test
    public void nameWorks() {
        mockInit();

        try {
            new Color.Builder(mockedHardwareMap).name("color").build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void nameThrowsException() {
        mockInit();

        new Color.Builder(mockedHardwareMap).name(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void thresholdThrowsException() {
        mockInit();

        new Color.Builder(mockedHardwareMap).threshold(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void rgbOffsetsThrowsException() {
        mockInit();

        new Color.Builder(mockedHardwareMap).rgbOffsets(new int[] {0, 0, 256}).build();
    }

    @Test
    public void state_isCorrect() {
        mockInit();

        Color mockedColor = new Color.Builder(mockedHardwareMap).build();

        // red is the max after offset
        mockRGB(76, 110, 85);
        RGB result = mockedColor.state();
        assertEquals(RGB.RED, result);

        // green is the max after offset
        mockRGB(75, 111, 85);
        result = mockedColor.state();
        assertEquals(RGB.GREEN, result);

        // blue is the max after offset
        mockRGB(75, 110, 86);
        result = mockedColor.state();
        assertEquals(RGB.BLUE, result);

        // reversed-state

        Color mockedColorReverse = new Color.Builder(mockedHardwareMap).reverse().build();

        // red is the max after offset
        mockRGB(74, 110, 85);
        result = mockedColorReverse.state();
        assertEquals(RGB.RED, result);

        // green is the max after offset
        mockRGB(75, 109, 85);
        result = mockedColorReverse.state();
        assertEquals(RGB.GREEN, result);

        // blue is the max after offset
        mockRGB(75, 110, 84);
        result = mockedColorReverse.state();
        assertEquals(RGB.BLUE, result);
    }

    @Test
    public void dominantColor_isCorrect() {
        mockInit();

        Color color =
                new Color.Builder(mockedHardwareMap)
                        .rgbOffsets(new int[] {10, -25, 0})
                        .threshold(85.0)
                        .build();
        int[][] rgbRawArrays = {
            {76, 110, 85}, {75, 111, 85}, {75, 110, 86}, {75, 110, 85}, {76, 111, 86}
        };

        RGB[] colorsExpected = {RGB.RED, RGB.GREEN, RGB.BLUE, null, null};
        RGB[] colorsActual = {
            color.dominantColor(rgbRawArrays[0]),
            color.dominantColor(rgbRawArrays[1]),
            color.dominantColor(rgbRawArrays[2]),
            color.dominantColor(rgbRawArrays[3]),
            color.dominantColor(rgbRawArrays[4])
        };

        for (int i = 0; i < colorsActual.length; i++) {
            assertEquals(colorsExpected[i], colorsActual[i]);
        }
    }

    @Test
    public void weakColor_isCorrect() {
        mockInit();

        Color color =
                new Color.Builder(mockedHardwareMap).rgbOffsets(new int[] {10, -25, 0}).build();
        int[][] rgbRawArrays = {{74, 110, 85}, {75, 109, 85}, {75, 110, 84}, {75, 110, 85}};

        RGB[] colorsExpected = {RGB.RED, RGB.GREEN, RGB.BLUE, null};
        RGB[] colorsActual = {
            color.weakColor(rgbRawArrays[0]),
            color.weakColor(rgbRawArrays[1]),
            color.weakColor(rgbRawArrays[2]),
            color.weakColor(rgbRawArrays[3])
        };

        for (int i = 0; i < colorsActual.length; i++) {
            assertEquals(colorsExpected[i], colorsActual[i]);
        }
    }
}
