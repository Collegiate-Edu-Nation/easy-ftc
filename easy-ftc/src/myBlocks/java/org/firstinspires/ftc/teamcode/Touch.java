// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Touch extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Return touch sensor state (whether the sensor has been pressed or not)")
    public static boolean state() {
        org.edu_nation.easy_ftc.sensor.Touch touch =
                new org.edu_nation.easy_ftc.sensor.Touch.Builder(hardwareMap).build();
        return touch.state();
    }
}
