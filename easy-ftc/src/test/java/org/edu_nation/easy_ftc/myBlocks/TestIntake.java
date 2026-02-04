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
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.junit.Test;

public class TestIntake extends LinearOpMode {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "intake")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "intake")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotor.class, "intakeLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "intakeRight")).thenReturn(mockedMotor);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void runOpMode() {
        mockInit();
        BlocksOpModeCompanion.opMode = mockedOpMode;
        BlocksOpModeCompanion.gamepad1 = mockedGamepad;
        BlocksOpModeCompanion.gamepad2 = mockedGamepad;
        BlocksOpModeCompanion.linearOpMode = mockedOpMode;
        BlocksOpModeCompanion.hardwareMap = mockedHardwareMap;
        org.edu_nation.easy_ftc.mechanism.Intake intake;
        org.edu_nation.easy_ftc.mechanism.Intake intake2;
        org.edu_nation.easy_ftc.mechanism.Intake.Builder builder;
        org.edu_nation.easy_ftc.mechanism.Intake.Builder builder2;
        builder = Intake.Builder();
        builder2 = Intake.Builder();

        intake = Intake.build(Intake.reverse(builder));
        intake = Intake.build(Intake.reverse(builder, "intake"));
        intake = Intake.build(Intake.gamepad(builder));
        intake = Intake.build(Intake.gamepad2(builder));
        intake = Intake.build(Intake.encoder(builder));
        intake = Intake.build(Intake.diameter(builder, 1));
        intake = Intake.build(Intake.gearing(builder, 1));
        intake = Intake.build(Intake.count(builder, 1));
        intake = Intake.build(Intake.names(builder, "intake"));
        intake = Intake.build(Intake.behavior(builder, DcMotor.ZeroPowerBehavior.BRAKE));
        intake = Intake.build(Intake.in(builder, 1));
        intake = Intake.build(Intake.out(builder, -1));

        // 2-motor wrapper methods
        intake2 = Intake.build(Intake.count(builder2, 2));
        intake2 = Intake.build(Intake.reverse(builder2, "intakeLeft", "intakeRight"));
        intake2 = Intake.build(Intake.names(builder2, "intakeLeft", "intakeRight"));

        Intake.control(intake);
        Intake.control(intake, 1);
        Intake.command(intake, Intake.IN(), 1, 1);
        Intake.command(intake, Intake.OUT(), 1, 1);
    }
}
