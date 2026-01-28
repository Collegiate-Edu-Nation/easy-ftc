// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.junit.Test;

public class TestClaw extends LinearOpMode {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    Servo mockedClaw = mock(Servo.class);

    private void mockInit() {
        when(mockedHardwareMap.get(Servo.class, "claw")).thenReturn(mockedClaw);
        when(mockedClaw.getPosition()).thenReturn(0.0);
    }

    @Test
    public void runOpMode() {
        mockInit();
        BlocksOpModeCompanion.opMode = mockedOpMode;
        BlocksOpModeCompanion.gamepad1 = mockedGamepad;
        BlocksOpModeCompanion.linearOpMode = mockedOpMode;
        BlocksOpModeCompanion.hardwareMap = mockedHardwareMap;
        org.edu_nation.easy_ftc.mechanism.Claw claw;
        org.edu_nation.easy_ftc.mechanism.Claw.Builder builder;
        builder = Claw.Builder();

        claw = Claw.build(Claw.reverse(builder));
        claw = Claw.build(Claw.reverse(builder, "claw"));
        claw = Claw.build(Claw.reverse(builder, new String[] {"claw"}));
        claw = Claw.build(Claw.gamepad(builder));
        claw = Claw.build(Claw.smooth(builder));
        claw = Claw.build(Claw.increment(builder, 1));
        claw = Claw.build(Claw.incrementDelay(builder, 1));
        claw = Claw.build(Claw.delay(builder, 1));
        claw = Claw.build(Claw.count(builder, 1));
        claw = Claw.build(Claw.names(builder, new String[] {"claw"}));
        claw = Claw.build(Claw.open(builder, 1));
        claw = Claw.build(Claw.close(builder, 0));

        Claw.control(claw);
        Claw.command(claw, Claw.OPEN());
        Claw.command(claw, Claw.CLOSE());
    }
}
