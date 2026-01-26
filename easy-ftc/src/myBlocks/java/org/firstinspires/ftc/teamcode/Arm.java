// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.edu_nation.easy_ftc.mechanism.Arm.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Arm extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Create an Arm Builder object")
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder Builder() {
        return new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, String deviceName) {
        return builder.reverse(deviceName);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, String[] deviceNames) {
        return builder.reverse(deviceNames);
    }

    @ExportToBlocks(
            comment = "Add a gamepad to the arm to enable teleoperated control",
            parameterLabels = {"Arm"})
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder gamepad(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder) {
        return builder.gamepad(gamepad1);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder encoder(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder) {
        return builder.encoder();
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder diameter(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double diameter) {
        return builder.diameter(diameter);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder length(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double length) {
        return builder.length(length);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder gearing(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double gearing) {
        return builder.gearing(gearing);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder count(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, int count) {
        return builder.count(count);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder names(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, String[] names) {
        return builder.names(names);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder behavior(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder,
            DcMotor.ZeroPowerBehavior behavior) {
        return builder.behavior(behavior);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder up(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double up) {
        return builder.up(up);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm.Builder down(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder, double down) {
        return builder.down(down);
    }

    @ExportToBlocks
    public static org.edu_nation.easy_ftc.mechanism.Arm build(
            org.edu_nation.easy_ftc.mechanism.Arm.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Initiate an automated arm movement",
            parameterLabels = {"Arm", "Direction", "Time", "Power"})
    public static void command(
            org.edu_nation.easy_ftc.mechanism.Arm arm,
            Direction direction,
            double time,
            double power) {
        arm.command(direction, time, power);
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
