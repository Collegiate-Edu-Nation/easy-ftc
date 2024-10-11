package org.edu_nation.easy_ftc.arm;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Implements a dual-motor arm by extending the functionality of {@link Arm}.
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
 *          <li>{@link #move(double power, String direction, double movement)}
 *          <li>{@link #reverse()}
 *          <li>{@link #reverse(String motorName)}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Arm})
 */
public class DualArm extends Arm {
    private DcMotor left_arm, right_arm;
    private DcMotorEx left_armEx, right_armEx; // w/ encoder

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     *           <li>gamepad = null
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     *           <li>gamepad = null
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double length) {
        super(opMode, hardwareMap, useEncoder, length);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Constructor
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double length,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, length, gamepad);
    }

    /**
     * Initializes arm motors based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            left_armEx = hardwareMap.get(DcMotorEx.class, "left_arm");
            right_armEx = hardwareMap.get(DcMotorEx.class, "right_arm");

            MotorConfigurationType[] motorType =
                    {left_armEx.getMotorType(), right_armEx.getMotorType()};

            // Reverse direction of left motor for convenience (switch if arm is backwards)
            left_armEx.setDirection(DcMotorEx.Direction.REVERSE);
            right_armEx.setDirection(DcMotorEx.Direction.FORWARD);

            // Reset encoders
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (length == 0.0) {
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
            left_arm = hardwareMap.get(DcMotor.class, "left_arm");
            right_arm = hardwareMap.get(DcMotor.class, "right_arm");

            // Reverse direction of left motor for convenience (switch if arm is backwards)
            left_arm.setDirection(DcMotor.Direction.REVERSE);
            right_arm.setDirection(DcMotor.Direction.FORWARD);

            // Set motors to run without the encoders (power, not velocity or position)
            setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Enables teleoperated arm movement with gamepad at a specified power (defaults to 0.5).
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele(double power) {
        double[] movements =
                DualArmUtil.controlToDirection(power, gamepad.left_bumper, gamepad.right_bumper);
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
    @Override
    public void move(double power, String direction, double measurement) {
        double[] movements = DualArmUtil.languageToDirection(power, direction);

        if (length == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            double[] unscaledMovements = DualArmUtil.languageToDirection(1, direction);
            // length is the radius of arm's ROM, so double it for arc length = distance
            int[] positions = DualArmUtil.calculatePositions(measurement, 2.0 * length,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions =
                    {left_armEx.getCurrentPosition(), right_armEx.getCurrentPosition()};

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setAllPower(movements);
            while (left_armEx.isBusy() || right_armEx.isBusy()) {
                setAllPower(movements);
            }
            setAllPower();

            // Reset motors to run using velocity (allows for using move() w/ diameter along w/
            // tele())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Correct the gear-ratio of all arm motors using encoders. Automatically updates
     * distanceMultiplier
     */
    public void setGearing(double gearing) {
        if (gearing <= 0) {
            throw new IllegalArgumentException("Unexpected gearing value: " + gearing
                    + ", passed to DualArm.setGearing(). Valid values are numbers > 0");
        }
        MotorConfigurationType[] motorType =
                {left_armEx.getMotorType(), right_armEx.getMotorType()};

        // find current gearing (minimum of all motors)
        double[] currentGearings = {motorType[0].getGearing(), motorType[1].getGearing()};
        double currentGearing = Math.min(currentGearings[0], currentGearings[1]);

        // update multiplier based on ratio of current and new
        distanceMultiplier *= gearing / currentGearing;
    }

    /**
     * Sets the target position for each motor before setting the mode to RUN_TO_POSITION
     */
    private void setPositions(int[] positions, int[] currentPositions) {
        // set target-position (relative + current = desired)
        left_armEx.setTargetPosition(positions[0] + currentPositions[0]);
        right_armEx.setTargetPosition(positions[1] + currentPositions[1]);

        // Set motors to run using the encoder (position, not velocity)
        setModesEx(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /**
     * Sets all extended motors to the specified mode
     */
    private void setModesEx(DcMotorEx.RunMode runMode) {
        left_armEx.setMode(runMode);
        right_armEx.setMode(runMode);
    }

    /**
     * Sets all basic motors to the specified mode
     */
    private void setModes(DcMotor.RunMode runMode) {
        left_arm.setMode(runMode);
        right_arm.setMode(runMode);
    }

    /**
     * Reverse the direction of the arm motors
     */
    @Override
    public void reverse() {
        if (useEncoder) {
            left_armEx.setDirection(DcMotorEx.Direction.FORWARD);
            right_armEx.setDirection(DcMotorEx.Direction.REVERSE);
        } else {
            left_arm.setDirection(DcMotor.Direction.FORWARD);
            right_arm.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    public void reverse(String motorName) {
        switch (motorName) {
            case "left_arm":
                if (useEncoder) {
                    left_armEx.setDirection(DcMotorEx.Direction.FORWARD);
                } else {
                    left_arm.setDirection(DcMotor.Direction.FORWARD);
                }
                break;
            case "right_arm":
                if (useEncoder) {
                    right_armEx.setDirection(DcMotorEx.Direction.REVERSE);
                } else {
                    right_arm.setDirection(DcMotor.Direction.REVERSE);
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected motorName: " + motorName
                        + ", passed to DualArm.reverse(). Valid names are: left_arm, right_arm");
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
        if (useEncoder && length != 0.0) {
            left_armEx.setPower(movements[0]);
            right_armEx.setPower(movements[1]);
        } else if (useEncoder) {
            left_armEx.setVelocity(movements[0] * velocityMultiplier);
            right_armEx.setVelocity(movements[1] * velocityMultiplier);
        } else {
            left_arm.setPower(movements[0]);
            right_arm.setPower(movements[1]);
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
