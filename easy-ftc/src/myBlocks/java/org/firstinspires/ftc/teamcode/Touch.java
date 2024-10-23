package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class Touch extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Returns touch sensor state (whether the sensor has been pressed or not)")
    public static boolean state() {
        org.edu_nation.easy_ftc.sensor.Touch touchSensor =
                new org.edu_nation.easy_ftc.sensor.Touch.Builder(hardwareMap).build();
        return touchSensor.state();
    }
}