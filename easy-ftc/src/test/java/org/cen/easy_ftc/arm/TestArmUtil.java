package org.cen.easy_ftc.arm;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestArmUtil {
    @Test
    public void whenOneMotor_scaleDirections_isCorrect() {
        final double[] powers = {0, 0.5, 1};
        final double[][] motorDirections = {{0}, {1}, {-1}};
        final double[][][] expectedValues =
                {{{0}, {0}, {0}}, {{0}, {0.5}, {-0.5}}, {{0}, {1}, {-1}}};

        // Test power zero
        for (int i = 0; i < 3; i++) {
            double[] movements = ArmUtil.scaleDirections(powers[0], motorDirections[i]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = ArmUtil.scaleDirections(powers[1], motorDirections[i]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = ArmUtil.scaleDirections(powers[2], motorDirections[i]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[2][i][j], movements[j], 0.01);
            }
        }
    }

    @Test
    public void whenTwoMotor_scaleDirections_isCorrect() {
        final double[] powers = {0, 0.5, 1};
        final double[][] motorDirections = {{0, 0}, {1, 1}, {-1, -1}};
        final double[][][] expectedValues = {{{0, 0}, {0, 0}, {0, 0}},
                {{0, 0}, {0.5, 0.5}, {-0.5, -0.5}}, {{0, 0}, {1, 1}, {-1, -1}}};

        // Test power zero
        for (int i = 0; i < 3; i++) {
            double[] movements = ArmUtil.scaleDirections(powers[0], motorDirections[i]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = ArmUtil.scaleDirections(powers[1], motorDirections[i]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = ArmUtil.scaleDirections(powers[2], motorDirections[i]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[2][i][j], movements[j], 0.01);
            }
        }
    }
}
