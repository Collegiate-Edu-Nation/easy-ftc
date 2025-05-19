// SPDX-FileCopyrightText: 2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.util.ArrayList;

/**
 * Create a list of commands to execute sequentially in TeleOp, enabling automation of common tasks
 *
 * <p><b>Basic Usage:</b>
 *
 * <pre>{@code
 * // Construction
 * CommandSequence sequence =
 *         new CommandSequence()
 *                 .command(drive, Drive.Direction.FORWARD, 2, 0.2)
 *                 .command(claw, Claw.Direction.OPEN);
 *
 * // Usage within main loop
 * sequence.use();
 * }</pre>
 */
public class CommandSequence {
    private ArrayList<Command<?>> commands;
    private int state;

    /** Class representation of passed commands for simplified access */
    class Command<E> {
        protected Mechanism mechanism;
        protected E direction;
        protected double measurement;
        protected double power;

        protected Command(Mechanism mechanism, E direction, double measurement, double power) {
            this.mechanism = mechanism;
            this.direction = direction;
            this.measurement = measurement;
            this.power = power;
        }

        protected Command(Mechanism mechanism, E direction) {
            this.mechanism = mechanism;
            this.direction = direction;
        }
    }

    /** Construct a blank sequence of commands */
    public CommandSequence() {
        this.commands = new ArrayList<>();
        this.state = 0;
    }

    /**
     * Add a MotorMechanism command to the sequence via method chaining
     *
     * @param mechanism instance of the MotorMechanism associated with this command
     * @param direction direction to move the mechanism; see the passed mechanism's Direction enum
     *     for accepted values
     * @param measurement time(s) or distance to move the mechanism
     * @param power fraction of total power/velocity to use for mechanism command
     * @throws NullPointerException if mechanism is null
     * @return CommandSequence instance
     */
    public <E> CommandSequence command(
            MotorMechanism<E> mechanism, E direction, double measurement, double power) {
        if (mechanism == null) {
            throw new NullPointerException("Null mechanism passed to CommandSequence()");
        }
        this.commands.add(new Command<>(mechanism, direction, measurement, power));
        return this;
    }

    /**
     * Add a ServoMechanism command to the sequence via method chaining
     *
     * @param mechanism instance of the ServoMechanism associated with this command
     * @param direction direction to move the mechanism; see the passed mechanism's Direction enum
     *     for accepted values
     * @throws NullPointerException if mechanism is null
     * @return CommandSequence instance
     */
    public <E> CommandSequence command(ServoMechanism<E> mechanism, E direction) {
        if (mechanism == null) {
            throw new NullPointerException("Null mechanism passed to CommandSequence())");
        }
        this.commands.add(new Command<>(mechanism, direction));
        return this;
    }

    /**
     * Use the constructed sequence in the main loop
     *
     * @throws IllegalArgumentException if the mechanism is an unknown type
     */
    public void use() {
        int count = commands.size();
        for (int i = 0; i < count; i++) {
            Mechanism mechanism = commands.get(i).mechanism;
            // terminate sequence when requested
            if (mechanism.gamepad.dpad_left) {
                state = 0;
                return;
            }

            if (i == state) {
                // return early if sequence hasn't been initiated
                if (i == 0 && !mechanism.gamepad.dpad_right) {
                    return;
                }

                // execute current command and increment state
                useHelper(commands.get(i));
                state += 1;
            }
        }

        // reset state-machine when completed
        state = 0;
    }

    /** Helper for avoiding unsafe type conversions */
    @SuppressWarnings("unchecked")
    private <E> void useHelper(Command<E> command) {
        if (command.mechanism instanceof MotorMechanism) {
            ((MotorMechanism<E>) command.mechanism)
                    .command(command.direction, command.measurement, command.power);
        } else if (command.mechanism instanceof ServoMechanism) {
            ((ServoMechanism<E>) command.mechanism).command(command.direction);
        } else {
            throw new IllegalArgumentException(
                    "Unknown object passed to CommandSequence.command(). This "
                            + "shouln't happen, but if it does, ensure the object "
                            + "is either a MotorMechanism or a ServoMechanism");
        }
    }
}
