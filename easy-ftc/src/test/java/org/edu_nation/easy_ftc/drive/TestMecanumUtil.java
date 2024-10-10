package org.edu_nation.easy_ftc.drive;

import java.lang.Math;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestMecanumUtil {
    @Test
    public void whenRobotCentric_controlToDirection_isCorrect() {
        final double deadZone = 0.1;
        final double heading = 0;

        // Test no movement
        double[] result = MecanumUtil.controlToDirection("", deadZone, heading, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test forward
        result = MecanumUtil.controlToDirection("", deadZone, heading, -1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test backward
        result = MecanumUtil.controlToDirection("", deadZone, heading, 1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenFieldCentric_controlToDirection_isCorrect() {
        final double deadZone = 0.1;
        final double heading = Math.PI / 2; // equals 90 degrees
        final double[][] expectedValues = {{0, 0, 0, 0}, {1, -1, -1, 1}, {-1, 1, 1, -1}};

        // Test no movement
        double[] result = MecanumUtil.controlToDirection("field", deadZone, heading, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test forward
        result = MecanumUtil.controlToDirection("field", deadZone, heading, -1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test backward
        result = MecanumUtil.controlToDirection("field", deadZone, heading, 1, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void controlToDirection_garbageThrowsException() {
        // Test "abc"
        MecanumUtil.controlToDirection("abc", 0.1, 0, 0, 0, 0);
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "forward"
        double[] result = MecanumUtil.languageToDirection(1, "forward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "backward"
        result = MecanumUtil.languageToDirection(1, "backward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }

        // Test "left"
        double[] expectedLeft = {-1, 1, 1, -1};
        result = MecanumUtil.languageToDirection(1, "left");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedLeft[i], result[i], 0.01);
        }

        // Test "right"
        double[] expectedRight = {1, -1, -1, 1};
        result = MecanumUtil.languageToDirection(1, "right");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRight[i], result[i], 0.01);
        }

        // Test "rotateLeft"
        double[] expectedRotateLeft = {-1, 1, -1, 1};
        result = MecanumUtil.languageToDirection(1, "rotateLeft");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test "rotateRight"
        double[] expectedRotateRight = {1, -1, 1, -1};
        result = MecanumUtil.languageToDirection(1, "rotateRight");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }

        // Test "forwardLeft"
        double[] expectedForwardLeft = {0, 1, 1, 0};
        result = MecanumUtil.languageToDirection(1, "forwardLeft");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardLeft[i], result[i], 0.01);
        }

        // Test "forwardRight"
        double[] expectedForwardRight = {1, 0, 0, 1};
        result = MecanumUtil.languageToDirection(1, "forwardRight");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardRight[i], result[i], 0.01);
        }

        // Test "backwardLeft"
        double[] expectedBackwardLeft = {-1, 0, 0, -1};
        result = MecanumUtil.languageToDirection(1, "backwardLeft");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardLeft[i], result[i], 0.01);
        }

        // Test "backwardRight"
        double[] expectedBackwardRight = {0, -1, -1, 0};
        result = MecanumUtil.languageToDirection(1, "backwardRight");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardRight[i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_garbageThrowsException() {
        // Test "abc"
        MecanumUtil.languageToDirection(1, "abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_emptyThrowsException() {
        // Test ""
        MecanumUtil.languageToDirection(1, "");
    }

    @Test
    public void calculatePositions_isCorrect() {
        final double[] movements = {1, 1, 1, 1};
        final double[] movementsStrafe = {1, 0, 0, 1};
        final int[][] expectedValues = {{400, 400, 400, 400}, {0, 0, 0, 0}, {382, 382, 382, 382}, {414, 414, 414, 414}, {400, 0, 0, 400}};

        // Test distance = circumference
        int[] result = MecanumUtil.calculatePositions(Math.PI*4, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test distance = 0
        result = MecanumUtil.calculatePositions(0, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test distance < circumference
        result = MecanumUtil.calculatePositions(12, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }

        // Test distance > circumference
        result = MecanumUtil.calculatePositions(13, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[3][i], result[i], 0.01);
        }

        // Test distance = circumference, strafe
        result = MecanumUtil.calculatePositions(Math.PI*4, 4, 400, movementsStrafe);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[4][i], result[i], 0.01);
        }
    }
}
