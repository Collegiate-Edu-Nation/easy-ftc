// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.mechanism.Arm.Direction;

public class Arm extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void command(double power, Direction direction, double time) {
        org.edu_nation.easy_ftc.mechanism.Arm arm =
                new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                        .build();
        arm.command(power, direction, time);
    }

    @ExportToBlocks(
            comment = "Enables teleoperated arm movement with gamepad at a specified power (defaults to 0.5)",
            parameterLabels = {"Power"})
    public static void control(double power) {
        org.edu_nation.easy_ftc.mechanism.Arm arm =
                new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        arm.control(power);
    }

    @ExportToBlocks(
            comment = "Enables teleoperated arm movement with gamepad at a power of 0.5 (this is the default case)")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Arm arm =
                new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        arm.control();
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
