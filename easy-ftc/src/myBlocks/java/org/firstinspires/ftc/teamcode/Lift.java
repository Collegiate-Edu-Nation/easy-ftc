// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Lift extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void command(double power, String direction, double time) {
        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .build();
        lift.command(power, direction, time);
    }

    @ExportToBlocks(comment = "Enables teleoperated lift movement with gamepad")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        lift.control();
    }
}
