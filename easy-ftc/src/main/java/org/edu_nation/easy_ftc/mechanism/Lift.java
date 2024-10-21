package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a lift by extending the functionality of {@link MotorMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Boolean useEncoder (true or false)
 * @param Double diameter (> 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double measurement)}
 *          <li>{@link #reverse()}
 *          <li>{@link #reverse(String motorName)}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Lift})
 */
public class Lift extends MotorMechanism {
    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap) {
        this(opMode, hardwareMap, 1);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors) {
        this(opMode, hardwareMap, numMotors, false);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        this(opMode, hardwareMap, 1, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>useEncoder = false
     *           <li>diameter = 0.0
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        this(opMode, hardwareMap, 1, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, boolean useEncoder) {
        this(opMode, hardwareMap, numMotors, useEncoder, 0.0);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, Gamepad gamepad) {
        this(opMode, hardwareMap, numMotors, false, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double diameter) {
        this(opMode, hardwareMap, 1, useEncoder, diameter);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     *           <li>diameter = 0.0
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad) {
        this(opMode, hardwareMap, 1, useEncoder, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, boolean useEncoder, double diameter) {
        this(opMode, hardwareMap, numMotors, useEncoder, diameter, null);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, boolean useEncoder, Gamepad gamepad) {
        this(opMode, hardwareMap, numMotors, useEncoder, 0.0, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults numMotors = 1
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double diameter,
            Gamepad gamepad) {
        this(opMode, hardwareMap, 1, useEncoder, diameter, gamepad);
    }

    /**
     * Constructor
     */
    public Lift(LinearOpMode opMode, HardwareMap hardwareMap, int numMotors, boolean useEncoder, double diameter,
            Gamepad gamepad) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.numMotors = numMotors;
        this.useEncoder = useEncoder;
        this.diameter = diameter;
        this.gamepad = gamepad;
        this.mechanismName = "Lift";
        hardwareInit();
    }
    
    /**
     * Initializes lift motors based on constructor args (e.g. numMotors and using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            motorsEx = new DcMotorEx[numMotors];
            if (numMotors == 2) {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "liftLeft");
                motorsEx[1] = hardwareMap.get(DcMotorEx.class, "liftRight");
            } else {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "lift");
            }

            MotorConfigurationType[] motorTypes = getMotorTypes();

            // Reverse direction of left motor for convenience (switch if lift is backwards)
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
            motors = new DcMotor[numMotors];
            if (numMotors == 2) {
                motors[0] = hardwareMap.get(DcMotor.class, "liftLeft");
                motors[1] = hardwareMap.get(DcMotor.class, "liftRight");
            } else {
                motors[0] = hardwareMap.get(DcMotor.class, "lift");
            }

            // Reverse direction of left motor for convenience (switch if lift is backwards)
            setDirections();

            // Set motors to run without the encoders (power, not velocity or position)
            setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Enables teleoperated lift movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double[] movements = new double[numMotors];
        double direction = LiftUtil.controlToDirection(deadZone, gamepad.left_trigger, gamepad.right_trigger);
        for (int i = 0; i < movements.length; i++) {
            movements[i] = direction;
        }
        setAllPower(movements);
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
        double movement = LiftUtil.languageToDirection(direction);
        double[] unscaledMovements = new double[numMotors];
        for (int i = 0; i < numMotors; i++) {
            unscaledMovements[i] = movement;
        }
        double[] movements = LiftUtil.scaleDirections(power, unscaledMovements);

        if (diameter == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            int[] positions = LiftUtil.calculatePositions(measurement, diameter,
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
        if (numMotors == 2) {
            switch (motorName) {
                case "liftLeft":
                    if (useEncoder) {
                        motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[0].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "liftRight":
                    if (useEncoder) {
                        motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[1].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected motorName: " + motorName
                            + ", passed to Lift.reverse(). Valid names are: liftLeft, liftRight");
            }
        } else {
            switch (motorName) {
                case "lift":
                    reverse();
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected motorName: " + motorName
                            + ", passed to Lift.reverse(). Valid names are: lift");
            }
        }
    }
}
