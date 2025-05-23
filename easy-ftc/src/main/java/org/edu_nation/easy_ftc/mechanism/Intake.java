// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.Arrays;

/**
 * Implements an intake by extending the functionality of {@link MotorMechanism}
 *
 * @see Builder
 * @see Direction
 */
public class Intake extends MotorMechanism<Intake.Direction> {
    /** Constructor */
    private Intake(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.names = builder.names;
        this.behavior = builder.behavior;
        this.dir1 = builder.dir1;
        this.dir2 = builder.dir2;
        MECHANISM_NAME = Builder.MECHANISM_NAME;
        init();
    }

    /**
     * Construct an {@link Intake} object using the builder design pattern
     *
     * <p><b>Basic Usage:</b>
     *
     * <pre>{@code
     * Intake intake = new Intake.Builder(this, hardwareMap).build();
     * }</pre>
     *
     * <b>Defaults:</b>
     *
     * <ul>
     *   <li>reverse = false
     *   <li>reverseDevices = {}
     *   <li>gamepad = null
     *   <li>encoder = false
     *   <li>diameter = 0.0
     *   <li>length = 0.0
     *   <li>gearing = 0.0
     *   <li>count = 1
     *   <li>names = {"intake"}
     *   <li>behavior = BRAKE
     *   <li>dir1 = 0.0
     *   <li>dir2 = 0.0
     * </ul>
     */
    @SuppressWarnings("java:S1185")
    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 1;
        private String[] names = {"intake"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.BRAKE;
        private double dir1 = 0.0;
        private double dir2 = 0.0;
        private static final String MECHANISM_NAME = "Intake";

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

        // methods inherited from MotorMechanism.Builder
        @Override
        public Builder encoder() {
            return super.encoder();
        }

        /**
         * @throws IllegalArgumentException {@inheritDoc}
         */
        @Override
        public Builder diameter(double diameter) {
            return super.diameter(diameter);
        }

        /**
         * @throws IllegalArgumentException {@inheritDoc}
         */
        @Override
        public Builder gearing(double gearing) {
            return super.gearing(gearing);
        }

        // intake-specific methods
        /**
         * Specify the number of motors
         *
         * @param count the number of motors in the intake mechanism
         * @return builder instance
         * @throws IllegalArgumentException if count isn't 1 or 2
         */
        public Builder count(int count) {
            if (count < 1 || count > 2) {
                throw new IllegalArgumentException(
                        "Unexpected count value: "
                                + count
                                + ", passed to Intake.Builder().count(). Valid values are integers in the interval [1, 2]");
            }
            this.count = count;
            if (count == 2) {
                this.names = new String[] {"intakeLeft", "intakeRight"};
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
                throw new NullPointerException("Null names passed to Intake.Builder().names()");
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
                throw new NullPointerException(
                        "Null behavior passed to Intake.Builder().behavior()");
            }
            this.behavior = behavior;
            return this;
        }

        /**
         * Specify the positional limit for {@link Direction IN}
         *
         * @param in positional limit for {@link Direction IN}
         * @return builder instance
         */
        public Builder in(double in) {
            this.dir1 = in;
            return this;
        }

        /**
         * Specify the positional limit for {@link Direction OUT}
         *
         * @param out positional limit for {@link Direction OUT}
         * @return builder instance
         */
        public Builder out(double out) {
            this.dir2 = out;
            return this;
        }

        /**
         * Build the intake
         *
         * @return intake instance
         * @throws IllegalStateException if count != names.length
         * @throws IllegalStateException if encoder = false and one of: diameter, length, or gearing
         *     has been set
         * @throws IllegalStateException if dir1 &lt; dir2
         */
        @Override
        public Intake build() {
            if (this.count != this.names.length) {
                throw new IllegalStateException(
                        "Unexpected array length for array passed to Intake.Builder().names(). The length of this array must be equal to count");
            }
            if (!this.encoder && (this.diameter != 0 || this.length != 0 || this.gearing != 0)) {
                throw new IllegalStateException(
                        "One of: Intake.Builder().diameter(), Intake.Builder().length(), or Intake.Builder().gearing() has been set without enabling Intake.Builder().encoder(). Enable Intake.Builder().encoder()");
            }
            if (this.dir1 < this.dir2) {
                throw new IllegalStateException(
                        "Unexpected in and out values: "
                                + this.dir1
                                + ", "
                                + this.dir2
                                + ", passed to Intake.Builder().in() and Intake.Builder().out(). In must be greater than out");
            }
            return new Intake(this);
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

    /** Directions that can be passed to {@link Intake#command(direction, measurement, power)} */
    public enum Direction {
        IN,
        OUT
    }

    /**
     * Enable teleoperated intake movement with gamepad (dpadUp, dpadDown) at the specified power
     *
     * @param power fraction of total power/velocity to use for mechanism control
     * @throws IllegalArgumentException if power is not in the interval (0, 1]
     */
    @Override
    public void control(double power) {
        validate(power);
        double[] unscaledMovements = new double[count];
        double[] movements = new double[count];
        double direction = controlToDirection(gamepad.dpad_up, gamepad.dpad_down);
        Arrays.fill(unscaledMovements, direction);
        Arrays.fill(movements, power * direction);

        // setPowers if limits haven't been specified
        if (dir1 == dir2) {
            setPowers(movements);
        } else {
            // setPowers if limits have not been reached
            if (limitsNotReached(direction, unscaledMovements)) {
                setPowers(movements);
            } else {
                setPowers();
            }
        }
    }

    /** Enable teleoperated intake movement with gamepad (dpadUp, dpadDown) at a power of 0.5 */
    @Override
    public void control() {
        control(0.5);
    }

    /**
     * Initiate an automated intake movement
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
        moveForMeasurement(unscaledMovements, measurement, power, dir1 != dir2);
    }

    /** Sets intake motor movements based on dpad up, down */
    protected double controlToDirection(boolean dpadUp, boolean dpadDown) {
        double out = dpadUp ? 1 : 0;
        double in = dpadDown ? 1 : 0;
        return in - out;
    }

    /** Translate natural-language direction to numeric values */
    protected double languageToDirection(Direction direction) {
        if (direction == null) {
            throw new NullPointerException("Null direction passed to Intake.command()");
        }
        switch (direction) {
            case IN:
                return 1;
            case OUT:
                return -1;
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction passed to Intake.command(). Valid directions are: Intake.Direction.IN, Intake.Direction.OUT");
        }
    }
}
