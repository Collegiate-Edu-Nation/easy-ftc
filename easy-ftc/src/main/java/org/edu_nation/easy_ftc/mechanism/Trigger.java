// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Implements a trigger by extending the functionality of {@link ServoMechanism}
 *
 * @see Builder
 * @see Direction
 */
public class Trigger extends ServoMechanism<Trigger.Direction> {
    private final double open;
    private final double close;

    /** Constructor */
    private Trigger(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.names = builder.names;
        this.open = builder.open;
        this.close = builder.close;
        MECHANISM_NAME = Builder.MECHANISM_NAME;
        init();
    }

    /**
     * Construct a {@link Trigger} object using the builder design pattern
     *
     * <p><b>Basic Usage:</b>
     *
     * <pre>{@code
     * Trigger trigger = new Trigger.Builder(this, hardwareMap).build();
     * }</pre>
     *
     * <b>Defaults:</b>
     *
     * <ul>
     *   <li>reverse = false
     *   <li>reverseDevices = {}
     *   <li>gamepad = null
     *   <li>smooth = false
     *   <li>increment = 0.02
     *   <li>incrementDelay = 0.02
     *   <li>delay = 2
     *   <li>count = 1
     *   <li>names = {"trigger"}
     *   <li>open = 1.0
     *   <li>close = 0.0
     * </ul>
     */
    @SuppressWarnings("java:S1185")
    public static class Builder extends ServoMechanism.Builder<Builder> {
        private int count = 1;
        private String[] names = {"trigger"};
        private double open = 1.0;
        private double close = 0.0;
        private static final String MECHANISM_NAME = "Trigger";

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

        /**
         * @throws NullPointerException {@inheritDoc}
         */
        @Override
        public Builder reverse(String deviceName) {
            return super.reverse(deviceName);
        }

        /**
         * @throws NullPointerException {@inheritDoc}
         */
        @Override
        public Builder reverse(String[] deviceNames) {
            return super.reverse(deviceNames);
        }

        /**
         * @throws NullPointerException {@inheritDoc}
         */
        @Override
        public Builder gamepad(Gamepad gamepad) {
            return super.gamepad(gamepad);
        }

        // methods inherited from ServoMechanism.Builder
        @Override
        public Builder smooth() {
            return super.smooth();
        }

        /**
         * @throws IllegalArgumentException {@inheritDoc}
         */
        @Override
        public Builder increment(double increment) {
            return super.increment(increment);
        }

        /**
         * @throws IllegalArgumentException {@inheritDoc}
         */
        @Override
        public Builder incrementDelay(double incrementDelay) {
            return super.incrementDelay(incrementDelay);
        }

        /**
         * @throws IllegalArgumentException {@inheritDoc}
         */
        @Override
        public Builder delay(double delay) {
            return super.delay(delay);
        }

        // trigger-specific methods
        /**
         * Specify the number of servos
         *
         * @param count the number of servos in the trigger mechanism
         * @return builder instance
         * @throws IllegalArgumentException if count isn't 1 or 2
         */
        public Builder count(int count) {
            if (count < 1 || count > 2) {
                throw new IllegalArgumentException(
                        "Unexpected count value: "
                                + count
                                + ", passed to Trigger.Builder().count(). Valid values are integers in the interval [1, 2]");
            }
            this.count = count;
            if (count == 2) {
                this.names = new String[] {"triggerLeft", "triggerRight"};
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
                throw new NullPointerException("Null names passed to Trigger.Builder().names()");
            }
            this.names = names;
            return this;
        }

        /**
         * Specify the position for {@link Direction OPEN}
         *
         * @param open position for {@link Direction OPEN}
         * @return builder instance
         * @throws IllegalArgumentException if open not in the interval [0, 1]
         */
        public Builder open(double open) {
            if (open < 0 || open > 1) {
                throw new IllegalArgumentException(
                        "Unexpected open value: "
                                + open
                                + ", passed to Trigger.Builder().open(). Valid values are numbers in the interval [0, 1]");
            }
            this.open = open;
            return this;
        }

        /**
         * Specify the position for {@link Direction CLOSE}
         *
         * @param close position for {@link Direction CLOSE}
         * @return builder instance
         * @throws IllegalArgumentException if close not in the interval [0, 1]
         */
        public Builder close(double close) {
            if (close < 0 || close > 1) {
                throw new IllegalArgumentException(
                        "Unexpected close value: "
                                + close
                                + ", passed to Trigger.Builder().close(). Valid values are numbers in the interval [0, 1]");
            }
            this.close = close;
            return this;
        }

        /**
         * Build the trigger
         *
         * @return trigger instance
         * @throws IllegalStateException if count != names.length
         * @throws IllegalStateException if smooth = false and one of: increment, incrementDelay has
         *     been set
         * @throws IllegalStateException if open &lt;= close
         */
        @Override
        public Trigger build() {
            if (this.count != this.names.length) {
                throw new IllegalStateException(
                        "Unexpected array length for array passed to Trigger.Builder().names(). The length of this array must be equal to count");
            }
            if (!this.smooth && (this.increment != 0 || this.incrementDelay != 0)) {
                throw new IllegalStateException(
                        "One of: Trigger.Builder().increment() or Trigger.Builder().incrementDelay() has been set without enabling Trigger.Builder().smooth(). Enable Trigger.Builder().smooth() for intended functionality");
            }
            if (this.open <= this.close) {
                throw new IllegalStateException(
                        "Unexpected open and close values: "
                                + this.open
                                + ", "
                                + this.close
                                + ", passed to Trigger.Builder().open() and Trigger.Builder().close(). Open must be greater than close");
            }
            return new Trigger(this);
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

    /** Directions that can be passed to {@link Trigger#command(direction)} */
    public enum Direction {
        OPEN,
        CLOSE
    }

    /** Enable teleoperated trigger movement with gamepad (y, x) */
    @Override
    public void control() {
        double position = servos[0].getPosition();
        double movement = controlToDirection(position, gamepad.y, gamepad.x);
        if (smooth) {
            setPositionsByIncrement(position, movement);
        } else {
            setPositions(movement);
        }
    }

    /**
     * Initiate an automated trigger movement
     *
     * @param direction direction to move the mechanism; see {@link Direction} for accepted values
     * @throws NullPointerException if direction is null
     * @throws IllegalArgumentException if direction is an unexpected value
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

    /** Helper to call command with casted direction */
    @Override
    protected void commandGeneric(Object direction) {
        command((Direction) direction);
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
            throw new NullPointerException("Null direction passed to Trigger.command()");
        }
        switch (direction) {
            case OPEN:
                return open;
            case CLOSE:
                return close;
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction passed to Trigger.command(). Valid directions are: Trigger.Direction.OPEN, Trigger.Direction.CLOSE");
        }
    }
}
