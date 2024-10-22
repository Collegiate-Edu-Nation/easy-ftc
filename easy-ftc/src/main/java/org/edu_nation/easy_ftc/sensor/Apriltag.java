package org.edu_nation.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

/**
 * Implements an AprilTag sensor by extending the functionality of {@link Sensor}.
 * <p>
 * 
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverseState
 *        <p>
 * @Methods {@link #state()}
 */
public class Apriltag extends Sensor<Boolean> {
    private AprilTagProcessor sensor;
    private VisionPortal portal;
    private List<AprilTagDetection> detections;

    /**
     * Constructor
     */
    private Apriltag(Builder builder) {
        this.hardwareMap = builder.hardwareMap;
        this.reverseState = builder.reverseState;
        hardwareInit();
    }

    public static class Builder {
        private HardwareMap hardwareMap;
        private boolean reverseState = false;

        /**
         * Apriltag Builder
         * 
         * @Defaults reverseState = false
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
         * Build the sensor
         */
        public Apriltag build() {
            return new Apriltag(this);
        }
    }

    /**
     * Initializes AprilTag sensor via Webcam1
     */
    @Override
    protected void hardwareInit() {
        sensor = AprilTagProcessor.easyCreateWithDefaults();
        portal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"),
                sensor);
    }

    /**
     * Returns AprilTag sensor state (whether an object has been detected or not)
     */
    @Override
    public Boolean state() {
        if (reverseState) {
            return !(objectDetected());
        } else {
            return objectDetected();
        }
    }

    /**
     * Helper function that returns whether any detections have occurred
     */
    private boolean objectDetected() {
        detections = sensor.getDetections();
        return (detections.size() > 0);
    }
}
