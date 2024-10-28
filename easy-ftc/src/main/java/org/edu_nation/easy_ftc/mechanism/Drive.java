package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Implements a drivetrain by extending the functionality of {@link MotorMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer count (2 or 4)
 * @param Boolean encoder
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double diameter (> 0.0)
 * @param Double gearing (> 0.0)
 * @param Double deadzone (>= 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * @param String type ("mecanum" or "differential")
 * @param String layout ("tank" or "arcade")
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double measurement)}
 */
public class Drive extends MotorMechanism {
    private IMU imu;
    private String type;
    private String layout;

    /**
     * Constructor
     */
    private Drive(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.behavior = builder.behavior;
        this.type = builder.type;
        this.layout = builder.layout;
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder extends MotorMechanism.Builder<Builder>{
        private int count = 2;
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;
        private String type = "";
        private String layout = "";
        private String mechanismName = "Drive";

        /**
         * Drive Builder
         * 
         * @Defaults count = 2
         *           <li>behavior = FLOAT
         *           <li>encoder = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>diameter = 0.0
         *           <li>gearing = 0.0
         *           <li>deadzone = 0.0
         *           <li>gamepad = null
         *           <li>type = "" (interpreted as "differential")
         *           <li>layout = "" (interpreted as "tank" for differential, "robot" for mecanum)
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Specify the number of motors (2, 4)
         */
        public Builder count(int count) {
            this.count = count;
            return this;
        }

        /**
         * Specify the zero-power behavior of the motors (DcMotor.ZeroPowerBehavior.BRAKE or FLOAT)
         */
        public Builder behavior(DcMotor.ZeroPowerBehavior behavior) {
            this.behavior = behavior;
            return this;
        }

        /**
         * Specify the drivetrain type: "differential" (default) or "mecanum"
         */
        public Builder type(String type) {
            this.type = type;
            if (type == "mecanum" && this.count == 2) {
                this.count = 4;
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
        @Override
        public Drive build() {
            return new Drive(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    /**
     * Initializes drive motors based on constructor args (e.g. using encoders or not)
     */
    @Override
    protected void init() {
        if (encoder) {
            // Instantiate motors
            motorsEx = new DcMotorEx[count];

            if (count == 4) {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "frontLeft");
                motorsEx[1] = hardwareMap.get(DcMotorEx.class, "frontRight");
                motorsEx[2] = hardwareMap.get(DcMotorEx.class, "backLeft");
                motorsEx[3] = hardwareMap.get(DcMotorEx.class, "backRight");
            } else {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "driveLeft");
                motorsEx[1] = hardwareMap.get(DcMotorEx.class, "driveRight");
            }

            MotorConfigurationType[] motorTypes = getMotorTypes();

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
            motors = new DcMotor[count];
            if (count == 4) {
                motors[0] = hardwareMap.get(DcMotor.class, "frontLeft");
                motors[1] = hardwareMap.get(DcMotor.class, "frontRight");
                motors[2] = hardwareMap.get(DcMotor.class, "backLeft");
                motors[3] = hardwareMap.get(DcMotor.class, "backRight");
            } else {
                motors[0] = hardwareMap.get(DcMotor.class, "driveLeft");
                motors[1] = hardwareMap.get(DcMotor.class, "driveRight");
            }

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

        // specify zeroPowerBehavior of motors
        setBehaviors(behavior);
        
        // Reverse direction of left motors for convenience (switch if robot drives backwards)
        setDirections(reverse);

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
                DriveUtil.controlToDirection(count, type, layout, deadzone, heading, gamepad.left_stick_y,
                        gamepad.left_stick_x, gamepad.right_stick_y, gamepad.right_stick_x);
        setPowers(movements);
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
        double[] movements = DriveUtil.languageToDirection(count, type, power, direction);

        if (diameter == 0.0) {
            setPowers(movements);
            wait(measurement);
            setPowers();
        } else {
            double[] unscaledMovements = DriveUtil.languageToDirection(count, type, 1, direction);
            int[] positions = DriveUtil.calculatePositions(measurement, diameter,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions = getCurrentPositions();

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setPowers(movements);
            while (motorsAreBusy()) {
                setPowers(movements);
            }
            setPowers();

            // Reset motors to run using velocity (allows for using move() w/ diameter along w/
            // tele())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Reverse the direction of the specified motor
     */
    @Override
    protected void reverse(String deviceName) {
        if (count == 4) {
            switch (deviceName) {
                case "frontLeft":
                    if (encoder) {
                        motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[0].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "frontRight":
                    if (encoder) {
                        motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[1].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                case "backLeft":
                    if (encoder) {
                        motorsEx[2].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[2].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "backRight":
                    if (encoder) {
                        motorsEx[3].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[3].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected deviceName: " + deviceName
                            + ", passed to Lift.reverse(). Valid names are: frontLeft, frontRight, backLeft, backRight");
            }
        } else {
            switch (deviceName) {
                case "driveLeft":
                    if (encoder) {
                        motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[0].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "driveRight":
                    if (encoder) {
                        motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[1].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected deviceName: " + deviceName
                            + ", passed to Lift.reverse(). Valid names are: driveLeft, driveRight");
            }
        }
    }
}
