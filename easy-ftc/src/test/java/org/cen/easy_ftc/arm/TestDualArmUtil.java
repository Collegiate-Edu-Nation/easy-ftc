package org.cen.easy_ftc.arm;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestDualArmUtil {
    @Test
    public void controlToDirection_iscorrect() {
        double power = 0.5;

        // Test no movement (both true)
        double[] result = DualArmUtil.controlToDirection(power, true, true);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test no movement (both false)
        result = DualArmUtil.controlToDirection(power, false, false);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test up
        result = DualArmUtil.controlToDirection(power, false, true);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0.5, result[i], 0.01);
        }

        // Test down
        result = DualArmUtil.controlToDirection(power, true, false);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-0.5, result[i], 0.01);
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "up"
        double[] result = DualArmUtil.languageToDirection(1, "up");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "down"
        result = DualArmUtil.languageToDirection(1, "down");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }
}
