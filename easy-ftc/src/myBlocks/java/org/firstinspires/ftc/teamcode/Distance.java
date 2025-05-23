// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Distance extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment =
                    "Return distance sensor state (whether an object is within the distance cutoff)")
    public static boolean state() {
        org.edu_nation.easy_ftc.sensor.Distance distance =
                new org.edu_nation.easy_ftc.sensor.Distance.Builder(hardwareMap).build();
        return distance.state();
    }
}
