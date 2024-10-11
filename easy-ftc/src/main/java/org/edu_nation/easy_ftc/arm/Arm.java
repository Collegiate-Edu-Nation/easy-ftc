package org.edu_nation.easy_ftc.arm;

import org.edu_nation.easy_ftc.mechanism.Mechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract arm, providing basic functionalities, options, and objects common to all
 * arms. Cannot be instantiated, only extended by actual arm classes (see {@link SoloArm} and
 * {@link DualArm}).
 * <p>
 * 
 * @Methods {@link #wait(double time)} (inherited from {@link Mechanism})
 */
abstract class Arm extends Mechanism {
    protected boolean useEncoder;
    protected double velocityMultiplier;
    protected double distanceMultiplier;
    protected double length;

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, false);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        this(opMode, hardwareMap, useEncoder, null);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, false, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double length) {
        this(opMode, hardwareMap, useEncoder, length, null);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad) {
        this(opMode, hardwareMap, useEncoder, 0.0, gamepad);
    }

    /**
     * Constructor
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double length,
            Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.useEncoder = useEncoder;
        this.length = length;
        this.gamepad = gamepad;
        hardwareInit();
    }

    public abstract void tele(double power);

    public abstract void move(double power, String direction, double measurement);

    public abstract void setAllPower(double[] movements);

    public abstract void setAllPower();
}
