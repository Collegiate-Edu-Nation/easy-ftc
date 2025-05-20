// SPDX-FileCopyrightText: 2024-2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.edu_nation.easy_ftc.mechanism.Trigger.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Trigger extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Initiate an automated trigger movement",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void command(Direction direction) {
        org.edu_nation.easy_ftc.mechanism.Trigger trigger =
                new org.edu_nation.easy_ftc.mechanism.Trigger.Builder(linearOpMode, hardwareMap)
                        .build();
        trigger.command(direction);
    }

    @ExportToBlocks(comment = "Enable teleoperated trigger movement with gamepad (y, x)")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Trigger trigger =
                new org.edu_nation.easy_ftc.mechanism.Trigger.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1)
                        .build();
        trigger.control();
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
