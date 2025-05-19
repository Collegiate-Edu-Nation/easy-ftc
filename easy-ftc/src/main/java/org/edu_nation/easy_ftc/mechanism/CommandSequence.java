// SPDX-FileCopyrightText: 2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.util.ArrayList;

/**
 * List of commands to execute sequentially for the associated mechanisms. This is an abstraction
 * over a variable-length state-machine, enabling automated sequences of related commands in TeleOp
 *
 * <p><b>Basic Usage:</b>
 *
 * <pre>{@code
 * // Construction
 * CommandSequence sequence =
 *         new CommandSequence()
 *                 .command(drive, Drive.Direction.FORWARD, 2, 0.2)
 *                 .command(intake, Intake.Direction.IN, 5, 0.5);
 *
 * // Usage within main loop
 * sequence.use();
 * }</pre>
 */
public class CommandSequence {
    private ArrayList<Command> commands;
    private int state;

    /** Class representation of passed commands for simplified access */
    class Command {
        protected MotorMechanism<?> mechanism;
        protected Object direction;
        protected double measurement;
        protected double power;

        protected Command(
                MotorMechanism<?> mechanism, Object direction, double measurement, double power) {
            this.mechanism = mechanism;
            this.direction = direction;
            this.measurement = measurement;
            this.power = power;
        }
    }

    /**
     * Construct a blank sequence of commands for the associated mechanisms
     *
     * @param mechanism
     */
    public CommandSequence() {
        this.commands = new ArrayList<>();
        this.state = 0;
    }

    /**
     * Construct a sequence of commands via method chaining
     *
     * @param direction
     * @param measurement
     * @param power
     * @throws NullPointerException if mechanism is null
     * @return Sequence instance
     */
    public CommandSequence command(
            MotorMechanism<?> mechanism, Object direction, double measurement, double power) {
        if (mechanism == null) {
            throw new NullPointerException("Null mechanism passed to CommandSequence()");
        }
        this.commands.add(new Command(mechanism, direction, measurement, power));
        return this;
    }

    /** Leverage the constructed sequence in the main loop via state-machine */
    public void use() {
        int count = commands.size();
        for (int i = 0; i < count; i++) {
            MotorMechanism<?> mechanism = commands.get(i).mechanism;
            // terminate sequence when requested
            if (mechanism.gamepad.dpad_left) {
                state = 0;
                return;
            }

            if (i == state) {
                if (i == 0 && !mechanism.gamepad.dpad_right) {
                    // return early if sequence hasn't been initiated
                    return;
                }

                // execute current command and increment state
                Command command = commands.get(i);
                mechanism.commandGeneric(command.direction, command.measurement, command.power);
                state += 1;
            }
        }

        // reset state-machine when completed
        state = 0;
    }
}
