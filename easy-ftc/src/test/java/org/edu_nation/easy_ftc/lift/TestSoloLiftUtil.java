package org.edu_nation.easy_ftc.lift;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSoloLiftUtil {
    @Test
    public void controlToDirection_iscorrect() {
        final double deadZone = 0.1;
        final float[] controllerValues = {0.1f, 0.5f, 1};
        final double[] expectedValues = {0, 0.45, 1};

        // Test no movement (both 1)
        double[] result = SoloLiftUtil.controlToDirection(deadZone, 1, 1);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test no movement (both 0)
        result = SoloLiftUtil.controlToDirection(deadZone, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test up
        for (int i = 0; i < controllerValues.length; i++) {
            result = SoloLiftUtil.controlToDirection(deadZone, 0, controllerValues[i]);
            for (int j = 0; j < result.length; j++) {
                assertEquals(expectedValues[i], result[j], 0.01);
            }
        }

        // Test down
        for (int i = 0; i < controllerValues.length; i++) {
            result = SoloLiftUtil.controlToDirection(deadZone, controllerValues[i], 0);
            for (int j = 0; j < result.length; j++) {
                assertEquals(-expectedValues[i], result[j], 0.01);
            }
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "up"
        double[] result = SoloLiftUtil.languageToDirection(1, "up");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "down"
        result = SoloLiftUtil.languageToDirection(1, "down");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_garbageThrowsException() {
        // Test "abc"
        SoloLiftUtil.languageToDirection(1, "abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_emptyThrowsException() {
        // Test ""
        SoloLiftUtil.languageToDirection(1, "");
    }
}
