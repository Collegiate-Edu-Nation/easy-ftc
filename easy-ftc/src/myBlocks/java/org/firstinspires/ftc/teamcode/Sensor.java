package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.edu_nation.easy_ftc.sensor.*;

public class Sensor extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Returns touch sensor state (whether the sensor has been pressed or not)")
    public static boolean state() {
        Touch touchSensor = new Touch(hardwareMap);
        return touchSensor.state();
    }
}
