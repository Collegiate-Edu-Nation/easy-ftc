package org.cen.easy_ftc.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Implements a differential drivetrain by extending the functionality of {@link Drive}.
 * <p>
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Boolean useEncoder (true or false)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * @param String layout ("robot" or "field")
 * <p>
 * @Methods
 * {@link #tele()}
 * <li>{@link #move(double power, String direction, double time)}
 * <li>{@link #setAllPower(double [] movements)}
 * <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 * <li>{@link #wait(double time)} (inherited from {@link Drive})
 */
public class Differential extends Drive {
    private DcMotor left_drive, right_drive;
    private DcMotorEx left_driveEx, right_driveEx; // w/ encoder

    /**
     * Constructor
     * @Defaults
     * useEncoder = false
     * <li>gamepad = null
     * <li>layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap) {super(opMode, hardwareMap);}
    /**
     * Constructor
     * @Defaults
     * gamepad = null
     * <li>layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {super(opMode, hardwareMap, useEncoder);}
    /**
     * Constructor
     * @Defaults
     * useEncoder = false
     * <li>layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {super(opMode, hardwareMap, gamepad);}
    /**
     * Constructor
     * @Defaults
     * useEncoder = false
     * <li>gamepad = null
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, String layout) {super(opMode, hardwareMap, layout);}
    /**
     * Constructor
     * @Defaults
     * layout = ""
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad) {super(opMode, hardwareMap, useEncoder, gamepad);}
    /**
     * Constructor
     * @Defaults
     * gamepad = null
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, String layout) {super(opMode, hardwareMap, useEncoder, layout);}
    /**
     * Constructor
     * @Defaults
     * useEncoder = false
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad, String layout) {super(opMode, hardwareMap, gamepad, layout);}
    /**
     * Constructor
     */
    public Differential(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, Gamepad gamepad, String layout) {super(opMode, hardwareMap, useEncoder, gamepad, layout);}

    /**
     * Initializes drive motors based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if(useEncoder) {
            // Instantiate motors
            left_driveEx = hardwareMap.get(DcMotorEx.class, "left_drive");
            right_driveEx = hardwareMap.get(DcMotorEx.class, "right_drive");

            // Reverse direction of left motor for convenience (switch if robot drives backwards)
            left_driveEx.setDirection(DcMotorEx.Direction.REVERSE);
            right_driveEx.setDirection(DcMotorEx.Direction.FORWARD);

            // Reset encoders
            left_driveEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            right_driveEx.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            left_driveEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            right_driveEx.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            // Sets velocityMultiplier to minimum ticks/rev of all drive motors (reduces the impact of mixing motor types)
            MotorConfigurationType [] motorType = {
                left_driveEx.getMotorType(),
                right_driveEx.getMotorType()
            };
            double [] velocityMultiplierArr = {
                motorType[0].getAchieveableMaxTicksPerSecond(),
                motorType[1].getAchieveableMaxTicksPerSecond()
            };
            velocityMultiplier = Math.min(
                velocityMultiplierArr[0],
                velocityMultiplierArr[1]
            );
        }
        else {
            // Instantiate motors
            left_drive = hardwareMap.get(DcMotor.class, "left_drive");
            right_drive = hardwareMap.get(DcMotor.class, "right_drive");

            // Reverse direction of left motor for convenience (switch if robot drives backwards)
            left_drive.setDirection(DcMotor.Direction.REVERSE);
            right_drive.setDirection(DcMotor.Direction.FORWARD);

            // Set motors to run without the encoders (power, not velocity or position)
            left_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            right_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Enables teleoperated differential movement with gamepad (inherits layout).
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double [] movements = DifferentialUtil.controlToDirection(
            layout,
            deadZone,
            gamepad.left_stick_y,
            gamepad.right_stick_y,
            gamepad.right_stick_x
        );
        setAllPower(movements);
    }

    /**
     * Intermediate function that assigns individual motor powers based on direction specified in runOpMode() calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: forward, backward, rotateLeft, rotateRight
     */
    @Override
    public void move(double power, String direction, double time) {
        double [] motorDirections = DifferentialUtil.languageToDirection(direction);
        double [] movements = DifferentialUtil.scaleDirections(power, motorDirections);
        setAllPower(movements);
        wait(time);
        setAllPower();
    }

    /**
     * Helper function to set all motor powers to received values (defaults to 0 if no args provided).
     * <p>
     * Public, so custom movements [] can be passed directly if needed (tele() is an example of this).
     */
    @Override
    public void setAllPower(double [] movements) {
        if(useEncoder) {
            left_driveEx.setVelocity(movements[0] * velocityMultiplier);
            right_driveEx.setVelocity(movements[1] * velocityMultiplier);
        }
        else {
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
        double [] zeros = {0,0};
        setAllPower(zeros);
    }
}
