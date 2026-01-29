// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class Touch extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct a Touch sensor object using the builder design pattern")
    public static org.edu_nation.easy_ftc.sensor.Touch.Builder Builder() {
        return new org.edu_nation.easy_ftc.sensor.Touch.Builder(hardwareMap);
    }

    @ExportToBlocks(
            comment = "Reverse the sensor's state",
            parameterLabels = {"Touch.Builder"})
    public static org.edu_nation.easy_ftc.sensor.Touch.Builder reverse(
            org.edu_nation.easy_ftc.sensor.Touch.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Change the name of the hardware device",
            parameterLabels = {"Touch.Builder", "Name"})
    public static org.edu_nation.easy_ftc.sensor.Touch.Builder name(
            org.edu_nation.easy_ftc.sensor.Touch.Builder builder, String name) {
        return builder.name(name);
    }

    @ExportToBlocks(
            comment = "Build the sensor",
            parameterLabels = {"Touch.Builder"})
    public static org.edu_nation.easy_ftc.sensor.Touch build(
            org.edu_nation.easy_ftc.sensor.Touch.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Return touch sensor state (whether the sensor has been pressed or not)",
            parameterLabels = {"Touch"})
    public static boolean state(org.edu_nation.easy_ftc.sensor.Touch touch) {
        return touch.state();
    }
}
