package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.claw.*;

public class Claw extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual servo positions based on direction specified in runOpMode() calls",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void move(String direction) {
        DualClaw claw = new DualClaw(linearOpMode, hardwareMap);
        claw.move(direction);
    }

    @ExportToBlocks(comment = "Enables teleoperated claw movement with gamepad")
    public static void tele() {
        DualClaw claw = new DualClaw(linearOpMode, hardwareMap, gamepad1);
        claw.tele();
    }
}
