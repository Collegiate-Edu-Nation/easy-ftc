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
    private DcMotor left_drive, right_drive;
    private DcMotorEx left_driveEx, right_driveEx; // w/ encoder

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
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad, String layout) {
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
            left_driveEx = hardwareMap.get(DcMotorEx.class, "left_drive");
            right_driveEx = hardwareMap.get(DcMotorEx.class, "right_drive");

            MotorConfigurationType[] motorType =
                    {left_driveEx.getMotorType(), right_driveEx.getMotorType()};

            // Reverse direction of left motor for convenience (switch if robot drives backwards)
            left_driveEx.setDirection(DcMotorEx.Direction.REVERSE);
            right_driveEx.setDirection(DcMotorEx.Direction.FORWARD);

            // Reset encoders
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (diameter == 0.0) {
                // Sets velocityMultiplier to minimum ticks/sec of all drive motors
                double[] velocityMultiplierArr = {motorType[0].getAchieveableMaxTicksPerSecond(),
                        motorType[1].getAchieveableMaxTicksPerSecond()};
                velocityMultiplier = Math.min(velocityMultiplierArr[0], velocityMultiplierArr[1]);
            } else {
                // sets distanceMultiplier to minimum ticks/rev of all drive motors
                double[] distanceMultiplierArr =
                        {motorType[0].getTicksPerRev(), motorType[1].getTicksPerRev()};
                distanceMultiplier = Math.min(distanceMultiplierArr[0], distanceMultiplierArr[1]);
            }
        } else {
            // Instantiate motors
            left_drive = hardwareMap.get(DcMotor.class, "left_drive");
            right_drive = hardwareMap.get(DcMotor.class, "right_drive");

            // Reverse direction of left motor for convenience (switch if robot drives backwards)
            left_drive.setDirection(DcMotor.Direction.REVERSE);
            right_drive.setDirection(DcMotor.Direction.FORWARD);

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
            int[] currentPositions =
                    {left_driveEx.getCurrentPosition(), right_driveEx.getCurrentPosition()};

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setAllPower(movements);
            while (left_driveEx.isBusy() || right_driveEx.isBusy()) {
                setAllPower(movements);
            }
            setAllPower();

            // Reset motors to run using velocity (allows for using move() w/ diameter along w/
            // tele())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Correct the gear-ratio of all drive motors using encoders. Automatically updates
     * distanceMultiplier, velocityMultiplier
     */
    public void setGearing(double gearing) {
        if (gearing <= 0) {
            throw new IllegalArgumentException("Unexpected gearing value: " + gearing
                    + ", passed to Differential.setGearing(). Valid values are numbers > 0");
        }
        MotorConfigurationType[] motorType = {left_driveEx.getMotorType(),
                right_driveEx.getMotorType()};

        // find current gearing (minimum of all motors)
        double[] currentGearings = {motorType[0].getGearing(), motorType[1].getGearing()};
        double currentGearing = Math.min(currentGearings[0], currentGearings[1]);

        // update multipliers based on ratio of current and new
        velocityMultiplier *= currentGearing / gearing;
        distanceMultiplier *= gearing / currentGearing;
    }

    /**
     * Sets the target position for each motor before setting the mode to RUN_TO_POSITION
     */
    private void setPositions(int[] positions, int[] currentPositions) {
        // set target-position (relative + current = desired)
        left_driveEx.setTargetPosition(positions[0] + currentPositions[0]);
        right_driveEx.setTargetPosition(positions[1] + currentPositions[1]);

        // Set motors to run using the encoder (position, not velocity)
        setModesEx(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets all extended motors to the specified mode
     */
    private void setModesEx(DcMotorEx.RunMode runMode) {
        left_driveEx.setMode(runMode);
        right_driveEx.setMode(runMode);
    }

    /**
     * Sets all basic motors to the specified mode
     */
    private void setModes(DcMotor.RunMode runMode) {
        left_drive.setMode(runMode);
        right_drive.setMode(runMode);
    }

    /**
     * Reverse the direction of the drive motors
     */
    @Override
    public void reverse() {
        if (useEncoder) {
            left_driveEx.setDirection(DcMotorEx.Direction.FORWARD);
            right_driveEx.setDirection(DcMotorEx.Direction.REVERSE);
        } else {
            left_drive.setDirection(DcMotor.Direction.FORWARD);
            right_drive.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    public void reverse(String motorName) {
        switch (motorName) {
            case "left_drive":
                if (useEncoder) {
                    left_driveEx.setDirection(DcMotorEx.Direction.FORWARD);
                } else {
                    left_drive.setDirection(DcMotor.Direction.FORWARD);
                }
                break;
            case "right_drive":
                if (useEncoder) {
                    right_driveEx.setDirection(DcMotorEx.Direction.REVERSE);
                } else {
                    right_drive.setDirection(DcMotor.Direction.REVERSE);
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected motorName: " + motorName
                        + ", passed to Differential.reverse(). Valid names are: left_drive, right_drive");
        }
    }

    /**
     * Helper function to set all motor powers to received values (defaults to 0 if no args
     * provided).
     * <p>
     * Public, so custom movements [] can be passed directly if needed (tele() is an example of
     * this).
     */
    @Override
    public void setAllPower(double[] movements) {
        if (useEncoder && diameter != 0.0) {
            left_driveEx.setPower(movements[0]);
            right_driveEx.setPower(movements[1]);
        } else if (useEncoder) {
            left_driveEx.setVelocity(movements[0] * velocityMultiplier);
            right_driveEx.setVelocity(movements[1] * velocityMultiplier);
        } else {
            left_drive.setPower(movements[0]);
            right_drive.setPower(movements[1]);
        }
    }

    /**
     * Helper function to set all motor powers to zero (this is the default case).
     * <p>
     * Public, so motors can be stopped if needed (tele() is an example of this).
     */
    @Override
    public void setAllPower() {
        double[] zeros = {0, 0};
        setAllPower(zeros);
    }
}
