// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestMotorMechanism {
    @Test
    public void map_isCorrect() {
        final double deadzone = 0.1;
        final double[] controllerValues = {0.1, 0.5, 1, -0.1, -0.5, -1};
        final double[] expectedValues = {0, 0.45, 1, 0, -0.45, -1};


        // Test positive controllerValues
        for (int i = 0; i < controllerValues.length / 2; i++) {
            assertEquals(expectedValues[i], MotorMechanism.map(controllerValues[i], deadzone),
                    0.01);
        }

        // Test negative controllerValues
        for (int i = 3; i < controllerValues.length; i++) {
            assertEquals(expectedValues[i], MotorMechanism.map(controllerValues[i], deadzone),
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
            double[] movements = MotorMechanism.scaleDirections(powers[0], motorDirections[i]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanism.scaleDirections(powers[1], motorDirections[i]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanism.scaleDirections(powers[2], motorDirections[i]);
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
            double[] movements = MotorMechanism.scaleDirections(powers[0], motorDirections[i]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanism.scaleDirections(powers[1], motorDirections[i]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanism.scaleDirections(powers[2], motorDirections[i]);
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
            double[] movements = MotorMechanism.scaleDirections(powers[0], motorDirections[i]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanism.scaleDirections(powers[1], motorDirections[i]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = MotorMechanism.scaleDirections(powers[2], motorDirections[i]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[2][i][j], movements[j], 0.01);
            }
        }
    }

    @Test
    public void whenOneMotor_calculatePositions_isCorrect() {
        final double[] movements = {1};
        final double[] movementsBack = {-1};
        final int[][] expectedValues = {{400}, {0}, {382}, {414}, {-400}};

        // Test distance = circumference
        int[] result = MotorMechanism.calculatePositions(Math.PI * 4, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test distance = 0
        result = MotorMechanism.calculatePositions(0, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test distance < circumference
        result = MotorMechanism.calculatePositions(12, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }

        // Test distance > circumference
        result = MotorMechanism.calculatePositions(13, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[3][i], result[i], 0.01);
        }

        // Test distance = circumference, back
        result = MotorMechanism.calculatePositions(Math.PI * 4, 4, 400, movementsBack);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[4][i], result[i], 0.01);
        }
    }

    @Test
    public void whenTwoMotor_calculatePositions_isCorrect() {
        final double[] movements = {1, 1};
        final double[] movementsRotate = {1, -1};
        final int[][] expectedValues = {{400, 400}, {0, 0}, {382, 382}, {414, 414}, {400, -400}};

        // Test distance = circumference
        int[] result = MotorMechanism.calculatePositions(Math.PI * 4, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test distance = 0
        result = MotorMechanism.calculatePositions(0, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test distance < circumference
        result = MotorMechanism.calculatePositions(12, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }

        // Test distance > circumference
        result = MotorMechanism.calculatePositions(13, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[3][i], result[i], 0.01);
        }

        // Test distance = circumference, rotate
        result = MotorMechanism.calculatePositions(Math.PI * 4, 4, 400, movementsRotate);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[4][i], result[i], 0.01);
        }
    }

    @Test
    public void whenFourMotor_calculatePositions_isCorrect() {
        final double[] movements = {1, 1, 1, 1};
        final double[] movementsStrafe = {1, 0, 0, 1};
        final int[][] expectedValues = {{400, 400, 400, 400}, {0, 0, 0, 0}, {382, 382, 382, 382},
                {414, 414, 414, 414}, {400, 0, 0, 400}};

        // Test distance = circumference
        int[] result = MotorMechanism.calculatePositions(Math.PI * 4, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test distance = 0
        result = MotorMechanism.calculatePositions(0, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test distance < circumference
        result = MotorMechanism.calculatePositions(12, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }

        // Test distance > circumference
        result = MotorMechanism.calculatePositions(13, 4, 400, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[3][i], result[i], 0.01);
        }

        // Test distance = circumference, strafe
        result = MotorMechanism.calculatePositions(Math.PI * 4, 4, 400, movementsStrafe);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[4][i], result[i], 0.01);
        }
    }
}
