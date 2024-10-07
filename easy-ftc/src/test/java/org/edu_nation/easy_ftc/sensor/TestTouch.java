package org.edu_nation.easy_ftc.sensor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class TestTouch {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    TouchSensor mockedTouchSensor = mock(TouchSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(TouchSensor.class, "touchSensor")).thenReturn(mockedTouchSensor);
    }

    @Test
    public void Touch_initializes() {
        mockInit();

        try {
            new Touch(mockedHardwareMap);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void state_isCorrect() {
        mockInit();
        Touch mockedTouch = new Touch(mockedHardwareMap);

        // getDistance() >= calibrationValue
        when(mockedTouchSensor.isPressed()).thenReturn(true);
        boolean result = mockedTouch.state();
        assertEquals(true, result);

        // getDistance() < calibrationValue
        when(mockedTouchSensor.isPressed()).thenReturn(false);
        result = mockedTouch.state();
        assertEquals(false, result);

        // reversed-state
        mockedTouch.reverse();

        // getDistance() >= calibrationValue
        when(mockedTouchSensor.isPressed()).thenReturn(true);
        result = mockedTouch.state();
        assertEquals(false, result);

        // getDistance() < calibrationValue
        when(mockedTouchSensor.isPressed()).thenReturn(false);
        result = mockedTouch.state();
        assertEquals(true, result);
    }

    @Test
    public void reverse_isCalled() {
        mockInit();

        try {
            Touch mockedTouch = new Touch(mockedHardwareMap);
            mockedTouch.reverse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
