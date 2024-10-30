package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Color extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Returns color sensor state (color of detection, one of: red, green, blue, or empty)")
    public static boolean state() {
        org.edu_nation.easy_ftc.sensor.Color colorSensor =
                new org.edu_nation.easy_ftc.sensor.Color.Builder(hardwareMap).build();
        return colorSensor.state();
    }
}
