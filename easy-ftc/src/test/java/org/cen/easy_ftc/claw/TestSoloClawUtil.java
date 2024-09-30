package org.cen.easy_ftc.claw;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSoloClawUtil {
    @Test
    public void languageToDirection_isCorrect() {
        // Test "open"
        double result = SoloClawUtil.languageToDirection("open", 1, 0);
        assertEquals(1, result, 0.01);

        // Test "close"
        result = SoloClawUtil.languageToDirection("close", 1, 0);
        assertEquals(0, result, 0.01);
    }
}
