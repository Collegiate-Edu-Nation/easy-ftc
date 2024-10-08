package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.drive.*;

public class Drive extends BlocksOpModeCompanion {
    @ExportToBlocks (
        comment = "Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls",
        parameterLabels = {"Power", "Direction", "Time"}
    )
    public static void move(double power, String direction, double time) {
        Mecanum mecanum = new Mecanum(linearOpMode, hardwareMap);
        mecanum.move(power, direction, time);
    }
    
    @ExportToBlocks (
        comment = "Enables teleoperated mecanum movement with gamepad (inherits layout)"
    )
    public static void tele() {
        Mecanum mecanum = new Mecanum(linearOpMode, hardwareMap, gamepad1);
        mecanum.tele();
    }
}
