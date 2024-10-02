package org.cen.easy_ftc.sensor;

import java.lang.Math;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Implements a color sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Double calibrationValue (0-255, cutoff for what constitutes a significant color reading)
 *        <p>
 * @Methods {@link #state()}
 */
public class Color extends Sensor<String> {
    private ColorSensor sensor;
    private int[] rgbOffsets = {10, -25, 0};

    /**
     * Constructor
     * 
     * @defaults calibrationValue = 85.0
     */
    public Color(HardwareMap hardwareMap) {
        super(hardwareMap, 85.0);
    }

    /**
     * Constructor
     */
    public Color(HardwareMap hardwareMap, double calibrationValue) {
        super(hardwareMap, calibrationValue);
    }

    /**
     * Initializes color sensor
     */
    @Override
    protected void hardwareInit() {
        sensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }

    /**
     * Returns color sensor state (color of detection, one of: "red", "green", "blue", or "")
     */
    @Override
    public String state() {
        int[] rgbRaw = {sensor.red(), sensor.green(), sensor.blue()};
        int[] rgbNormalized = {0, 0, 0};

        // normalize color readings by applying offsets
        for (int i = 0; i < rgbRaw.length; i++) {
            rgbNormalized[i] = rgbRaw[i] + rgbOffsets[i];
        }

        // set color to corresponding maximum of normalized rgb values
        int max = Math.max(Math.max(rgbNormalized[0], rgbNormalized[1]),
                Math.max(rgbNormalized[1], rgbNormalized[2]));

        String color;
        if (max > calibrationValue) {
            if (max == rgbNormalized[0]) {
                color = "red";
            } else if (max == rgbNormalized[1]) {
                color = "green";
            } else if (max == rgbNormalized[2]) {
                color = "blue";
            } else {
                color = "";
            }
        } else {
            color = "";
        }
        return color;
    }
}
