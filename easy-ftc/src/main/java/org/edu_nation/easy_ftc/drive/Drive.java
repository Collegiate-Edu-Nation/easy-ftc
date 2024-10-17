package org.edu_nation.easy_ftc.drive;

import org.edu_nation.easy_ftc.mechanism.MotorMechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract drivetrain, providing basic functionalities, options, and objects common
 * to all drivetrains. Cannot be instantiated, only extended by actual drivetrain classes (see
 * {@link Mecanum} and {@link Differential}).
 * <p>
 * 
 * @Methods {@link #wait(double time)} (inherited from {@link Mechanism})
 */
abstract class Drive extends MotorMechanism {
    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, false);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        this(opMode, hardwareMap, useEncoder, "");
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, false, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String layout) {
        this(opMode, hardwareMap, false, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter) {
        this(opMode, hardwareMap, useEncoder, diameter, "");
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        this(opMode, hardwareMap, useEncoder, gamepad, "");
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, String layout) {
        this(opMode, hardwareMap, useEncoder, null, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad, String layout) {
        this(opMode, hardwareMap, false, gamepad, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad,
            String layout) {
        this(opMode, hardwareMap, useEncoder, 0.0, gamepad, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double diameter,
            String layout) {
        this(opMode, hardwareMap, useEncoder, diameter, null, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double diameter,
            Gamepad gamepad) {
        this(opMode, hardwareMap, useEncoder, diameter, gamepad, "");
    }

    /**
     * Constructor
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double diameter,
            Gamepad gamepad, String layout) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.useEncoder = useEncoder;
        this.diameter = diameter;
        this.gamepad = gamepad;
        this.layout = layout;
        hardwareInit();
    }

    public abstract void move(double power, String direction, double measurement);
}
