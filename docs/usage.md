# Usage

## Introduction

### Low-Level vs. High-Level Interfaces

When programming complex systems, there is an almost universal need for low-level control. In the context of FTC, this is often encountered in the hardware initialization stage of an OpMode, where, for example, you'll need to decide the behavior of your motors (direction, run mode, etc.).

The FTC SDK does a good job of providing APIs (application programming interfaces) for accomplishing this, but this comes with some challenges for users. Namely, low-level control of individual motors, servos, and sensors does not always neatly translate into making robots perform high-level actions (like moving entire mechanisms).

Thus, there's room in the FTC ecosystem for what are called 'facade-pattern APIs', which interact with the FTC SDK's APIs at a low-level, but provide users with massively simplified interfaces to control their robots' high-level actions in an intuitive manner.

This is what easy-ftc seeks to provide.

### Mechanisms

An obvious way to provide higher-level control of your robot is to think in terms of mechanisms, rather than motors or servos. For instance, many teams have four drive motors which, intuitively, just combine to form a single mechanism- the drivetrain. In this example, rather than focusing on ways to reduce the repetition of setting up and controlling four closely-related but distinct pieces of hardware, teams could opt to leverage easy-ftc's suitable mechanism named Drive, which can allow them to implement their drivetrain in as little as three lines of code.

### Standardized APIs

Another, less obvious, tactic for creating simple, high-level interfaces, is to standardize the APIs as much as possible. As a counter-example, motors in the FTC SDK are often controlled via the function 'setPower()', whereas servos are most often controlled via 'setPosition()'. In isolation, this isn't a big deal, but as more advanced features and approaches are implemented, these subtleties can propagate through your codebase to such an extent that forgetting a single function name may cause hours of head-scratching.

This brings us to easy-ftc's APIs

## APIs

### Command() - Mechanisms

command() is a function/method that tells (commands) a mechanism to move in a specific manner. This is most often seen in Autonomous routines, where most teams will program their robot to simply perform consecutive actions, though automated sequences in TeleOp can make use of commands as well. easy-ftc provides simple ways to command each mechanism at a specified power and direction for either time-based or distance-based movement.

### Control() - Mechanisms

control() is a function/method that enables tele-operated control of each mechanism, which is only allowed in the TeleOp portion of a match. More explicitly, this function reads inputs from the gamepad, processes those inputs, then controls the mechanism based on these processed inputs. easy-ftc decides the control-scheme for you, which obviously takes away some user control, but comes with the benefit of being able to program a competitive robot with a single function.

### State() - Sensors

state() is a function/method that returns the current state/value of each sensor. The return value depends on the exact sensor, but is either a boolean (Touch return true if the sensor is pressed, false if not) or String (Color returns one of "blue", "red", or "green"). When combined with conditional statements and command()/control(), this allows teams to differentiate their robots' actions based on the surrounding environment. For example, if the color sensor returns "blue", move forward.

## Getting Started

### Blocks

### Java