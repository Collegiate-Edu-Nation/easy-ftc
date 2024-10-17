package org.edu_nation.easy_ftc.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Implements a differential drivetrain by extending the functionality of {@link Drive}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Boolean useEncoder (true or false)
 * @param Double diameter (> 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * @param String layout ("tank" or "arcade")
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double measurement)}
 *          <li>{@link #reverse()}
 *          <li>{@link #reverse(String motorName)}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Drive})
 */
public class Differential extends Drive {
    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, String layout) {
        super(opMode, hardwareMap, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter) {
        super(opMode, hardwareMap, useEncoder, diameter);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            String layout) {
        super(opMode, hardwareMap, useEncoder, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad,
            String layout) {
        super(opMode, hardwareMap, gamepad, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad, String layout) {
        super(opMode, hardwareMap, useEncoder, gamepad, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter, String layout) {
        super(opMode, hardwareMap, useEncoder, diameter, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter, Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, diameter, gamepad);
    }

    /**
     * Constructor
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter, Gamepad gamepad, String layout) {
        super(opMode, hardwareMap, useEncoder, diameter, gamepad, layout);
    }

    /**
     * Initializes drive motors based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            motorsEx = new DcMotorEx[2];
            motorsEx[0] = hardwareMap.get(DcMotorEx.class, "driveLeft");
            motorsEx[1] = hardwareMap.get(DcMotorEx.class, "driveRight");

            MotorConfigurationType[] motorTypes = getMotorTypes();

            // Reverse direction of left motor for convenience (switch if robot drives backwards)
            setDirections();

            // Reset encoders
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (diameter == 0.0) {
                velocityMultiplier = getAchieveableMaxTicksPerSecond(motorTypes);
            } else {
                distanceMultiplier = getTicksPerRev(motorTypes);
            }
        } else {
            // Instantiate motors
            motors = new DcMotor[2];
            motors[0] = hardwareMap.get(DcMotor.class, "driveLeft");
            motors[1] = hardwareMap.get(DcMotor.class, "driveRight");

            // Reverse direction of left motor for convenience (switch if robot drives backwards)
            setDirections();

            // Set motors to run without the encoders (power, not velocity or position)
            setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Enables teleoperated differential movement with gamepad (inherits layout).
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double[] movements = DifferentialUtil.controlToDirection(layout, deadZone,
                gamepad.left_stick_y, gamepad.right_stick_y, gamepad.right_stick_x);
        setAllPower(movements);
    }

    /**
     * Intermediate function that assigns individual motor powers based on direction specified in
     * runOpMode() calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: forward, backward, rotateLeft, rotateRight
     */
    @Override
    public void move(double power, String direction, double measurement) {
        double[] movements = DifferentialUtil.languageToDirection(power, direction);

        if (diameter == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            double[] unscaledMovements = DifferentialUtil.languageToDirection(1, direction);
            int[] positions = DifferentialUtil.calculatePositions(measurement, diameter,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions = getCurrentPositions();

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setAllPower(movements);
            while (motorsAreBusy()) {
                setAllPower(movements);
            }
            setAllPower();

            // Reset motors to run using velocity (allows for using move() w/ diameter along w/
            // tele())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    public void reverse(String motorName) {
        switch (motorName) {
            case "driveLeft":
                if (useEncoder) {
                    motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                } else {
                    motors[0].setDirection(DcMotor.Direction.FORWARD);
                }
                break;
            case "driveRight":
                if (useEncoder) {
                    motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
                } else {
                    motors[1].setDirection(DcMotor.Direction.REVERSE);
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected motorName: " + motorName
                        + ", passed to Differential.reverse(). Valid names are: driveLeft, driveRight");
        }
    }
}
