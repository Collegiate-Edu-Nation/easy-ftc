// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.mechanism.Claw.Direction;

public class Claw extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual servo positions based on direction specified in runOpMode() calls",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void command(Direction direction) {
        org.edu_nation.easy_ftc.mechanism.Claw claw =
                new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap)
                        .build();
        claw.command(direction);
    }

    @ExportToBlocks(comment = "Enables teleoperated claw movement with gamepad")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Claw claw =
                new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        claw.control();
    }

    @ExportToBlocks(comment = "Returns the OPEN Direction")
    public static Direction OPEN() {
        return Direction.OPEN;
    }

    @ExportToBlocks(comment = "Returns the CLOSE Direction")
    public static Direction CLOSE() {
        return Direction.CLOSE;
    }
}
