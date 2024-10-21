package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Implements a drivetrain by extending the functionality of {@link MotorMechanism}.
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
public class Drive extends MotorMechanism {
    private IMU imu;
    private String type;

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type) {
        this(opMode, hardwareMap, type, false);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, boolean useEncoder) {
        this(opMode, hardwareMap, type, useEncoder, "");
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, Gamepad gamepad) {
        this(opMode, hardwareMap, type, false, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, String layout) {
        this(opMode, hardwareMap, type, false, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, boolean useEncoder,
            double diameter) {
        this(opMode, hardwareMap, type, useEncoder, diameter, "");
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, boolean useEncoder,
            Gamepad gamepad) {
        this(opMode, hardwareMap, type, useEncoder, gamepad, "");
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, boolean useEncoder, String layout) {
        this(opMode, hardwareMap, type, useEncoder, null, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, Gamepad gamepad, String layout) {
        this(opMode, hardwareMap, type, false, gamepad, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, boolean useEncoder, Gamepad gamepad,
            String layout) {
        this(opMode, hardwareMap, type, useEncoder, 0.0, gamepad, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, boolean useEncoder, double diameter,
            String layout) {
        this(opMode, hardwareMap, type, useEncoder, diameter, null, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults layout = ""
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, boolean useEncoder, double diameter,
            Gamepad gamepad) {
        this(opMode, hardwareMap, type, useEncoder, diameter, gamepad, "");
    }

    /**
     * Constructor
     */
    public Drive(LinearOpMode opMode, HardwareMap hardwareMap, String type, boolean useEncoder, double diameter,
            Gamepad gamepad, String layout) {
        this.opMode = opMode;
        this.hardwareMap = hardwareMap;
        this.type = type;
        if (type == "differential") {
            this.numMotors = 2;
        } else {
            this.numMotors = 4;
        }
        this.useEncoder = useEncoder;
        this.diameter = diameter;
        this.gamepad = gamepad;
        this.layout = layout;
        this.mechanismName = "Drive";
        hardwareInit();
    }

    /**
     * Initializes drive motors based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            motorsEx = new DcMotorEx[numMotors];

            if (numMotors == 4) {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "frontLeft");
                motorsEx[1] = hardwareMap.get(DcMotorEx.class, "frontRight");
                motorsEx[2] = hardwareMap.get(DcMotorEx.class, "backLeft");
                motorsEx[3] = hardwareMap.get(DcMotorEx.class, "backRight");
            } else {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "driveLeft");
                motorsEx[1] = hardwareMap.get(DcMotorEx.class, "driveRight");
            }

            MotorConfigurationType[] motorTypes = getMotorTypes();

            // Reverse direction of left motors for convenience (switch if robot drives backwards)
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
            if (numMotors == 4) {
                motors[0] = hardwareMap.get(DcMotor.class, "frontLeft");
                motors[1] = hardwareMap.get(DcMotor.class, "frontRight");
                motors[2] = hardwareMap.get(DcMotor.class, "backLeft");
                motors[3] = hardwareMap.get(DcMotor.class, "backRight");
            } else {
                motors[0] = hardwareMap.get(DcMotor.class, "driveLeft");
                motors[1] = hardwareMap.get(DcMotor.class, "driveRight");
            }

            // Reverse direction of left motors for convenience (switch if robot drives backwards)
            setDirections();

            // Set motors to run without the encoders (power, not velocity or position)
            setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        
        double[] movements = DriveUtil.controlToDirection(type, layout, deadZone, heading,
                gamepad.left_stick_y, gamepad.left_stick_x, gamepad.right_stick_y, gamepad.right_stick_x);
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
    public void move(double power, String direction, double measurement) {
        double[] movements = DriveUtil.languageToDirection(type, power, direction);

        if (diameter == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            double[] unscaledMovements = DriveUtil.languageToDirection(type, 1, direction);
            int[] positions = DriveUtil.calculatePositions(measurement, diameter,
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
        if (numMotors == 4) {
            switch (motorName) {
                case "frontLeft":
                    if (useEncoder) {
                        motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[0].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "frontRight":
                    if (useEncoder) {
                        motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[1].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                case "backLeft":
                    if (useEncoder) {
                        motorsEx[2].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[2].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "backRight":
                    if (useEncoder) {
                        motorsEx[3].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[3].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected motorName: " + motorName
                            + ", passed to Lift.reverse(). Valid names are: frontLeft, frontRight, backLeft, backRight");
            }
        } else {
            switch (motorName) {
                case "driveLeft":
                    if (useEncoder) {
                        motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[0].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "driveRight":
                    if (useEncoder) {
                        motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[1].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected motorName: " + motorName
                            + ", passed to Lift.reverse(). Valid names are: driveLeft, driveRight");
            }
        }
    }
}
