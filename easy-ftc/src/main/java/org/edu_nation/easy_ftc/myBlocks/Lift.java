// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.edu_nation.easy_ftc.mechanism.Lift.Direction;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

@ExportClassToBlocks
public class Lift extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct a Lift object using the builder design pattern")
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder Builder() {
        return new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap);
    }

    @ExportToBlocks(
            comment = "Whether to reverse devices",
            parameterLabels = {"Lift.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Reverse the specified device",
            parameterLabels = {"Lift.Builder", "deviceName"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, String deviceName) {
        return builder.reverse(deviceName);
    }

    @ExportToBlocks(
            comment = "Reverse the specified devices",
            parameterLabels = {"Lift.Builder", "deviceName", "deviceName"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder,
            String deviceName1,
            String deviceName2) {
        return builder.reverse(new String[] {deviceName1, deviceName2});
    }

    @ExportToBlocks(
            comment = "Pass gamepad1 for teleop control",
            parameterLabels = {"Lift.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder gamepad(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder) {
        return builder.gamepad(gamepad1);
    }

    @ExportToBlocks(
            comment = "Pass gamepad2 for teleop control",
            parameterLabels = {"Lift.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder gamepad2(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder) {
        return builder.gamepad(gamepad2);
    }

    @ExportToBlocks(
            comment = "Whether to enable encoders (time-based)",
            parameterLabels = {"Lift.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder encoder(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder) {
        return builder.encoder();
    }

    @ExportToBlocks(
            comment = "Specify the diameter for encoder control (distance-based)",
            parameterLabels = {"Lift.Builder", "Diameter"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder diameter(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, double diameter) {
        return builder.diameter(diameter);
    }

    @ExportToBlocks(
            comment =
                    "Specify the gearing of the motors (increases accuracy of distance-based movement)",
            parameterLabels = {"Lift.Builder", "Gearing"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder gearing(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, double gearing) {
        return builder.gearing(gearing);
    }

    @ExportToBlocks(
            comment = "Specify the joystick deadzone",
            parameterLabels = {"Lift.Builder", "Deadzone"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder deadzone(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, double deadzone) {
        return builder.deadzone(deadzone);
    }

    @ExportToBlocks(
            comment = "Specify the number of motors",
            parameterLabels = {"Lift.Builder", "Count"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder count(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, int count) {
        return builder.count(count);
    }

    @ExportToBlocks(
            comment = "Change the name of the hardware device",
            parameterLabels = {"Lift.Builder", "Name"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder names(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, String name) {
        return builder.names(new String[] {name});
    }

    @ExportToBlocks(
            comment = "Change the names of the hardware devices",
            parameterLabels = {"Lift.Builder", "Name", "Name"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder names(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, String name1, String name2) {
        return builder.names(new String[] {name1, name2});
    }

    @ExportToBlocks(
            comment = "Specify the zero-power behavior of the motors",
            parameterLabels = {"Lift.Builder", "Behavior"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder behavior(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder,
            DcMotor.ZeroPowerBehavior behavior) {
        return builder.behavior(behavior);
    }

    @ExportToBlocks(
            comment = "Specify the positional limit for Direction UP",
            parameterLabels = {"Lift.Builder", "Up"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder up(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, double up) {
        return builder.up(up);
    }

    @ExportToBlocks(
            comment = "Specify the positional limit for Direction DOWN",
            parameterLabels = {"Lift.Builder", "Down"})
    public static org.edu_nation.easy_ftc.mechanism.Lift.Builder down(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder, double down) {
        return builder.down(down);
    }

    @ExportToBlocks(
            comment = "Build the lift",
            parameterLabels = {"Lift.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Lift build(
            org.edu_nation.easy_ftc.mechanism.Lift.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Initiate an automated lift movement",
            parameterLabels = {"Lift", "Direction", "Time", "Power"})
    public static void command(
            org.edu_nation.easy_ftc.mechanism.Lift lift,
            Direction direction,
            double time,
            double power) {
        lift.command(direction, time, power);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated lift movement with gamepad (lt, rt), scaling by multiplier",
            parameterLabels = {"Lift", "Multiplier"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Lift lift, double multiplier) {
        lift.control(multiplier);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated lift movement with gamepad (lt, rt), with multiplier = 1.0",
            parameterLabels = {"Lift"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Lift lift) {
        lift.control();
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
