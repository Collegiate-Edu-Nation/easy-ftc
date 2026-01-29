// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.myBlocks;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.edu_nation.easy_ftc.mechanism.Drive.Direction;
import org.edu_nation.easy_ftc.mechanism.Drive.Layout;
import org.edu_nation.easy_ftc.mechanism.Drive.Type;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportClassToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@ExportClassToBlocks
public class Drive extends BlocksOpModeCompanion {
    @ExportToBlocks(comment = "Construct an Drive object using the builder design pattern")
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder Builder() {
        return new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap);
    }

    @ExportToBlocks(
            comment = "Whether to reverse devices",
            parameterLabels = {"Drive.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder) {
        return builder.reverse();
    }

    @ExportToBlocks(
            comment = "Reverse the specified device",
            parameterLabels = {"Drive.Builder", "deviceName"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, String deviceName) {
        return builder.reverse(deviceName);
    }

    @ExportToBlocks(
            comment = "Reverse the specified devices",
            parameterLabels = {"Drive.Builder", "deviceNames"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder reverse(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, String[] deviceNames) {
        return builder.reverse(deviceNames);
    }

    @ExportToBlocks(
            comment = "Pass gamepad for teleop control",
            parameterLabels = {"Drive.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder gamepad(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder) {
        return builder.gamepad(gamepad1);
    }

    @ExportToBlocks(
            comment = "Whether to enable encoders (time-based)",
            parameterLabels = {"Drive.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder encoder(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder) {
        return builder.encoder();
    }

    @ExportToBlocks(
            comment = "Specify the diameter for encoder control (distance-based)",
            parameterLabels = {"Drive.Builder", "Diameter"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder diameter(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, double diameter) {
        return builder.diameter(diameter);
    }

    @ExportToBlocks(
            comment =
                    "Specify the gearing of the motors (increases accuracy of distance-based movement)",
            parameterLabels = {"Drive.Builder", "Gearing"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder gearing(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, double gearing) {
        return builder.gearing(gearing);
    }

    @ExportToBlocks(
            comment = "Specify the joystick deadzone",
            parameterLabels = {"Drive.Builder", "Deadzone"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder deadzone(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, double deadzone) {
        return builder.deadzone(deadzone);
    }

    @ExportToBlocks(
            comment = "Specify the logo direction of the IMU/gyro",
            parameterLabels = {"Drive.Builder", "Logo"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder logo(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, LogoFacingDirection logo) {
        return builder.logo(logo);
    }

    @ExportToBlocks(
            comment = "Specify the USB port direction of the IMU/gyro",
            parameterLabels = {"Drive.Builder", "USB"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder usb(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, UsbFacingDirection usb) {
        return builder.usb(usb);
    }

    @ExportToBlocks(
            comment = "Specify the number of motors",
            parameterLabels = {"Drive.Builder", "Count"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder count(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, int count) {
        return builder.count(count);
    }

    @ExportToBlocks(
            comment = "Change the names of the hardware devices",
            parameterLabels = {"Drive.Builder", "Names"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder names(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, String[] names) {
        return builder.names(names);
    }

    @ExportToBlocks(
            comment = "Specify the zero-power behavior of the motors",
            parameterLabels = {"Drive.Builder", "Behavior"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder behavior(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder,
            DcMotor.ZeroPowerBehavior behavior) {
        return builder.behavior(behavior);
    }

    @ExportToBlocks(
            comment = "Specify the drivetrain type",
            parameterLabels = {"Drive.Builder", "Type"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder type(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, Type type) {
        return builder.type(type);
    }

    @ExportToBlocks(
            comment = "Specify the drivetrain layout",
            parameterLabels = {"Drive.Builder", "Layout"})
    public static org.edu_nation.easy_ftc.mechanism.Drive.Builder layout(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder, Layout layout) {
        return builder.layout(layout);
    }

    @ExportToBlocks(
            comment = "Build the Drive",
            parameterLabels = {"Drive.Builder"})
    public static org.edu_nation.easy_ftc.mechanism.Drive build(
            org.edu_nation.easy_ftc.mechanism.Drive.Builder builder) {
        return builder.build();
    }

    @ExportToBlocks(
            comment = "Initiate an automated drivetrain movement",
            parameterLabels = {"Drive", "Direction", "Measurement", "Power"})
    public static void command(
            org.edu_nation.easy_ftc.mechanism.Drive drive,
            Direction direction,
            double measurement,
            double power) {
        drive.command(direction, measurement, power);
    }

    @ExportToBlocks(
            comment = "Initiate an automated drivetrain turn using the IMU's gyro",
            parameterLabels = {"Drive", "Direction", "Measurement", "Power", "Unit"})
    public static void command(
            org.edu_nation.easy_ftc.mechanism.Drive drive,
            Direction direction,
            double measurement,
            double power,
            AngleUnit unit) {
        drive.command(direction, measurement, power, unit);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated drivetrain movement with gamepad (joysticks), scaling by multiplier",
            parameterLabels = {"Drive", "Multiplier"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Drive drive, double multiplier) {
        drive.control(multiplier);
    }

    @ExportToBlocks(
            comment =
                    "Enable teleoperated drivetrain movement with gamepad (joysticks), with multiplier = 1.0",
            parameterLabels = {"Drive"})
    public static void control(org.edu_nation.easy_ftc.mechanism.Drive drive) {
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

    @ExportToBlocks(comment = "Return the DIFFERENTIAL Type")
    public static Type DIFFERENTIAL() {
        return Type.DIFFERENTIAL;
    }

    @ExportToBlocks(comment = "Return the MECANUM Type")
    public static Type MECANUM() {
        return Type.MECANUM;
    }

    @ExportToBlocks(comment = "Return the ARCADE Layout")
    public static Layout ARCADE() {
        return Layout.ARCADE;
    }

    @ExportToBlocks(comment = "Return the TANK Layout")
    public static Layout TANK() {
        return Layout.TANK;
    }

    @ExportToBlocks(comment = "Return the FIELD Layout")
    public static Layout FIELD() {
        return Layout.FIELD;
    }

    @ExportToBlocks(comment = "Return the ROBOT Layout")
    public static Layout ROBOT() {
        return Layout.ROBOT;
    }
}
