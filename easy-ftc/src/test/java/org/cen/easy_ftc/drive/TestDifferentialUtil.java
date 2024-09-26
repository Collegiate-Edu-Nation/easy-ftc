package org.cen.easy_ftc.drive;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class TestDifferentialUtil {
    @Test
    public void WhenTank_ControlToDirection_isCorrect() {
        double deadZone = 0.1;

        // Test "", no movement
        double [] result = DifferentialUtil.ControlToDirection("", deadZone, 0, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test "", forward
        result = DifferentialUtil.ControlToDirection("", deadZone, -1, -1, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "", backward
        result = DifferentialUtil.ControlToDirection("", deadZone, 1, 1, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void WhenArcade_ControlToDirection_isCorrect() {
        double deadZone = 0.1;

        // Test "", no movement
        double [] result = DifferentialUtil.ControlToDirection("arcade", deadZone, 0, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test "", forward
        result = DifferentialUtil.ControlToDirection("arcade", deadZone, -1, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "", backward
        result = DifferentialUtil.ControlToDirection("arcade", deadZone, 1, 0, 0);
        for(int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void LanguageToDirection_isCorrect() {
        // Test "forward"
        double [] result = DifferentialUtil.LanguageToDirection("forward");
        for(int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "backward"
        result = DifferentialUtil.LanguageToDirection("backward");
        for(int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }
}
