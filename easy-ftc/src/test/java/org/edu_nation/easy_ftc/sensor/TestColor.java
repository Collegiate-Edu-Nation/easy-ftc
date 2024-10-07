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
            new Color(mockedHardwareMap);
            new Color(mockedHardwareMap, 1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void state_isCorrect() {
        mockInit();
        Color mockedColor = new Color(mockedHardwareMap);

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
        mockedColor.reverse();

        // red is the max after offset
        mockRGB(74, 110, 85);
        result = mockedColor.state();
        assertEquals("red", result);

        // green is the max after offset
        mockRGB(75, 109, 85);
        result = mockedColor.state();
        assertEquals("green", result);

        // blue is the max after offset
        mockRGB(75, 110, 84);
        result = mockedColor.state();
        assertEquals("blue", result);
    }

    @Test
    public void reverse_isCalled() {
        mockInit();

        try {
            Color mockedColor = new Color(mockedHardwareMap);
            mockedColor.reverse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
