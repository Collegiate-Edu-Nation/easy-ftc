package org.edu_nation.easy_ftc.claw;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestDualClawUtil {
    @Test
    public void languageToDirection_isCorrect() {
        // Test "open"
        double result = DualClawUtil.languageToDirection("open", 1, 0);
        assertEquals(1, result, 0.01);

        // Test "close"
        result = DualClawUtil.languageToDirection("close", 1, 0);
        assertEquals(0, result, 0.01);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_garbageThrowsException() {
        // Test "abc"
        DualClawUtil.languageToDirection("abc", 1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_emptyThrowsException() {
        // Test ""
        DualClawUtil.languageToDirection("", 1, 0);
    }
}
