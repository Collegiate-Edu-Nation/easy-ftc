// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Apriltag extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Returns AprilTag sensor state (whether an object has been detected or not)")
    public static boolean state() {
        org.edu_nation.easy_ftc.sensor.Apriltag apriltagSensor =
                new org.edu_nation.easy_ftc.sensor.Apriltag.Builder(hardwareMap).build();
        return apriltagSensor.state();
    }
}
