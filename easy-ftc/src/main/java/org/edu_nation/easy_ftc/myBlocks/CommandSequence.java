// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import org.edu_nation.easy_ftc.mechanism.MotorMechanism;
import org.edu_nation.easy_ftc.mechanism.ServoMechanism;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@ExportClassToBlocks
public class CommandSequence<E> extends BlocksOpModeCompanion {
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
            comment = "Add a MotorMechanism command to the sequence via method chaining",
            parameterLabels = {
                "CommandSequence",
                "Motor Mechanism",
                "Direction",
                "Measurement",
                "Power"
            })
    public static <E> org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            MotorMechanism<E> mechanism,
            E direction,
            double measurement,
            double power) {
        return sequence.command(mechanism, direction, measurement, power);
    }

    @ExportToBlocks(
            comment = "Add a ServoMechanism command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Servo Mechanism", "Direction"})
    public static <E> org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            ServoMechanism<E> mechanism,
            E direction) {
        return sequence.command(mechanism, direction);
    }

    @ExportToBlocks(
            comment = "Add an angular Drive command to the sequence via method chaining",
            parameterLabels = {
                "CommandSequence",
                "Motor Mechanism",
                "Direction",
                "Measurement",
                "Power",
                "Unit"
            })
    public static <E> org.edu_nation.easy_ftc.mechanism.CommandSequence command(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            MotorMechanism<E> mechanism,
            E direction,
            double measurement,
            double power,
            AngleUnit unit) {
        return sequence.command(mechanism, direction, measurement, power, unit);
    }
}
