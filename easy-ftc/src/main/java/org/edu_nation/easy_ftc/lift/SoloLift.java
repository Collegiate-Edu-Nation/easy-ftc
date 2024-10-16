package org.edu_nation.easy_ftc.lift;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Implements a solo-motor lift by extending the functionality of {@link Lift}.
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
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Lift})
 */
public class SoloLift extends Lift {
    private DcMotor[] liftMotors;
    private DcMotorEx[] liftMotorsEx;

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter) {
        super(opMode, hardwareMap, useEncoder, diameter);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Constructor
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter, Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, diameter, gamepad);
    }

    /**
     * Initializes lift motor based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motor
            liftMotorsEx = new DcMotorEx[1];
            liftMotorsEx[0] = hardwareMap.get(DcMotorEx.class, "lift");

            MotorConfigurationType motorType = liftMotorsEx[0].getMotorType();

            // Set direction of lift motor (switch to BACKWARD if motor orientation is flipped)
            liftMotorsEx[0].setDirection(DcMotor.Direction.FORWARD);

            // Reset encoder
            liftMotorsEx[0].setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Sets motor to run using the encoder (velocity, not position)
            liftMotorsEx[0].setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (diameter == 0.0) {
                // Sets velocityMultiplier to ticks/sec of lift motor
                velocityMultiplier = motorType.getAchieveableMaxTicksPerSecond();
            } else {
                // sets distanceMultiplier to ticks/rev of lift motor
                distanceMultiplier = motorType.getTicksPerRev();
            }
        } else {
            // Instantiate motor
            liftMotors = new DcMotor[1];
            liftMotors[0] = hardwareMap.get(DcMotor.class, "lift");

            // Set direction of lift motor (switch to BACKWARD if motor orientation is flipped)
            liftMotors[0].setDirection(DcMotor.Direction.FORWARD);

            // Set motor to run without the encoders (power, not velocity or position)
            liftMotors[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Enables teleoperated lift movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double[] movements = SoloLiftUtil.controlToDirection(deadZone, gamepad.left_trigger,
                gamepad.right_trigger);
        setAllPower(movements);
    }

    /**
     * Intermediate function that assigns motor power based on direction specified in runOpMode()
     * calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: up, down
     */
    @Override
    public void move(double power, String direction, double measurement) {
        double[] movements = SoloLiftUtil.languageToDirection(power, direction);

        if (diameter == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            double[] unscaledMovements = SoloLiftUtil.languageToDirection(1, direction);
            int[] positions = SoloLiftUtil.calculatePositions(measurement, diameter,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions = {liftMotorsEx[0].getCurrentPosition()};

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setAllPower(movements);
            while (liftMotorsEx[0].isBusy()) {
                setAllPower(movements);
            }
            setAllPower();

            // Reset motors to run using velocity (allows for using move() w/ diameter along w/
            // tele())
            liftMotorsEx[0].setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Correct the gear-ratio of all lift motors using encoders. Automatically updates
     * distanceMultiplier
     */
    public void setGearing(double gearing) {
        if (gearing <= 0) {
            throw new IllegalArgumentException("Unexpected gearing value: " + gearing
                    + ", passed to SoloLift.setGearing(). Valid values are numbers > 0");
        }
        MotorConfigurationType motorType = liftMotorsEx[0].getMotorType();

        // find current gearing
        double currentGearing = motorType.getGearing();

        // update multiplier based on ratio of current and new
        distanceMultiplier *= gearing / currentGearing;
    }

    /**
     * Sets the target position for each motor before setting the mode to RUN_TO_POSITION
     */
    private void setPositions(int[] positions, int[] currentPositions) {
        // set target-position (relative + current = desired)
        liftMotorsEx[0].setTargetPosition(positions[0] + currentPositions[0]);

        // Set motors to run using the encoder (position, not velocity)
        liftMotorsEx[0].setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /**
     * Reverse the direction of the lift motor
     */
    @Override
    public void reverse() {
        if (useEncoder) {
            liftMotorsEx[0].setDirection(DcMotorEx.Direction.REVERSE);
        } else {
            liftMotors[0].setDirection(DcMotor.Direction.REVERSE);
        }
    }

    /**
     * Helper function to set motor power to received values (defaults to 0 if no args provided).
     * <p>
     * Public, so custom movements [] can be passed directly if needed (tele() is an example of
     * this).
     */
    @Override
    public void setAllPower(double[] movements) {
        if (useEncoder && diameter != 0.0) {
            liftMotorsEx[0].setPower(movements[0]);
        } else if (useEncoder) {
            liftMotorsEx[0].setVelocity(movements[0] * velocityMultiplier);
        } else {
            liftMotors[0].setPower(movements[0]);
        }
    }

    /**
     * Helper function to set motor power to zero (this is the default case).
     * <p>
     * Public, so motor can be stopped if needed (tele() is an example of this).
     */
    @Override
    public void setAllPower() {
        double[] zeros;
        if (useEncoder) {
            zeros = new double[liftMotorsEx.length];
        } else {
            zeros = new double[liftMotors.length];
        }
        setAllPower(zeros);
    }
}
