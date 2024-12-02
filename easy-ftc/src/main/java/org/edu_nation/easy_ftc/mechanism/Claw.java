// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Implements a claw by extending the functionality of {@link ServoMechanism}.
 * 
 * @see Builder
 * @see Direction
 */
public class Claw extends ServoMechanism<Claw.Direction> {
    private double open;
    private double close;

    /** Constructor */
    private Claw(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.names = builder.names;
        this.open = builder.open;
        this.close = builder.close;
        this.mechanismName = builder.mechanismName;
        init();
    }

    /**
     * Claw Builder
     * <p>
     * <b>Defaults:</b>
     * <ul>
     * <li>count = 1
     * <li>names = {"claw"}
     * <li>smooth = false
     * <li>reverse = false
     * <li>reverseDevices = {}
     * <li>open = 1.0
     * <li>close = 0.0
     * <li>increment = 0.02
     * <li>incrementDelay = 0.02
     * <li>delay = 2
     * <li>gamepad = null
     * </ul>
     */
    public static class Builder extends ServoMechanism.Builder<Builder> {
        private int count = 1;
        private String[] names = {"claw"};
        private double open = 1.0;
        private double close = 0.0;
        private String mechanismName = "Claw";

        /**
         * Builder constructor
         * 
         * @param opMode instance of the calling opMode
         * @param hardwareMap instance of the calling opMode's hardwareMap
         * @throws NullPointerException if opMode or hardwareMap are null
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        // methods inherited from Mechanism.Builder
        @Override
        public Builder reverse() {
            return super.reverse();
        }

        @Override
        public Builder reverse(String deviceName) {
            return super.reverse(deviceName);
        }

        @Override
        public Builder reverse(String[] deviceNames) {
            return super.reverse(deviceNames);
        }

        @Override
        public Builder gamepad(Gamepad gamepad) {
            return super.gamepad(gamepad);
        }

        // methods inherited from ServoMechanism.Builder
        @Override
        public Builder smooth() {
            return super.smooth();
        }

        @Override
        public Builder increment(double increment) {
            return super.increment(increment);
        }

        @Override
        public Builder incrementDelay(double incrementDelay) {
            return super.incrementDelay(incrementDelay);
        }

        @Override
        public Builder delay(double delay) {
            return super.delay(delay);
        }

        // claw-specific methods
        /**
         * Specify the number of servos
         * 
         * @param count the number of servos in the claw mechanism
         * @return builder instance
         * @throws IllegalArgumentException if count isn't 1 or 2
         */
        public Builder count(int count) {
            if (count < 1 || count > 2) {
                throw new IllegalArgumentException("Unexpected count value: " + count
                        + ", passed to Claw.Builder().count(). Valid values are integers in the interval [1, 2]");
            }
            this.count = count;
            if (count == 2) {
                String[] newNames = {"clawLeft", "clawRight"};
                this.names = newNames;
            }
            return this;
        }

        /**
         * Change the names of the hardware devices
         * 
         * @param names an array of the names for the hardware devices
         * @return builder instance
         * @throws NullPointerException if names is null
         */
        public Builder names(String[] names) {
            if (names == null) {
                throw new NullPointerException("Null names passed to Claw.Builder().names()");
            }
            this.names = names;
            return this;
        }

        /**
         * Specify the open posiiton
         * 
         * @param open open position of the servo(s)
         * @return builder instance
         * @throws IllegalArgumentException if open not in the interval [0, 1]
         */
        public Builder open(double open) {
            if (open < 0 || open > 1) {
                throw new IllegalArgumentException("Unexpected open value: " + open
                        + ", passed to Claw.Builder().open(). Valid values are numbers in the interval [0, 1]");
            }
            this.open = open;
            return this;
        }

        /**
         * Specify the close position
         * 
         * @param close close position of the servo(s)
         * @return builder instance
         * @throws IllegalArgumentException if close not in the interval [0, 1]
         */
        public Builder close(double close) {
            if (close < 0 || close > 1) {
                throw new IllegalArgumentException("Unexpected close value: " + close
                        + ", passed to Claw.Builder().close(). Valid values are numbers in the interval [0, 1]");
            }
            this.close = close;
            return this;
        }

        /**
         * Build the claw
         * 
         * @return claw instance
         * @throws IllegalStateException if count != names.length
         * @throws IllegalStateException if smooth = false and one of: increment, incrementDelay has
         *         been set
         * @throws IllegalStateException if open &lt; close
         */
        @Override
        public Claw build() {
            if (this.count != this.names.length) {
                throw new IllegalStateException(
                        "Unexpected array length for array passed to Claw.Builder().names(). The length of this array must be equal to count");
            }
            if (!this.smooth && (this.increment != 0 || this.incrementDelay != 0)) {
                throw new IllegalStateException(
                        "One of: Claw.Builder().increment() or Claw.Builder().incrementDelay() has been set without enabling Claw.Builder().smooth(). Enable Claw.Builder().smooth() for intended functionality");
            }
            if (this.open < this.close) {
                throw new IllegalStateException("Unexpected open and close values: " + this.open
                        + ", " + this.close
                        + ", passed to Claw.Builder().open() and Claw.Builder().close(). Open must be greater than close");
            }
            return new Claw(this);
        }

        /**
         * Return builder instance
         * 
         * @return builder instance
         */
        @Override
        protected Builder self() {
            return this;
        }
    }

    /** Directions that can be passed to {@link Claw#command(direction)} */
    public enum Direction {
        OPEN, CLOSE
    }

    /** Enables teleoperated claw movement with gamepad. */
    @Override
    public void control() {
        double position = servos[0].getPosition();
        double movement = controlToDirection(position, gamepad.b, gamepad.a);
        if (smooth) {
            setPositionsByIncrement(position, movement);
        } else {
            setPositions(movement);
        }
    }

    /**
     * Intermediate function that assigns individual servo positions based on direction specified in
     * runOpMode() calls.
     * <p>
     * Valid directions are: open, close
     */
    @Override
    public void command(Direction direction) {
        double servoDirection = languageToDirection(direction);
        if (smooth) {
            double position = servos[0].getPosition();
            setPositionsByIncrementUntilComplete(position, servoDirection);
        } else {
            setPositions(servoDirection);
            wait(delay);
        }
    }

    /** Set servo movement based on open, close values and current position */
    protected double controlToDirection(double current, boolean openButton, boolean closeButton) {
        double movement;
        if (openButton && !closeButton) {
            movement = open;
        } else if (closeButton && !openButton) {
            movement = close;
        } else { // do nothing otherwise
            movement = current;
        }
        return movement;
    }

    /** Translate natural-language direction to numeric values */
    protected double languageToDirection(Direction direction) {
        if (direction == null) {
            throw new NullPointerException("Null direction passed to Claw.command()");
        }
        switch (direction) {
            case OPEN:
                return open;
            case CLOSE:
                return close;
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction passed to Claw.command(). Valid directions are: Claw.Direction.OPEN, Claw.Direction.CLOSE");
        }
    }
}
