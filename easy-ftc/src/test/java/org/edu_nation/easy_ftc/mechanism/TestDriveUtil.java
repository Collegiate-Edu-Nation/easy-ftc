package org.edu_nation.easy_ftc.mechanism;

import java.lang.Math;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDriveUtil {
    @Test
    public void whenTank_controlToDirection_isCorrect() {
        double deadZone = 0.1;
        double heading = 0;
        final String type = "differential";

        // Test "", no movement
        double[] result = DriveUtil.controlToDirection(type, "", deadZone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test "", forward
        result = DriveUtil.controlToDirection(type, "", deadZone, heading, -1, 0, -1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "", backward
        result = DriveUtil.controlToDirection(type, "", deadZone, heading, 1, 0, 1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenArcade_controlToDirection_isCorrect() {
        final String type = "differential";
        double deadZone = 0.1;
        double heading = 0;

        // Test "", no movement
        double[] result = DriveUtil.controlToDirection(type, "arcade", deadZone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test "", forward
        result = DriveUtil.controlToDirection(type, "arcade", deadZone, heading, -1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "", backward
        result = DriveUtil.controlToDirection(type, "arcade", deadZone, heading, 1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenRobotCentric_controlToDirection_isCorrect() {
        final double deadZone = 0.1;
        final double heading = 0;
        final String type = "mecanum";

        // Test no movement
        double[] result = DriveUtil.controlToDirection(type, "", deadZone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test forward
        result = DriveUtil.controlToDirection(type, "", deadZone, heading, -1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test backward
        result = DriveUtil.controlToDirection(type, "", deadZone, heading, 1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenFieldCentric_controlToDirection_isCorrect() {
        final double deadZone = 0.1;
        final double heading = Math.PI / 2; // equals 90 degrees
        final double[][] expectedValues = {{0, 0, 0, 0}, {1, -1, -1, 1}, {-1, 1, 1, -1}};
        final String type = "mecanum";

        // Test no movement
        double[] result = DriveUtil.controlToDirection(type, "field", deadZone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test forward
        result = DriveUtil.controlToDirection(type, "field", deadZone, heading, -1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test backward
        result = DriveUtil.controlToDirection(type, "field", deadZone, heading, 1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void controlToDirectionDif_garbageThrowsException() {
        final String type = "differential";

        // Test "abc"
        DriveUtil.controlToDirection(type, "abc", 0.1, 0, 0, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void controlToDirectionMec_garbageThrowsException() {
        final String type = "mecanum";

        // Test "abc"
        DriveUtil.controlToDirection(type, "abc", 0.1, 0, 0, 0, 0, 0);
    }

    @Test
    public void languageToDirectionDif_isCorrect() {
        final String type = "differential";

        // Test "forward"
        double[] result = DriveUtil.languageToDirection(type, 1, "forward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "backward"
        result = DriveUtil.languageToDirection(type, 1, "backward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }

        // Test "rotateLeft"
        double[] expectedRotateLeft = {-1, 1};
        result = DriveUtil.languageToDirection(type, 1, "rotateLeft");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test "rotateRight"
        double[] expectedRotateRight = {1, -1};
        result = DriveUtil.languageToDirection(type, 1, "rotateRight");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }
    }

    @Test
    public void languageToDirectionMec_isCorrect() {
        final String type = "mecanum";

        // Test "forward"
        double[] result = DriveUtil.languageToDirection(type, 1, "forward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "backward"
        result = DriveUtil.languageToDirection(type, 1, "backward");
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }

        // Test "left"
        double[] expectedLeft = {-1, 1, 1, -1};
        result = DriveUtil.languageToDirection(type, 1, "left");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedLeft[i], result[i], 0.01);
        }

        // Test "right"
        double[] expectedRight = {1, -1, -1, 1};
        result = DriveUtil.languageToDirection(type, 1, "right");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRight[i], result[i], 0.01);
        }

        // Test "rotateLeft"
        double[] expectedRotateLeft = {-1, 1, -1, 1};
        result = DriveUtil.languageToDirection(type, 1, "rotateLeft");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test "rotateRight"
        double[] expectedRotateRight = {1, -1, 1, -1};
        result = DriveUtil.languageToDirection(type, 1, "rotateRight");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }

        // Test "forwardLeft"
        double[] expectedForwardLeft = {0, 1, 1, 0};
        result = DriveUtil.languageToDirection(type, 1, "forwardLeft");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardLeft[i], result[i], 0.01);
        }

        // Test "forwardRight"
        double[] expectedForwardRight = {1, 0, 0, 1};
        result = DriveUtil.languageToDirection(type, 1, "forwardRight");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardRight[i], result[i], 0.01);
        }

        // Test "backwardLeft"
        double[] expectedBackwardLeft = {-1, 0, 0, -1};
        result = DriveUtil.languageToDirection(type, 1, "backwardLeft");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardLeft[i], result[i], 0.01);
        }

        // Test "backwardRight"
        double[] expectedBackwardRight = {0, -1, -1, 0};
        result = DriveUtil.languageToDirection(type, 1, "backwardRight");
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardRight[i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirectionDif_garbageThrowsException() {
        final String type = "differential";

        // Test "abc"
        DriveUtil.languageToDirection(type, 1, "abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirectionMec_garbageThrowsException() {
        final String type = "mecanum";

        // Test "abc"
        DriveUtil.languageToDirection(type, 1, "abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirectionDif_emptyThrowsException() {
        final String type = "differential";

        // Test ""
        DriveUtil.languageToDirection(type, 1, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirectionMec_emptyThrowsException() {
        final String type = "mecanum";

        // Test ""
        DriveUtil.languageToDirection(type, 1, "");
    }
}
