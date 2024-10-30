package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Claw extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Intermediate function that assigns individual servo positions based on direction specified in runOpMode() calls",
            parameterLabels = {"Power", "Direction", "Time"})
    public static void move(String direction) {
        org.edu_nation.easy_ftc.mechanism.Claw claw =
                new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap)
                        .build();
        claw.move(direction);
    }

    @ExportToBlocks(comment = "Enables teleoperated claw movement with gamepad")
    public static void tele() {
        org.edu_nation.easy_ftc.mechanism.Claw claw =
                new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).build();
        claw.tele();
    }
}
