package org.cen.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Implements a color sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Double calibrationValue (in CM)
 *        <p>
 * @Methods {@link #state()}
 */
public class Color extends Sensor<String> {
    private ColorSensor sensor;

    /**
     * Constructor
     * 
     * @defaults calibrationValue = 65.0
     */
    public Color(HardwareMap hardwareMap) {
        super(hardwareMap, 65.0);
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
        if (sensor.red() > calibrationValue) {
            return "red";
        } else if (sensor.green() > calibrationValue) {
            return "green";
        } else if (sensor.blue() > calibrationValue) {
            return "blue";
        } else {
            return "";
        }
    }
}
