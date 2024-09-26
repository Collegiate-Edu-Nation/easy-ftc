package org.cen.easy_ftc.drive;

import java.lang.Math;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class TestMecanumUtil {
    @Test
    public void WhenRobotCentric_ControlToDirection_isCorrect() {
        final double deadZone = 0.1;
        final double heading = 0;

        // Test no movement
        double [] result = MecanumUtil.ControlToDirection("", deadZone, heading, 0, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test forward
        result = MecanumUtil.ControlToDirection("", deadZone, heading, -1, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test backward
        result = MecanumUtil.ControlToDirection("", deadZone, heading, 1, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void WhenFieldCentric_ControlToDirection_isCorrect() {
        final double deadZone = 0.1;
        final double heading = Math.PI / 2; // equals 90 degrees
        final double [][] expectedValues = {
            {0, 0, 0, 0},
            {1, -1, -1, 1},
            {-1, 1, 1, -1}
        };

        // Test no movement
        double [] result = MecanumUtil.ControlToDirection("field", deadZone, heading, 0, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test forward
        result = MecanumUtil.ControlToDirection("field", deadZone, heading, -1, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test backward
        result = MecanumUtil.ControlToDirection("field", deadZone, heading, 1, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }
    }

    @Test
    public void LanguageToDirection_isCorrect() {
        // Test "forward"
        double [] result = MecanumUtil.LanguageToDirection("forward");
        for(int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "backward"
        result = MecanumUtil.LanguageToDirection("backward");
        for(int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }
}
