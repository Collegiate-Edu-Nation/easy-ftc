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
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele(double power)}
 *          <li>{@link #tele()} (defaults to 0.5 power if nothing is passed)
 *          <li>{@link #move(double power, String direction, double time)}
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
     *           <li>gamepad = null
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     */
    public DualArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
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

            // Reverse direction of left motor for convenience (switch if arm is backwards)
            left_armEx.setDirection(DcMotorEx.Direction.REVERSE);
            right_armEx.setDirection(DcMotorEx.Direction.FORWARD);

            // Reset encoders
            left_armEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            right_armEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            left_armEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            right_armEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            // Sets velocityMultiplier to minimum ticks/rev of all arm motors (reduces the impact of
            // mixing motor types)
            MotorConfigurationType[] motorType =
                    {left_armEx.getMotorType(), right_armEx.getMotorType()};
            double[] velocityMultiplierArr = {motorType[0].getAchieveableMaxTicksPerSecond(),
                    motorType[1].getAchieveableMaxTicksPerSecond()};
            velocityMultiplier = Math.min(velocityMultiplierArr[0], velocityMultiplierArr[1]);
        } else {
            // Instantiate motors
            left_arm = hardwareMap.get(DcMotor.class, "left_arm");
            right_arm = hardwareMap.get(DcMotor.class, "right_arm");

            // Reverse direction of left motor for convenience (switch if arm is backwards)
            left_arm.setDirection(DcMotor.Direction.REVERSE);
            right_arm.setDirection(DcMotor.Direction.FORWARD);

            // Set motors to run without the encoders (power, not velocity or position)
            left_arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            right_arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
    public void move(double power, String direction, double time) {
        double[] movements = DualArmUtil.languageToDirection(power, direction);
        setAllPower(movements);
        wait(time);
        setAllPower();
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
        if (useEncoder) {
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
