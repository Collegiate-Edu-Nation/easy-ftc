// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.mechanism.Drive.Direction;

public class Drive extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls",
            parameterLabels = {"Direction", "Time", "Power"})
    public static void command(Direction direction, double time, double power) {
        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .build();
        drive.command(direction, time, power);
    }

    @ExportToBlocks(comment = "Enables teleoperated drive movement with gamepad (inherits layout)")
    public static void control() {
        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        drive.control();
    }

    @ExportToBlocks(comment = "Returns the FORWARD Direction")
    public static Direction FORWARD() {
        return Direction.FORWARD;
    }

    @ExportToBlocks(comment = "Returns the BACKWARD Direction")
    public static Direction BACKWARD() {
        return Direction.BACKWARD;
    }

    @ExportToBlocks(comment = "Returns the LEFT Direction")
    public static Direction LEFT() {
        return Direction.LEFT;
    }

    @ExportToBlocks(comment = "Returns the RIGHT Direction")
    public static Direction RIGHT() {
        return Direction.RIGHT;
    }

    @ExportToBlocks(comment = "Returns the ROTATE_LEFT Direction")
    public static Direction ROTATE_LEFT() {
        return Direction.ROTATE_LEFT;
    }

    @ExportToBlocks(comment = "Returns the ROTATE_RIGHT Direction")
    public static Direction ROTATE_RIGHT() {
        return Direction.ROTATE_RIGHT;
    }

    @ExportToBlocks(comment = "Returns the FORWARD_LEFT Direction")
    public static Direction FORWARD_LEFT() {
        return Direction.FORWARD_LEFT;
    }

    @ExportToBlocks(comment = "Returns the FORWARD_RIGHT Direction")
    public static Direction FORWARD_RIGHT() {
        return Direction.FORWARD_RIGHT;
    }

    @ExportToBlocks(comment = "Returns the BACKWARD_LEFT Direction")
    public static Direction BACKWARD_LEFT() {
        return Direction.BACKWARD_LEFT;
    }

    @ExportToBlocks(comment = "Returns the BACKWARD_RIGHT Direction")
    public static Direction BACKWARD_RIGHT() {
        return Direction.BACKWARD_RIGHT;
    }
}
