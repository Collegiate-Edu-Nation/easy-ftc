package org.edu_nation.easy_ftc.arm;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

/**
 * Implements a solo-motor arm by extending the functionality of {@link Arm}.
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
 *          <li>{@link #move(double power, String direction, double measurement)}
 *          <li>{@link #reverse()}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Arm})
 */
public class SoloArm extends Arm {
    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     *           <li>gamepad = null
     */
    public SoloArm(LinearOpMode opMode, HardwareMap hardwareMap) {
        super(opMode, hardwareMap);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     *           <li>gamepad = null
     */
    public SoloArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder) {
        super(opMode, hardwareMap, useEncoder);
    }

    /**
     * Constructor
     * 
     * @Defaults useEncoder = false
     *           <li>length = 0.0
     */
    public SoloArm(LinearOpMode opMode, HardwareMap hardwareMap, Gamepad gamepad) {
        super(opMode, hardwareMap, gamepad);
    }

    /**
     * Constructor
     * 
     * @Defaults gamepad = null
     */
    public SoloArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            double length) {
        super(opMode, hardwareMap, useEncoder, length);
    }

    /**
     * Constructor
     * 
     * @Defaults length = 0.0
     */
    public SoloArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, gamepad);
    }

    /**
     * Constructor
     */
    public SoloArm(LinearOpMode opMode, HardwareMap hardwareMap, boolean useEncoder, double length,
            Gamepad gamepad) {
        super(opMode, hardwareMap, useEncoder, length, gamepad);
    }

    /**
     * Initializes arm motor based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motor
            armMotorsEx = new DcMotorEx[1];
            armMotorsEx[0] = hardwareMap.get(DcMotorEx.class, "arm");

            MotorConfigurationType motorType = armMotorsEx[0].getMotorType();

            // Set direction of arm motor (switch to BACKWARD if motor orientation is flipped)
            armMotorsEx[0].setDirection(DcMotor.Direction.FORWARD);

            // Reset encoder
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Sets motor to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (length == 0.0) {
                // Sets velocityMultiplier to ticks/sec of lift motor
                velocityMultiplier = motorType.getAchieveableMaxTicksPerSecond();
            } else {
                // sets distanceMultiplier to ticks/rev of lift motor
                distanceMultiplier = motorType.getTicksPerRev();
            }
        } else {
            // Instantiate motor
            armMotors = new DcMotor[1];
            armMotors[0] = hardwareMap.get(DcMotor.class, "arm");

            // Set direction of arm motor (switch to BACKWARD if motor orientation is flipped)
            armMotors[0].setDirection(DcMotor.Direction.FORWARD);

            // Set motor to run without the encoders (power, not velocity or position)
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
                SoloArmUtil.controlToDirection(power, gamepad.left_bumper, gamepad.right_bumper);
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
     * Intermediate function that assigns motor power based on direction specified in runOpMode()
     * calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: up, down
     */
    @Override
    public void move(double power, String direction, double measurement) {
        double[] movements = SoloArmUtil.languageToDirection(power, direction);

        if (length == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            double[] unscaledMovements = SoloArmUtil.languageToDirection(1, direction);
            // length is the radius of arm's ROM, so double it for arc length = distance
            int[] positions = SoloArmUtil.calculatePositions(measurement, 2.0 * length,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions = {armMotorsEx[0].getCurrentPosition()};

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setAllPower(movements);
            while (armMotorsEx[0].isBusy()) {
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
                    + ", passed to SoloArm.setGearing(). Valid values are numbers > 0");
        }
        MotorConfigurationType motorType = armMotorsEx[0].getMotorType();

        // find current gearing
        double currentGearing = motorType.getGearing();

        // update multiplier based on ratio of current and new
        distanceMultiplier *= gearing / currentGearing;
    }

    /**
     * Reverse the direction of the arm motor
     */
    @Override
    public void reverse() {
        if (useEncoder) {
            armMotorsEx[0].setDirection(DcMotorEx.Direction.REVERSE);
        } else {
            armMotors[0].setDirection(DcMotor.Direction.REVERSE);
        }
    }
}
