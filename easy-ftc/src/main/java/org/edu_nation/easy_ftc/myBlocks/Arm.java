// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.List;
import org.edu_nation.easy_ftc.mechanism.Arm.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class Arm extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct an Arm object using the builder design pattern")
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder Builder() {
        return new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap);
    }

    @ExportToBlocks(
            comment = "Whether to reverse devices",
            parameterLabels = {"Arm.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Reverse the specified device",
            parameterLabels = {"Arm.Builder", "deviceName"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, String deviceName) {
        return builder.reverse(deviceName);
    }

    @ExportToBlocks(
            comment = "Reverse the specified devices",
            parameterLabels = {"Arm.Builder", "deviceNames"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, List<String> deviceNames) {
        return builder.reverse(deviceNames.toArray(new String[0]));
    }

    @ExportToBlocks(
            comment = "Pass gamepad for teleop control",
            parameterLabels = {"Arm.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder gamepad(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder) {
        return builder.gamepad(gamepad1);
    }

    @ExportToBlocks(
            comment = "Whether to enable encoders (time-based)",
            parameterLabels = {"Arm.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder encoder(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder) {
        return builder.encoder();
    }

    @ExportToBlocks(
            comment = "Specify the diameter for encoder control (distance-based)",
            parameterLabels = {"Arm.Builder", "Diameter"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder diameter(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double diameter) {
        return builder.diameter(diameter);
    }

    @ExportToBlocks(
            comment = "Specify the length for encoder control (distance-based)",
            parameterLabels = {"Arm.Builder", "Length"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder length(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double length) {
        return builder.length(length);
    }

    @ExportToBlocks(
            comment =
                    "Specify the gearing of the motors (increases accuracy of distance-based movement)",
            parameterLabels = {"Arm.Builder", "Gearing"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder gearing(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double gearing) {
        return builder.gearing(gearing);
    }

    @ExportToBlocks(
            comment = "Specify the number of motors",
            parameterLabels = {"Arm.Builder", "Count"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder count(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, int count) {
        return builder.count(count);
    }

    @ExportToBlocks(
            comment = "Change the names of the hardware devices",
            parameterLabels = {"Arm.Builder", "Names"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder names(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, List<String> names) {
        return builder.names(names.toArray(new String[0]));
    }

    @ExportToBlocks(
            comment = "Specify the zero-power behavior of the motors",
            parameterLabels = {"Arm.Builder", "Behavior"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder behavior(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder,
            DcMotor.ZeroPowerBehavior behavior) {
        return builder.behavior(behavior);
    }

    @ExportToBlocks(
            comment = "Specify the positional limit for Direction UP",
            parameterLabels = {"Arm.Builder", "Up"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder up(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double up) {
        return builder.up(up);
    }

    @ExportToBlocks(
            comment = "Specify the positional limit for Direction DOWN",
            parameterLabels = {"Arm.Builder", "Down"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder down(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double down) {
        return builder.down(down);
    }

    @ExportToBlocks(
            comment = "Build the arm",
            parameterLabels = {"Arm.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Arm build(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Initiate an automated arm movement",
            parameterLabels = {"Arm", "Direction", "Measurement", "Power"})
    public static void command(
            org.edu_nation.easy_ftc.mechanism.Arm arm,
            Direction direction,
            double measurement,
            double power) {
        arm.command(direction, measurement, power);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated arm movement with gamepad (lb, rb) at the specified power",
            parameterLabels = {"Arm", "Power"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Arm arm, double power) {
        arm.control(power);
    }

    @ExportToBlocks(
            comment = "Enable teleoperated arm movement with gamepad (lb, rb) at a power of 0.5")
    public static void control(org.edu_nation.easy_ftc.mechanism.Arm arm) {
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
