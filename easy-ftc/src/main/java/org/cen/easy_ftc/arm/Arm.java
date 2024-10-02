package org.cen.easy_ftc.arm;

import org.cen.easy_ftc.mechanism.Mechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract arm, providing basic functionalities, options, and objects common to all
 * lifts. Cannot be instantiated, only extended by actual lift classes (see {@link SoloArm} and
 * {@link DualArm}).
 * <p>
 * 
 * @Methods {@link #wait(double time)} (inherited from {@link Mechanism})
 */
abstract class Arm extends Mechanism {
    protected boolean useEncoder;
    protected double velocityMultiplier; // scales user-provided power (-1 to 1) to useable unit for
                                         // setVelocity()

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, false);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        this(opMode, hardwareMap, useEncoder, null);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, false, gamepad);
    }

    /**
     * Constructor
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.useEncoder = useEncoder;
        this.gamepad = gamepad;
        hardwareInit();
    }

    public abstract void tele(double power);

    public abstract void move(double power, String direction, double time);

    public abstract void setAllPower(double[] movements);

    public abstract void setAllPower();
}
