// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

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
    private double up;
    private double down;

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
        public Builder self() {
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
        double[] movements = new double[count];
        double direction =
                ArmUtil.controlToDirection(power, gamepad.left_bumper, gamepad.right_bumper);
        for (int i = 0; i < count; i++) {
            movements[i] = direction;
        }

        // setPowers if up and down limits haven't been specified
        if (up == down) {
            setPowers(movements);
        } else {
            int[] currentPositions = getCurrentPositions();
            boolean move = true;

            // determine if positional limits have been reached
            if (length == 0.0) {
                if (direction > 0) {
                    for (int position : currentPositions) {
                        move = (position < up) ? true : false;
                        if (!move) {
                            break;
                        }
                    }
                } else if (direction < 0) {
                    for (int position : currentPositions) {
                        move = (position > down) ? true : false;
                        if (!move) {
                            break;
                        }
                    }
                }
            } else {
                if (direction > 0) {
                    int[] positions = ArmUtil.calculatePositions(up, 2.0 * length,
                            distanceMultiplier, movements);
                    for (int i = 0; i < count; i++) {
                        move = (currentPositions[i] < positions[i]) ? true : false;
                        if (!move) {
                            break;
                        }
                    }
                } else if (direction < 0) {
                    int[] positions = ArmUtil.calculatePositions(down, 2.0 * length,
                            distanceMultiplier, movements);
                    for (int i = 0; i < count; i++) {
                        move = (currentPositions[i] > positions[i]) ? true : false;
                        if (!move) {
                            break;
                        }
                    }
                }
            }

            // setPowers if not
            if (move) {
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
        double movement = ArmUtil.languageToDirection(direction);
        double[] unscaledMovements = new double[count];
        for (int i = 0; i < count; i++) {
            unscaledMovements[i] = movement;
        }
        double[] movements = ArmUtil.scaleDirections(power, unscaledMovements);

        if (length == 0.0) {
            setPowers(movements);
            wait(measurement);
            setPowers();
        } else {
            // length is the radius of arm's ROM, so double it for arc length = distance
            int[] positions = ArmUtil.calculatePositions(measurement, 2.0 * length,
                    distanceMultiplier, unscaledMovements);
            int[] currentPositions = getCurrentPositions();

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setPowers(movements);
            while (motorsAreBusy()) {
                setPowers(movements);
            }
            setPowers();

            // Reset motors to run using velocity (allows for using move() w/ length along w/
            // tele())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }
}
