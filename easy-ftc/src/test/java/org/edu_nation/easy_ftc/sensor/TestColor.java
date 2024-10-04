package org.edu_nation.easy_ftc.sensor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class TestColor {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    ColorSensor mockedColorSensor = mock(ColorSensor.class);

    @Test
    public void Color_initializes() {
        when(mockedHardwareMap.get(ColorSensor.class, "colorSensor")).thenReturn(mockedColorSensor);

        try {
            new Color(mockedHardwareMap);
            new Color(mockedHardwareMap, 1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void state_isCorrect() {
        when(mockedHardwareMap.get(ColorSensor.class, "colorSensor")).thenReturn(mockedColorSensor);
        Color mockedColor = new Color(mockedHardwareMap);

        // red is the max after offset
        when(mockedColorSensor.red()).thenReturn(76);
        when(mockedColorSensor.green()).thenReturn(110);
        when(mockedColorSensor.blue()).thenReturn(85);
        String result = mockedColor.state();
        assertEquals("red", result);

        // green is the max after offset
        when(mockedColorSensor.red()).thenReturn(75);
        when(mockedColorSensor.green()).thenReturn(111);
        when(mockedColorSensor.blue()).thenReturn(85);
        result = mockedColor.state();
        assertEquals("green", result);


        // blue is the max after offset
        when(mockedColorSensor.red()).thenReturn(75);
        when(mockedColorSensor.green()).thenReturn(110);
        when(mockedColorSensor.blue()).thenReturn(86);
        result = mockedColor.state();
        assertEquals("blue", result);
    }
}
