// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class Distance extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct a Distance sensor object using the builder design pattern")
    public static org.edu_nation.easy_ftc.sensor.Distance.Builder Builder() {
        return new org.edu_nation.easy_ftc.sensor.Distance.Builder(hardwareMap);
    }

    @ExportToBlocks(
            comment = "Reverse the sensor's state",
            parameterLabels = {"Distance.Builder"})
    public static org.edu_nation.easy_ftc.sensor.Distance.Builder reverse(
            org.edu_nation.easy_ftc.sensor.Distance.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Change the name of the hardware device",
            parameterLabels = {"Distance.Builder", "Name"})
    public static org.edu_nation.easy_ftc.sensor.Distance.Builder name(
            org.edu_nation.easy_ftc.sensor.Distance.Builder builder, String name) {
        return builder.name(name);
    }

    @ExportToBlocks(
            comment = "Specify the calibration value",
            parameterLabels = {"Distance.Builder", "Threshold"})
    public static org.edu_nation.easy_ftc.sensor.Distance.Builder threshold(
            org.edu_nation.easy_ftc.sensor.Distance.Builder builder, double threshold) {
        return builder.threshold(threshold);
    }

    @ExportToBlocks(
            comment = "Build the sensor",
            parameterLabels = {"Distance.Builder"})
    public static org.edu_nation.easy_ftc.sensor.Distance build(
            org.edu_nation.easy_ftc.sensor.Distance.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment =
                    "Return distance sensor state (whether an object is within the distance cutoff)",
            parameterLabels = {"Distance"})
    public static boolean state(org.edu_nation.easy_ftc.sensor.Distance distance) {
        return distance.state();
    }
}
