// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.sensor.Color.RGB;

public class Color extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Return color sensor state (color of detection, one of RGB or null)")
    public static RGB state() {
        org.edu_nation.easy_ftc.sensor.Color color =
                new org.edu_nation.easy_ftc.sensor.Color.Builder(hardwareMap).build();
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
