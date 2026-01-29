// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.edu_nation.easy_ftc.mechanism.Intake.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class Intake extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct an Intake object using the builder design pattern")
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder Builder() {
        return new org.edu_nation.easy_ftc.mechanism.Intake.Builder(linearOpMode, hardwareMap);
    }

    @ExportToBlocks(
            comment = "Whether to reverse devices",
            parameterLabels = {"Intake.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Reverse the specified device",
            parameterLabels = {"Intake.Builder", "deviceName"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder, String deviceName) {
        return builder.reverse(deviceName);
    }

    @ExportToBlocks(
            comment = "Reverse the specified devices",
            parameterLabels = {"Intake.Builder", "deviceNames"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder, String[] deviceNames) {
        return builder.reverse(deviceNames);
    }

    @ExportToBlocks(
            comment = "Pass gamepad for teleop control",
            parameterLabels = {"Intake.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder gamepad(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder) {
        return builder.gamepad(gamepad1);
    }

    @ExportToBlocks(
            comment = "Whether to enable encoders (time-based)",
            parameterLabels = {"Intake.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder encoder(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder) {
        return builder.encoder();
    }

    @ExportToBlocks(
            comment = "Specify the diameter for encoder control (distance-based)",
            parameterLabels = {"Intake.Builder", "Diameter"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder diameter(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder, double diameter) {
        return builder.diameter(diameter);
    }

    @ExportToBlocks(
            comment =
                    "Specify the gearing of the motors (increases accuracy of distance-based movement)",
            parameterLabels = {"Intake.Builder", "Gearing"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder gearing(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder, double gearing) {
        return builder.gearing(gearing);
    }

    @ExportToBlocks(
            comment = "Specify the number of motors",
            parameterLabels = {"Intake.Builder", "Count"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder count(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder, int count) {
        return builder.count(count);
    }

    @ExportToBlocks(
            comment = "Change the names of the hardware devices",
            parameterLabels = {"Intake.Builder", "Names"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder names(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder, String[] names) {
        return builder.names(names);
    }

    @ExportToBlocks(
            comment = "Specify the zero-power behavior of the motors",
            parameterLabels = {"Intake.Builder", "Behavior"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder behavior(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder,
            DcMotor.ZeroPowerBehavior behavior) {
        return builder.behavior(behavior);
    }

    @ExportToBlocks(
            comment = "Specify the positional limit for Direction IN",
            parameterLabels = {"Intake.Builder", "In"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder in(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder, double in) {
        return builder.in(in);
    }

    @ExportToBlocks(
            comment = "Specify the positional limit for Direction OUT",
            parameterLabels = {"Intake.Builder", "Out"})
    public static org.edu_nation.easy_ftc.mechanism.Intake.Builder out(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder, double out) {
        return builder.out(out);
    }

    @ExportToBlocks(
            comment = "Build the intake",
            parameterLabels = {"Intake.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Intake build(
            org.edu_nation.easy_ftc.mechanism.Intake.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Initiate an automated intake movement",
            parameterLabels = {"Intake", "Direction", "Measurement", "Power"})
    public static void command(
            org.edu_nation.easy_ftc.mechanism.Intake intake,
            Direction direction,
            double measurement,
            double power) {
        intake.command(direction, measurement, power);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated intake movement with gamepad (dpadUp, dpadDown) at the specified power",
            parameterLabels = {"Intake", "Power"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Intake intake, double power) {
        intake.control(power);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated intake movement with gamepad (dpadUp, dpadDown) at a power of 0.5",
            parameterLabels = {"Intake"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Intake intake) {
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
