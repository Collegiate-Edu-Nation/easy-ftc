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

public class TestTrigger extends LinearOpMode {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    Servo mockedTrigger = mock(Servo.class);

    private void mockInit() {
        when(mockedHardwareMap.get(Servo.class, "trigger")).thenReturn(mockedTrigger);
        when(mockedHardwareMap.get(Servo.class, "triggerLeft")).thenReturn(mockedTrigger);
        when(mockedHardwareMap.get(Servo.class, "triggerRight")).thenReturn(mockedTrigger);
        when(mockedTrigger.getPosition()).thenReturn(0.0);
    }

    @Test
    public void runOpMode() {
        mockInit();
        BlocksOpModeCompanion.opMode = mockedOpMode;
        BlocksOpModeCompanion.gamepad1 = mockedGamepad;
        BlocksOpModeCompanion.gamepad2 = mockedGamepad;
        BlocksOpModeCompanion.linearOpMode = mockedOpMode;
        BlocksOpModeCompanion.hardwareMap = mockedHardwareMap;
        org.edu_nation.easy_ftc.mechanism.Trigger trigger;
        org.edu_nation.easy_ftc.mechanism.Trigger trigger2;
        org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder;
        org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder2;
        builder = Trigger.Builder();
        builder2 = Trigger.Builder();

        trigger = Trigger.build(Trigger.reverse(builder));
        trigger = Trigger.build(Trigger.reverse(builder, "trigger"));
        trigger = Trigger.build(Trigger.gamepad(builder));
        trigger = Trigger.build(Trigger.gamepad2(builder));
        trigger = Trigger.build(Trigger.smooth(builder));
        trigger = Trigger.build(Trigger.increment(builder, 1));
        trigger = Trigger.build(Trigger.incrementDelay(builder, 1));
        trigger = Trigger.build(Trigger.delay(builder, 1));
        trigger = Trigger.build(Trigger.count(builder, 1));
        trigger = Trigger.build(Trigger.names(builder, "trigger"));
        trigger = Trigger.build(Trigger.open(builder, 1));
        trigger = Trigger.build(Trigger.close(builder, 0));

        // 2-motor methods
        trigger2 = Trigger.build(Trigger.count(builder2, 2));
        trigger2 = Trigger.build(Trigger.reverse(builder2, "triggerLeft", "triggerRight"));
        trigger2 = Trigger.build(Trigger.names(builder2, "triggerLeft", "triggerRight"));

        Trigger.control(trigger);
        Trigger.command(trigger, Trigger.OPEN());
        Trigger.command(trigger, Trigger.CLOSE());
    }
}
