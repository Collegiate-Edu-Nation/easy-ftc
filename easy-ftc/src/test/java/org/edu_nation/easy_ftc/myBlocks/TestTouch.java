// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.junit.Test;

public class TestTouch extends LinearOpMode {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    TouchSensor mockedTouchSensor = mock(TouchSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(TouchSensor.class, "touch")).thenReturn(mockedTouchSensor);
    }

    @Test
    public void runOpMode() {
        mockInit();
        BlocksOpModeCompanion.hardwareMap = mockedHardwareMap;
        org.edu_nation.easy_ftc.sensor.Touch touch;
        org.edu_nation.easy_ftc.sensor.Touch.Builder builder;
        builder = Touch.Builder();

        touch = Touch.build(Touch.reverse(builder));
        touch = Touch.build(Touch.name(builder, "touch"));

        Touch.state(touch);
    }
}
