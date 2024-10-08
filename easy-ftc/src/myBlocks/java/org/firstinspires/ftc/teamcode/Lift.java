package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.lift.*;

public class Lift extends BlocksOpModeCompanion {
    @ExportToBlocks (
        comment = "Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls",
        parameterLabels = {"Power", "Direction", "Time"}
    )
    public static void move(double power, String direction, double time) {
        SoloLift lift = new SoloLift(linearOpMode, hardwareMap);
        lift.move(power, direction, time);
    }
    
    @ExportToBlocks (
        comment = "Enables teleoperated lift movement with gamepad"
    )
    public static void tele() {
        SoloLift lift = new SoloLift(linearOpMode, hardwareMap, gamepad1);
        lift.tele();
    }
}
