// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.util.Arrays;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Implements a lift by extending the functionality of {@link MotorMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer count (1-2)
 * @param String[] names
 * @param Boolean encoder
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double diameter (> 0.0)
 * @param Double gearing (> 0.0)
 * @param Double deadzone (>= 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #control()}
 *          <li>{@link #command(double power, String direction, double measurement)}
 */
public class Lift extends MotorMechanism<Lift.Direction> {
    /**
     * Constructor
     */
    private Lift(Builder builder) {
        super(builder);
        this.count = builder.count;
        if (builder.count != builder.names.length) {
            throw new IllegalStateException(
                    "Unexpected array length for array passed to Lift.Builder.names(). The length of this array must be equal to count");
        }
        this.names = builder.names;
        this.behavior = builder.behavior;
        if (builder.up < builder.down) {
            throw new IllegalStateException("Unexpected up and down values: " + builder.up + ", "
                    + builder.down
                    + ", passed to Lift.Builder.up() and Lift.Builder.down(). Up must be greater than down");
        }
        this.up = builder.up;
        this.down = builder.down;
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 1;
        private String[] names = {"lift"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;
        private double up = 0.0;
        private double down = 0.0;
        private String mechanismName = "Lift";

        /**
         * Lift Builder
         * 
         * @Defaults count = 1
         *           <li>names = {"lift"}
         *           <li>behavior = FLOAT
         *           <li>encoder = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>diameter = 0.0
         *           <li>gearing = 0.0
         *           <li>deadzone = 0.0
         *           <li>gamepad = null
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Specify the number of motors (1-2)
         */
        public Builder count(int count) {
            if (count < 1 || count > 2) {
                throw new IllegalArgumentException("Unexpected count value: " + count
                        + ", passed to Lift.Builder.count(). Valid values are integers in the interval [1, 2]");
            }
            this.count = count;
            if (count == 2) {
                String[] names = {"liftLeft", "liftRight"};
                this.names = names;
            }
            return this;
        }

        /**
         * Change the names of the hardware devices
         */
        public Builder names(String[] names) {
            if (names == null) {
                throw new NullPointerException("Null names passed to Lift.Builder.names()");
            }
            this.names = names;
            return this;
        }

        /**
         * Specify the zero-power behavior of the motors (DcMotor.ZeroPowerBehavior.BRAKE or FLOAT)
         */
        public Builder behavior(DcMotor.ZeroPowerBehavior behavior) {
            if (behavior == null) {
                throw new NullPointerException("Null behavior passed to Lift.Builder.behavior()");
            }
            this.behavior = behavior;
            return this;
        }

        /**
         * Specify the positional limit for the "up" direction
         */
        public Builder up(double up) {
            this.up = up;
            return this;
        }

        /**
         * Specify the positional limit for the "down" direction
         */
        public Builder down(double down) {
            this.down = down;
            return this;
        }

        /**
         * Build the lift
         */
        @Override
        public Lift build() {
            return new Lift(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    /**
     * Directions that can be passed to command
     */
    public enum Direction {
        UP, DOWN
    }

    /**
     * Enables teleoperated lift movement with gamepad, scaling by multiplier < 1
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void control(double multiplier) {
        validate(multiplier);
        double[] movements = new double[count];
        double[] unscaledMovements = new double[count];
        double direction =
                controlToDirection(deadzone, gamepad.left_trigger, gamepad.right_trigger);
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

    /**
     * Enables teleoperated lift movement with gamepad with multiplier = 1.0
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void control() {
        control(1.0);
    }

    /**
     * Intermediate function that assigns individual motor powers based on direction specified in
     * runOpMode() calls.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: up, down
     */
    @Override
    public void command(double power, Direction direction, double measurement) {
        validate(power, measurement);
        double movement = languageToDirection(direction);
        double[] unscaledMovements = new double[count];
        for (int i = 0; i < count; i++) {
            unscaledMovements[i] = movement;
        }
        moveForMeasurement(unscaledMovements, power, measurement);
    }

    /**
     * Set lift motor movements based on triggers
     */
    protected static double controlToDirection(double deadzone, float lt, float rt) {
        double lift = map(rt, deadzone) - map(lt, deadzone);
        return lift;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double languageToDirection(Direction direction) {
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
                        "Unexpected direction passed to Lift.command(). Valid direction are: Lift.Direction.UP, Lift.Direction.DOWN");
        }
    }
}
