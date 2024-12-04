// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.util.Arrays;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements a lift by extending the functionality of {@link MotorMechanism}
 * 
 * @see Builder
 * @see Direction
 */
public class Lift extends MotorMechanism<Lift.Direction> {
    /** Constructor */
    private Lift(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.names = builder.names;
        this.behavior = builder.behavior;
        this.up = builder.up;
        this.down = builder.down;
        this.mechanismName = builder.mechanismName;
        init();
    }

    /**
     * Construct a Lift object using the Builder design pattern
     * <p>
     * <b>Basic Usage:</b>
     * 
     * <pre>
     * {@code
     * Lift lift = new Lift.Builder(this, hardwareMap).build();
     * }
     * </pre>
     * 
     * <b>Defaults:</b>
     * <ul>
     * <li>reverse = false
     * <li>reverseDevices = {}
     * <li>gamepad = null
     * <li>encoder = false
     * <li>diameter = 0.0
     * <li>gearing = 0.0
     * <li>deadzone = 0.0
     * <li>count = 1
     * <li>names = {"lift"}
     * <li>behavior = FLOAT
     * <li>up = 0.0
     * <li>down = 0.0
     * </ul>
     */
    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 1;
        private String[] names = {"lift"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;
        private double up = 0.0;
        private double down = 0.0;
        private String mechanismName = "Lift";

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

        // methods inherited from MotorMechanism.Builder
        @Override
        public Builder encoder() {
            return super.encoder();
        }

        @Override
        public Builder diameter(double diameter) {
            return super.diameter(diameter);
        }

        @Override
        public Builder gearing(double gearing) {
            return super.gearing(gearing);
        }

        @Override
        public Builder deadzone(double deadzone) {
            return super.deadzone(deadzone);
        }

        // lift-specific methods
        /**
         * Specify the number of motors
         * 
         * @param count the number of motors in the lift mechanism
         * @return builder instance
         * @throws IllegalArgumentException if count isn't 1 or 2
         */
        public Builder count(int count) {
            if (count < 1 || count > 2) {
                throw new IllegalArgumentException("Unexpected count value: " + count
                        + ", passed to Lift.Builder().count(). Valid values are integers in the interval [1, 2]");
            }
            this.count = count;
            if (count == 2) {
                String[] newNames = {"liftLeft", "liftRight"};
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
                throw new NullPointerException("Null names passed to Lift.Builder().names()");
            }
            this.names = names;
            return this;
        }

        /**
         * Specify the zero-power behavior of the motors
         * 
         * @param behavior the zero-power behavior, one of ZeroPowerBehavior.BRAKE or FLOAT
         * @return builder instance
         * @throws NullPointerException if behavior is null
         */
        public Builder behavior(DcMotor.ZeroPowerBehavior behavior) {
            if (behavior == null) {
                throw new NullPointerException("Null behavior passed to Lift.Builder().behavior()");
            }
            this.behavior = behavior;
            return this;
        }

        /**
         * Specify the positional limit for {@link Direction UP}
         * 
         * @param up positional limit for {@link Direction UP}
         * @return builder instance
         */
        public Builder up(double up) {
            this.up = up;
            return this;
        }

        /**
         * Specify the positional limit for {@link Direction DOWN}
         * 
         * @param down positional limit for {@link Direction DOWN}
         * @return builder instance
         */
        public Builder down(double down) {
            this.down = down;
            return this;
        }

        /**
         * Build the lift
         * 
         * @return lift instance
         * @throws IllegalStateException if count != names.length
         * @throws IllegalStateException if encoder = false and one of: diameter, length, or gearing
         *         has been set
         * @throws IllegalStateException if up &lt; down
         */
        @Override
        public Lift build() {
            if (this.count != this.names.length) {
                throw new IllegalStateException(
                        "Unexpected array length for array passed to Lift.Builder().names(). The length of this array must be equal to count");
            }
            if (!this.encoder && (this.diameter != 0 || this.length != 0 || this.gearing != 0)) {
                throw new IllegalStateException(
                        "One of: Lift.Builder().diameter(), Lift.Builder().length(), or Lift.Builder().gearing() has been set without enabling Lift.Builder().encoder(). Enable Lift.Builder().encoder()");
            }
            if (this.up < this.down) {
                throw new IllegalStateException("Unexpected up and down values: " + this.up + ", "
                        + this.down
                        + ", passed to Lift.Builder().up() and Lift.Builder().down(). Up must be greater than down");
            }
            return new Lift(this);
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

    /** Directions that can be passed to {@link Lift#command(direction, measurement, power)} */
    public enum Direction {
        UP, DOWN
    }

    /**
     * Enable teleoperated lift movement with gamepad (lt, rt), scaling by multiplier
     *
     * @param multiplier fraction of total power/velocity to use for mechanism control at given
     *        input value
     * @throws IllegalArgumentException if multiplier is not in the interval (0, 1]
     */
    @Override
    public void control(double multiplier) {
        validate(multiplier);
        double[] movements = new double[count];
        double[] unscaledMovements = new double[count];
        double direction = controlToDirection(gamepad.left_trigger, gamepad.right_trigger);
        Arrays.fill(movements, direction);
        if (direction != 0) {
            direction = (direction > 0) ? 1 : -1;
        }
        Arrays.fill(unscaledMovements, direction);

        // set powers if up and down limits haven't been specified
        if (up == down) {
            setPowers(movements, multiplier);
        } else {
            // setPowers if limits have not been reached
            if (limitsNotReached(direction, unscaledMovements)) {
                setPowers(movements, multiplier);
            } else {
                setPowers();
            }
        }
    }

    /** Enable teleoperated lift movement with gamepad (lt, rt), with multiplier = 1.0 */
    @Override
    public void control() {
        control(1.0);
    }

    /**
     * Initiate an automated lift movement
     * 
     * @param direction direction to move the mechanism; see {@link Direction} for accepted values
     * @param measurement time(s) or distance to move the mechanism
     * @param power fraction of total power/velocity to use for mechanism command
     * @throws NullPointerException if direction is null
     * @throws IllegalArgumentException if direction is an unexpected value
     * @throws IllegalArgumentException if measurement &lt; 0
     * @throws IllegalArgumentException if power is not in the interval (0, 1]
     */
    @Override
    public void command(Direction direction, double measurement, double power) {
        validate(measurement, power);
        double movement = languageToDirection(direction);
        double[] unscaledMovements = new double[count];
        Arrays.fill(unscaledMovements, movement);
        moveForMeasurement(unscaledMovements, measurement, power, up != down);
    }

    /** Set lift motor movements based on triggers */
    protected double controlToDirection(float lt, float rt) {
        return map(rt) - map(lt);
    }

    /** Translate natural-language direction to numeric values */
    protected double languageToDirection(Direction direction) {
        if (direction == null) {
            throw new NullPointerException("Null direction passed to Lift.command()");
        }
        switch (direction) {
            case UP:
                return 1;
            case DOWN:
                return -1;
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction passed to Lift.command(). Valid directions are: Lift.Direction.UP, Lift.Direction.DOWN");
        }
    }
}
