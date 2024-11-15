// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Implements a claw by extending the functionality of {@link ServoMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer count (1 or 2)
 * @param String[] names
 * @param Boolean smooth
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double open (0-1)
 * @param Double close (0-1)
 * @param Double increment (0-1)
 * @param Double incrementDelay (> 0, in s)
 * @param Double delay (> 0, in s)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 *        <p>
 * @Methods {@link #control()}
 *          <li>{@link #command(String direction)}
 */
public class Claw extends ServoMechanism<Claw.Direction> {
    private double open, close;

    /**
     * Constructor
     */
    private Claw(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.names = builder.names;
        this.open = builder.open;
        this.close = builder.close;
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder extends ServoMechanism.Builder<Builder> {
        protected int count = 1;
        protected String[] names = {"claw"};
        protected double open = 1.0;
        protected double close = 0.0;
        private String mechanismName = "Claw";

        /**
         * Claw Builder
         * 
         * @Defaults count = 1
         *           <li>names = {"claw"}
         *           <li>smooth = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>open = 1.0
         *           <li>close = 0.0
         *           <li>increment = 0.02
         *           <li>incrementDelay = 0.02
         *           <li>delay = 2
         *           <li>gamepad = null
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Specify the number of servos (1-2)
         */
        public Builder count(int count) {
            this.count = count;
            if (count == 2) {
                String[] names = {"clawLeft", "clawRight"};
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
         * Specify the open posiiton of the servo(s) (0-1)
         */
        public Builder open(double open) {
            this.open = open;
            return this;
        }

        /**
         * Specify the close position of the servo(s) (0-1)
         */
        public Builder close(double close) {
            this.close = close;
            return this;
        }

        /**
         * Build the claw
         */
        @Override
        public Claw build() {
            return new Claw(this);
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
        OPEN, CLOSE
    }

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void control() {
        double position = servos[0].getPosition();
        double movement = controlToDirection(open, close, position, gamepad.b, gamepad.a);
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
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Valid directions are: open, close
     */
    @Override
    public void command(Direction direction) {
        double servoDirection = languageToDirection(direction, open, close, mechanismName);
        if (smooth) {
            double position = servos[0].getPosition();
            setPositionsByIncrementUntilComplete(position, servoDirection);
        } else {
            setPositions(servoDirection);
            wait(delay);
        }
    }

    /**
     * Set servo movement based on open, close values as well as current position
     */
    protected static double controlToDirection(double open, double close, double current,
            boolean openButton, boolean closeButton) {
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

    /**
     * Translate natural-language direction to numeric values
     */
    protected static double languageToDirection(Direction direction, double open, double close,
            String mechanismName) {
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
