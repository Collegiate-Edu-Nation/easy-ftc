package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a claw by extending the functionality of {@link ServoMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer numServos (1 or 2)
 * @param Boolean smoothServo (true or false)
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double open (0-1)
 * @param Double close (0-1)
 * @param Double increment (0-1)
 * @param Double incrementDelay (> 0, in s)
 * @param Double delay (> 0, in s)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(String direction)}
 */
public class Claw extends ServoMechanism {

    /**
     * Constructor
     */
    private Claw(Builder builder) {
        this.opMode = builder.opMode;
        this.hardwareMap = builder.hardwareMap;
        this.numServos = builder.numServos;
        this.smoothServo = builder.smoothServo;
        this.reverse = builder.reverse;
        this.reverseDevices = builder.reverseDevices;
        this.open = builder.open;
        this.close = builder.close;
        this.increment = builder.increment;
        this.incrementDelay = builder.incrementDelay;
        this.delay = builder.delay;
        this.gamepad = builder.gamepad;
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder {
        private LinearOpMode opMode;
        private HardwareMap hardwareMap;
        private int numServos = 1;
        private boolean smoothServo = false;
        private boolean reverse = false;
        private String[] reverseDevices = {};
        private double open = 1.0;
        private double close = 0.0;
        private double increment = 0.02;
        private double incrementDelay = 0.02;
        private double delay = 2;
        private Gamepad gamepad = null;
        private String mechanismName = "Claw";

        /**
         * Claw Builder
         * 
         * @Defaults numServos = 1
         *           <li>smoothServo = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>open = 1.0
         *           <li>close = 0.0
         *           <li>increment = 0.02
         *           <li>incrementDelay = 0.02
         *           <li>delay = 2
         *           <li>gamepad = null
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            this.opMode = opMode;
            this.hardwareMap = hardwareMap;
        }

        /**
         * Specify the number of servos (1-2)
         */
        public Builder numServos(int numServos) {
            this.numServos = numServos;
            return this;
        }

        /**
         * Whether to enable smooth-servo control
         */
        public Builder smoothServo(boolean smoothServo) {
            this.smoothServo = smoothServo;
            return this;
        }

        /**
         * Whether to reverse servos
         */
        public Builder reverse() {
            this.reverse = true;
            return this;
        }

        /**
         * Reverse the specified servo
         */
        public Builder reverse(String deviceName) {
            int arrLength = reverseDevices.length;
            String[] reverseDevices = new String[arrLength + 1];
            for (int i = 0; i < arrLength; i++) {
                reverseDevices[i] = this.reverseDevices[i];
            }
            reverseDevices[arrLength] = deviceName;

            this.reverseDevices = reverseDevices;
            return this;
        }

        /**
         * Specify the open posiiton of the servo(s) (0-1)
         */
        public Builder open(double open) {
            this.open = open;
            return this;
        }

        /**
         * Specify the close position of the servo(s) (0-1)
         */
        public Builder close(double close) {
            this.close = close;
            return this;
        }

        /**
         * Specify the increment to move by for smooth-servo control (0-1)
         */
        public Builder increment(double increment) {
            this.increment = increment;
            return this;
        }

        /**
         * Specify the time (s) to wait between each increment for smooth-servo control (> 0)
         */
        public Builder incrementDelay(double incrementDelay) {
            this.incrementDelay = incrementDelay;
            return this;
        }

        /**
         * Specify the time to wait for servo movements to complete (for normal servo control) (> 0)
         */
        public Builder delay(double delay) {
            this.delay = delay;
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
         * Build the claw
         */
        public Claw build() {
            return new Claw(this);
        }
    }

    /**
     * Initializes claw servos
     */
    @Override
    protected void init() {
        // Instantiate servos
        servos = new Servo[numServos];
        if (numServos == 2) {
            servos[0] = hardwareMap.get(Servo.class, "clawLeft");
            servos[1] = hardwareMap.get(Servo.class, "clawRight");
        } else {
            servos[0] = hardwareMap.get(Servo.class, "claw");
        }
        setDirections(reverse);

        // reverse direction of specified motors
        for (String device : reverseDevices) {
            reverse(device);
        }
    }

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double current = servos[0].getPosition();
        double movement =
                ServoMechanismUtil.controlToDirection(open, close, current, gamepad.b, gamepad.a);
        if (smoothServo) {
            double position = current;
            setPositionByIncrement(position, movement);
        } else {
            setPositions(movement);
        }
    }

    /**
     * Intermediate function that assigns individual servo positions based on direction specified in
     * runOpMode() calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: open, close
     */
    @Override
    public void move(String direction) {
        double servoDirection =
                ServoMechanismUtil.languageToDirection(direction, open, close, mechanismName);
        if (smoothServo) {
            double position = servos[0].getPosition();
            setPositionByIncrement(position, servoDirection);
        } else {
            setPositions(servoDirection);
            wait(delay);
        }
    }

    /**
     * Reverse the direction of the specified servo
     */
    @Override
    protected void reverse(String deviceName) {
        if (numServos == 2) {
            switch (deviceName) {
                case "clawLeft":
                    servos[0].setDirection(Servo.Direction.REVERSE);
                    break;
                case "clawRight":
                    servos[1].setDirection(Servo.Direction.FORWARD);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected deviceName: " + deviceName
                            + ", passed to Claw.reverse(). Valid names are: clawLeft, clawRight");
            }
        } else {
            switch (deviceName) {
                case "claw":
                    servos[0].setDirection(Servo.Direction.REVERSE);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected deviceName: " + deviceName
                            + ", passed to Claw.reverse(). Valid names are: claw");
            }
        }
    }
}
