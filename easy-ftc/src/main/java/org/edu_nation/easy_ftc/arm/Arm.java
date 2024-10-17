package org.edu_nation.easy_ftc.arm;

import org.edu_nation.easy_ftc.mechanism.MotorMechanism;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements an arm by extending the functionality of {@link MotorMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Boolean useEncoder (true or false)
 * @param Double length (> 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele(double power)}
 *          <li>{@link #tele()} (defaults to 0.5 power if nothing is passed)
 *          <li>{@link #move(double power, String direction, double measurement)}
 *          <li>{@link #reverse()}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Arm})
 */
public class Arm extends MotorMechanism {
    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>useEncoder = false
     *           <li>length = 0.0
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, 1);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors) {
        this(opMode, hardwareMap, numMotors, false);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>length = 0.0
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        this(opMode, hardwareMap, 1, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>useEncoder = false
     *           <li>length = 0.0
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, 1, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, boolean useEncoder) {
        this(opMode, hardwareMap, numMotors, useEncoder, 0.0);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, Gamepad gamepad) {
        this(opMode, hardwareMap, numMotors, false, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double length) {
        this(opMode, hardwareMap, 1, useEncoder, length);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>length = 0.0
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad) {
        this(opMode, hardwareMap, 1, useEncoder, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, boolean useEncoder, double length) {
        this(opMode, hardwareMap, numMotors, useEncoder, length, null);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, boolean useEncoder, Gamepad gamepad) {
        this(opMode, hardwareMap, numMotors, useEncoder, 0.0, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double length,
            Gamepad gamepad) {
        this(opMode, hardwareMap, 1, useEncoder, length, gamepad);
    }

    /**
     * Constructor
     */
    public Arm(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, boolean useEncoder, double length,
            Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.numMotors = numMotors;
        this.useEncoder = useEncoder;
        this.length = length;
        this.gamepad = gamepad;
        this.mechanismName = "Arm";
        hardwareInit();
    }
    
    /**
     * Initializes arm motors based on constructor args (e.g. numMotors and using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            motorsEx = new DcMotorEx[numMotors];
            if (numMotors == 2) {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "armLeft");
                motorsEx[1] = hardwareMap.get(DcMotorEx.class, "armRight");
            } else {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "arm");
            }

            MotorConfigurationType[] motorTypes = getMotorTypes();

            // Reverse direction of left motor for convenience (switch if arm is backwards)
            setDirections();

            // Reset encoders
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (length == 0.0) {
                velocityMultiplier = getAchieveableMaxTicksPerSecond(motorTypes);
            } else {
                distanceMultiplier = getTicksPerRev(motorTypes);
            }
        } else {
            // Instantiate motors
            motors = new DcMotor[numMotors];
            if (numMotors == 2) {
                motors[0] = hardwareMap.get(DcMotor.class, "armLeft");
                motors[1] = hardwareMap.get(DcMotor.class, "armRight");
            } else {
                motors[0] = hardwareMap.get(DcMotor.class, "arm");
            }

            // Reverse direction of left motor for convenience (switch if arm is backwards)
            setDirections();

            // Set motors to run without the encoders (power, not velocity or position)
            setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Enables teleoperated arm movement with gamepad at a specified power (defaults to 0.5).
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    public void tele(double power) {
        double[] movements = new double[numMotors];
        double direction = ArmUtil.controlToDirection(power, gamepad.left_bumper, gamepad.right_bumper);
        for (double movement : movements) {
            movement = direction;
        }
        setAllPower(movements);
    }

    /**
     * Enables teleoperated arm movement with gamepad at a power of 0.5 (this is the default case).
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        tele(0.5);
    }

    /**
     * Intermediate function that assigns individual motor powers based on direction specified in
     * runOpMode() calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: up, down
     */
    public void move(double power, String direction, double measurement) {
        double movement = ArmUtil.languageToDirection(direction);
        double[] unscaledMovements = new double[numMotors];
        for (int i = 0; i < numMotors; i++) {
            unscaledMovements[i] = movement;
        }
        double[] movements = ArmUtil.scaleDirections(power, unscaledMovements);

        if (length == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            // length is the radius of arm's ROM, so double it for arc length = distance
            int[] positions = ArmUtil.calculatePositions(measurement, 2.0 * length,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions = getCurrentPositions();

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setAllPower(movements);
            while (motorsAreBusy()) {
                setAllPower(movements);
            }
            setAllPower();

            // Reset motors to run using velocity (allows for using move() w/ length along w/
            // tele())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    public void reverse(String motorName) {
        if (numMotors == 2) {
            switch (motorName) {
                case "armLeft":
                    if (useEncoder) {
                        motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[0].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "armRight":
                    if (useEncoder) {
                        motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[1].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected motorName: " + motorName
                            + ", passed to Arm.reverse(). Valid names are: armLeft, armRight");
            }
        } else {
            switch (motorName) {
                case "arm":
                    reverse();
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected motorName: " + motorName
                            + ", passed to Arm.reverse(). Valid names are: arm");
            }
        }
    }
}
