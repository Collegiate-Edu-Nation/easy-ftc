// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.edu_nation.easy_ftc.mechanism.Drive.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Drive extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Initiate an automated drivetrain movement",
            parameterLabels = {"Direction", "Time", "Power"})
    public static void command(Direction direction, double time, double power) {
        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .build();
        drive.command(direction, time, power);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated drivetrain movement with gamepad (joysticks), scaling by multiplier",
            parameterLabels = {"Multiplier"})
    public static void control(double multiplier) {
        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1)
                        .build();
        drive.control(multiplier);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated drivetrain movement with gamepad (joysticks), with multiplier = 1.0")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1)
                        .build();
        drive.control();
    }

    @ExportToBlocks(comment = "Return the FORWARD Direction")
    public static Direction FORWARD() {
        return Direction.FORWARD;
    }

    @ExportToBlocks(comment = "Return the BACKWARD Direction")
    public static Direction BACKWARD() {
        return Direction.BACKWARD;
    }

    @ExportToBlocks(comment = "Return the LEFT Direction")
    public static Direction LEFT() {
        return Direction.LEFT;
    }

    @ExportToBlocks(comment = "Return the RIGHT Direction")
    public static Direction RIGHT() {
        return Direction.RIGHT;
    }

    @ExportToBlocks(comment = "Return the ROTATE_LEFT Direction")
    public static Direction ROTATE_LEFT() {
        return Direction.ROTATE_LEFT;
    }

    @ExportToBlocks(comment = "Return the ROTATE_RIGHT Direction")
    public static Direction ROTATE_RIGHT() {
        return Direction.ROTATE_RIGHT;
    }

    @ExportToBlocks(comment = "Return the FORWARD_LEFT Direction")
    public static Direction FORWARD_LEFT() {
        return Direction.FORWARD_LEFT;
    }

    @ExportToBlocks(comment = "Return the FORWARD_RIGHT Direction")
    public static Direction FORWARD_RIGHT() {
        return Direction.FORWARD_RIGHT;
    }

    @ExportToBlocks(comment = "Return the BACKWARD_LEFT Direction")
    public static Direction BACKWARD_LEFT() {
        return Direction.BACKWARD_LEFT;
    }

    @ExportToBlocks(comment = "Return the BACKWARD_RIGHT Direction")
    public static Direction BACKWARD_RIGHT() {
        return Direction.BACKWARD_RIGHT;
    }
}
