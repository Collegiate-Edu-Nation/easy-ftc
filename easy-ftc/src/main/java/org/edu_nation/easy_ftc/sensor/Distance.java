package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Implements a distance sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Double calibrationValue (in CM)
 *        <p>
 * @Methods {@link #state()}
 *          <li>{@link #reverse()}
 */
public class Distance extends Sensor<Boolean> {
    private DistanceSensor sensor;

    /**
     * Constructor
     * 
     * @defaults calibrationValue = 7.0
     */
    public Distance(HardwareMap hardwareMap) {
        super(hardwareMap, 7.0);
    }

    /**
     * Constructor
     */
    public Distance(HardwareMap hardwareMap, double calibrationValue) {
        super(hardwareMap, calibrationValue);
    }

    /**
     * Initializes distance sensor
     */
    @Override
    protected void hardwareInit() {
        sensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
    }

    /**
     * Returns distance sensor state (whether an object is within the distance cutoff)
     */
    @Override
    public Boolean state() {
        if (reverseState) {
            return !(sensor.getDistance(DistanceUnit.CM) < calibrationValue);
        } else {
            return (sensor.getDistance(DistanceUnit.CM) < calibrationValue);
        }
    }

    /**
     * Reverses sensor state
     */
    @Override
    public void reverse() {
        this.reverseState = true;
    }
}
