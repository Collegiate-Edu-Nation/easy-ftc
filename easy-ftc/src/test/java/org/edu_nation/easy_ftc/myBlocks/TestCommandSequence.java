// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

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
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
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
        when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "driveLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "driveRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "intake")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "lift")).thenReturn(mockedMotor);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);

        when(mockedHardwareMap.get(Servo.class, "claw")).thenReturn(mockedClaw);
        when(mockedHardwareMap.get(Servo.class, "trigger")).thenReturn(mockedClaw);
        when(mockedClaw.getPosition()).thenReturn(0.0);
    }

    @Test
    public void runOpMode() {
        mockInit();
        BlocksOpModeCompanion.opMode = mockedOpMode;
        BlocksOpModeCompanion.gamepad1 = mockedGamepad;
        BlocksOpModeCompanion.linearOpMode = mockedOpMode;
        BlocksOpModeCompanion.hardwareMap = mockedHardwareMap;
        org.edu_nation.easy_ftc.mechanism.CommandSequence sequence;
        org.edu_nation.easy_ftc.mechanism.Arm arm;
        org.edu_nation.easy_ftc.mechanism.Drive drive;
        org.edu_nation.easy_ftc.mechanism.Intake intake;
        org.edu_nation.easy_ftc.mechanism.Lift lift;
        org.edu_nation.easy_ftc.mechanism.Claw claw;
        org.edu_nation.easy_ftc.mechanism.Trigger trigger;
        arm = Arm.build(Arm.gamepad(Arm.Builder()));
        drive = Drive.build(Drive.gamepad(Drive.Builder()));
        intake = Intake.build(Intake.gamepad(Intake.Builder()));
        lift = Lift.build(Lift.gamepad(Lift.Builder()));
        claw = Claw.build(Claw.gamepad(Claw.Builder()));
        trigger = Trigger.build(Trigger.gamepad(Trigger.Builder()));

        sequence = CommandSequence.CommandSequence();
        sequence = CommandSequence.command(sequence, arm, Arm.UP(), 1, 1);
        sequence = CommandSequence.command(sequence, drive, Drive.ROTATE_LEFT(), 1, 1);
        sequence = CommandSequence.command(sequence, intake, Intake.IN(), 1, 1);
        sequence = CommandSequence.command(sequence, lift, Lift.UP(), 1, 1);
        sequence = CommandSequence.command(sequence, claw, Claw.OPEN());
        sequence = CommandSequence.command(sequence, trigger, Trigger.OPEN());

        try {
            FieldUtils.writeField(mockedGamepad, "dpad_left", false);
            FieldUtils.writeField(mockedGamepad, "dpad_right", true);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        CommandSequence.control(sequence);

        // after control() call since we don't mock the IMU
        sequence =
                CommandSequence.command(
                        sequence, drive, Drive.ROTATE_LEFT(), 1, 1, AngleUnit.DEGREES);
    }
}
