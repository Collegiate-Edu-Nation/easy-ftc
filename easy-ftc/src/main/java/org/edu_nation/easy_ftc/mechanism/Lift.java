package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a lift by extending the functionality of {@link MotorMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer numMotors (1-2)
 * @param Boolean useEncoder (true or false)
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double diameter (> 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double measurement)}
 *          <li>{@link #reverse()}
 *          <li>{@link #reverse(String motorName)}
 *          <li>{@link #setAllPower(double [] movements)}
 *          <li>{@link #setAllPower()} (defaults to array of zeros if nothing is passed)
 *          <li>{@link #wait(double time)} (inherited from {@link Lift})
 */
public class Lift extends MotorMechanism {

    /**
     * Constructor
     */
    private Lift(Builder builder) {
        this.opMode = builder.opMode;
        this.hardwareMap = builder.hardwareMap;
        this.numMotors = builder.numMotors;
        this.useEncoder = builder.useEncoder;
        this.reverse = builder.reverse;
        this.reverseDevices = builder.reverseDevices;
        this.diameter = builder.diameter;
        this.gearing = builder.gearing;
        this.gamepad = builder.gamepad;
        this.mechanismName = builder.mechanismName;
        hardwareInit();
    }

    public static class Builder {
        private LinearOpMode opMode;
        private HardwareMap hardwareMap;
        private int numMotors = 1;
        private boolean useEncoder = false;
        private boolean reverse = false;
        private String[] reverseDevices = {};
        private double diameter = 0.0;
        private double gearing = 0.0;
        private Gamepad gamepad = null;
        private String mechanismName = "Lift";

        /**
         * Lift Builder
         * 
         * @Defaults numMotors = 1
         *           <li>useEncoder = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>diameter = 0.0
         *           <li>gearing = 0.0
         *           <li>gamepad = null
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            this.opMode = opMode;
            this.hardwareMap = hardwareMap;
        }

        /**
         * Specify the number of motors (1-2)
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
         * Specify the diameter of the lift for encoder control (distance-based)
         */
        public Builder diameter(double diameter) {
            this.diameter = diameter;
            return this;
        }

        /**
         * Specify the gearing of the lift motors (increases accuracy of distance-based movement)
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
         * Pass the gamepad instance for teleop control
         */
        public Builder gamepad(Gamepad gamepad) {
            this.gamepad = gamepad;
            return this;
        }

        /**
         * Build the lift
         */
        public Lift build() {
            return new Lift(this);
        }
    }

    /**
     * Initializes lift motors based on constructor args (e.g. numMotors and using encoders or not)
     */
    @Override
    protected void hardwareInit() {
        if (useEncoder) {
            // Instantiate motors
            motorsEx = new DcMotorEx[numMotors];
            if (numMotors == 2) {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "liftLeft");
                motorsEx[1] = hardwareMap.get(DcMotorEx.class, "liftRight");
            } else {
                motorsEx[0] = hardwareMap.get(DcMotorEx.class, "lift");
            }

            MotorConfigurationType[] motorTypes = getMotorTypes();

            // Reverse direction of left motor for convenience (switch if lift is backwards)
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
            if (numMotors == 2) {
                motors[0] = hardwareMap.get(DcMotor.class, "liftLeft");
                motors[1] = hardwareMap.get(DcMotor.class, "liftRight");
            } else {
                motors[0] = hardwareMap.get(DcMotor.class, "lift");
            }

            // Reverse direction of left motor for convenience (switch if lift is backwards)
            setDirections(reverse);

            // Set motors to run without the encoders (power, not velocity or position)
            setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // reverse direction of specified motors
        for (String device : reverseDevices) {
            reverse(device);
        }
    }

    /**
     * Enables teleoperated lift movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double[] movements = new double[numMotors];
        double direction =
                LiftUtil.controlToDirection(deadZone, gamepad.left_trigger, gamepad.right_trigger);
        for (int i = 0; i < movements.length; i++) {
            movements[i] = direction;
        }
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
    public void move(double power, String direction, double measurement) {
        double movement = LiftUtil.languageToDirection(direction);
        double[] unscaledMovements = new double[numMotors];
        for (int i = 0; i < numMotors; i++) {
            unscaledMovements[i] = movement;
        }
        double[] movements = LiftUtil.scaleDirections(power, unscaledMovements);

        if (diameter == 0.0) {
            setAllPower(movements);
            wait(measurement);
            setAllPower();
        } else {
            int[] positions = LiftUtil.calculatePositions(measurement, diameter, distanceMultiplier,
                    unscaledMovements);
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
        if (numMotors == 2) {
            switch (motorName) {
                case "liftLeft":
                    if (useEncoder) {
                        motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[0].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                case "liftRight":
                    if (useEncoder) {
                        motorsEx[1].setDirection(DcMotorEx.Direction.REVERSE);
                    } else {
                        motors[1].setDirection(DcMotor.Direction.REVERSE);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected motorName: " + motorName
                            + ", passed to Lift.reverse(). Valid names are: liftLeft, liftRight");
            }
        } else {
            switch (motorName) {
                case "lift":
                    if (useEncoder) {
                        motorsEx[0].setDirection(DcMotorEx.Direction.FORWARD);
                    } else {
                        motors[0].setDirection(DcMotor.Direction.FORWARD);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected motorName: " + motorName
                            + ", passed to Lift.reverse(). Valid names are: lift");
            }
        }
    }
}
