package org.cen.easy_ftc.sensor;

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
    public Apriltag(HardwareMap hardwareMap) {
        super(hardwareMap);
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
