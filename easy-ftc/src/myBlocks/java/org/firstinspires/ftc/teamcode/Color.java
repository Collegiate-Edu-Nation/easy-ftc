// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.sensor.Color.RGB;

public class Color extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Returns color sensor state (color of detection, one of: RED, GREEN, BLUE)")
    public static RGB state() {
        org.edu_nation.easy_ftc.sensor.Color colorSensor =
                new org.edu_nation.easy_ftc.sensor.Color.Builder(hardwareMap).build();
        return colorSensor.state();
    }

    @ExportToBlocks(comment = "Returns the RED RGB Color")
    public static RGB RED() {
        return RGB.RED;
    }

    @ExportToBlocks(comment = "Returns the GREEN RGB Color")
    public static RGB GREEN() {
        return RGB.GREEN;
    }

    @ExportToBlocks(comment = "Returns the BLUE RGB Color")
    public static RGB BLUE() {
        return RGB.BLUE;
    }
}
