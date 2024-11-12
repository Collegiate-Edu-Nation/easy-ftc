// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.util.Arrays;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Implements an arm by extending the functionality of {@link MotorMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer count (1-2)
 * @param String[] names
 * @param Boolean encoder
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double length (> 0.0)
 * @param Double gearing (> 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #tele(double power)}
 *          <li>{@link #tele()} (defaults to 0.5 power if nothing is passed)
 *          <li>{@link #move(double power, String direction, double measurement)}
 */
public class Arm extends MotorMechanism {
    /**
     * Constructor
     */
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

    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 1;
        private String[] names = {"arm"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.BRAKE;
        private double up = 0.0;
        private double down = 0.0;
        private String mechanismName = "Arm";

        /**
         * Arm Builder
         * 
         * @Defaults count = 1
         *           <li>names = {"arm"}
         *           <li>behavior = BRAKE
         *           <li>encoder = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>length = 0.0
         *           <li>gearing = 0.0
         *           <li>gamepad = null
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Specify the number of motors (1-2)
         */
        public Builder count(int count) {
            this.count = count;
            if (count == 2) {
                String[] names = {"armLeft", "armRight"};
                this.names = names;
            }
            return this;
        }

        /**
         * Change the names of the hardware devices
         */
        public Builder names(String[] names) {
            this.names = names;
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
         * Build the arm
         */
        @Override
        public Arm build() {
            return new Arm(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    /**
     * Enables teleoperated arm movement with gamepad at a specified power (defaults to 0.5).
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele(double power) {
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

    /**
     * Enables teleoperated arm movement with gamepad at a power of 0.5 (this is the default case).
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        tele(0.5);
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
    public void move(double power, String direction, double measurement) {
        double movement = languageToDirection(direction);
        double[] unscaledMovements = new double[count];
        for (int i = 0; i < count; i++) {
            unscaledMovements[i] = movement;
        }
        moveForMeasurement(unscaledMovements, power, measurement);
    }

    /**
     * Sets arm motor movements based on bumpers
     */
    protected static double controlToDirection(boolean lb, boolean rb) {
        int down = lb ? 1 : 0;
        int up = rb ? 1 : 0;
        double arm = up - down;
        return arm;
    }

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double languageToDirection(String direction) {
        double motorDirection;
        switch (direction) {
            case "up":
                motorDirection = 1;
                break;
            case "down":
                motorDirection = -1;
                break;
            default:
                throw new IllegalArgumentException("Unexpected direction: " + direction
                        + ", passed to Arm.move(). Valid directions are: up, down");
        }
        return motorDirection;
    }
}
