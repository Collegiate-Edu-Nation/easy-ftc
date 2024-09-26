package org.cen.easy_ftc.drive;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class TestDriveUtil {
    @Test
    public void map_isCorrect() {
        final double deadZone = 0.1;
        final double [] controllerValues = {0.1, 0.5, 1, -0.1, -0.5, -1};
        final double [] expectedValues = {0, 0.45, 1, 0, -0.45, -1};

        // Test positive controllerValues
        for(int i = 0; i < controllerValues.length / 2; i++) {
            assertEquals(expectedValues[i], DriveUtil.map(controllerValues[i], deadZone), 0.01);
        }

        // Test negative controllerValues
        for(int i = 3; i < controllerValues.length; i++) {
            assertEquals(expectedValues[i], DriveUtil.map(controllerValues[i], deadZone), 0.01);
        }
    }
}
