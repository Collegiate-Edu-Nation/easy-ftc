// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.sensor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.junit.Test;

public class TestDistance {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    DistanceSensor mockedDistanceSensor = mock(DistanceSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(DistanceSensor.class, "distance"))
                .thenReturn(mockedDistanceSensor);
    }

    @Test
    public void Distance_initializes() {
        mockInit();

        try {
            new Distance.Builder(mockedHardwareMap).build();
            new Distance.Builder(mockedHardwareMap).name("distance").build();
            new Distance.Builder(mockedHardwareMap).threshold(7).build();
            new Distance.Builder(mockedHardwareMap).reverse().build();
            new Distance.Builder(mockedHardwareMap).threshold(7).reverse().build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void nameThrowsException() {
        mockInit();

        new Distance.Builder(mockedHardwareMap).name(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void thresholdThrowsException() {
        mockInit();

        new Distance.Builder(mockedHardwareMap).threshold(-1).build();
    }

    @Test
    public void state_isCorrect() {
        mockInit();

        Distance mockedDistance = new Distance.Builder(mockedHardwareMap).build();

        // getDistance() >= threshold
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(7.0);
        boolean result = mockedDistance.state();
        assertEquals(false, result);

        // getDistance() < threshold
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(6.0);
        result = mockedDistance.state();
        assertEquals(true, result);

        // reversed-state
        Distance mockedDistanceReverse = new Distance.Builder(mockedHardwareMap).reverse().build();

        // getDistance() >= threshold
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(7.0);
        result = mockedDistanceReverse.state();
        assertEquals(true, result);

        // getDistance() < threshold
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(6.0);
        result = mockedDistanceReverse.state();
        assertEquals(false, result);
    }
}
