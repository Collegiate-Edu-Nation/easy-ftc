package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract Mechanism, providing basic functionalities, options, and objects common to
 * all claws. Cannot be instantiated, only extended by other abstract classes.
 * 
 * @Methods {@link #wait(double time)} (used by subclasses)
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

    /**
     * Constructor
     */
    protected Mechanism(Builder<?> builder) {
        this.opMode = builder.opMode;
        this.hardwareMap = builder.hardwareMap;
        this.reverse = builder.reverse;
        this.reverseDevices = builder.reverseDevices;
        this.gamepad = builder.gamepad;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected LinearOpMode opMode;
        protected HardwareMap hardwareMap;
        protected boolean reverse = false;
        protected String[] reverseDevices = {};
        protected Gamepad gamepad = null;

        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            this.opMode = opMode;
            this.hardwareMap = hardwareMap;
        }

        /**
         * Whether to reverse devices
         */
        public T reverse() {
            this.reverse = true;
            return self();
        }

        /**
         * Reverse the specified device
         */
        public T reverse(String deviceName) {
            int arrLength = reverseDevices.length;
            String[] reverseDevices = new String[arrLength + 1];
            for (int i = 0; i < arrLength; i++) {
                reverseDevices[i] = this.reverseDevices[i];
            }
            reverseDevices[arrLength] = deviceName;

            this.reverseDevices = reverseDevices;
            return self();
        }

        /**
         * Pass the gamepad instance for teleop control
         */
        public T gamepad(Gamepad gamepad) {
            this.gamepad = gamepad;
            return self();
        }

        public abstract Mechanism build();

        public abstract T self();
    }

    protected abstract void init();

    public abstract void tele();

    protected abstract void reverse(String deviceName);

    /**
     * Helper function to wait (but not suspend) for specified time in s.
     */
    protected void wait(double time) {
        this.timer.reset();
        while (opMode.opModeIsActive() && (this.timer.time() < time)) {
        }
    }
}
