// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.junit.Test;

public class TestColor extends LinearOpMode {
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    ColorSensor mockedColorSensor = mock(ColorSensor.class);

    private void mockInit() {
        when(mockedHardwareMap.get(ColorSensor.class, "color")).thenReturn(mockedColorSensor);
    }

    private void mockRGB(int red, int green, int blue) {
        when(mockedColorSensor.red()).thenReturn(red);
        when(mockedColorSensor.green()).thenReturn(green);
        when(mockedColorSensor.blue()).thenReturn(blue);
    }

    @Test
    public void runOpMode() {
        mockInit();
        BlocksOpModeCompanion.hardwareMap = mockedHardwareMap;
        org.edu_nation.easy_ftc.sensor.Color color;
        org.edu_nation.easy_ftc.sensor.Color.Builder builder;
        org.edu_nation.easy_ftc.sensor.Color.RGB rgb;
        builder = Color.Builder();

        color = Color.build(Color.reverse(builder));
        color = Color.build(Color.name(builder, "color"));
        color = Color.build(Color.threshold(builder, 1));
        color = Color.build(Color.rgbOffsets(builder, new int[] {1, 1, 1}));

        Color.state(color);
        rgb = Color.RED();
        rgb = Color.GREEN();
        rgb = Color.BLUE();
    }
}
