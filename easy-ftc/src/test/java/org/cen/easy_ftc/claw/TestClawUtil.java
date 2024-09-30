package org.cen.easy_ftc.claw;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestClawUtil {
    @Test
    public void controlToDirection_isCorrect() {
        // Test open
        double result = ClawUtil.controlToDirection(1, 0, 0, true, false);
        assertEquals(1, result, 0.01);

        // Test close
        result = ClawUtil.controlToDirection(1, 0, 1, false, true);
        assertEquals(0, result, 0.01);

        // Test doNothing (both false)
        result = ClawUtil.controlToDirection(1, 0, 1, false, false);
        assertEquals(1, result, 0.01);

        // Test doNothing (both true)
        result = ClawUtil.controlToDirection(1, 0, 1, true, true);
        assertEquals(1, result, 0.01);
    }
}
