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

public class TestLift extends LinearOpMode {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "liftLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "liftRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "lift")).thenReturn(mockedMotorEx);
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
        org.edu_nation.easy_ftc.mechanism.Lift lift;
        org.edu_nation.easy_ftc.mechanism.Lift lift2;
        org.edu_nation.easy_ftc.mechanism.Lift.Builder builder;
        org.edu_nation.easy_ftc.mechanism.Lift.Builder builder2;
        builder = Lift.Builder();
        builder2 = Lift.Builder();

        lift = Lift.build(Lift.reverse(builder));
        lift = Lift.build(Lift.reverse(builder, "lift"));
        lift = Lift.build(Lift.gamepad(builder));
        lift = Lift.build(Lift.gamepad2(builder));
        lift = Lift.build(Lift.encoder(builder));
        lift = Lift.build(Lift.diameter(builder, 1));
        lift = Lift.build(Lift.gearing(builder, 1));
        lift = Lift.build(Lift.deadzone(builder, 0.5));
        lift = Lift.build(Lift.count(builder, 1));
        lift = Lift.build(Lift.names(builder, "lift"));
        lift = Lift.build(Lift.behavior(builder, DcMotor.ZeroPowerBehavior.BRAKE));
        lift = Lift.build(Lift.up(builder, 1));
        lift = Lift.build(Lift.down(builder, -1));

        // 2-motor wrapper methods
        lift2 = Lift.build(Lift.count(builder2, 2));
        lift2 = Lift.build(Lift.reverse(builder2, "liftLeft", "liftRight"));
        lift2 = Lift.build(Lift.names(builder2, "liftLeft", "liftRight"));

        Lift.control(lift);
        Lift.control(lift, 1);
        Lift.command(lift, Lift.UP(), 1, 1);
        Lift.command(lift, Lift.DOWN(), 1, 1);
    }
}
