package org.cen.easy_ftc.drive;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestDifferentialUtil {
    @Test
    public void whenTank_controlToDirection_isCorrect() {
        double deadZone = 0.1;

        // Test "", no movement
        double[] result = DifferentialUtil.controlToDirection("", deadZone, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test "", forward
        result = DifferentialUtil.controlToDirection("", deadZone, -1, -1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "", backward
        result = DifferentialUtil.controlToDirection("", deadZone, 1, 1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenArcade_controlToDirection_isCorrect() {
        double deadZone = 0.1;

        // Test "", no movement
        double[] result = DifferentialUtil.controlToDirection("arcade", deadZone, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test "", forward
        result = DifferentialUtil.controlToDirection("arcade", deadZone, -1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "", backward
        result = DifferentialUtil.controlToDirection("arcade", deadZone, 1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "forward"
        double[] result = DifferentialUtil.languageToDirection("forward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "backward"
        result = DifferentialUtil.languageToDirection("backward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }
}
