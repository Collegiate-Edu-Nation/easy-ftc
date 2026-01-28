// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.junit.Test;

public class TestDistance extends LinearOpMode {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    DistanceSensor mockedDistanceSensor = mock(DistanceSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(DistanceSensor.class, "distance"))
                .thenReturn(mockedDistanceSensor);
    }

    @Test
    public void runOpMode() {
        mockInit();
        BlocksOpModeCompanion.hardwareMap = mockedHardwareMap;
        org.edu_nation.easy_ftc.sensor.Distance distance;
        org.edu_nation.easy_ftc.sensor.Distance.Builder builder;
        builder = Distance.Builder();

        distance = Distance.build(Distance.reverse(builder));
        distance = Distance.build(Distance.name(builder, "distance"));
        distance = Distance.build(Distance.threshold(builder, 1));

        Distance.state(distance);
    }
}
