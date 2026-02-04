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

public class TestArm extends LinearOpMode {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "arm")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotor.class, "armLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "armRight")).thenReturn(mockedMotor);
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
        org.edu_nation.easy_ftc.mechanism.Arm arm;
        org.edu_nation.easy_ftc.mechanism.Arm arm2;
        org.edu_nation.easy_ftc.mechanism.Arm.Builder builder;
        org.edu_nation.easy_ftc.mechanism.Arm.Builder builder2;
        builder = Arm.Builder();
        builder2 = Arm.Builder();

        arm = Arm.build(Arm.reverse(builder));
        arm = Arm.build(Arm.reverse(builder, "arm"));
        arm = Arm.build(Arm.count(builder, 1));
        arm = Arm.build(Arm.gamepad(builder));
        arm = Arm.build(Arm.gamepad2(builder));
        arm = Arm.build(Arm.encoder(builder));
        arm = Arm.build(Arm.diameter(builder, 1));
        arm = Arm.build(Arm.length(builder, 1));
        arm = Arm.build(Arm.gearing(builder, 1));
        arm = Arm.build(Arm.count(builder, 1));
        arm = Arm.build(Arm.names(builder, "arm"));
        arm = Arm.build(Arm.behavior(builder, DcMotor.ZeroPowerBehavior.BRAKE));
        arm = Arm.build(Arm.up(builder, 1));
        arm = Arm.build(Arm.down(builder, -1));

        // 2-motor wrapper methods
        arm2 = Arm.build(Arm.count(builder2, 2));
        arm2 = Arm.build(Arm.reverse(builder2, "armLeft", "armRight"));
        arm2 = Arm.build(Arm.names(builder2, "armLeft", "armRight"));

        Arm.control(arm);
        Arm.control(arm, 1);
        Arm.command(arm, Arm.UP(), 1, 1);
        Arm.command(arm, Arm.DOWN(), 1, 1);
    }
}
