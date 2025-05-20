// SPDX-FileCopyrightText: 2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.junit.Test;

public class TestCommandSequence {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();
    Servo mockedClaw = mock(Servo.class);

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "driveLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "driveRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "driveLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "driveRight")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotor.class, "frontLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "frontRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "backLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "backRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "frontLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "frontRight")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "backLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "backRight")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);

        when(mockedHardwareMap.get(Servo.class, "clawLeft")).thenReturn(mockedClaw);
        when(mockedHardwareMap.get(Servo.class, "clawRight")).thenReturn(mockedClaw);
        when(mockedHardwareMap.get(Servo.class, "claw")).thenReturn(mockedClaw);
        when(mockedClaw.getPosition()).thenReturn(0.0);
    }

    @Test
    public void CommandSequence_multipleCommands() {
        mockInit();

        try {
            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            CommandSequence sequence =
                    new CommandSequence()
                            .command(drive, Drive.Direction.FORWARD, 2, 0.5)
                            .command(drive, Drive.Direction.BACKWARD, 2, 0.5);

            FieldUtils.writeField(mockedGamepad, "dpad_left", false);
            FieldUtils.writeField(mockedGamepad, "dpad_right", true);

            sequence.use();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void CommandSequence_multipleTypes() {
        mockInit();

        try {
            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            Claw claw =
                    new Claw.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            CommandSequence sequence =
                    new CommandSequence()
                            .command(drive, Drive.Direction.FORWARD, 2, 0.5)
                            .command(claw, Claw.Direction.OPEN);

            FieldUtils.writeField(mockedGamepad, "dpad_left", false);
            FieldUtils.writeField(mockedGamepad, "dpad_right", true);

            sequence.use();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void CommandSequence_angularType() {
        mockInit();

        try {

            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            CommandSequence sequence =
                    new CommandSequence()
                            .command(drive, Drive.Direction.ROTATE_LEFT, 2, 0.5, AngleUnit.DEGREES);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void CommandSequence_angularThrowsException() {
        when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
        CommandSequence sequence =
                new CommandSequence().command(arm, Arm.Direction.UP, 90, 0.5, AngleUnit.DEGREES);
    }

    @Test(expected = NullPointerException.class)
    public void CommandSequence_nullMotorThrowsException() {
        CommandSequence sequence =
                new CommandSequence().command(null, Drive.Direction.FORWARD, 2, 0.5);
    }

    @Test(expected = NullPointerException.class)
    public void CommandSequence_nullServoThrowsException() {
        CommandSequence sequence = new CommandSequence().command(null, Claw.Direction.OPEN, 2, 0.5);
    }
}
