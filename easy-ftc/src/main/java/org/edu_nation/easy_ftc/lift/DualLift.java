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
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double time)}
 *          <li>{@link #reverse()}
 *          <li>{@link #reverse(String motorName)}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Lift})
 */
public class DualLift extends Lift {
    private DcMotor left_lift, right_lift;
    private DcMotorEx left_liftEx, right_liftEx; // w/ encoder

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>gamepad = null
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     */
    public DualLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Initializes lift motors based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            left_liftEx = hardwareMap.get(DcMotorEx.class, "left_lift");
            right_liftEx = hardwareMap.get(DcMotorEx.class, "right_lift");

            // Reverse direction of left motor for convenience (switch if lift is backwards)
            left_liftEx.setDirection(DcMotorEx.Direction.REVERSE);
            right_liftEx.setDirection(DcMotorEx.Direction.FORWARD);

            // Reset encoders
            left_liftEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            right_liftEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            left_liftEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            right_liftEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            // Sets velocityMultiplier to minimum ticks/rev of all lift motors (reduces the impact
            // of mixing motor types)
            MotorConfigurationType[] motorType =
                    {left_liftEx.getMotorType(), right_liftEx.getMotorType()};
            double[] velocityMultiplierArr = {motorType[0].getAchieveableMaxTicksPerSecond(),
                    motorType[1].getAchieveableMaxTicksPerSecond()};
            velocityMultiplier = Math.min(velocityMultiplierArr[0], velocityMultiplierArr[1]);
        } else {
            // Instantiate motors
            left_lift = hardwareMap.get(DcMotor.class, "left_lift");
            right_lift = hardwareMap.get(DcMotor.class, "right_lift");

            // Reverse direction of left motor for convenience (switch if lift is backwards)
            left_lift.setDirection(DcMotor.Direction.REVERSE);
            right_lift.setDirection(DcMotor.Direction.FORWARD);

            // Set motors to run without the encoders (power, not velocity or position)
            left_lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            right_lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
    public void move(double power, String direction, double time) {
        double[] movements = DualLiftUtil.languageToDirection(power, direction);
        setAllPower(movements);
        wait(time);
        setAllPower();
    }

    /**
     * Reverse the direction of the lift motors
     */
    @Override
    public void reverse() {
        if (useEncoder) {
            left_liftEx.setDirection(DcMotorEx.Direction.FORWARD);
            right_liftEx.setDirection(DcMotorEx.Direction.REVERSE);
        } else {
            left_lift.setDirection(DcMotor.Direction.FORWARD);
            right_lift.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    public void reverse(String motorName) {
        switch (motorName) {
            case "left_lift":
                if (useEncoder) {
                    left_liftEx.setDirection(DcMotorEx.Direction.FORWARD);
                } else {
                    left_lift.setDirection(DcMotor.Direction.FORWARD);
                }
                break;
            case "right_lift":
                if (useEncoder) {
                    right_liftEx.setDirection(DcMotorEx.Direction.REVERSE);
                } else {
                    right_lift.setDirection(DcMotor.Direction.REVERSE);
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected motorName: " + motorName
                        + ", passed to DualLift.reverse(). Valid names are: left_lift, right_lift");
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
        if (useEncoder) {
            left_liftEx.setVelocity(movements[0] * velocityMultiplier);
            right_liftEx.setVelocity(movements[1] * velocityMultiplier);
        } else {
            left_lift.setPower(movements[0]);
            right_lift.setPower(movements[1]);
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