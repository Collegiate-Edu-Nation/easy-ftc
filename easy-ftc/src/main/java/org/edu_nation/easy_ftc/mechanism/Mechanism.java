// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.Arrays;

/**
 * Blueprints an abstract Mechanism, providing basic functionalities, options, and objects common to
 * all Mechanisms. Cannot be instantiated; only extended by other abstract classes.
 */
abstract class Mechanism {
    protected LinearOpMode opMode;
    protected HardwareMap hardwareMap;
    protected int count;
    protected String[] names;
    protected boolean reverse;
    protected String[] reverseDevices;
    protected Gamepad gamepad;
    protected ElapsedTime timer = new ElapsedTime();
    protected String mechanismName;

    /** Constructor */
    protected Mechanism(Builder<?> builder) {
        this.opMode = builder.opMode;
        this.hardwareMap = builder.hardwareMap;
        this.reverse = builder.reverse;
        this.reverseDevices = builder.reverseDevices;
        this.gamepad = builder.gamepad;
    }

    public abstract static class Builder<T extends Builder<T>> {
        private LinearOpMode opMode;
        private HardwareMap hardwareMap;
        private boolean reverse = false;
        private String[] reverseDevices = {};
        private Gamepad gamepad = null;

        /**
         * Builder constructor
         *
         * @param opMode instance of the calling opMode
         * @param hardwareMap instance of the calling opMode's hardwareMap
         * @throws NullPointerException if opMode or hardwareMap are null
         */
        protected Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            if (opMode == null) {
                throw new NullPointerException("Null opMode passed to Mechanism.Builder()");
            }
            if (hardwareMap == null) {
                throw new NullPointerException("Null hardwareMap passed to Mechanism.Builder()");
            }
            this.opMode = opMode;
            this.hardwareMap = hardwareMap;
        }

        /**
         * Whether to reverse devices
         *
         * @return builder instance
         */
        public T reverse() {
            this.reverse = true;
            return self();
        }

        /**
         * Reverse the specified device
         *
         * @param deviceName name of the device to be reversed
         * @return builder instance
         * @throws NullPointerException if deviceName is null
         */
        public T reverse(String deviceName) {
            if (deviceName == null) {
                throw new NullPointerException(
                        "Null deviceName passed to Mechanism.Builder().reverse()");
            }
            int arrLength = reverseDevices.length;
            reverseDevices = Arrays.copyOf(this.reverseDevices, arrLength + 1);
            reverseDevices[arrLength] = deviceName;

            return self();
        }

        /**
         * Reverse the specified devices
         *
         * @param deviceNames an array of the names of devices to be reversed
         * @return builder instance
         * @throws NullPointerException if deviceNames is null
         */
        public T reverse(String[] deviceNames) {
            if (deviceNames == null) {
                throw new NullPointerException(
                        "Null deviceNames passed to Mechanism.Builder().reverse()");
            }
            for (String deviceName : deviceNames) {
                reverse(deviceName);
            }
            return self();
        }

        /**
         * Pass gamepad for teleop control
         *
         * @param gamepad instance of the gamepad
         * @return builder instance
         * @throws NullPointerException if gamepad is null
         */
        public T gamepad(Gamepad gamepad) {
            if (gamepad == null) {
                throw new NullPointerException(
                        "Null gamepad passed to Mechanism.Builder().gamepad()");
            }
            this.gamepad = gamepad;
            return self();
        }

        public abstract T names(String[] names);

        public abstract Mechanism build();

        protected abstract T self();
    }

    protected abstract void init();

    public abstract void control();

    protected abstract void reverse(String deviceName);

    /** Helper function to wait (but not suspend) for specified time in s */
    protected void wait(double time) {
        this.timer.reset();
        while (opMode.opModeIsActive() && (this.timer.time() < time))
            ;
    }
}
