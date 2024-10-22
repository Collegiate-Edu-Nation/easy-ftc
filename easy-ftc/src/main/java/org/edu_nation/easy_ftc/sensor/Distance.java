package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Implements a distance sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverseState
 * @param Double calibrationValue (in CM)
 *        <p>
 * @Methods {@link #state()}
 */
public class Distance extends Sensor<Boolean> {
    private DistanceSensor sensor;

    /**
     * Constructor
     */
    private Distance(Builder builder) {
        this.hardwareMap = builder.hardwareMap;
        this.reverseState = builder.reverseState;
        this.calibrationValue = builder.calibrationValue;
        hardwareInit();
    }

    public static class Builder {
        private HardwareMap hardwareMap;
        private boolean reverseState = false;
        private double calibrationValue = 7.0;

        /**
         * Distance Builder
         * 
         * @Defaults reverseState = false
         *           <li>calibrationValue = 7.0
         */
        public Builder(HardwareMap hardwareMap) {
            this.hardwareMap = hardwareMap;
        }

        /**
         * Reverse the sensor's state
         */
        public Builder reverse() {
            this.reverseState = true;
            return this;
        }

        /**
         * Specify the calibration value
         */
        public Builder calibrationValue(double calibrationValue) {
            this.calibrationValue = calibrationValue;
            return this;
        }

        /**
         * Build the sensor
         */
        public Distance build() {
            return new Distance(this);
        }
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
}
