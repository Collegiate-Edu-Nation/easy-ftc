// SPDX-FileCopyrightText: 2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import java.util.Vector;
import org.edu_nation.easy_ftc.mechanism.Drive.Direction;

/**
 * List of commands to execute sequentially for the associated mechanisms. This is an abstraction
 * over a variable-length state-machine, enabling automated sequences of related commands in TeleOp
 *
 * <p><b>Basic Usage:</b>
 *
 * <pre>{@code
 * // Construction
 * CommandSequence sequence =
 *         new CommandSequence(drive)
 *                 .command(Direction.FORWARD, 2, 0.5)
 *                 .command(Direction.BACKWARD, 2, 0.5);
 *
 * // Usage within main loop
 * sequence.use();
 * }</pre>
 */
public class CommandSequence {
    private Drive drive;
    private Vector<Command> commands;
    private int state;

    /** Class representation of passed commands for simplified access */
    class Command {
        protected Direction direction;
        protected double measurement;
        protected double power;

        protected Command(Direction direction, double measurement, double power) {
            this.direction = direction;
            this.measurement = measurement;
            this.power = power;
        }
    }

    /**
     * Construct a blank sequence of commands for the associated mechanisms
     *
     * @param drive
     * @throws NullPointerException if drive is null
     */
    public CommandSequence(Drive drive) {
        if (drive == null) {
            throw new NullPointerException("Null drive passed to Sequence()");
        }
        this.drive = drive;
        this.commands = new Vector<>();
        this.state = 0;
    }

    /**
     * Construct a sequence of commands via method chaining
     *
     * @param direction
     * @param measurement
     * @param power
     * @return Sequence instance
     */
    public CommandSequence command(Direction direction, double measurement, double power) {
        this.commands.addElement(new Command(direction, measurement, power));
        return this;
    }

    /** Leverage the constructed sequence in the main loop via state-machine */
    public void use() {
        int count = commands.size();
        for (int i = 0; i < count; i++) {
            // terminate sequence when requested
            if (drive.gamepad.dpad_left) {
                state = 0;
                return;
            }

            if (i == state) {
                if (i == 0) {
                    // return early if sequence hasn't been initiated
                    if (!drive.gamepad.dpad_right) {
                        return;
                    }
                }

                // execute current command and increment state
                Command command = commands.elementAt(i);
                drive.command(command.direction, command.measurement, command.power);
                state += 1;
            }
        }

        // reset state-machine when completed
        state = 0;
    }
}
