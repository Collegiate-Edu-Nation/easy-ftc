// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.Objects;

/**
 * Blueprints an abstract Servo Mechanism, providing basic functionalities, options, and objects
 * common to all Servo Mechanisms. Cannot be instantiated, only extended by other classes.
 */
abstract class ServoMechanism<E> extends Mechanism {
    protected Servo[] servos;
    protected boolean smooth;
    protected double increment;
    protected double incrementDelay;
    protected double delay;

    /** Constructor */
    protected ServoMechanism(Builder<?> builder) {
        super(builder);
        this.smooth = builder.smooth;
        this.increment = builder.increment;
        this.incrementDelay = builder.incrementDelay;
        this.delay = builder.delay;
    }

    public abstract static class Builder<T extends Builder<T>> extends Mechanism.Builder<T> {
        protected boolean smooth = false;
        protected double increment = 0.0;
        protected double incrementDelay = 0.0;
        protected double delay = 2;

        protected Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Whether to enable smooth-servo control
         * 
         * @return builder instance
         */
        public T smooth() {
            this.smooth = true;
            this.increment = 0.02;
            this.incrementDelay = 0.02;
            this.delay = 0.0;
            return self();
        }

        /**
         * Specify the increment to move by for smooth-servo control
         * 
         * @param increment portion of total range-of-motion moved in each iteration
         * @return builder instance
         * @throws IllegalArgumentException if increment is not in the interval (0, 1]
         */
        public T increment(double increment) {
            if (increment <= 0 || increment > 1) {
                throw new IllegalArgumentException("Unexpected increment value: " + increment
                        + ", passed to ServoMechanism.Builder().increment(). Valid values are numbers in the interval (0, 1]");
            }
            this.increment = increment;
            return self();
        }

        /**
         * Specify the increment delay for smooth-servo control
         * 
         * @param incrementDelay the time (in s) to wait between each increment
         * @return builder instance
         * @throws IllegalArgumentException if incrementDelay &lt;= 0
         */
        public T incrementDelay(double incrementDelay) {
            if (incrementDelay <= 0) {
                throw new IllegalArgumentException("Unexpected incrementDelay value: "
                        + incrementDelay
                        + ", passed to ServoMechanism.Builder().incrementDelay(). Valid values are numbers > 0");
            }
            this.incrementDelay = incrementDelay;
            return self();
        }

        /**
         * Specify the delay for normal servo control
         * 
         * @param delay the time to wait (in s) for servo movements to complete
         * @return builder instance
         * @throws IllegalArgumentException if delay &lt;= 0
         */
        public T delay(double delay) {
            if (delay <= 0) {
                throw new IllegalArgumentException("Unexpected delay value: " + delay
                        + ", passed to ServoMechanism.Builder().delay(). Valid values are numbers > 0");
            }
            this.delay = delay;
            return self();
        }

        public abstract T names(String[] names);

        public abstract ServoMechanism<?> build();

        protected abstract T self();
    }

    public abstract void command(E direction);

    /** Reverse the direction of the specified servo */
    @Override
    protected void reverse(String deviceName) {
        boolean found = false;

        // reverse the device
        for (int i = 0; i < count; i++) {
            if (Objects.equals(deviceName, names[i])) {
                found = true;
                Servo.Direction direction =
                        (i % 2 == 0) ? Servo.Direction.REVERSE : Servo.Direction.FORWARD;
                servos[i].setDirection(direction);
            }
        }

        // throw exception if device not found
        if (!found) {
            StringBuilder bld = new StringBuilder();
            for (String name : names) {
                bld.append(name + ", ");
            }
            String validNames = bld.toString();
            validNames = validNames.substring(0, validNames.length() - 2);
            throw new IllegalArgumentException(
                    "Unexpected deviceName: " + deviceName + ", passed to " + mechanismName
                            + ".Builder().reverse(). Valid names are: " + validNames);
        }
    }

    /** Initializes servos */
    @Override
    protected void init() {
        // Instantiate servos
        servos = new Servo[count];
        for (int i = 0; i < count; i++) {
            servos[i] = hardwareMap.get(Servo.class, names[i]);
        }

        // reverse direction of left servo for convenience
        setDirections(reverse);

        // reverse direction of specified servos
        for (String device : reverseDevices) {
            reverse(device);
        }
    }

    /** Wrapper around setPositions that enables smooth servo control */
    protected double setPositionsByIncrement(double position, double movement) {
        double signedIncrement = (movement > position) ? increment : -increment;
        position += (movement == position) ? 0 : signedIncrement;
        position = Math.min(Math.max(position, 0), 1);
        setPositions(position);
        wait(incrementDelay);
        return position;
    }

    /**
     * Wrapper around setPositionsByIncrement that enables smooth servo movement until the desired
     * position is reached. The loop causes thread-blocking, so it's not used for control() calls
     */
    protected void setPositionsByIncrementUntilComplete(double position, double movement) {
        while (opMode.opModeIsActive() && position != movement) {
            position = setPositionsByIncrement(position, movement);
        }
    }

    /** Wrapper around setPosition for all servos */
    protected void setPositions(double movement) {
        for (Servo servo : servos) {
            servo.setPosition(movement);
        }
    }

    /** Wrapper around setDirection for all servos */
    protected void setDirections(boolean reverse) {
        if (!reverse) {
            for (int i = 0; i < count; i++) {
                Servo.Direction direction =
                        (i % 2 == 0) ? Servo.Direction.FORWARD : Servo.Direction.REVERSE;
                servos[i].setDirection(direction);
            }
        } else {
            for (int i = 0; i < count; i++) {
                Servo.Direction direction =
                        (i % 2 == 0) ? Servo.Direction.REVERSE : Servo.Direction.FORWARD;
                servos[i].setDirection(direction);
            }
        }
    }
}
