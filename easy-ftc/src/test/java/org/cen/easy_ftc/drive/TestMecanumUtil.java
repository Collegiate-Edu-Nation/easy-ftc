package org.cen.easy_ftc.drive;

import java.lang.Math;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestMecanumUtil {
    @Test
    public void whenRobotCentric_controlToDirection_isCorrect() {
        final double deadZone = 0.1;
        final double heading = 0;

        // Test no movement
        double[] result = MecanumUtil.controlToDirection("", deadZone, heading, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test forward
        result = MecanumUtil.controlToDirection("", deadZone, heading, -1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test backward
        result = MecanumUtil.controlToDirection("", deadZone, heading, 1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenFieldCentric_controlToDirection_isCorrect() {
        final double deadZone = 0.1;
        final double heading = Math.PI / 2; // equals 90 degrees
        final double[][] expectedValues = {{0, 0, 0, 0}, {1, -1, -1, 1}, {-1, 1, 1, -1}};

        // Test no movement
        double[] result = MecanumUtil.controlToDirection("field", deadZone, heading, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test forward
        result = MecanumUtil.controlToDirection("field", deadZone, heading, -1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test backward
        result = MecanumUtil.controlToDirection("field", deadZone, heading, 1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "forward"
        double[] result = MecanumUtil.languageToDirection("forward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "backward"
        result = MecanumUtil.languageToDirection("backward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }
}
