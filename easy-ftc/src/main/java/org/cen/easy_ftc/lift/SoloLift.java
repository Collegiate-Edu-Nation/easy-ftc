package org.cen.easy_ftc.lift;

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
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double time)}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Lift})
 */
public class SoloLift extends Lift {
    private DcMotor lift;
    private DcMotorEx liftEx; // w/ encoder

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>gamepad = null
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     */
    public SoloLift(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Initializes lift motor based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motor
            liftEx = hardwareMap.get(DcMotorEx.class, "lift");

            // Set direction of lift motor (switch to BACKWARD if motor orientation is flipped)
            liftEx.setDirection(DcMotor.Direction.FORWARD);

            // Reset encoder
            liftEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Sets motor to run using the encoder (velocity, not position)
            liftEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            // Sets velocityMultiplier to ticks/rev of lift motor
            MotorConfigurationType[] motorType = {liftEx.getMotorType()};
            velocityMultiplier = motorType[0].getAchieveableMaxTicksPerSecond();
        } else {
            // Instantiate motor
            lift = hardwareMap.get(DcMotor.class, "lift");

            // Set direction of lift motor (switch to BACKWARD if motor orientation is flipped)
            lift.setDirection(DcMotor.Direction.FORWARD);

            // Set motor to run without the encoders (power, not velocity or position)
            lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
    public void move(double power, String direction, double time) {
        double[] motorDirections = SoloLiftUtil.languageToDirection(direction);
        double[] movements = SoloLiftUtil.scaleDirections(power, motorDirections);
        setAllPower(movements);
        wait(time);
        setAllPower();
    }

    /**
     * Helper function to set motor power to received values (defaults to 0 if no args provided).
     * <p>
     * Public, so custom movements [] can be passed directly if needed (tele() is an example of
     * this).
     */
    @Override
    public void setAllPower(double[] movements) {
        if (useEncoder) {
            liftEx.setVelocity(movements[0] * velocityMultiplier);
        } else {
            lift.setPower(movements[0]);
        }
    }

    /**
     * Helper function to set motor power to zero (this is the default case).
     * <p>
     * Public, so motor can be stopped if needed (tele() is an example of this).
     */
    @Override
    public void setAllPower() {
        double[] zeros = {0};
        setAllPower(zeros);
    }
}
