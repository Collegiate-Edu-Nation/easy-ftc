package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Blueprints an abstract Servo Mechanism, providing basic functionalities, options, and objects
 * common to all Servo Mechanisms. Cannot be instantiated, only extended by other classes.
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
 */
abstract class ServoMechanism extends Mechanism {
    protected Servo[] servos;
    protected boolean smooth;
    protected double open, close;
    protected double increment;
    protected double incrementDelay;
    protected double delay;

    protected ServoMechanism(Builder<?> builder) {
        this.opMode = builder.opMode;
        this.hardwareMap = builder.hardwareMap;
        this.count = builder.count;
        this.smooth = builder.smooth;
        this.reverse = builder.reverse;
        this.reverseDevices = builder.reverseDevices;
        this.open = builder.open;
        this.close = builder.close;
        this.increment = builder.increment;
        this.incrementDelay = builder.incrementDelay;
        this.delay = builder.delay;
        this.gamepad = builder.gamepad;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected LinearOpMode opMode;
        protected HardwareMap hardwareMap;
        protected int count = 1;
        protected boolean smooth = false;
        protected boolean reverse = false;
        protected String[] reverseDevices = {};
        protected double open = 1.0;
        protected double close = 0.0;
        protected double increment = 0.02;
        protected double incrementDelay = 0.02;
        protected double delay = 2;
        protected Gamepad gamepad = null;

        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            this.opMode = opMode;
            this.hardwareMap = hardwareMap;
        }

        /**
         * Specify the number of servos (1-2)
         */
        public T count(int count) {
            this.count = count;
            return (T) this;
        }

        /**
         * Whether to enable smooth-servo control
         */
        public T smooth() {
            this.smooth = true;
            return (T) this;
        }

        /**
         * Whether to reverse servos
         */
        public T reverse() {
            this.reverse = true;
            return (T) this;
        }

        /**
         * Reverse the specified servo
         */
        public T reverse(String deviceName) {
            int arrLength = reverseDevices.length;
            String[] reverseDevices = new String[arrLength + 1];
            for (int i = 0; i < arrLength; i++) {
                reverseDevices[i] = this.reverseDevices[i];
            }
            reverseDevices[arrLength] = deviceName;

            this.reverseDevices = reverseDevices;
            return (T) this;
        }

        /**
         * Specify the open posiiton of the servo(s) (0-1)
         */
        public T open(double open) {
            this.open = open;
            return (T) this;
        }

        /**
         * Specify the close position of the servo(s) (0-1)
         */
        public T close(double close) {
            this.close = close;
            return (T) this;
        }

        /**
         * Specify the increment to move by for smooth-servo control (0-1)
         */
        public T increment(double increment) {
            this.increment = increment;
            return (T) this;
        }

        /**
         * Specify the time (s) to wait between each increment for smooth-servo control (> 0)
         */
        public T incrementDelay(double incrementDelay) {
            this.incrementDelay = incrementDelay;
            return (T) this;
        }

        /**
         * Specify the time to wait for servo movements to complete (for normal servo control) (> 0)
         */
        public T delay(double delay) {
            this.delay = delay;
            return (T) this;
        }

        /**
         * Pass the gamepad instance for teleop control
         */
        public T gamepad(Gamepad gamepad) {
            this.gamepad = gamepad;
            return (T) this;
        }
    }

    public abstract void move(String direction);

    /**
     * Wrapper around setPosition that enables smooth, synchronized servo control
     */
    protected void setPositionByIncrement(double position, double movement) {
        while (opMode.opModeIsActive() && position != movement) {
            position += (movement - position > 0) ? increment : -increment;
            position = Math.min(Math.max(position, 0), 1);
            for (Servo servo : servos) {
                servo.setPosition(position);
            }
            wait(incrementDelay);
        }
    }

    /**
     * Wrapper around setPosition for all servos
     */
    protected void setPositions(double movement) {
        for (Servo servo : servos) {
            servo.setPosition(movement);
        }
    }

    /**
     * Wrapper around setDirection for all servos
     */
    protected void setDirections(boolean reverse) {
        if (!reverse) {
            for (int i = 0; i < servos.length; i++) {
                Servo.Direction direction =
                        (i % 2 == 0) ? Servo.Direction.FORWARD : Servo.Direction.REVERSE;
                servos[i].setDirection(direction);
            }
        } else {
            for (int i = 0; i < servos.length; i++) {
                Servo.Direction direction =
                        (i % 2 == 0) ? Servo.Direction.REVERSE : Servo.Direction.FORWARD;
                servos[i].setDirection(direction);
            }
        }
    }
}
