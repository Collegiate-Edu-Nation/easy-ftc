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

public class TestIntake {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "intakeLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "intakeRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "intakeLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "intakeRight")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotor.class, "intake")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "intake")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void Intake_initializes() {
        mockInit();

        try {
            new Intake.Builder(mockedOpMode, mockedHardwareMap)
                    .behavior(DcMotor.ZeroPowerBehavior.BRAKE)
                    .build();
            new Intake.Builder(mockedOpMode, mockedHardwareMap).reverse().build();
            new Intake.Builder(mockedOpMode, mockedHardwareMap).reverse("intake").build();
            new Intake.Builder(mockedOpMode, mockedHardwareMap)
                    .reverse(new String[] {"intake"})
                    .build();
            new Intake.Builder(mockedOpMode, mockedHardwareMap)
                    .encoder()
                    .diameter(2)
                    .gearing(19.2)
                    .build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void opModeThrowsException() {
        mockInit();

        new Intake.Builder(null, mockedHardwareMap).build();
    }

    @Test(expected = NullPointerException.class)
    public void hardwareMapThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, null).build();
    }

    @Test(expected = NullPointerException.class)
    public void deviceNameThrowsException() {
        mockInit();

        String nullString = null;
        new Intake.Builder(mockedOpMode, mockedHardwareMap).reverse(nullString).build();
    }

    @Test(expected = NullPointerException.class)
    public void deviceNamesThrowsException() {
        mockInit();

        String[] nullString = null;
        new Intake.Builder(mockedOpMode, mockedHardwareMap).reverse(nullString).build();
    }

    @Test(expected = NullPointerException.class)
    public void gamepadThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).gamepad(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void diameterNoEncoderThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).diameter(1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void diameterThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).diameter(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).length(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void gearingThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).gearing(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void deadzoneThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).deadzone(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void countThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).count(0).build();
    }

    @Test(expected = NullPointerException.class)
    public void namesThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).names(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void namesLengthThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .names(new String[] {"abc"})
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void behaviorThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).behavior(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void inThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).in(-1).build();
    }

    @Test(expected = IllegalStateException.class)
    public void outThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).out(2).build();
    }

    @Test
    public void controlSolo_isCalled() {
        mockInit();

        try {
            Intake intake =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            Intake intakeEnc =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .gamepad(mockedGamepad)
                            .build();
            Intake intakePos =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .in(1.0)
                            .out(-1.0)
                            .gamepad(mockedGamepad)
                            .build();
            Intake intakeDia =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .length(5)
                            .in(1.0)
                            .out(-1.0)
                            .gamepad(mockedGamepad)
                            .build();

            intake.control();
            intake.control(0.5);
            intakeEnc.control();
            intakeEnc.control(0.5);

            FieldUtils.writeField(mockedGamepad, "left_bumper", true);
            intakePos.control();
            intakePos.control(0.5);
            intakeDia.control();
            intakeDia.control(0.5);

            FieldUtils.writeField(mockedGamepad, "left_bumper", false);
            FieldUtils.writeField(mockedGamepad, "right_bumper", true);
            intakePos.control();
            intakePos.control(0.5);
            intakeDia.control();
            intakeDia.control(0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void controlDual_isCalled() {
        mockInit();

        try {
            Intake intake =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .gamepad(mockedGamepad)
                            .build();
            Intake intakeEnc =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .gamepad(mockedGamepad)
                            .build();
            Intake intakePos =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .in(1.0)
                            .out(-1.0)
                            .gamepad(mockedGamepad)
                            .build();
            Intake intakeDia =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .length(5)
                            .in(1.0)
                            .out(-1.0)
                            .gamepad(mockedGamepad)
                            .build();

            intake.control();
            intake.control(0.5);
            intakeEnc.control();
            intakeEnc.control(0.5);

            FieldUtils.writeField(mockedGamepad, "left_bumper", true);
            intakePos.control();
            intakePos.control(0.5);
            intakeDia.control();
            intakeDia.control(0.5);

            FieldUtils.writeField(mockedGamepad, "left_bumper", false);
            FieldUtils.writeField(mockedGamepad, "right_bumper", true);
            intakePos.control();
            intakePos.control(0.5);
            intakeDia.control();
            intakeDia.control(0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandSolo_isCalled() {
        mockInit();

        try {
            Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();
            Intake intakeEnc =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            Intake intakePos =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4).build();
            Intake intakeDia =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .length(5)
                            .in(1.0)
                            .out(-1.0)
                            .build();
            Intake intakeLim =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .in(1.0)
                            .out(0.0)
                            .build();

            intake.command(Intake.Direction.IN, 1, 0.5);
            intakeEnc.command(Intake.Direction.IN, 1, 0.5);
            intakePos.command(Intake.Direction.IN, 12, 0.5);

            when(mockedMotorEx.isBusy()).thenReturn(true, false);
            when(mockedOpMode.opModeIsActive()).thenReturn(true, false);
            intakeDia.command(Intake.Direction.IN, 12, 0.5);
            intakeDia.command(Intake.Direction.OUT, 12, 0.5);
            intakeLim.command(Intake.Direction.IN, 1, 0.5);
            intakeLim.command(Intake.Direction.OUT, 1, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandDual_isCalled() {
        mockInit();

        try {
            Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
            Intake intakeEnc =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder().build();
            Intake intakePos =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .length(4)
                            .build();
            Intake intakeDia =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .length(5)
                            .in(1.0)
                            .out(-1.0)
                            .build();
            Intake intakeLim =
                    new Intake.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .encoder()
                            .in(1.0)
                            .out(0.0)
                            .build();

            intake.command(Intake.Direction.IN, 1, 0.5);
            intakeEnc.command(Intake.Direction.IN, 1, 0.5);
            intakePos.command(Intake.Direction.IN, 12, 0.5);
            intakeDia.command(Intake.Direction.IN, 12, 0.5);
            intakeDia.command(Intake.Direction.OUT, 12, 0.5);
            intakeLim.command(Intake.Direction.IN, 1, 0.5);
            intakeLim.command(Intake.Direction.OUT, 1, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLargePower_controlThrowsException() {
        mockInit();

        Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();
        intake.control(1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativePower_controlThrowsException() {
        mockInit();

        Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();
        intake.control(-0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLargePower_commandThrowsException() {
        mockInit();

        Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();
        intake.command(Intake.Direction.IN, 1, 1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativePower_commandThrowsException() {
        mockInit();

        Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();
        intake.command(Intake.Direction.IN, 1, -0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidMeasurement_commandThrowsException() {
        mockInit();

        Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();
        intake.command(Intake.Direction.IN, -1, 0.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap)
                .reverse("abc")
                .gamepad(mockedGamepad)
                .build();
        new Intake.Builder(mockedOpMode, mockedHardwareMap)
                .reverse("abc")
                .encoder()
                .gamepad(mockedGamepad)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseDual_ThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("abc").build();
        new Intake.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .reverse("abc")
                .encoder()
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingSolo_ThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4).gearing(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDual_ThrowsException() {
        mockInit();

        new Intake.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .encoder()
                .length(4)
                .gearing(-1)
                .build();
    }

    @Test
    public void controlToDirection_isCorrect() {
        mockInit();

        Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();

        // Test no movement (both true)
        double result = intake.controlToDirection(true, true);
        assertEquals(0, result, 0.01);

        // Test no movement (both false)
        result = intake.controlToDirection(false, false);
        assertEquals(0, result, 0.01);

        // Test in
        result = intake.controlToDirection(false, true);
        assertEquals(1.0, result, 0.01);

        // Test out
        result = intake.controlToDirection(true, false);
        assertEquals(-1.0, result, 0.01);
    }

    @Test
    public void languageToDirection_isCorrect() {
        mockInit();

        Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();

        // Test Intake.Direction.IN
        double result = intake.languageToDirection(Intake.Direction.IN);
        assertEquals(1, result, 0.01);

        // Test Intake.Direction.OUT
        result = intake.languageToDirection(Intake.Direction.OUT);
        assertEquals(-1, result, 0.01);
    }

    @Test(expected = NullPointerException.class)
    public void languageToDirection_nullThrowsException() {
        mockInit();

        Intake intake = new Intake.Builder(mockedOpMode, mockedHardwareMap).build();
        intake.command(null, 1, 1);
    }
}
