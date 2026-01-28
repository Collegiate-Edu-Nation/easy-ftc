// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import org.edu_nation.easy_ftc.mechanism.Trigger.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class Trigger extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct a Trigger object using the builder design pattern")
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder Builder() {
        return new org.edu_nation.easy_ftc.mechanism.Trigger.Builder(linearOpMode, hardwareMap);
    }

    @ExportToBlocks(
            comment = "Whether to reverse devices",
            parameterLabels = {"Trigger.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Reverse the specified device",
            parameterLabels = {"Trigger.Builder", "deviceName"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, String deviceName) {
        return builder.reverse(deviceName);
    }

    @ExportToBlocks(
            comment = "Reverse the specified devices",
            parameterLabels = {"Trigger.Builder", "deviceNames"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, String[] deviceNames) {
        return builder.reverse(deviceNames);
    }

    @ExportToBlocks(
            comment = "Pass gamepad for teleop control",
            parameterLabels = {"Trigger.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder gamepad(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder) {
        return builder.gamepad(gamepad1);
    }

    @ExportToBlocks(
            comment = "Whether to enable smooth-servo control",
            parameterLabels = {"Trigger.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder smooth(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder) {
        return builder.smooth();
    }

    @ExportToBlocks(
            comment = "Specify the increment to move by for smooth-servo control",
            parameterLabels = {"Trigger.Builder", "Increment"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder increment(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, double increment) {
        return builder.increment(increment);
    }

    @ExportToBlocks(
            comment = "Specify the increment to move by for smooth-servo control",
            parameterLabels = {"Trigger.Builder", "Increment Delay"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder incrementDelay(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, double incrementDelay) {
        return builder.incrementDelay(incrementDelay);
    }

    @ExportToBlocks(
            comment = "Specify the delay for normal servo control",
            parameterLabels = {"Trigger.Builder", "Delay"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder delay(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, double delay) {
        return builder.delay(delay);
    }

    @ExportToBlocks(
            comment = "Specify the number of servos",
            parameterLabels = {"Trigger.Builder", "Count"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder count(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, int count) {
        return builder.count(count);
    }

    @ExportToBlocks(
            comment = "Change the names of the hardware devices",
            parameterLabels = {"Trigger.Builder", "Names"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder names(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, String[] names) {
        return builder.names(names);
    }

    @ExportToBlocks(
            comment = "Specify the position for Direction OPEN",
            parameterLabels = {"Trigger.Builder", "Open"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder open(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, double open) {
        return builder.open(open);
    }

    @ExportToBlocks(
            comment = "Specify the position for Direction CLOSE",
            parameterLabels = {"Trigger.Builder", "Close"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger.Builder close(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder, double close) {
        return builder.close(close);
    }

    @ExportToBlocks(
            comment = "Build the trigger",
            parameterLabels = {"Trigger.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Trigger build(
            org.edu_nation.easy_ftc.mechanism.Trigger.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Initiate an automated trigger movement",
            parameterLabels = {"Trigger", "Direction"})
    public static void command(
            org.edu_nation.easy_ftc.mechanism.Trigger trigger, Direction direction) {
        trigger.command(direction);
    }

    @ExportToBlocks(
            comment = "Enable teleoperated trigger movement with gamepad (y, x)",
            parameterLabels = {"Trigger"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Trigger trigger) {
        trigger.control();
    }

    @ExportToBlocks(comment = "Return the OPEN Direction")
    public static Direction OPEN() {
        return Direction.OPEN;
    }

    @ExportToBlocks(comment = "Return the CLOSE Direction")
    public static Direction CLOSE() {
        return Direction.CLOSE;
    }
}
