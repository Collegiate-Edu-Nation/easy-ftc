// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.edu_nation.easy_ftc.mechanism.Intake.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Intake extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Initiate an automated intake movement",
            parameterLabels = {"Direction", "Time", "Power"})
    public static void command(Direction direction, double time, double power) {
        org.edu_nation.easy_ftc.mechanism.Intake intake =
                new org.edu_nation.easy_ftc.mechanism.Intake.Builder(linearOpMode, hardwareMap)
                        .build();
        intake.command(direction, time, power);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated intake movement with gamepad (dpadUp, dpadDown) at the specified power",
            parameterLabels = {"Power"})
    public static void control(double power) {
        org.edu_nation.easy_ftc.mechanism.Intake intake =
                new org.edu_nation.easy_ftc.mechanism.Intake.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1)
                        .build();
        intake.control(power);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated intake movement with gamepad (dpadUp, dpadDown) at a power of 0.5")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Intake intake =
                new org.edu_nation.easy_ftc.mechanism.Intake.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1)
                        .build();
        intake.control();
    }

    @ExportToBlocks(comment = "Return the IN Direction")
    public static Direction IN() {
        return Direction.IN;
    }

    @ExportToBlocks(comment = "Return the OUT Direction")
    public static Direction OUT() {
        return Direction.OUT;
    }
}
