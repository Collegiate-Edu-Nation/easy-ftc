// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.junit.Test;

public class TestDrive extends LinearOpMode {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

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
    }

    @Test
    public void runOpMode() {
        mockInit();
        BlocksOpModeCompanion.opMode = mockedOpMode;
        BlocksOpModeCompanion.gamepad1 = mockedGamepad;
        BlocksOpModeCompanion.gamepad2 = mockedGamepad;
        BlocksOpModeCompanion.linearOpMode = mockedOpMode;
        BlocksOpModeCompanion.hardwareMap = mockedHardwareMap;
        org.edu_nation.easy_ftc.mechanism.Drive drive;
        org.edu_nation.easy_ftc.mechanism.Drive.Builder builder;
        builder = Drive.Builder();

        drive = Drive.build(Drive.reverse(builder));
        drive = Drive.build(Drive.gamepad(builder));
        drive = Drive.build(Drive.gamepad2(builder));
        drive = Drive.build(Drive.encoder(builder));
        drive = Drive.build(Drive.diameter(builder, 1));
        drive = Drive.build(Drive.gearing(builder, 1));
        drive = Drive.build(Drive.count(builder, 2));
        drive = Drive.build(Drive.names(builder, "driveLeft", "driveRight"));
        drive = Drive.build(Drive.behavior(builder, DcMotor.ZeroPowerBehavior.BRAKE));
        drive = Drive.build(Drive.deadzone(builder, 0.5));
        drive = Drive.build(Drive.logo(builder, LogoFacingDirection.UP));
        drive = Drive.build(Drive.usb(builder, UsbFacingDirection.FORWARD));
        drive = Drive.build(Drive.type(builder, Drive.DIFFERENTIAL()));
        drive = Drive.build(Drive.layout(builder, Drive.ARCADE()));
        drive = Drive.build(Drive.layout(builder, Drive.TANK()));
        drive = Drive.build(Drive.count(builder, 4));
        drive =
                Drive.build(
                        Drive.names(builder, "frontLeft", "frontRight", "backLeft", "backRight"));
        drive = Drive.build(Drive.reverse(builder, "frontLeft"));
        drive = Drive.build(Drive.reverse(builder, "frontLeft", "frontRight"));
        drive = Drive.build(Drive.reverse(builder, "frontLeft", "frontRight", "backLeft"));
        drive =
                Drive.build(
                        Drive.reverse(builder, "frontLeft", "frontRight", "backLeft", "backRight"));
        drive = Drive.build(Drive.type(builder, Drive.MECANUM()));
        drive = Drive.build(Drive.layout(builder, Drive.ROBOT()));
        // don't build since we don't mock the IMU
        builder = Drive.layout(builder, Drive.FIELD());

        Drive.command(drive, Drive.FORWARD(), 1, 1);
        Drive.command(drive, Drive.BACKWARD(), 1, 1);
        Drive.command(drive, Drive.LEFT(), 1, 1);
        Drive.command(drive, Drive.RIGHT(), 1, 1);
        Drive.command(drive, Drive.ROTATE_RIGHT(), 1, 1);
        Drive.command(drive, Drive.ROTATE_LEFT(), 1, 1);
        Drive.command(drive, Drive.FORWARD_LEFT(), 1, 1);
        Drive.command(drive, Drive.FORWARD_RIGHT(), 1, 1);
        Drive.command(drive, Drive.BACKWARD_LEFT(), 1, 1);
        Drive.command(drive, Drive.BACKWARD_RIGHT(), 1, 1);
        Drive.control(drive);
        Drive.control(drive, 1);
    }
}
