package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.arm.*;

public class Arm extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void move(double power, String direction, double time) {
        SoloArm arm = new SoloArm(linearOpMode, hardwareMap);
        arm.move(power, direction, time);
    }

    @ExportToBlocks(
            comment = "Enables teleoperated arm movement with gamepad at a specified power (defaults to 0.5)",
            parameterLabels = {"Power"})
    public static void tele(double power) {
        SoloArm arm = new SoloArm(linearOpMode, hardwareMap);
        arm.tele(power);
    }

    @ExportToBlocks(
            comment = "Enables teleoperated arm movement with gamepad at a power of 0.5 (this is the default case)")
    public static void tele() {
        SoloArm arm = new SoloArm(linearOpMode, hardwareMap, gamepad1);
        arm.tele();
    }
}
