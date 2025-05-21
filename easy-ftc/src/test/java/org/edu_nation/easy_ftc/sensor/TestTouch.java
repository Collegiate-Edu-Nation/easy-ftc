// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.junit.Test;

public class TestTouch {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    TouchSensor mockedTouchSensor = mock(TouchSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(TouchSensor.class, "touch")).thenReturn(mockedTouchSensor);
    }

    @Test
    public void nameWorks() {
        mockInit();

        try {
            new Touch.Builder(mockedHardwareMap).name("touch").build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void nameThrowsException() {
        mockInit();

        new Touch.Builder(mockedHardwareMap).name(null).build();
    }

    @Test
    public void state_isCorrect() {
        mockInit();
        Touch mockedTouch = new Touch.Builder(mockedHardwareMap).build();

        // getDistance() >= threshold
        when(mockedTouchSensor.isPressed()).thenReturn(true);
        boolean result = mockedTouch.state();
        assertTrue(result);

        // getDistance() < threshold
        when(mockedTouchSensor.isPressed()).thenReturn(false);
        result = mockedTouch.state();
        assertFalse(result);

        // reversed-state
        Touch mockedTouchReverse = new Touch.Builder(mockedHardwareMap).reverse().build();

        // getDistance() >= threshold
        when(mockedTouchSensor.isPressed()).thenReturn(true);
        result = mockedTouchReverse.state();
        assertFalse(result);

        // getDistance() < threshold
        when(mockedTouchSensor.isPressed()).thenReturn(false);
        result = mockedTouchReverse.state();
        assertTrue(result);
    }
}
