// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.lang.Math;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Implements a drivetrain by extending the functionality of {@link MotorMechanism}.
 * <p>
 * 
 * @param LinearOpMode opMode (required)
 * @param HardwareMap hardwareMap (required)
 * @param Integer count (2 or 4)
 * @param String[] names
 * @param Boolean encoder
 * @param Boolean reverse
 * @param String[] reverseDevices
 * @param Double diameter (> 0.0)
 * @param Double gearing (> 0.0)
 * @param Double deadzone (>= 0.0)
 * @param Gamepad gamepad (gamepad1 or gamepad2)
 * @param String type ("mecanum" or "differential")
 * @param String layout ("tank" or "arcade")
 *        <p>
 * @Methods {@link #tele()}
 *          <li>{@link #move(double power, String direction, double measurement)}
 */
public class Drive extends MotorMechanism {
    private String type;

    /**
     * Constructor
     */
    private Drive(Builder builder) {
        super(builder);
        this.count = builder.count;
        this.names = builder.names;
        this.behavior = builder.behavior;
        this.type = builder.type;
        this.layout = builder.layout;
        this.mechanismName = builder.mechanismName;
        init();
    }

    public static class Builder extends MotorMechanism.Builder<Builder> {
        private int count = 2;
        private String[] names = {"driveLeft", "driveRight"};
        private DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.FLOAT;
        private String type = "";
        private String layout = "";
        private String mechanismName = "Drive";

        /**
         * Drive Builder
         * 
         * @Defaults count = 2
         *           <li>names = {"driveLeft", "driveRight"}
         *           <li>behavior = FLOAT
         *           <li>encoder = false
         *           <li>reverse = false
         *           <li>reverseDevices = {}
         *           <li>diameter = 0.0
         *           <li>gearing = 0.0
         *           <li>deadzone = 0.0
         *           <li>gamepad = null
         *           <li>type = "" (interpreted as "differential")
         *           <li>layout = "" (interpreted as "tank" for differential, "robot" for mecanum)
         */
        public Builder(LinearOpMode opMode, HardwareMap hardwareMap) {
            super(opMode, hardwareMap);
        }

        /**
         * Specify the number of motors (2, 4)
         */
        public Builder count(int count) {
            this.count = count;
            if (count == 4) {
                String[] names = {"frontLeft", "frontRight", "backLeft", "backRight"};
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
         * Specify the drivetrain type: "differential" (default) or "mecanum"
         */
        public Builder type(String type) {
            this.type = type;
            if (type == "mecanum" && this.count == 2) {
                this.count = 4;
                if (this.names.length == 2) {
                    String[] names = {"frontLeft", "frontRight", "backLeft", "backRight"};
                    this.names = names;
                }
            }
            return this;
        }

        /**
         * Specify the control layout. For Differential, "tank" (default) or "arcade". For Mecanum,
         * "robot" (default) or "field"
         */
        public Builder layout(String layout) {
            this.layout = layout;
            return this;
        }

        /**
         * Build the arm
         */
        @Override
        public Drive build() {
            return new Drive(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    /**
     * Enables teleoperated mecanum movement with gamepad (inherits layout), scaling by multiplier <
     * 1
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Basic mecanum algorithm largely taken from the BasicOmniOpMode block example.
     * <p>
     * Field-centric algorithm taken from:
     * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
     */
    @Override
    public void tele(double multiplier) {
        double heading = 0;
        // Press option to reset imu to combat drift, set heading if applicable
        if (layout == "field") {
            heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            if (gamepad.options) {
                imu.resetYaw();
            }
        }

        double[] movements = DriveUtil.controlToDirection(count, type, layout, deadzone, heading,
                gamepad.left_stick_y, gamepad.left_stick_x, gamepad.right_stick_y,
                gamepad.right_stick_x);

        if (multiplier == 1.0) {
            setPowers(movements);
        } else {
            double[] scaledMovements = MotorMechanismUtil
                    .scaleDirections(Math.min(Math.abs(multiplier), 1), movements);
            setPowers(scaledMovements);
        }
    }

    /**
     * Enables teleoperated mecanum movement with gamepad (inherits layout) with multiplier = 1.0
     * <p>
     * Calling this directly is one of the primary use-cases of this class.
     * <p>
     * Basic mecanum algorithm largely taken from the BasicOmniOpMode block example.
     * <p>
     * Field-centric algorithm taken from:
     * https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html
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
     * Valid directions are: forward, backward, left, right, rotateLeft, rotateRight, forwaredLeft,
     * forwardRight, backwardLeft, backwardRight
     */
    @Override
    public void move(double power, String direction, double measurement) {
        double[] unscaledMovements = DriveUtil.languageToDirection(count, type, direction);
        double[] movements = DriveUtil.scaleDirections(power, unscaledMovements);

        if (diameter == 0.0) {
            setPowers(movements);
            wait(measurement);
            setPowers();
        } else {
            int[] positions = DriveUtil.calculatePositions(measurement, diameter,
                    distanceMultiplier, unscaledMovements);
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
