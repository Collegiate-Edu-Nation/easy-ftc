package org.cen.easy_ftc.claw;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestDualClawUtil {
    @Test
    public void controlToDirection_isCorrect() {
        // Test open
        double[] result = DualClawUtil.controlToDirection(1, 0, 0, 0, true, false);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test close
        result = DualClawUtil.controlToDirection(1, 0, 1, 1, false, true);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test doNothing (both false)
        result = DualClawUtil.controlToDirection(1, 0, 1, 1, false, false);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test doNothing (both true)
        result = DualClawUtil.controlToDirection(1, 0, 1, 1, true, true);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "open"
        double[] result = DualClawUtil.languageToDirection("open", 1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "close"
        result = DualClawUtil.languageToDirection("close", 1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }
    }
}
