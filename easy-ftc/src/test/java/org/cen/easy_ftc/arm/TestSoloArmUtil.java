package org.cen.easy_ftc.arm;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSoloArmUtil {
    @Test
    public void controlToDirection_iscorrect() {
        double power = 0.5;

        // Test no movement (both true)
        double[] result = SoloArmUtil.controlToDirection(power, true, true);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test no movement (both false)
        result = SoloArmUtil.controlToDirection(power, false, false);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test up
        result = SoloArmUtil.controlToDirection(power, false, true);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0.5, result[i], 0.01);
        }

        // Test down
        result = SoloArmUtil.controlToDirection(power, true, false);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-0.5, result[i], 0.01);
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "up"
        double[] result = SoloArmUtil.languageToDirection(1, "up");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "down"
        result = SoloArmUtil.languageToDirection(1, "down");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }
}
