// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Color extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Returns color sensor state (color of detection, one of: RED, GREEN, BLUE)")
    public static org.edu_nation.easy_ftc.sensor.Color.RGB state() {
        org.edu_nation.easy_ftc.sensor.Color colorSensor =
                new org.edu_nation.easy_ftc.sensor.Color.Builder(hardwareMap).build();
        return colorSensor.state();
    }
}
