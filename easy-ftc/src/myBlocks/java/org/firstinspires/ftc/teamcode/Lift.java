// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.mechanism.Lift.Direction;

public class Lift extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls",
            parameterLabels = {"Direction", "Time", "Power"})
    public static void command(Direction direction, double time, double power) {
        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .build();
        lift.command(direction, time, power);
    }

    @ExportToBlocks(comment = "Enables teleoperated lift movement with gamepad")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        lift.control();
    }

    @ExportToBlocks(comment = "Returns the UP Direction")
    public static Direction UP() {
        return Direction.UP;
    }

    @ExportToBlocks(comment = "Returns the DOWN Direction")
    public static Direction DOWN() {
        return Direction.DOWN;
    }
}
