// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import org.edu_nation.easy_ftc.mechanism.Arm;
import org.edu_nation.easy_ftc.mechanism.Claw;
import org.edu_nation.easy_ftc.mechanism.Drive;
import org.edu_nation.easy_ftc.mechanism.Lift;
import org.edu_nation.easy_ftc.mechanism.Trigger;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@ExportClassToBlocks
public class CommandSequence extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Leverage the constructed sequence with gamepad (dpadLeft, dpadRight)")
    public static void control(org.edu_nation.easy_ftc.mechanism.CommandSequence sequence) {
        sequence.control();
    }

    @ExportToBlocks(comment = "Construct a blank sequence of commands")
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence CommandSequence() {
        return new org.edu_nation.easy_ftc.mechanism.CommandSequence();
    }

    @ExportToBlocks(
            comment = "Add a Arm command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Arm", "Direction", "Measurement", "Power"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Arm mechanism,
            Arm.Direction direction,
            double measurement,
            double power) {
        return sequence.command(mechanism, direction, measurement, power);
    }

    @ExportToBlocks(
            comment = "Add a Drive command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Drive", "Direction", "Measurement", "Power"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Drive mechanism,
            Drive.Direction direction,
            double measurement,
            double power) {
        return sequence.command(mechanism, direction, measurement, power);
    }

    @ExportToBlocks(
            comment = "Add a Lift command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Lift", "Direction", "Measurement", "Power"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Lift mechanism,
            Lift.Direction direction,
            double measurement,
            double power) {
        return sequence.command(mechanism, direction, measurement, power);
    }

    @ExportToBlocks(
            comment = "Add a Trigger command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Trigger", "Direction"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Trigger mechanism,
            Trigger.Direction direction) {
        return sequence.command(mechanism, direction);
    }

    @ExportToBlocks(
            comment = "Add a Claw command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Claw", "Direction"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Claw mechanism,
            Claw.Direction direction) {
        return sequence.command(mechanism, direction);
    }

    @ExportToBlocks(
            comment = "Add an angular Drive command to the sequence via method chaining",
            parameterLabels = {
                "CommandSequence",
                "Drive",
                "Direction",
                "Measurement",
                "Power",
                "Unit"
            })
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Drive mechanism,
            Drive.Direction direction,
            double measurement,
            double power,
            AngleUnit unit) {
        return sequence.command(mechanism, direction, measurement, power, unit);
    }
}
