package org.edu_nation.easy_ftc.lift;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestLiftUtil {
    @Test
    public void controlToDirection_iscorrect() {
        final double deadZone = 0.1;
        final float[] controllerValues = {0.1f, 0.5f, 1};
        final double[] expectedValues = {0, 0.45, 1};

        // Test no movement (both 1)
        double result = LiftUtil.controlToDirection(deadZone, 1, 1);
        assertEquals(0, result, 0.01);

        // Test no movement (both 0)
        result = LiftUtil.controlToDirection(deadZone, 0, 0);
        assertEquals(0, result, 0.01);

        // Test up
        for (int i = 0; i < controllerValues.length; i++) {
            result = LiftUtil.controlToDirection(deadZone, 0, controllerValues[i]);
            assertEquals(expectedValues[i], result, 0.01);
        }

        // Test down
        for (int i = 0; i < controllerValues.length; i++) {
            result = LiftUtil.controlToDirection(deadZone, controllerValues[i], 0);
            assertEquals(-expectedValues[i], result, 0.01);
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "up"
        double result = LiftUtil.languageToDirection("up");
        assertEquals(1, result, 0.01);

        // Test "down"
        result = LiftUtil.languageToDirection("down");
        assertEquals(-1, result, 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_garbageThrowsException() {
        // Test "abc"
        LiftUtil.languageToDirection("abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_emptyThrowsException() {
        // Test ""
        LiftUtil.languageToDirection("");
    }
}
