// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Drive extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void command(double power,
            org.edu_nation.easy_ftc.mechanism.Drive.Direction direction, double time) {
        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .build();
        drive.command(power, direction, time);
    }

    @ExportToBlocks(comment = "Enables teleoperated drive movement with gamepad (inherits layout)")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        drive.control();
    }
}
