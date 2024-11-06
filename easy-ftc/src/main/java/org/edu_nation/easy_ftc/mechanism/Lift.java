// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.lang.Math;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

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
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double measurement)}
 */
public class Lift extends MotorMechanism {

    /**
     * Constructor
     */
    private Lift(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.names = builder.names;
        this.behavior = builder.behavior;
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 1;
        private String[] names = {"lift"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;
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
         * Build the lift
         */
        @Override
        public Lift build() {
            return new Lift(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    /**
     * Enables teleoperated lift movement with gamepad, scaling by multiplier < 1
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele(double multiplier) {
        double[] movements = new double[count];
        double direction =
                LiftUtil.controlToDirection(deadzone, gamepad.left_trigger, gamepad.right_trigger);
        for (int i = 0; i < count; i++) {
            movements[i] = direction;
        }

        if (multiplier == 1.0) {
            setPowers(movements);
        } else {
            double[] scaledMovements = MotorMechanismUtil
                    .scaleDirections(Math.min(Math.abs(multiplier), 1), movements);
            setPowers(scaledMovements);
        }
    }

    /**
     * Enables teleoperated lift movement with gamepad with multiplier = 1.0
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        tele(1.0);
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
        double movement = LiftUtil.languageToDirection(direction);
        double[] unscaledMovements = new double[count];
        for (int i = 0; i < count; i++) {
            unscaledMovements[i] = movement;
        }
        double[] movements = LiftUtil.scaleDirections(power, unscaledMovements);

        if (diameter == 0.0) {
            setPowers(movements);
            wait(measurement);
            setPowers();
        } else {
            int[] positions = LiftUtil.calculatePositions(measurement, diameter, distanceMultiplier,
                    unscaledMovements);
            int[] currentPositions = getCurrentPositions();

            // move the motors at power until they've reached the position
            setPositions(positions, currentPositions);
            setPowers(movements);
            while (motorsAreBusy()) {
                setPowers(movements);
            }
            setPowers();

            // Reset motors to run using velocity (allows for using move() w/ diameter along w/
            // tele())
            setModesEx(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }
}
