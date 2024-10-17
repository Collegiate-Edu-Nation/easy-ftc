package org.edu_nation.easy_ftc.mechanism;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestServoMechanismUtil {
    @Test
    public void controlToDirection_isCorrect() {
        // Test open
        double result = ServoMechanismUtil.controlToDirection(1, 0, 0, true, false);
        assertEquals(1, result, 0.01);

        // Test close
        result = ServoMechanismUtil.controlToDirection(1, 0, 1, false, true);
        assertEquals(0, result, 0.01);

        // Test doNothing (both false)
        result = ServoMechanismUtil.controlToDirection(1, 0, 1, false, false);
        assertEquals(1, result, 0.01);

        // Test doNothing (both true)
        result = ServoMechanismUtil.controlToDirection(1, 0, 1, true, true);
        assertEquals(1, result, 0.01);
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "open"
        double result = ServoMechanismUtil.languageToDirection("open", 1, 0, "Claw");
        assertEquals(1, result, 0.01);

        // Test "close"
        result = ServoMechanismUtil.languageToDirection("close", 1, 0, "Claw");
        assertEquals(0, result, 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_garbageThrowsException() {
        // Test "abc"
        ServoMechanismUtil.languageToDirection("abc", 1, 0, "Claw");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_emptyThrowsException() {
        // Test ""
        ServoMechanismUtil.languageToDirection("", 1, 0, "Claw");
    }
}
