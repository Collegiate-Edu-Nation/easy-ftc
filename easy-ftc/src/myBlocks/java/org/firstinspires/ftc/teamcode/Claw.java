// SPDX-FileCopyrightText: 2024-2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.edu_nation.easy_ftc.mechanism.Claw.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Claw extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Initiate an automated claw movement",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void command(Direction direction) {
        org.edu_nation.easy_ftc.mechanism.Claw claw =
                new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap)
                        .build();
        claw.command(direction);
    }

    @ExportToBlocks(comment = "Enable teleoperated claw movement with gamepad (b, a)")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Claw claw =
                new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1)
                        .build();
        claw.control();
    }

    @ExportToBlocks(comment = "Return the OPEN Direction")
    public static Direction OPEN() {
        return Direction.OPEN;
    }

    @ExportToBlocks(comment = "Return the CLOSE Direction")
    public static Direction CLOSE() {
        return Direction.CLOSE;
    }
}
