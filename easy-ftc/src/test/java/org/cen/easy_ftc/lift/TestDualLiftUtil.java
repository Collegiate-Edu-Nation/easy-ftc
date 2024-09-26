package org.cen.easy_ftc.lift;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestDualLiftUtil {
    @Test
    public void controlToDirection_iscorrect() {
        final double deadZone = 0.1;
        final float [] controllerValues = {0.1f, 0.5f, 1};
        final double [] expectedValues = {0, 0.45, 1};

        // Test no movement (both 1)
        double [] result = DualLiftUtil.controlToDirection(deadZone, 1, 1);
        for(int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }
        
        // Test no movement (both 0)
        result = DualLiftUtil.controlToDirection(deadZone, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test up
        for(int i = 0; i < controllerValues.length; i++) {
            result = DualLiftUtil.controlToDirection(deadZone, 0, controllerValues[i]);
            for(int j = 0; j < result.length; j++) {
                assertEquals(expectedValues[i], result[j], 0.01);
            }
        }

        // Test down
        for(int i = 0; i < controllerValues.length; i++) {
            result = DualLiftUtil.controlToDirection(deadZone, controllerValues[i], 0);
            for(int j = 0; j < result.length; j++) {
                assertEquals(-expectedValues[i], result[j], 0.01);
            }
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "up"
        double [] result = DualLiftUtil.languageToDirection("up");
        for(int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "down"
        result = DualLiftUtil.languageToDirection("down");
        for(int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }
}