// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.mechanism.Lift.Direction;

public class Lift extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Initiate an automated lift movement",
            parameterLabels = {"Direction", "Time", "Power"})
    public static void command(Direction direction, double time, double power) {
        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .build();
        lift.command(direction, time, power);
    }

    @ExportToBlocks(
            comment = "Enable teleoperated lift movement with gamepad (lt, rt), scaling by multiplier",
            parameterLabels = {"Multiplier"})
    public static void control(double multiplier) {
        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        lift.control(multiplier);
    }

    @ExportToBlocks(
            comment = "Enable teleoperated lift movement with gamepad (lt, rt), with multiplier = 1.0")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        lift.control();
    }

    @ExportToBlocks(comment = "Return the UP Direction")
    public static Direction UP() {
        return Direction.UP;
    }

    @ExportToBlocks(comment = "Return the DOWN Direction")
    public static Direction DOWN() {
        return Direction.DOWN;
    }
}
