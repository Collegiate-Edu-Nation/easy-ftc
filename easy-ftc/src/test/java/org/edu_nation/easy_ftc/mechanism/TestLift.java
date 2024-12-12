// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
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

public class TestLift {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "liftLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "liftRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "liftLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "liftRight")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotor.class, "lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "lift")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void Lift_initializes() {
        mockInit();

        try {
            new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).up(1.0).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).down(-1.0).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).names(new String[] {"lift"}).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .reverse(new String[] {"lift"})
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .behavior(DcMotor.ZeroPowerBehavior.FLOAT);
            new Lift.Builder(mockedOpMode, mockedHardwareMap).deadzone(0.1).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).reverse("lift").build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("lift").build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse().build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).reverse().build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .encoder()
                    .gamepad(mockedGamepad)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .encoder()
                    .diameter(4)
                    .gamepad(mockedGamepad)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .encoder()
                    .diameter(4)
                    .gearing(19.2)
                    .gamepad(mockedGamepad)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .encoder()
                    .diameter(4)
                    .reverse()
                    .gamepad(mockedGamepad)
                    .build();

            new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).deadzone(0.1).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder().build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse().build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("liftLeft").build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder().reverse().build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .encoder()
                    .reverse("liftLeft")
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .reverse("liftLeft")
                    .reverse("liftRight")
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("liftRight").build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .encoder()
                    .reverse("liftRight")
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .gamepad(mockedGamepad)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .encoder()
                    .diameter(4)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .encoder()
                    .gamepad(mockedGamepad)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .encoder()
                    .diameter(4)
                    .gamepad(mockedGamepad)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .encoder()
                    .diameter(4)
                    .gearing(19.2)
                    .gamepad(mockedGamepad)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .encoder()
                    .diameter(4)
                    .reverse()
                    .gamepad(mockedGamepad)
                    .build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void countThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(0).build();
    }

    @Test(expected = NullPointerException.class)
    public void namesThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).names(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void namesLengthThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .names(new String[] {"abc"})
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void diameterNoEncoderThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).diameter(1).build();
    }

    @Test(expected = NullPointerException.class)
    public void behaviorThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).behavior(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void upThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).up(-1).build();
    }

    @Test(expected = IllegalStateException.class)
    public void downThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).down(2).build();
    }

    @Test
    public void controlSolo_isCalled() {
        mockInit();

        try {
            Lift lift =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            Lift liftEnc =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .gamepad(mockedGamepad)
                            .build();
            Lift liftPos =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .up(1.0)
                            .down(-1.0)
                            .gamepad(mockedGamepad)
                            .build();
            Lift liftDia =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .diameter(2)
                            .up(1.0)
                            .down(-1.0)
                            .gamepad(mockedGamepad)
                            .build();

            lift.control();
            lift.control(0.9);
            liftEnc.control();
            liftEnc.control(0.9);

            FieldUtils.writeField(mockedGamepad, "left_trigger", 1.0f);
            liftPos.control();
            liftPos.control(0.9);
            liftDia.control();
            liftDia.control(0.9);

            FieldUtils.writeField(mockedGamepad, "left_trigger", 0.0f);
            FieldUtils.writeField(mockedGamepad, "right_trigger", 1.0f);
            liftPos.control();
            liftPos.control(0.9);
            liftDia.control();
            liftDia.control(0.9);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void controlDual_isCalled() {
        mockInit();

        try {
            Lift lift =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .gamepad(mockedGamepad)
                            .build();
            Lift liftEnc =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .gamepad(mockedGamepad)
                            .build();
            Lift liftPos =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .up(1.0)
                            .down(-1.0)
                            .gamepad(mockedGamepad)
                            .build();
            Lift liftDia =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .diameter(2)
                            .up(1.0)
                            .down(-1.0)
                            .gamepad(mockedGamepad)
                            .build();

            lift.control();
            lift.control(0.9);
            liftEnc.control();
            liftEnc.control(0.9);

            FieldUtils.writeField(mockedGamepad, "left_trigger", 1.0f);
            liftPos.control();
            liftPos.control(0.9);
            liftDia.control();
            liftDia.control(0.9);

            FieldUtils.writeField(mockedGamepad, "left_trigger", 0.0f);
            FieldUtils.writeField(mockedGamepad, "right_trigger", 1.0f);
            liftPos.control();
            liftPos.control(0.9);
            liftDia.control();
            liftDia.control(0.9);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandSolo_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
            Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            Lift liftPos =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).build();

            lift.command(Lift.Direction.UP, 1, 0.5);
            liftEnc.command(Lift.Direction.UP, 1, 0.5);
            liftPos.command(Lift.Direction.UP, 12, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandDual_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
            Lift liftEnc =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder().build();
            Lift liftPos =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .diameter(4)
                            .build();

            lift.command(Lift.Direction.UP, 1, 0.5);
            liftEnc.command(Lift.Direction.UP, 1, 0.5);
            liftPos.command(Lift.Direction.UP, 12, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLargeMultiplier_controlThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
        lift.control(1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativeMultiplier_controlThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
        lift.control(-0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLargePower_commandThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
        lift.command(Lift.Direction.UP, 1, 1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativePower_commandThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
        lift.command(Lift.Direction.UP, 1, -0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidMeasurment_commandThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
        lift.command(Lift.Direction.UP, -1, 0.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).reverse("abc").build();
        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("abc").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseDual_ThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("abc").build();
        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder().reverse("abc").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingSolo_ThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).gearing(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDual_ThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .encoder()
                .diameter(4)
                .gearing(-1)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setdeadzoneSolo_ThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap)
                .encoder()
                .diameter(4)
                .deadzone(-1)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setdeadzoneDual_ThrowsException() {
        mockInit();

        new Lift.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .encoder()
                .diameter(4)
                .deadzone(-1)
                .build();
    }

    @Test
    public void controlToDirection_iscorrect() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).deadzone(0.1).build();
        final float[] controllerValues = {0.1f, 0.5f, 1};
        final double[] expectedValues = {0, 0.45, 1};

        // Test no movement (both 1)
        double result = lift.controlToDirection(1, 1);
        assertEquals(0, result, 0.01);

        // Test no movement (both 0)
        result = lift.controlToDirection(0, 0);
        assertEquals(0, result, 0.01);

        // Test up
        for (int i = 0; i < controllerValues.length; i++) {
            result = lift.controlToDirection(0, controllerValues[i]);
            assertEquals(expectedValues[i], result, 0.01);
        }

        // Test down
        for (int i = 0; i < controllerValues.length; i++) {
            result = lift.controlToDirection(controllerValues[i], 0);
            assertEquals(-expectedValues[i], result, 0.01);
        }
    }

    @Test
    public void languageToDirection_isCorrect() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();

        // Test Direction.UP
        double result = lift.languageToDirection(Lift.Direction.UP);
        assertEquals(1, result, 0.01);

        // Test Direction.DOWN
        result = lift.languageToDirection(Lift.Direction.DOWN);
        assertEquals(-1, result, 0.01);
    }

    @Test(expected = NullPointerException.class)
    public void languageToDirection_nullThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
        lift.command(null, 1, 1);
    }
}
