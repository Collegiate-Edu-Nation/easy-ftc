// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

public class TestArm {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "armLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "armRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "armLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "armRight")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "arm")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void Arm_initializes() {
        mockInit();

        try {
            new Arm.Builder(mockedOpMode, mockedHardwareMap)
                    .behavior(DcMotor.ZeroPowerBehavior.BRAKE)
                    .build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse().build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse("arm").build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse(new String[] {"arm"}).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap)
                    .encoder()
                    .length(2)
                    .gearing(19.2)
                    .build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void opModeThrowsException() {
        mockInit();

        new Arm.Builder(null, mockedHardwareMap).build();
    }

    @Test(expected = NullPointerException.class)
    public void hardwareMapThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, null).build();
    }

    @Test(expected = NullPointerException.class)
    public void deviceNameThrowsException() {
        mockInit();

        String nullString = null;
        new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse(nullString).build();
    }

    @Test(expected = NullPointerException.class)
    public void deviceNamesThrowsException() {
        mockInit();

        String[] nullString = null;
        new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse(nullString).build();
    }

    @Test(expected = NullPointerException.class)
    public void gamepadThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).gamepad(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void diameterNoEncoderThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).diameter(1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void diameterThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).diameter(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).length(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void gearingThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).gearing(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void deadzoneThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).deadzone(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void countThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(0).build();
    }

    @Test(expected = NullPointerException.class)
    public void namesThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).names(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void namesLengthThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .names(new String[] {"abc"})
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void behaviorThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).behavior(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void upThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).up(-1).build();
    }

    @Test(expected = IllegalStateException.class)
    public void downThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).down(2).build();
    }

    @Test
    public void controlSolo_isCalled() {
        mockInit();

        try {
            Arm arm =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            Arm armEnc =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .gamepad(mockedGamepad)
                            .build();
            Arm armPos =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .up(1.0)
                            .down(-1.0)
                            .gamepad(mockedGamepad)
                            .build();
            Arm armDia =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .length(5)
                            .up(1.0)
                            .down(-1.0)
                            .gamepad(mockedGamepad)
                            .build();

            arm.control();
            arm.control(0.5);
            armEnc.control();
            armEnc.control(0.5);

            FieldUtils.writeField(mockedGamepad, "left_bumper", true);
            armPos.control();
            armPos.control(0.5);
            armDia.control();
            armDia.control(0.5);

            FieldUtils.writeField(mockedGamepad, "left_bumper", false);
            FieldUtils.writeField(mockedGamepad, "right_bumper", true);
            armPos.control();
            armPos.control(0.5);
            armDia.control();
            armDia.control(0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void controlDual_isCalled() {
        mockInit();

        try {
            Arm arm =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .gamepad(mockedGamepad)
                            .build();
            Arm armEnc =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .gamepad(mockedGamepad)
                            .build();
            Arm armPos =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .up(1.0)
                            .down(-1.0)
                            .gamepad(mockedGamepad)
                            .build();
            Arm armDia =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .length(5)
                            .up(1.0)
                            .down(-1.0)
                            .gamepad(mockedGamepad)
                            .build();

            arm.control();
            arm.control(0.5);
            armEnc.control();
            armEnc.control(0.5);

            FieldUtils.writeField(mockedGamepad, "left_bumper", true);
            armPos.control();
            armPos.control(0.5);
            armDia.control();
            armDia.control(0.5);

            FieldUtils.writeField(mockedGamepad, "left_bumper", false);
            FieldUtils.writeField(mockedGamepad, "right_bumper", true);
            armPos.control();
            armPos.control(0.5);
            armDia.control();
            armDia.control(0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandSolo_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
            Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            Arm armPos =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4).build();
            Arm armDia =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .length(5)
                            .up(1.0)
                            .down(-1.0)
                            .build();
            Arm armLim =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .up(1.0)
                            .down(0.0)
                            .build();

            arm.command(Arm.Direction.UP, 1, 0.5);
            armEnc.command(Arm.Direction.UP, 1, 0.5);
            armPos.command(Arm.Direction.UP, 12, 0.5);

            when(mockedMotorEx.isBusy()).thenReturn(true, false);
            when(mockedOpMode.opModeIsActive()).thenReturn(true, false);
            armDia.command(Arm.Direction.UP, 12, 0.5);
            armDia.command(Arm.Direction.DOWN, 12, 0.5);
            armLim.command(Arm.Direction.UP, 1, 0.5);
            armLim.command(Arm.Direction.DOWN, 1, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandDual_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
            Arm armEnc =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder().build();
            Arm armPos =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .length(4)
                            .build();
            Arm armDia =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .length(5)
                            .up(1.0)
                            .down(-1.0)
                            .build();
            Arm armLim =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .up(1.0)
                            .down(0.0)
                            .build();

            arm.command(Arm.Direction.UP, 1, 0.5);
            armEnc.command(Arm.Direction.UP, 1, 0.5);
            armPos.command(Arm.Direction.UP, 12, 0.5);
            armDia.command(Arm.Direction.UP, 12, 0.5);
            armDia.command(Arm.Direction.DOWN, 12, 0.5);
            armLim.command(Arm.Direction.UP, 1, 0.5);
            armLim.command(Arm.Direction.DOWN, 1, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLargePower_controlThrowsException() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
        arm.control(1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativePower_controlThrowsException() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
        arm.control(-0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLargePower_commandThrowsException() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
        arm.command(Arm.Direction.UP, 1, 1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativePower_commandThrowsException() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
        arm.command(Arm.Direction.UP, 1, -0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidMeasurement_commandThrowsException() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
        arm.command(Arm.Direction.UP, -1, 0.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap)
                .reverse("abc")
                .gamepad(mockedGamepad)
                .build();
        new Arm.Builder(mockedOpMode, mockedHardwareMap)
                .reverse("abc")
                .encoder()
                .gamepad(mockedGamepad)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseDual_ThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("abc").build();
        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("abc").encoder().build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingSolo_ThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4).gearing(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDual_ThrowsException() {
        mockInit();

        new Arm.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .encoder()
                .length(4)
                .gearing(-1)
                .build();
    }

    @Test
    public void controlToDirection_isCorrect() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();

        // Test no movement (both true)
        double result = arm.controlToDirection(true, true);
        assertEquals(0, result, 0.01);

        // Test no movement (both false)
        result = arm.controlToDirection(false, false);
        assertEquals(0, result, 0.01);

        // Test up
        result = arm.controlToDirection(false, true);
        assertEquals(1.0, result, 0.01);

        // Test down
        result = arm.controlToDirection(true, false);
        assertEquals(-1.0, result, 0.01);
    }

    @Test
    public void languageToDirection_isCorrect() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();

        // Test Arm.Direction.UP
        double result = arm.languageToDirection(Arm.Direction.UP);
        assertEquals(1, result, 0.01);

        // Test Arm.Direction.DOWN
        result = arm.languageToDirection(Arm.Direction.DOWN);
        assertEquals(-1, result, 0.01);
    }

    @Test(expected = NullPointerException.class)
    public void languageToDirection_nullThrowsException() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
        arm.command(null, 1, 1);
    }

    @Test
    public void map_isCorrect() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).deadzone(0.1).build();
        final double[] controllerValues = {0.1, 0.5, 1, -0.1, -0.5, -1};
        final double[] expectedValues = {0, 0.45, 1, 0, -0.45, -1};

        // Test positive controllerValues
        for (int i = 0; i < controllerValues.length / 2; i++) {
            assertEquals(expectedValues[i], arm.map(controllerValues[i]), 0.01);
        }

        // Test negative controllerValues
        for (int i = 3; i < controllerValues.length; i++) {
            assertEquals(expectedValues[i], arm.map(controllerValues[i]), 0.01);
        }
    }

    @Test
    public void whenOneMotor_scaleDirections_isCorrect() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
        final double[] powers = {0, 0.5, 1};
        final double[][] motorDirections = {{0}, {1}, {-1}};
        final double[][][] expectedValues = {
            {{0}, {0}, {0}}, {{0}, {0.5}, {-0.5}}, {{0}, {1}, {-1}}
        };

        // Test power zero
        for (int i = 0; i < 3; i++) {
            double[] movements = arm.scaleDirections(motorDirections[i], powers[0]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = arm.scaleDirections(motorDirections[i], powers[1]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = arm.scaleDirections(motorDirections[i], powers[2]);
            for (int j = 0; j < 1; j++) {
                assertEquals(expectedValues[2][i][j], movements[j], 0.01);
            }
        }
    }

    @Test
    public void whenTwoMotor_scaleDirections_isCorrect() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
        final double[] powers = {0, 0.5, 1};
        final double[][] motorDirections = {{0, 0}, {1, 1}, {-1, -1}};
        final double[][][] expectedValues = {
            {{0, 0}, {0, 0}, {0, 0}}, {{0, 0}, {0.5, 0.5}, {-0.5, -0.5}}, {{0, 0}, {1, 1}, {-1, -1}}
        };

        // Test power zero
        for (int i = 0; i < 3; i++) {
            double[] movements = arm.scaleDirections(motorDirections[i], powers[0]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = arm.scaleDirections(motorDirections[i], powers[1]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = arm.scaleDirections(motorDirections[i], powers[2]);
            for (int j = 0; j < 2; j++) {
                assertEquals(expectedValues[2][i][j], movements[j], 0.01);
            }
        }
    }

    @Test
    public void whenOneMotor_calculatePositions_isCorrect() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).build();
        final double[] movements = {1};
        final double[] movementsBack = {-1};
        final int[][] expectedValues = {{400}, {0}, {382}, {414}, {-400}};
        try {
            FieldUtils.writeField(arm, "distanceMultiplier", 400.0, true);
        } catch (Exception ignored) {
        }

        // Test distance = circumference
        int[] result = arm.calculatePositions(Math.PI * 4, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test distance = 0
        result = arm.calculatePositions(0, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test distance < circumference
        result = arm.calculatePositions(12, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }

        // Test distance > circumference
        result = arm.calculatePositions(13, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[3][i], result[i], 0.01);
        }

        // Test distance = circumference, back
        result = arm.calculatePositions(Math.PI * 4, movementsBack);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[4][i], result[i], 0.01);
        }
    }

    @Test
    public void whenTwoMotor_calculatePositions_isCorrect() {
        mockInit();

        Arm arm =
                new Arm.Builder(mockedOpMode, mockedHardwareMap)
                        .count(2)
                        .encoder()
                        .diameter(4)
                        .build();
        final double[] movements = {1, 1};
        final double[] movementsRotate = {1, -1};
        final int[][] expectedValues = {{400, 400}, {0, 0}, {382, 382}, {414, 414}, {400, -400}};
        try {
            FieldUtils.writeField(arm, "distanceMultiplier", 400.0, true);
        } catch (Exception ignored) {
        }

        // Test distance = circumference
        int[] result = arm.calculatePositions(Math.PI * 4, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test distance = 0
        result = arm.calculatePositions(0, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test distance < circumference
        result = arm.calculatePositions(12, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }

        // Test distance > circumference
        result = arm.calculatePositions(13, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[3][i], result[i], 0.01);
        }

        // Test distance = circumference, rotate
        result = arm.calculatePositions(Math.PI * 4, movementsRotate);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[4][i], result[i], 0.01);
        }
    }
}
