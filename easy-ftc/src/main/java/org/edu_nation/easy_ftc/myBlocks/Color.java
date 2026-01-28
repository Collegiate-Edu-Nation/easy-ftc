// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import org.edu_nation.easy_ftc.sensor.Color.RGB;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class Color extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct a Color sensor object using the builder design pattern")
    public static org.edu_nation.easy_ftc.sensor.Color.Builder Builder() {
        return new org.edu_nation.easy_ftc.sensor.Color.Builder(hardwareMap);
    }

    @ExportToBlocks(
            comment = "Reverse the sensor's state",
            parameterLabels = {"Color.Builder"})
    public static org.edu_nation.easy_ftc.sensor.Color.Builder reverse(
            org.edu_nation.easy_ftc.sensor.Color.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Change the name of the hardware device",
            parameterLabels = {"Color.Builder", "Name"})
    public static org.edu_nation.easy_ftc.sensor.Color.Builder name(
            org.edu_nation.easy_ftc.sensor.Color.Builder builder, String name) {
        return builder.name(name);
    }

    @ExportToBlocks(
            comment = "Specify the calibration value",
            parameterLabels = {"Color.Builder", "Threshold"})
    public static org.edu_nation.easy_ftc.sensor.Color.Builder threshold(
            org.edu_nation.easy_ftc.sensor.Color.Builder builder, double threshold) {
        return builder.threshold(threshold);
    }

    @ExportToBlocks(
            comment = "Specify the rgbOffsets (array of 3 integers, -255-255)",
            parameterLabels = {"Color.Builder", "RGB Offsets"})
    public static org.edu_nation.easy_ftc.sensor.Color.Builder rgbOffsets(
            org.edu_nation.easy_ftc.sensor.Color.Builder builder, int[] rgbOffsets) {
        return builder.rgbOffsets(rgbOffsets);
    }

    @ExportToBlocks(
            comment = "Build the sensor",
            parameterLabels = {"Color.Builder"})
    public static org.edu_nation.easy_ftc.sensor.Color build(
            org.edu_nation.easy_ftc.sensor.Color.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Return color sensor state (color of detection, one of RGB or null)",
            parameterLabels = {"Color"})
    public static RGB state(org.edu_nation.easy_ftc.sensor.Color color) {
        return color.state();
    }

    @ExportToBlocks(comment = "Return the RED RGB Color")
    public static RGB RED() {
        return RGB.RED;
    }

    @ExportToBlocks(comment = "Return the GREEN RGB Color")
    public static RGB GREEN() {
        return RGB.GREEN;
    }

    @ExportToBlocks(comment = "Return the BLUE RGB Color")
    public static RGB BLUE() {
        return RGB.BLUE;
    }
}
