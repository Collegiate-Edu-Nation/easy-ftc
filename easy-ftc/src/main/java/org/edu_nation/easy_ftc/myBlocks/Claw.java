// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import org.edu_nation.easy_ftc.mechanism.Claw.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class Claw extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct a Claw object using the builder design pattern")
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder Builder() {
        return new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap);
    }

    @ExportToBlocks(
            comment = "Whether to reverse devices",
            parameterLabels = {"Claw.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Reverse the specified device",
            parameterLabels = {"Claw.Builder", "deviceName"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, String deviceName) {
        return builder.reverse(deviceName);
    }

    @ExportToBlocks(
            comment = "Reverse the specified devices",
            parameterLabels = {"Claw.Builder", "deviceName", "deviceName"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder,
            String deviceName1,
            String deviceName2) {
        return builder.reverse(new String[] {deviceName1, deviceName2});
    }

    @ExportToBlocks(
            comment = "Pass gamepad1 for teleop control",
            parameterLabels = {"Claw.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder gamepad(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder) {
        return builder.gamepad(gamepad1);
    }

    @ExportToBlocks(
            comment = "Pass gamepad2 for teleop control",
            parameterLabels = {"Claw.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder gamepad2(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder) {
        return builder.gamepad(gamepad2);
    }

    @ExportToBlocks(
            comment = "Whether to enable smooth-servo control",
            parameterLabels = {"Claw.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder smooth(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder) {
        return builder.smooth();
    }

    @ExportToBlocks(
            comment = "Specify the increment to move by for smooth-servo control",
            parameterLabels = {"Claw.Builder", "Increment"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder increment(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, double increment) {
        return builder.increment(increment);
    }

    @ExportToBlocks(
            comment = "Specify the increment to move by for smooth-servo control",
            parameterLabels = {"Claw.Builder", "Increment Delay"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder incrementDelay(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, double incrementDelay) {
        return builder.incrementDelay(incrementDelay);
    }

    @ExportToBlocks(
            comment = "Specify the delay for normal servo control",
            parameterLabels = {"Claw.Builder", "Delay"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder delay(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, double delay) {
        return builder.delay(delay);
    }

    @ExportToBlocks(
            comment = "Specify the number of servos",
            parameterLabels = {"Claw.Builder", "Count"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder count(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, int count) {
        return builder.count(count);
    }

    @ExportToBlocks(
            comment = "Change the name of the hardware device",
            parameterLabels = {"Claw.Builder", "Name"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder names(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, String name) {
        return builder.names(new String[] {name});
    }

    @ExportToBlocks(
            comment = "Change the names of the hardware devices",
            parameterLabels = {"Claw.Builder", "Name", "Name"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder names(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, String name1, String name2) {
        return builder.names(new String[] {name1, name2});
    }

    @ExportToBlocks(
            comment = "Specify the position for Direction OPEN",
            parameterLabels = {"Claw.Builder", "Open"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder open(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, double open) {
        return builder.open(open);
    }

    @ExportToBlocks(
            comment = "Specify the position for Direction CLOSE",
            parameterLabels = {"Claw.Builder", "Close"})
    public static org.edu_nation.easy_ftc.mechanism.Claw.Builder close(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder, double close) {
        return builder.close(close);
    }

    @ExportToBlocks(
            comment = "Build the claw",
            parameterLabels = {"Claw.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Claw build(
            org.edu_nation.easy_ftc.mechanism.Claw.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Initiate an automated claw movement",
            parameterLabels = {"Claw", "Direction"})
    public static void command(org.edu_nation.easy_ftc.mechanism.Claw claw, Direction direction) {
        claw.command(direction);
    }

    @ExportToBlocks(
            comment = "Enable teleoperated claw movement with gamepad (b, a)",
            parameterLabels = {"Claw"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Claw claw) {
        claw.control();
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
