package org.edu_nation.easy_ftc.sensor;

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
        String color = ColorUtil.dominantColor(rgbRaw, rgbOffsets, calibrationValue);
        return color;
    }
}
