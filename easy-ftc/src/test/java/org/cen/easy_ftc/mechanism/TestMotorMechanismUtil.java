package org.cen.easy_ftc.mechanism;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestMotorMechanismUtil {
    @Test
    public void map_isCorrect() {
        final double deadZone = 0.1;
        final double[] controllerValues = {0.1, 0.5, 1, -0.1, -0.5, -1};
        final double[] expectedValues = {0, 0.45, 1, 0, -0.45, -1};

        // Test positive controllerValues
        for (int i = 0; i < controllerValues.length / 2; i++) {
            assertEquals(expectedValues[i], MotorMechanismUtil.map(controllerValues[i], deadZone),
                    0.01);
        }

        // Test negative controllerValues
        for (int i = 3; i < controllerValues.length; i++) {
            assertEquals(expectedValues[i], MotorMechanismUtil.map(controllerValues[i], deadZone),
                    0.01);
        }
    }

    @Test
    public void whenOneMotor_scaleDirections_isCorrect() {
        final double[] powers = {0, 0.5, 1};
        final double[][] motorDirections = {{0}, {1}, {-1}};
        final double[][][] expectedValues =
                {{{0}, {0}, {0}}, {{0}, {0.5}, {-0.5}}, {{0}, {1}, {-1}}};

        // Test power zero
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanismUtil.scaleDirections(powers[0], motorDirections[i]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanismUtil.scaleDirections(powers[1], motorDirections[i]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanismUtil.scaleDirections(powers[2], motorDirections[i]);
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
            double[] movements = MotorMechanismUtil.scaleDirections(powers[0], motorDirections[i]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanismUtil.scaleDirections(powers[1], motorDirections[i]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanismUtil.scaleDirections(powers[2], motorDirections[i]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[2][i][j], movements[j], 0.01);
            }
        }
    }

    @Test
    public void whenFourMotor_scaleDirections_isCorrect() {
        final double[] powers = {0, 0.5, 1};
        final double[][] motorDirections = {{0, 0, 0, 0}, {1, 1, 1, 1}, {-1, -1, -1, -1}};
        final double[][][] expectedValues = {{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
                {{0, 0, 0, 0}, {0.5, 0.5, 0.5, 0.5}, {-0.5, -0.5, -0.5, -0.5}},
                {{0, 0, 0, 0}, {1, 1, 1, 1}, {-1, -1, -1, -1}}};

        // Test power zero
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanismUtil.scaleDirections(powers[0], motorDirections[i]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanismUtil.scaleDirections(powers[1], motorDirections[i]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanismUtil.scaleDirections(powers[2], motorDirections[i]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[2][i][j], movements[j], 0.01);
            }
        }
    }
}
