// SPDX-FileCopyrightText: 2024-2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.edu_nation.easy_ftc.mechanism.Arm.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Arm extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Initiate an automated arm movement",
            parameterLabels = {"Direction", "Time", "Power"})
    public static void command(Direction direction, double time, double power) {
        org.edu_nation.easy_ftc.mechanism.Arm arm =
                new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                        .build();
        arm.command(direction, time, power);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated arm movement with gamepad (lb, rb) at the specified power",
            parameterLabels = {"Power"})
    public static void control(double power) {
        org.edu_nation.easy_ftc.mechanism.Arm arm =
                new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1)
                        .build();
        arm.control(power);
    }

    @ExportToBlocks(
            comment = "Enable teleoperated arm movement with gamepad (lb, rb) at a power of 0.5")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Arm arm =
                new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1)
                        .build();
        arm.control();
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
