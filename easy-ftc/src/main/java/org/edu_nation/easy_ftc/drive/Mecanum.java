package org.edu_nation.easy_ftc.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Implements a mecanum drivetrain by extending the functionality of {@link Drive}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Boolean useEncoder (true or false)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * @param String layout ("robot" or "field")
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double time)}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Drive})
 */
public class Mecanum extends Drive {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotorEx frontLeftEx, frontRightEx, backLeftEx, backRightEx; // w/ encoder
    private IMU imu;

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     *           <li>layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>gamepad = null
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, String layout) {
        super(opMode, hardwareMap, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            String layout) {
        super(opMode, hardwareMap, useEncoder, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad, String layout) {
        super(opMode, hardwareMap, gamepad, layout);
    }

    /**
     * Constructor
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad, String layout) {
        super(opMode, hardwareMap, useEncoder, gamepad, layout);
    }

    /**
     * Initializes drive motors based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            frontLeftEx = hardwareMap.get(DcMotorEx.class, "frontLeft");
            frontRightEx = hardwareMap.get(DcMotorEx.class, "frontRight");
            backLeftEx = hardwareMap.get(DcMotorEx.class, "backLeft");
            backRightEx = hardwareMap.get(DcMotorEx.class, "backRight");

            // Reverse direction of left motors for convenience (switch if robot drives backwards)
            frontLeftEx.setDirection(DcMotorEx.Direction.REVERSE);
            frontRightEx.setDirection(DcMotorEx.Direction.FORWARD);
            backLeftEx.setDirection(DcMotorEx.Direction.REVERSE);
            backRightEx.setDirection(DcMotorEx.Direction.FORWARD);

            // Reset encoders
            frontLeftEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            frontRightEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            backLeftEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            backRightEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            frontLeftEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            frontRightEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            backLeftEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            backRightEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            // Sets velocityMultiplier to minimum ticks/rev of all drive motors
            MotorConfigurationType[] motorType =
                    {frontLeftEx.getMotorType(), frontRightEx.getMotorType(),
                            backLeftEx.getMotorType(), backRightEx.getMotorType()};
            double[] velocityMultiplierArr = {motorType[0].getAchieveableMaxTicksPerSecond(),
                    motorType[1].getAchieveableMaxTicksPerSecond(),
                    motorType[2].getAchieveableMaxTicksPerSecond(),
                    motorType[3].getAchieveableMaxTicksPerSecond()};
            // Banking on the associativity of min():
            // https://proofwiki.org/wiki/Min_Operation_is_Associative
            velocityMultiplier =
                    Math.min(Math.min(velocityMultiplierArr[0], velocityMultiplierArr[1]),
                            Math.min(velocityMultiplierArr[2], velocityMultiplierArr[3]));
        } else {
            // Instantiate motors
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            backRight = hardwareMap.get(DcMotor.class, "backRight");

            // Reverse direction of left motors for convenience (switch if robot drives backwards)
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            frontRight.setDirection(DcMotor.Direction.FORWARD);
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            backRight.setDirection(DcMotor.Direction.FORWARD);

            // Set motors to run without the encoders (power, not velocity or position)
            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        // Initializes imu for field-centric layout. Adjust "UP" and "FORWARD" if orientation is
        // reversed
        if (layout == "field") {
            imu = hardwareMap.get(IMU.class, "imu");
            IMU.Parameters parameters = new IMU.Parameters(
                    new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
                            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
            imu.initialize(parameters);
            imu.resetYaw();
        }
    }

    /**
     * Enables teleoperated mecanum movement with gamepad (inherits layout).
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Basic mecanum algorithm largely taken from the BasicOmniOpMode block example.
     * <p>
     * Field-centric algorithm taken from:
     * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
     */
    @Override
    public void tele() {
        double heading = 0;
        // Press option to reset imu to combat drift, set heading if applicable
        if (layout == "field") {
            heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            if (gamepad.options) {
                imu.resetYaw();
            }
        }
        double[] movements = MecanumUtil.controlToDirection(layout, deadZone, heading,
                gamepad.left_stick_y, gamepad.left_stick_x, gamepad.right_stick_x);
        setAllPower(movements);
    }

    /**
     * Intermediate function that assigns individual motor powers based on direction specified in
     * runOpMode() calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: forward, backward, left, right, rotateLeft, rotateRight, forwaredLeft,
     * forwardRight, backwardLeft, backwardRight
     */
    @Override
    public void move(double power, String direction, double time) {
        double[] movements = MecanumUtil.languageToDirection(power, direction);
        setAllPower(movements);
        wait(time);
        setAllPower();
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
            frontLeftEx.setVelocity(movements[0] * velocityMultiplier);
            frontRightEx.setVelocity(movements[1] * velocityMultiplier);
            backLeftEx.setVelocity(movements[2] * velocityMultiplier);
            backRightEx.setVelocity(movements[3] * velocityMultiplier);
        } else {
            frontLeft.setPower(movements[0]);
            frontRight.setPower(movements[1]);
            backLeft.setPower(movements[2]);
            backRight.setPower(movements[3]);
        }
    }

    /**
     * Helper function to set all motor powers to zero (this is the default case).
     * <p>
     * Public, so motors can be stopped if needed (tele() is an example of this).
     */
    @Override
    public void setAllPower() {
        double[] zeros = {0, 0, 0, 0};
        setAllPower(zeros);
    }
}
