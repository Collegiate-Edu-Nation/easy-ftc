---
hide:
  - toc
---

# Examples
Examples are provided in the form of code (either Java or Blockly) that must be added to an OpMode. Click an example's dropdown to see its contents.

## OpModes
Each example will use the following OpModes. For the sake of brevity, these are not repeated for each example. If an example mentions 'Control', then it must use the TeleOp OpMode. If it mentions 'Command', then it is primarily intended for use in Autonomous, but command() can be used in TeleOp as well. If neither term is used, then the OpMode type doesn't matter.

<i>Note the locations of Imports, Additional, Construction, and Methods.</i>

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


    ## Example Structure

    * <b>Imports</b>: classes and enums that must be imported
    * <b>Additional</b>: additional variables and objects that must be setup
    * <b>Construction</b>: easy-ftc object construction
    * <b>Methods</b>: easy-ftc methods called on constructed objects
    * <b>Notes</b>: any relevant details about using the example

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


    ### Enable Features


    <details>
    <summary>Encoder: Velocity-Based Control</summary>
    
    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;
        
    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .encoder()
            .build();

    ### Methods

        drive.control();

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

        // moves forward for 2 seconds at half power
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
    * The distance unit used in command() will be the same as what's used in .diameter()
    * .gearing() is optional here, but correcting it can improve accuracy

    </details>


    <details>
    <summary>Encoder: Limits Using Ticks</summary>
    
    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Lift;
        
    ### Construction

        Lift lift = new Lift.Builder(this, hardwareMap)
            .encoder().up(300).down(-300)
            .build();

    </details>


    <details>
    <summary>Encoder: Limits Using Distance</summary>
    
    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Lift;
        
    ### Construction

        Lift lift = new Lift.Builder(this, hardwareMap)
            .encoder().diameter(2).gearing(60).up(3).down(-3)
            .build();

    ### Notes
    * .gearing() is optional here, but correcting it can improve accuracy

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
        
        // define starting state
        State state = State.ROTATE_LEFT;

    ### Methods

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
                // restart sequence once final state is completed
                state = State.ROTATE_LEFT;
                break;
            default:
                state = State.ROTATE_LEFT;
        }
        
        // Termiante sequence when dpad_left is pressed
        if (gamepad1.dpad_left) {
            state = State.ROTATE_LEFT;
        }

        // normal drivetrain control
        drive.control();

    ### Notes 
    * Sequence is initiated by pressing dpad_right and can be interrupted with dpad_left
    * Holding down dpad_left works best since the loop used by command() is thread-blocking, meaning its value is only read between sequence states

    </details>


=== "Blockly"


    <details>
    <summary>TeleOp</summary>

    </details>


    <details>
    <summary>Autonomous</summary>

    </details>
    

    ## Example Structure

    * <b>Imports</b>: classes and enums that must be imported
    * <b>Additional</b>: additional variables and objects that must be setup
    * <b>Construction</b>: easy-ftc object construction
    * <b>Methods</b>: easy-ftc methods called on constructed objects
    * <b>Notes</b>: any relevant details about using the example

    ## Examples

    ### Basic Use    


    <details>
    <summary>Control of Motor Mechanism</summary>
    
    ### Imports
        
    ### Construction
    
    ### Methods
    
    </details>


    <details>
    <summary>Command of Motor Mechanism</summary>
    
    ### Imports
        
    ### Construction
    
    ### Methods
    
    </details>


    <details>
    <summary>Control of Servo Mechanism</summary>
    
    ### Imports
        
    ### Construction
    
    ### Methods
    
    </details>


    <details>
    <summary>Command of Servo Mechanism</summary>
    
    ### Imports
        
    ### Construction
    
    ### Methods
    
    </details>


    <details>
    <summary>Read and Display Sensor State</summary>

    ### Imports

    ### Construction

    ### Methods

    </details>


    ### Enable Features


    <details>
    <summary>Encoder: Velocity-Based Control</summary>
    
    ### Imports
        
    ### Construction

    ### Methods

    </details>


    <details>
    <summary>Encoder: Time-Based Command</summary>
    
    ### Imports
        
    ### Construction

    ### Methods

    </details>


    <details>
    <summary>Encoder: Distance-Based Command</summary>
    
    ### Imports
        
    ### Construction

    ### Methods

    ### Notes
    * The distance unit used in command() will be the same as what's used in .diameter()
    * .gearing() is optional here, but correcting it can improve accuracy

    </details>


    <details>
    <summary>Encoder: Limits Using Ticks</summary>
    
    ### Imports
        
    ### Construction

    </details>


    <details>
    <summary>Encoder: Limits Using Distance</summary>
    
    ### Imports
        
    ### Construction

    ### Notes
    * .gearing() is optional here, but correcting it can improve accuracy

    </details>


    ### Advanced Use


    <details>
    <summary>Control with Command Sequence</summary>

    ### Imports

    ### Additional
        
    ### Construction

    ### Methods

    ### Notes 
    * Sequence is initiated by pressing dpad_right and can be interrupted with dpad_left
    * Holding down dpad_left works best since the loop used by command() is thread-blocking, meaning its value is only read between sequence states

    </details>