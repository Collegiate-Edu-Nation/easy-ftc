package org.edu_nation.easy_ftc.lift;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Implements a dual-motor lift by extending the functionality of {@link Lift}.
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
public class DualLift extends Lift {
    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter) {
        super(opMode, hardwareMap, useEncoder, diameter);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Constructor
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter, Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, diameter, gamepad);
    }

    /**
     * Initializes lift motors based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            motorsEx = new DcMotorEx[2];
            motorsEx[0] = hardwareMap.get(DcMotorEx.class, "liftLeft");
            motorsEx[1] = hardwareMap.get(DcMotorEx.class, "liftRight");

            MotorConfigurationType[] motorType =
                    {motorsEx[0].getMotorType(), motorsEx[1].getMotorType()};

            // Reverse direction of left motor for convenience (switch if lift is backwards)
            motorsEx[0].setDirection(DcMotorEx.Direction.REVERSE);
            motorsEx[1].setDirection(DcMotorEx.Direction.FORWARD);

            // Reset encoders
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (diameter == 0.0) {
                // Sets velocityMultiplier to minimum ticks/sec of all lift motors
                double[] velocityMultiplierArr = {motorType[0].getAchieveableMaxTicksPerSecond(),
                        motorType[1].getAchieveableMaxTicksPerSecond()};
                velocityMultiplier = Math.min(velocityMultiplierArr[0], velocityMultiplierArr[1]);
            } else {
                // sets distanceMultiplier to minimum ticks/rev of all lift motors
                double[] distanceMultiplierArr =
                        {motorType[0].getTicksPerRev(), motorType[1].getTicksPerRev()};
                distanceMultiplier = Math.min(distanceMultiplierArr[0], distanceMultiplierArr[1]);
            }
        } else {
            // Instantiate motors
            motors = new DcMotor[2];
            motors[0] = hardwareMap.get(DcMotor.class, "liftLeft");
            motors[1] = hardwareMap.get(DcMotor.class, "liftRight");

            // Reverse direction of left motor for convenience (switch if lift is backwards)
            motors[0].setDirection(DcMotor.Direction.REVERSE);
            motors[1].setDirection(DcMotor.Direction.FORWARD);

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
        double[] movements = DualLiftUtil.controlToDirection(deadZone, gamepad.left_trigger,
                gamepad.right_trigger);
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
    @Override
    public void move(double power, String direction, double measurement) {
        double[] movements = DualLiftUtil.languageToDirection(power, direction);

        if (diameter == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            double[] unscaledMovements = DualLiftUtil.languageToDirection(1, direction);
            int[] positions = DualLiftUtil.calculatePositions(measurement, diameter,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions =
                    {motorsEx[0].getCurrentPosition(), motorsEx[1].getCurrentPosition()};

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setAllPower(movements);
            while (motorsEx[0].isBusy() || motorsEx[1].isBusy()) {
                setAllPower(movements);
            }
            setAllPower();

            // Reset motors to run using velocity (allows for using move() w/ diameter along w/
            // tele())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Correct the gear-ratio of all lift motors using encoders. Automatically updates
     * distanceMultiplier
     */
    public void setGearing(double gearing) {
        if (gearing <= 0) {
            throw new IllegalArgumentException("Unexpected gearing value: " + gearing
                    + ", passed to DualLift.setGearing(). Valid values are numbers > 0");
        }
        MotorConfigurationType[] motorType =
                {motorsEx[0].getMotorType(), motorsEx[1].getMotorType()};

        // find current gearing (minimum of all motors)
        double[] currentGearings = {motorType[0].getGearing(), motorType[1].getGearing()};
        double currentGearing = Math.min(currentGearings[0], currentGearings[1]);

        // update multiplier based on ratio of current and new
        distanceMultiplier *= gearing / currentGearing;
    }

    /**
     * Reverse the direction of the lift motors
     */
    @Override
    public void reverse() {
        if (useEncoder) {
            motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
            motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
        } else {
            motors[0].setDirection(DcMotor.Direction.FORWARD);
            motors[1].setDirection(DcMotor.Direction.REVERSE);
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    public void reverse(String motorName) {
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
                        + ", passed to DualLift.reverse(). Valid names are: liftLeft, liftRight");
        }
    }
}
