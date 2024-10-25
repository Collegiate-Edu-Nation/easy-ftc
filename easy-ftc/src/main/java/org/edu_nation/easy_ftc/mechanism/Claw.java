package org.edu_nation.easy_ftc.mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Implements a claw by extending the functionality of {@link ServoMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer count (1 or 2)
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
 * @Methods {@link #tele()}
 *          <li>{@link #move(String direction)}
 */
public class Claw extends ServoMechanism {

    /**
     * Constructor
     */
    private Claw(Builder builder) {
        super(builder);
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder extends ServoMechanism.Builder<Builder> {
        private String mechanismName = "Claw";

        /**
         * Claw Builder
         * 
         * @Defaults count = 1
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
         * Build the claw
         */
        @Override
        public Claw build() {
            return new Claw(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    /**
     * Initializes claw servos
     */
    @Override
    protected void init() {
        // Instantiate servos
        servos = new Servo[count];
        if (count == 2) {
            servos[0] = hardwareMap.get(Servo.class, "clawLeft");
            servos[1] = hardwareMap.get(Servo.class, "clawRight");
        } else {
            servos[0] = hardwareMap.get(Servo.class, "claw");
        }
        setDirections(reverse);

        // reverse direction of specified motors
        for (String device : reverseDevices) {
            reverse(device);
        }
    }

    /**
     * Enables teleoperated claw movement with gamepad.
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     */
    @Override
    public void tele() {
        double position = servos[0].getPosition();
        double movement =
                ServoMechanismUtil.controlToDirection(open, close, position, gamepad.b, gamepad.a);
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
    public void move(String direction) {
        double servoDirection =
                ServoMechanismUtil.languageToDirection(direction, open, close, mechanismName);
        if (smooth) {
            double position = servos[0].getPosition();
            setPositionsByIncrementUntilComplete(position, servoDirection);
        } else {
            setPositions(servoDirection);
            wait(delay);
        }
    }

    /**
     * Reverse the direction of the specified servo
     */
    @Override
    protected void reverse(String deviceName) {
        if (count == 2) {
            switch (deviceName) {
                case "clawLeft":
                    servos[0].setDirection(Servo.Direction.REVERSE);
                    break;
                case "clawRight":
                    servos[1].setDirection(Servo.Direction.FORWARD);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected deviceName: " + deviceName
                            + ", passed to Claw.reverse(). Valid names are: clawLeft, clawRight");
            }
        } else {
            switch (deviceName) {
                case "claw":
                    servos[0].setDirection(Servo.Direction.REVERSE);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected deviceName: " + deviceName
                            + ", passed to Claw.reverse(). Valid names are: claw");
            }
        }
    }
}
