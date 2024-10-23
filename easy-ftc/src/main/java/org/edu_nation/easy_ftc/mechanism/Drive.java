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
 * @param Integer numMotors (2 or 4)
 * @param Boolean useEncoder (true or false)
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double diameter (> 0.0)
 * @param Double gearing (> 0.0)
 * @param Double deadZone (>= 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * @param String type ("mecanum" or "differential")
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
    private String layout;

    /**
     * Constructor
     */
    private Drive(Builder builder) {
        this.opMode = builder.opMode;
        this.hardwareMap = builder.hardwareMap;
        this.numMotors = builder.numMotors;
        this.useEncoder = builder.useEncoder;
        this.reverse = builder.reverse;
        this.reverseDevices = builder.reverseDevices;
        this.diameter = builder.diameter;
        this.gearing = builder.gearing;
        this.deadZone = builder.deadZone;
        this.gamepad = builder.gamepad;
        this.type = builder.type;
        this.layout = builder.layout;
        this.mechanismName = builder.mechanismName;
        hardwareInit();
    }

    public static class Builder {
        private LinearOpMode opMode;
        private HardwareMap hardwareMap;
        private int numMotors = 2;
        private boolean useEncoder = false;
        private boolean reverse = false;
        private String[] reverseDevices = {};
        private double diameter = 0.0;
        private double gearing = 0.0;
        private double deadZone = 0.0;
        private Gamepad gamepad = null;
        private String type = "";
        private String layout = "";
        private String mechanismName = "Drive";

        /**
         * Drive Builder
         * 
         * @Defaults numMotors = 2
         *           <li>useEncoder = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>diameter = 0.0
         *           <li>gearing = 0.0
         *           <li>deadZone = 0.0
         *           <li>gamepad = null
         *           <li>type = ""
         *           <li>layout = ""
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            this.opMode = opMode;
            this.hardwareMap = hardwareMap;
        }

        /**
         * Specify the number of motors (2, 4)
         */
        public Builder numMotors(int numMotors) {
            this.numMotors = numMotors;
            return this;
        }

        /**
         * Whether to enable encoders (time-based)
         */
        public Builder useEncoder(boolean useEncoder) {
            this.useEncoder = useEncoder;
            return this;
        }

        /**
         * Whether to reverse motors
         */
        public Builder reverse() {
            this.reverse = true;
            return this;
        }

        /**
         * Reverse the specified motor
         */
        public Builder reverse(String motorName) {
            int arrLength = reverseDevices.length;
            String[] reverseDevices = new String[arrLength + 1];
            for (int i = 0; i < arrLength; i++) {
                reverseDevices[i] = this.reverseDevices[i];
            }
            reverseDevices[arrLength] = motorName;

            this.reverseDevices = reverseDevices;
            return this;
        }

        /**
         * Specify the diameter of the drive wheels for encoder control (distance-based)
         */
        public Builder diameter(double diameter) {
            this.diameter = diameter;
            return this;
        }

        /**
         * Specify the gearing of the drive motors (increases accuracy of distance-based movement)
         */
        public Builder gearing(double gearing) {
            if (gearing <= 0) {
                throw new IllegalArgumentException(
                        "Unexpected gearing value: " + gearing + ", passed to " + mechanismName
                                + ".gearing(). Valid values are numbers > 0");
            }
            this.gearing = gearing;
            return this;
        }

        /**
         * Specify the joystick deadZone (minimum value registered as input)
         */
        public Builder deadZone(double deadZone) {
            if (deadZone < 0) {
                throw new IllegalArgumentException(
                        "Unexpected deadZone value: " + deadZone + ", passed to " + mechanismName
                                + ".deadZone(). Valid values are numbers >= 0");
            }
            this.deadZone = deadZone;
            return this;
        }

        /**
         * Pass the gamepad instance for teleop control
         */
        public Builder gamepad(Gamepad gamepad) {
            this.gamepad = gamepad;
            return this;
        }

        /**
         * Specify the drivetrain type: "differential" (default) or "mecanum"
         */
        public Builder type(String type) {
            this.type = type;
            if (type == "mecanum" && this.numMotors == 2) {
                this.numMotors = 4;
            }
            return this;
        }

        /**
         * Specify the control layout. For Differential, "tank" (default) or "arcade". For Mecanum,
         * "robot" (default) or "field"
         */
        public Builder layout(String layout) {
            this.layout = layout;
            return this;
        }

        /**
         * Build the arm
         */
        public Drive build() {
            return new Drive(this);
        }
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
            setDirections(reverse);

            // Reset encoders
            setModesEx(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // Set motors to run using the encoder (velocity, not position)
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);

            if (diameter == 0.0) {
                velocityMultiplier = getAchieveableMaxTicksPerSecond(motorTypes);
            } else {
                distanceMultiplier = getTicksPerRev(motorTypes);
                if (gearing != 0.0) {
                    setGearing(gearing);
                }
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
            setDirections(reverse);

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

        // reverse direction of specified motors
        for (String device : reverseDevices) {
            reverse(device);
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

        double[] movements =
                DriveUtil.controlToDirection(type, layout, deadZone, heading, gamepad.left_stick_y,
                        gamepad.left_stick_x, gamepad.right_stick_y, gamepad.right_stick_x);
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
    protected void reverse(String motorName) {
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
