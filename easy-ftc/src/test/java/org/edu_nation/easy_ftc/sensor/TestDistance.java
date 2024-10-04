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

    @Test
    public void Distance_initializes() {
        when(mockedHardwareMap.get(DistanceSensor.class, "distanceSensor"))
                .thenReturn(mockedDistanceSensor);

        try {
            new Distance(mockedHardwareMap);
            new Distance(mockedHardwareMap, 7);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void state_isCorrect() {
        when(mockedHardwareMap.get(DistanceSensor.class, "distanceSensor"))
                .thenReturn(mockedDistanceSensor);
        Distance mockedDistance = new Distance(mockedHardwareMap);

        // getDistance() >= calibrationValue
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(7.0);
        boolean result = mockedDistance.state();
        assertEquals(false, result);

        // getDistance() < calibrationValue
        when(mockedDistanceSensor.getDistance(DistanceUnit.CM)).thenReturn(6.0);
        result = mockedDistance.state();
        assertEquals(true, result);
    }
}
