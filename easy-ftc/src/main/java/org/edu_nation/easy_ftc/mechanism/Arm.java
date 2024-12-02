// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.util.Arrays;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Implements an arm by extending the functionality of {@link MotorMechanism}.
 * <ul>
 * <li>See {@link Builder} for Builder methods and defaults.
 * <li>See {@link Direction} for directions that can be passed to
 * {@link #command(direction, measurement, power)}.
 * </ul>
 */
public class Arm extends MotorMechanism<Arm.Direction> {
    /** Constructor */
    private Arm(Builder builder) {
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
     * Arm Builder
     * <p>
     * <b>Defaults</b>:
     * <ul>
     * <li>count = 1
     * <li>names = {"arm"}
     * <li>behavior = BRAKE
     * <li>encoder = false
     * <li>reverse = false
     * <li>reverseDevices = {}
     * <li>length = 0.0
     * <li>gearing = 0.0
     * <li>gamepad = null
     * </ul>
     */
    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 1;
        private String[] names = {"arm"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.BRAKE;
        private double up = 0.0;
        private double down = 0.0;
        private String mechanismName = "Arm";

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
        public Builder length(double length) {
            return super.length(length);
        }

        @Override
        public Builder gearing(double gearing) {
            return super.gearing(gearing);
        }

        // arm-specific methods
        /**
         * Specify the number of motors
         * 
         * @param count the number of motors in the arm mechanism
         * @return builder instance
         * @throws IllegalArgumentException if count isn't 1 or 2
         */
        public Builder count(int count) {
            if (count < 1 || count > 2) {
                throw new IllegalArgumentException("Unexpected count value: " + count
                        + ", passed to Arm.Builder().count(). Valid values are integers in the interval [1, 2]");
            }
            this.count = count;
            if (count == 2) {
                String[] newNames = {"armLeft", "armRight"};
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
                throw new NullPointerException("Null names passed to Arm.Builder().names()");
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
                throw new NullPointerException("Null behavior passed to Arm.Builder().behavior()");
            }
            this.behavior = behavior;
            return this;
        }

        /**
         * Specify the positional limit for the UP direction
         * 
         * @param up positional limit for UP
         * @return builder instance
         */
        public Builder up(double up) {
            this.up = up;
            return this;
        }

        /**
         * Specify the positional limit for the DOWN direction
         * 
         * @param down positional limit for DOWN
         * @return builder instance
         */
        public Builder down(double down) {
            this.down = down;
            return this;
        }

        /**
         * Build the arm
         * 
         * @return arm instance
         * @throws IllegalStateException if count != names.length
         * @throws IllegalStateException if encoder = false and one of: diameter, length, or gearing has been set
         * @throws IllegalStateException if up &lt; down
         */
        @Override
        public Arm build() {
            if (this.count != this.names.length) {
                throw new IllegalStateException(
                        "Unexpected array length for array passed to Arm.Builder().names(). The length of this array must be equal to count");
            }
            if (!this.encoder
                    && (this.diameter != 0 || this.length != 0 || this.gearing != 0)) {
                throw new IllegalStateException(
                        "One of: Arm.Builder().diameter(), Arm.Builder().length(), or Arm.Builder().gearing() has been set without enabling Arm.Builder().encoder(). Enable Arm.Builder().encoder()");
            }
            if (this.up < this.down) {
                throw new IllegalStateException("Unexpected up and down values: " + this.up + ", "
                        + this.down
                        + ", passed to Arm.Builder().up() and Arm.Builder().down(). Up must be greater than down");
            }
            return new Arm(this);
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

    /** Directions that can be passed to command. */
    public enum Direction {
        UP, DOWN
    }

    /** Enables teleoperated arm movement with gamepad at the specified power. */
    @Override
    public void control(double power) {
        validate(power);
        double[] unscaledMovements = new double[count];
        double[] movements = new double[count];
        double direction = controlToDirection(gamepad.left_bumper, gamepad.right_bumper);
        Arrays.fill(unscaledMovements, direction);
        Arrays.fill(movements, power * direction);

        // setPowers if up and down limits haven't been specified
        if (up == down) {
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

    /** Enables teleoperated arm movement with gamepad at a power of 0.5. */
    @Override
    public void control() {
        control(0.5);
    }

    /**
     * Intermediate function that assigns individual motor powers based on direction specified in
     * runOpMode() calls.
     * <p>
     * Valid directions are: up, down
     */
    @Override
    public void command(Direction direction, double measurement, double power) {
        validate(measurement, power);
        double movement = languageToDirection(direction);
        double[] unscaledMovements = new double[count];
        Arrays.fill(unscaledMovements, movement);
        moveForMeasurement(unscaledMovements, measurement, power, up != down);
    }

    /** Sets arm motor movements based on bumpers */
    protected static double controlToDirection(boolean lb, boolean rb) {
        double down = lb ? 1 : 0;
        double up = rb ? 1 : 0;
        return up - down;
    }

    /** Translate natural-language direction to numeric values */
    protected static double languageToDirection(Direction direction) {
        if (direction == null) {
            throw new NullPointerException("Null direction passed to Arm.command()");
        }
        switch (direction) {
            case UP:
                return 1;
            case DOWN:
                return -1;
            default:
                throw new IllegalArgumentException(
                        "Unexpected direction passed to Arm.command(). Valid direction are: Arm.Direction.UP, Arm.Direction.DOWN");
        }
    }
}
