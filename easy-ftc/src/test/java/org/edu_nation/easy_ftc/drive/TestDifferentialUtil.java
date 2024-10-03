package org.edu_nation.easy_ftc.drive;

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

    @Test(expected = IllegalArgumentException.class)
    public void controlToDirection_garbageThrowsException() {
        // Test "abc"
        DifferentialUtil.controlToDirection("abc", 0.1, 0, 0, 0);
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "forward"
        double[] result = DifferentialUtil.languageToDirection(1, "forward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "backward"
        result = DifferentialUtil.languageToDirection(1, "backward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }

        // Test "rotateLeft"
        double[] expectedRotateLeft = {-1, 1};
        result = DifferentialUtil.languageToDirection(1, "rotateLeft");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test "rotateRight"
        double[] expectedRotateRight = {1, -1};
        result = DifferentialUtil.languageToDirection(1, "rotateRight");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_garbageThrowsException() {
        // Test "abc"
        DifferentialUtil.languageToDirection(1, "abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_emptyThrowsException() {
        // Test ""
        DifferentialUtil.languageToDirection(1, "");
    }
}
