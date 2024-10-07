package org.edu_nation.easy_ftc.sensor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TestDistance {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    DistanceSensor mockedDistanceSensor = mock(DistanceSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(DistanceSensor.class, "distanceSensor"))
                .thenReturn(mockedDistanceSensor);
    }

    @Test
    public void Distance_initializes() {
        mockInit();

        try {
            new Distance(mockedHardwareMap);
            new Distance(mockedHardwareMap, 7);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void state_isCorrect() {
        mockInit();
        Distance mockedDistance = new Distance(mockedHardwareMap);

        // getDistance() >= calibrationValue
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(7.0);
        boolean result = mockedDistance.state();
        assertEquals(false, result);

        // getDistance() < calibrationValue
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(6.0);
        result = mockedDistance.state();
        assertEquals(true, result);

        // reversed-state
        mockedDistance.reverse();

        // getDistance() >= calibrationValue
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(7.0);
        result = mockedDistance.state();
        assertEquals(true, result);

        // getDistance() < calibrationValue
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(6.0);
        result = mockedDistance.state();
        assertEquals(false, result);
    }

    @Test
    public void reverse_isCalled() {
        mockInit();

        try {
            Distance mockedDistance = new Distance(mockedHardwareMap);
            mockedDistance.reverse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
