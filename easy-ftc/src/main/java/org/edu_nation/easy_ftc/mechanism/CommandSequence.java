// SPDX-FileCopyrightText: 2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.util.ArrayList;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Create a list of commands to execute sequentially in TeleOp, enabling automation of routines
 *
 * <p><b>Basic Usage:</b>
 *
 * <pre>{@code
 * // Construction
 * CommandSequence sequence =
 *         new CommandSequence()
 *                 .command(drive, Drive.Direction.FORWARD, 2, 0.2)
 *                 .command(drive, Drive.Direction.ROTATE_LEFT, 90, 0.2, AngleUnit.DEGREES)
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
        private Mechanism mechanism;
        private E direction;
        private double measurement;
        private double power;
        private AngleUnit unit;

        private Command(
                Mechanism mechanism,
                E direction,
                double measurement,
                double power,
                AngleUnit unit) {
            this.mechanism = mechanism;
            this.direction = direction;
            this.measurement = measurement;
            this.power = power;
            this.unit = unit;
        }

        private Command(Mechanism mechanism, E direction, double measurement, double power) {
            this.mechanism = mechanism;
            this.direction = direction;
            this.measurement = measurement;
            this.power = power;
        }

        private Command(Mechanism mechanism, E direction) {
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
     * Add an angular {@link Drive} command to the sequence via method chaining
     *
     * @param mechanism instance of the {@link Drive} object associated with this command
     * @param direction direction to move the mechanism; see the {@link Drive.Direction} for
     *     accepted values (one of: ROTATE_LEFT, ROTATE_RIGHT)
     * @param measurement angle to rotate
     * @param power fraction of total power/velocity to use for mechanism command
     * @param unit AngleUnit to use for the measurement (one of: DEGREES, RADIANS)
     * @throws IllegalArgumentException if mechanism is not {@link Drive}
     * @throws NullPointerException if mechanism is null
     * @return CommandSequence instance
     */
    public <E> CommandSequence command(
            MotorMechanism<E> mechanism,
            E direction,
            double measurement,
            double power,
            AngleUnit unit) {
        if (!(mechanism instanceof Drive)) {
            throw new IllegalArgumentException(
                    "Illegal mechanism passed to CommandSequence.command(). The "
                            + "angular command only accepts Drive mechanisms");
        }
        validate(mechanism);
        this.commands.add(new Command<>(mechanism, direction, measurement, power, unit));
        return this;
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
        validate(mechanism);
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
        validate(mechanism);
        this.commands.add(new Command<>(mechanism, direction));
        return this;
    }

    /** Ensures mechanism is not null */
    private void validate(Mechanism mechanism) {
        if (mechanism == null) {
            throw new NullPointerException("Null mechanism passed to CommandSequence().command()");
        }
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
            if (command.mechanism instanceof Drive && command.unit instanceof AngleUnit) {
                ((Drive) command.mechanism)
                        .command(
                                (Drive.Direction) command.direction,
                                command.measurement,
                                command.power,
                                command.unit);
                return;
            }
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
