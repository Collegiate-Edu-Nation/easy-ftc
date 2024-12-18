---
hide:
  - toc
---

# Examples

Examples are provided in the form of code (either Java or Blockly) that must be added to an OpMode. Click an example's dropdown to see its contents.

## OpModes

Each example will use the following OpModes. For the sake of brevity, these are not repeated for each example. If an example mentions 'Control', then it must use the TeleOp OpMode. If it mentions 'Command', then it is primarily intended for use in Autonomous, but command() can be used in TeleOp as well. If neither term is used, then the OpMode type doesn't matter (and if no 'Methods' section is included, then either command() or control() can be used).

=== "Java"

    <details>
    <summary>TeleOp</summary>

        package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        // Imports

        @TeleOp(name="Tele", group="dev")
        public class Tele extends LinearOpMode {
            // Additional

            @Override
            public void runOpMode() {
                // Construction

                waitForStart();
                while (opModeIsActive()) {
                    // Methods
                }
            }
        }

    </details>

    <details>
    <summary>Autonomous</summary>

        package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        // Imports

        @Autonomous(name="Auto", group="dev")
        public class Auto extends LinearOpMode {
            // Additional

            @Override
            public void runOpMode() {
                // Construction

                waitForStart();
                if (opModeIsActive()) {
                    // Methods
                }
            }
        }

    </details>

    _Note the locations of Imports, Additional, Construction, and Methods_

    ## Example Structure

    - **Imports:** classes and enums that must be imported
    - **Additional:** additional variables and objects that must be setup
    - **Construction:** easy-ftc object construction
    - **Methods:** easy-ftc methods called on constructed objects
    - **Notes:** any relevant details about using the example

    ## Examples

    ### Basic Use

    <details>
    <summary>Control of Motor Mechanism</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;

    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .gamepad(gamepad1)
            .build();

    ### Methods

        drive.control();

    ### Notes

    - The power level of the mechanism can be scaled down (the default is full power)

            drive.control(0.3);

    </details>

    <details>
    <summary>Command of Motor Mechanism</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Arm;

    ### Construction

        Arm arm = new Arm.Builder(this, hardwareMap)
            .build();

    ### Methods

        arm.command(Arm.Direction.UP, 2, 0.5);

    </details>

    <details>
    <summary>Modify Motor Mechanism Attributes</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Lift;
        import com.qualcomm.robotcore.hardware.DcMotor;

    ### Construction

        Lift lift = new Lift.Builder(this, hardwareMap)
            .count(2)                                    // use two motors for this mechanism
            .names(new String[]{"liftLeft", "newName"})  // change the names of the motors
            .reverse()                                   // reverse all motors in the mechanism
            .reverse("liftLeft")                         // reverse specific motor
            .reverse(new String[]{"liftLeft", "newName}) // reverse multiple specific motors
            .behavior(DcMotor.ZeroPowerBehavior.FLOAT)   // change the zero-power behavior of the motors
            .build();

    </details>

    <details>
    <summary>Control of Servo Mechanism</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Claw;

    ### Construction

        Claw claw = new Claw.Builder(this, hardwareMap)
            .gamepad(gamepad1)
            .build();

    ### Methods

        claw.control();

    </details>

    <details>
    <summary>Command of Servo Mechanism</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Claw;

    ### Construction

        Claw claw = new Claw.Builder(this, hardwareMap)
            .build();

    ### Methods

        claw.command(Claw.Direction.CLOSE);

    </details>

    <details>
    <summary>Modify Servo Mechanism Attributes</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Claw;

    ### Construction

        Claw claw = new Claw.Builder(this, hardwareMap)
            .count(2)                                    // use two servos for this mechanism
            .names(new String[]{"clawLeft", "newName"})  // change the names of the servos
            .reverse()                                   // reverse all servos in the mechanism
            .reverse("clawLeft")                         // reverse specific servo
            .reverse(new String[]{"clawLeft", "newName}) // reverse multiple specific servos
            .open(0.8).close(0.2)                        // adjust open and close positions
            .delay(3)                                    // adjust the delay for non-smooth movement
            .build();

    </details>

    <details>
    <summary>Read and Display Sensor State</summary>

    ### Imports

        import org.edu_nation.easy_ftc.sensor.Touch;

    ### Construction

        Touch touch = new Touch.Builder(hardwareMap)
            .build();

    ### Methods

        telemetry.addData("Touch: ", touch.state());
        telemetry.update();

    </details>

    <details>
    <summary>Modify Sensor Attributes</summary>

    ### Imports

        import org.edu_nation.easy_ftc.sensor.Distance;

    ### Construction

        Distance distance = new Distance.Builder(hardwareMap)
            .name("distanceSensor") // change the name of the sensor
            .reverse()              // reverse the sensor state
            .threshold(6.0)         // change the distance threshold
            .build();

    </details>

    ### Enable Features

    <details>
    <summary>Encoder: Velocity-Based Control</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;

    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .encoder()
            .gamepad(gamepad1)
            .build();

    ### Methods

        drive.control();

    ### Notes

    - Unlike command(), adding .diameter() doesn't affect control(), so velocity will be used either way so long as .encoder() is enabled

    </details>

    <details>
    <summary>Encoder: Time-Based Command</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;

    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .encoder()
            .build();

    ### Methods

        // moves forward for 2 seconds at half velocity
        drive.command(Drive.Direction.FORWARD, 2, 0.5);

    </details>

    <details>
    <summary>Encoder: Distance-Based Command</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;

    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .encoder().diameter(4).gearing(19.2)
            .build();

    ### Methods

        // moves forward for 12 inches at half power
        drive.command(Drive.Direction.FORWARD, 12, 0.5);

    ### Notes

    - The distance unit used in command() will be the same as what's used in .diameter()
    - .gearing() is optional here, but correcting it can improve accuracy

    </details>

    <details>
    <summary>Encoder: Limits Using Ticks</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Lift;

    ### Construction

        Lift lift = new Lift.Builder(this, hardwareMap)
            .encoder().up(300).down(-300)
            .gamepad(gamepad1)  // only needed for teleop
            .build();

    </details>

    <details>
    <summary>Encoder: Limits Using Distance</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Lift;

    ### Construction

        Lift lift = new Lift.Builder(this, hardwareMap)
            .encoder().diameter(2).gearing(60).up(3).down(-3)
            .gamepad(gamepad1)  // only needed for teleop
            .build();

    ### Notes

    - .gearing() is optional here, but correcting it can improve accuracy

    </details>

    <details>
    <summary>Smooth Servo</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Claw;

    ### Construction

        Claw claw = new Claw.Builder(this, hardwareMap)
            .smooth()
            .gamepad(gamepad1)  // only needed for teleop
            .build();

    ### Notes

    - The increment and incrementDelay can be adjusted if you'd like to change the 'smoothness' and/or speed

            Claw claw ...
                .smooth().increment(0.04).incrementDelay(0.01)
                ...

    </details>

    <details>
    <summary>Arcade Drive Control</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;
        import org.edu_nation.easy_ftc.mechanism.Drive.Layout;

    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .layout(Layout.ARCADE)
            .gamepad(gamepad1)
            .build();

    ### Methods

        drive.control();

    </details>

    <details>
    <summary>Mecanum Drive</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;

    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .type(Drive.Type.MECANUM)
            .gamepad(gamepad1)  // only needed for teleop
            .build();

    </details>

    <details>
    <summary>Field-Centric Mecanum Drive</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;
        import org.edu_nation.easy_ftc.mechanism.Drive.Layout;

    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .type(Drive.Type.MECANUM).layout(Layout.FIELD)
            .gamepad(gamepad1)  // only needed for teleop
            .build();

    ### Notes

    - LogoFacingDirection and UsbFacingDirection can be changed if field-centric movement is offset

            Drive drive ...
                .logo(LogoFacingDirection.DOWN).usb(UsbFacingDirection.BACKWARD)
                ...

    </details>

    <details>
    <summary>Control of Motor Mechanism with Gamepad Deadzone</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Lift;

    ### Construction

        Lift lift = new Lift.Builder(this, hardwareMap)
            .gamepad(gamepad1).deadzone(0.1)
            .build();

    ### Methods

        lift.control();

    ### Notes

    - Deadzone only affects mechanisms using either Joysticks or Triggers (Drive and Lift)

    </details>

    ### Advanced Use

    <details>
    <summary>Control with Command Sequence</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;

    ### Additional

        public enum State {
            ROTATE_LEFT, ROTATE_RIGHT
        }

    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .gamepad(gamepad1)
            .build();

        // define the starting state
        State state = State.ROTATE_LEFT;

    ### Methods

        // state machine
        switch (state) {
            case ROTATE_LEFT:
                // initiate sequence when dpad_right is pressed
                if (gamepad1.dpad_right) {
                    drive.command(Drive.Direction.ROTATE_LEFT, 2, 0.5);
                    state = State.ROTATE_RIGHT;
                }
                break;
            case ROTATE_RIGHT:
                drive.command(Drive.Direction.ROTATE_RIGHT, 2, 0.5);
                // restart the sequence once final state is completed
                state = State.ROTATE_LEFT;
                break;
            default:
                state = State.ROTATE_LEFT;
        }

        // terminate sequence when dpad_left is pressed
        if (gamepad1.dpad_left) {
            state = State.ROTATE_LEFT;
        }

        // normal drivetrain control
        drive.control();

    ### Notes

    - Sequence is initiated by pressing dpad_right and can be interrupted with dpad_left
    - Holding down dpad_left works best since the loop used by command() is thread blocking, meaning its value is only read between sequence states

    </details>

=== "Blockly"

    <details>
    <summary>TeleOp</summary>
    <br>![TeleOp](./img/blk/ex/tele.png){ width=435 }
    </details>

    <details>
    <summary>Autonomous</summary>
    <br>![Autonomous](./img/blk/ex/auto.png){ width=350 }
    </details>

    _Note the locations of Additional and Methods_

    ## MyBlocks

    As Blockly doesn't yet allow the import of non-static, external Java classes, any changes to Imports and Construction must be done in a subsystem's associated MyBlock file rather than the Blockly OpModes above. An abridged form of Arm's MyBlock file is included below, but the exact MyBlock file used will be the one associated with the given subsystem (so if an example is using the Drive subsystem, any changes to Imports or Construction will be made in Drive.java).

    <details>
    <summary>TeleOp</summary>

        package org.firstinspires.ftc.teamcode;

        import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
        import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
        import org.edu_nation.easy_ftc.mechanism.Arm.Direction;
        // Imports

        public class Arm extends BlocksOpModeCompanion {
            @ExportToBlocks(comment = "Initiate an automated arm movement",
                    parameterLabels = {"Direction", "Time", "Power"})
            public static void command(Direction direction, double time, double power) {
                org.edu_nation.easy_ftc.mechanism.Arm arm =
                        new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                                .build();
                arm.command(direction, time, power);
            }

            @ExportToBlocks(
                    comment = "Enable teleoperated arm movement with gamepad (lb, rb) at the specified power",
                    parameterLabels = {"Power"})
            public static void control(double power) {
                // Construction
                arm.control(power);
            }

            @ExportToBlocks(
                    comment = "Enable teleoperated arm movement with gamepad (lb, rb) at a power of 0.5")
            public static void control() {
                // Construction
                arm.control();
            }

            // Direction methods ...
        }

    </details>

    <details>
    <summary>Autonomous</summary>

        package org.firstinspires.ftc.teamcode;

        import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
        import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
        import org.edu_nation.easy_ftc.mechanism.Arm.Direction;
        // Imports

        public class Arm extends BlocksOpModeCompanion {
            @ExportToBlocks(comment = "Initiate an automated arm movement",
                    parameterLabels = {"Direction", "Time", "Power"})
            public static void command(Direction direction, double time, double power) {
                // Construction
                arm.command(direction, time, power);
            }

            @ExportToBlocks(
                    comment = "Enable teleoperated arm movement with gamepad (lb, rb) at the specified power",
                    parameterLabels = {"Power"})
            public static void control(double power) {
                org.edu_nation.easy_ftc.mechanism.Arm arm =
                        new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                                .gamepad(gamepad1).build();
                arm.control(power);
            }

            @ExportToBlocks(
                    comment = "Enable teleoperated arm movement with gamepad (lb, rb) at a power of 0.5")
            public static void control() {
                org.edu_nation.easy_ftc.mechanism.Arm arm =
                        new org.edu_nation.easy_ftc.mechanism.Arm.Builder(linearOpMode, hardwareMap)
                                .gamepad(gamepad1).build();
                arm.control();
            }

            // Direction methods ...
        }

    </details>

    _Note the locations of Imports and Construction_

    ## Example Structure

    - **Imports:** classes and enums that must be imported
    - **Additional:** additional variables and objects that must be setup
    - **Construction:** easy-ftc object construction
    - **Methods:** easy-ftc methods called on constructed objects
    - **Notes:** any relevant details about using the example

    ## Examples

    ### Basic Use

    <details>
    <summary>Control of Motor Mechanism</summary>

    ### Methods

    <br>![Control Motor](./img/blk/ex/controlMotor.png){ width=225 }

    ### Notes

    - The power level of the mechanism can be scaled down (the default is full power)

      <br>![Control Multiplier](./img/blk/ex/controlMultiplier.png){ width=285 }

    </details>

    <details>
    <summary>Command of Motor Mechanism</summary>

    ### Methods

    <br>![Command Motor](./img/blk/ex/commandMotor.png){ width=450 }

    </details>

    <details>
    <summary>Modify Motor Mechanism Attributes</summary>

    ### Imports

        import com.qualcomm.robotcore.hardware.DcMotor;

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .count(2)                                    // use two motors for this mechanism
                        .names(new String[]{"liftLeft", "newName"})  // change the names of the motors
                        .reverse()                                   // reverse all motors in the mechanism
                        .reverse("liftLeft")                         // reverse specific motor
                        .reverse(new String[]{"liftLeft", "newName}) // reverse multiple specific motors
                        .behavior(DcMotor.ZeroPowerBehavior.FLOAT)   // change the zero-power behavior of the motors
                        .build();

    </details>

    <details>
    <summary>Control of Servo Mechanism</summary>

    ### Methods

    <br>![Control Servo](./img/blk/ex/controlServo.png){ width=225 }

    </details>

    <details>
    <summary>Command of Servo Mechanism</summary>

    ### Methods

    <br>![Command Servo](./img/blk/ex/commandServo.png){ width=450 }

    </details>

    <details>
    <summary>Modify Servo Mechanism Attributes</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Claw claw =
                new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap)
                        .count(2)                                    // use two servos for this mechanism
                        .names(new String[]{"clawLeft", "newName"})  // change the names of the servos
                        .reverse()                                   // reverse all servos in the mechanism
                        .reverse("clawLeft")                         // reverse specific servo
                        .reverse(new String[]{"clawLeft", "newName}) // reverse multiple specific servos
                        .open(0.8).close(0.2)                        // adjust open and close positions
                        .delay(3)                                    // adjust the delay for non-smooth movement
                        .build();

    </details>

    <details>
    <summary>Read and Display Sensor State</summary>

    ### Methods

    <br>![Display State](./img/blk/ex/displayState.png){ width=600 }

    </details>

    <details>
    <summary>Modify Sensor Attributes</summary>

    ### Construction

        org.edu_nation.easy_ftc.sensor.Distance distance =
                new org.edu_nation.easy_ftc.sensor.Distance.Builder(hardwareMap)
                        .name("distanceSensor") // change the name of the sensor
                        .reverse()              // reverse the sensor state
                        .threshold(6.0)         // change the distance threshold
                        .build();

    </details>

    ### Enable Features

    As an easy-ftc object must be constructed for every Blockly function call, these objects are unable to persist their state, which breaks the following features for Blockly users:

    - Encoder limits in control()
    - Field-centric driving

    If these features are must-haves, use Java instead.

    <details>
    <summary>Encoder: Velocity-Based Control</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .encoder()
                        .gamepad(gamepad1)
                        .build();

    ### Methods

    <br>![Control Motor](./img/blk/ex/controlMotor.png){ width=225 }

    ### Notes

    - Unlike command(), adding .diameter() doesn't affect control(), so velocity will be used either way so long as .encoder() is enabled

    </details>

    <details>
    <summary>Encoder: Time-Based Command</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .encoder()
                        .build();

    ### Methods

    <br>![Command Velocity](./img/blk/ex/commandVelocity.png){ width=450 }

    </details>

    <details>
    <summary>Encoder: Distance-Based Command</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .encoder().diameter(4).gearing(19.2)
                        .build();

    ### Methods

    <br>![Command Distance](./img/blk/ex/commandDistance.png){ width=450 }

    ### Notes

    - The distance unit used in command() will be the same as what's used in .diameter()
    - .gearing() is optional here, but correcting it can improve accuracy
    - The 'Time' parameterLabel in Drive.command() has been changed to 'Distance' here. This is optional, but it clarifies the intended functionality for Blockly users

    </details>

    <details>
    <summary>Encoder: Command Limits Using Ticks</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .encoder().up(300).down(-300)
                        .build();

    ### Methods

    <br>![Limits Ticks](./img/blk/ex/limitsTicks.png){ width=450 }

    </details>

    <details>
    <summary>Encoder: Command Limits Using Distance</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .encoder().diameter(2).gearing(60).up(3).down(-3)
                        .build();

    ### Methods

    <br>![Limits Distance](./img/blk/ex/limitsDistance.png){ width=450 }

    ### Notes

    - .gearing() is optional here, but correcting it can improve accuracy
    - The 'Time' parameterLabel in Lift.command() has been changed to 'Distance' here. This is optional, but it clarifies the intended functionality for Blockly users

    </details>

    <details>
    <summary>Smooth Servo</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Claw claw =
                new org.edu_nation.easy_ftc.mechanism.Claw.Builder(linearOpMode, hardwareMap)
                        .smooth()
                        .gamepad(gamepad1) // only needed for teleop
                        .build();

    ### Notes

    - The increment and incrementDelay can be adjusted if you'd like to change the 'smoothness' and/or speed

            org.edu_nation ...
                    new org.edu_nation ...
                            .smooth().increment(0.04).incrementDelay(0.01)
                            ...

    </details>

    <details>
    <summary>Arcade Drive Control</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive.Layout;

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .layout(Layout.ARCADE)
                        .gamepad(gamepad1)
                        .build();

    ### Methods

    <br>![Control Motor](./img/blk/ex/controlMotor.png){ width=225 }

    </details>

    <details>
    <summary>Mecanum Drive</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Drive drive =
                new org.edu_nation.easy_ftc.mechanism.Drive.Builder(linearOpMode, hardwareMap)
                        .type(org.edu_nation.easy_ftc.mechanism.Drive.Type.MECANUM)
                        .gamepad(gamepad1) // only needed for teleop
                        .build();

    </details>

    <details>
    <summary>Control of Motor Mechanism with Gamepad Deadzone</summary>

    ### Construction

        org.edu_nation.easy_ftc.mechanism.Lift lift =
                new org.edu_nation.easy_ftc.mechanism.Lift.Builder(linearOpMode, hardwareMap)
                        .gamepad(gamepad1).deadzone(0.1)
                        .build();

    ### Methods

    <br>![Control Deadzone](./img/blk/ex/controlDeadzone.png){ width=225 }

    ### Notes

    - Deadzone only affects mechanisms using either Joysticks or Triggers (Drive and Lift)

    </details>

    ### Advanced Use

    <details>
    <summary>Control with Command Sequence</summary>

    ### Additional

    <br>![Starting State](./img/blk/ex/startingState.png){ width=385 }

    ### Methods

    <br>![State Machine](./img/blk/ex/stateMachine.png){ width=600 }

    ### Notes

    - Sequence is initiated by pressing dpad_right and can be interrupted with dpad_left
    - Holding down dpad_left works best since the loop used by command() is thread blocking, meaning its value is only read between sequence states

    </details>
