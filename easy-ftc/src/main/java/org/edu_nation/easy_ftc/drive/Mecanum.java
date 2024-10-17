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
 * @param Double diameter (> 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * @param String layout ("robot" or "field")
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double measurement)}
 *          <li>{@link #reverse()}
 *          <li>{@link #reverse(String motorName)}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Drive})
 */
public class Mecanum extends Drive {
    private IMU imu;

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     *           <li>layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     *           <li>layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
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
            double diameter) {
        super(opMode, hardwareMap, useEncoder, diameter);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     *           <li>gamepad = null
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            String layout) {
        super(opMode, hardwareMap, useEncoder, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>diameter = 0.0
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad, String layout) {
        super(opMode, hardwareMap, gamepad, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults diameter = 0.0
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad, String layout) {
        super(opMode, hardwareMap, useEncoder, gamepad, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter, String layout) {
        super(opMode, hardwareMap, useEncoder, diameter, layout);
    }

    /**
     * Constructor
     * 
     * @Defaults layout = ""
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double diameter, Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, diameter, gamepad);
    }

    /**
     * Constructor
     */
    public Mecanum(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
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
            motorsEx = new DcMotorEx[4];
            motorsEx[0] = hardwareMap.get(DcMotorEx.class, "frontLeft");
            motorsEx[1] = hardwareMap.get(DcMotorEx.class, "frontRight");
            motorsEx[2] = hardwareMap.get(DcMotorEx.class, "backLeft");
            motorsEx[3] = hardwareMap.get(DcMotorEx.class, "backRight");

            MotorConfigurationType[] motorType =
                    {motorsEx[0].getMotorType(), motorsEx[1].getMotorType(),
                        motorsEx[2].getMotorType(), motorsEx[3].getMotorType()};

            // Reverse direction of left motors for convenience (switch if robot drives backwards)
            motorsEx[0].setDirection(DcMotorEx.Direction.REVERSE);
            motorsEx[1].setDirection(DcMotorEx.Direction.FORWARD);
            motorsEx[2].setDirection(DcMotorEx.Direction.REVERSE);
            motorsEx[3].setDirection(DcMotorEx.Direction.FORWARD);

            // Reset encoders
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (diameter == 0.0) {
                // Sets velocityMultiplier to minimum ticks/sec of all drive motors
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
                // sets distanceMultiplier to minimum ticks/rev of all drive motors
                double[] distanceMultiplierArr =
                        {motorType[0].getTicksPerRev(), motorType[1].getTicksPerRev(),
                                motorType[2].getTicksPerRev(), motorType[3].getTicksPerRev()};
                distanceMultiplier =
                        Math.min(Math.min(distanceMultiplierArr[0], distanceMultiplierArr[1]),
                                Math.min(distanceMultiplierArr[2], distanceMultiplierArr[3]));
            }
        } else {
            // Instantiate motors
            motors = new DcMotor[4];
            motors[0] = hardwareMap.get(DcMotor.class, "frontLeft");
            motors[1] = hardwareMap.get(DcMotor.class, "frontRight");
            motors[2] = hardwareMap.get(DcMotor.class, "backLeft");
            motors[3] = hardwareMap.get(DcMotor.class, "backRight");

            // Reverse direction of left motors for convenience (switch if robot drives backwards)
            motors[0].setDirection(DcMotor.Direction.REVERSE);
            motors[1].setDirection(DcMotor.Direction.FORWARD);
            motors[2].setDirection(DcMotor.Direction.REVERSE);
            motors[3].setDirection(DcMotor.Direction.FORWARD);

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
    public void move(double power, String direction, double measurement) {
        double[] movements = MecanumUtil.languageToDirection(power, direction);

        if (diameter == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            double[] unscaledMovements = MecanumUtil.languageToDirection(1, direction);
            int[] positions = MecanumUtil.calculatePositions(measurement, diameter,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions =
                    {motorsEx[0].getCurrentPosition(), motorsEx[1].getCurrentPosition(),
                        motorsEx[2].getCurrentPosition(), motorsEx[3].getCurrentPosition()};

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setAllPower(movements);
            while (motorsEx[0].isBusy() || motorsEx[1].isBusy() || motorsEx[2].isBusy()
                    || motorsEx[3].isBusy()) {
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
     * distanceMultiplier
     */
    public void setGearing(double gearing) {
        if (gearing <= 0) {
            throw new IllegalArgumentException("Unexpected gearing value: " + gearing
                    + ", passed to Mecanum.setGearing(). Valid values are numbers > 0");
        }
        MotorConfigurationType[] motorType = {motorsEx[0].getMotorType(),
            motorsEx[1].getMotorType(), motorsEx[2].getMotorType(), motorsEx[3].getMotorType()};

        // find current gearing (minimum of all motors)
        double[] currentGearings = {motorType[0].getGearing(), motorType[1].getGearing(),
                motorType[2].getGearing(), motorType[3].getGearing()};
        double currentGearing = Math.min(Math.min(currentGearings[0], currentGearings[1]),
                Math.min(currentGearings[2], currentGearings[3]));

        // update multiplier based on ratio of current and new
        distanceMultiplier *= gearing / currentGearing;
    }

    /**
     * Reverse the direction of the drive motors
     */
    @Override
    public void reverse() {
        if (useEncoder) {
            motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
            motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
            motorsEx[2].setDirection(DcMotorEx.Direction.FORWARD);
            motorsEx[3].setDirection(DcMotorEx.Direction.REVERSE);
        } else {
            motors[0].setDirection(DcMotor.Direction.FORWARD);
            motors[1].setDirection(DcMotor.Direction.REVERSE);
            motors[2].setDirection(DcMotor.Direction.FORWARD);
            motors[3].setDirection(DcMotor.Direction.REVERSE);
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    public void reverse(String motorName) {
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
                        + ", passed to Mecanum.reverse(). Valid names are: frontLeft, frontRight, backLeft, backRight");
        }
    }
}
