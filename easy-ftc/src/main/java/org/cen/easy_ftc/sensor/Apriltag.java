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
 * @param Boolean reverseState (true or false)
 *        <p>
 * @Methods {@link #state()}
 */
public class Apriltag extends Sensor {
    private AprilTagProcessor sensor;
    private VisionPortal portal;
    private List<AprilTagDetection> detections;

    public Apriltag(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    public Apriltag(HardwareMap hardwareMap, boolean reverseState) {
        super(hardwareMap, reverseState);
    }

    @Override
    protected void hardwareInit() {
        sensor = AprilTagProcessor.easyCreateWithDefaults();
        portal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"),
                sensor);
    }

    @Override
    public boolean state() {
        if (reverseState) {
            return !(objectDetected());
        } else {
            return objectDetected();
        }
    }

    private boolean objectDetected() {
        detections = sensor.getDetections();
        return (detections.size() > 0);
    }
}
