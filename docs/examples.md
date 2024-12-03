---
hide:
  - toc
---

# Examples
Examples are provided in the form of code (either Java or Blockly) that must be added to an OpMode. Click an example's dropdown to see its contents.

## OpModes
Each example will use one of the following OpModes. For the sake of brevity, these are not repeated for each example.

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
    <summary>TeleOp Control of Mecanum drivetrain using gamepad1</summary>
    
    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;
        
    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .gamepad(gamepad1)
            .type(Drive.Type.MECANUM)
            .build();
    
    ### Methods

        drive.control();
    </details>


    ### Enable Features


    <details>
    <summary>Autonomous Command of Mecanum drivetrain with encoders and field-centric layout enabled</summary>
    
    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;
        
    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .encoder()
            .type(Drive.Type.MECANUM)
            .layout(Drive.Layout.FIELD)
            .build();
                    
    ### Methods

        drive.command(Drive.Direction.FORWARD, 2, 0.5);
    </details>


    ### Advanced Use


    <details>
    <summary>TeleOp Control of Mecanum drivetrain with Command Sequence</summary>

    ### Imports

        import org.edu_nation.easy_ftc.mechanism.Drive;

    ### Additional
    
        public enum State {
            ROTATE_LEFT, ROTATE_RIGHT
        }
        
    ### Construction

        Drive drive = new Drive.Builder(this, hardwareMap)
            .gamepad(gamepad1)
            .type(Drive.Type.MECANUM)
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
    <summary>TeleOp Control of Mecanum drivetrain using gamepad1</summary>
    
    ### Imports
        
    ### Construction
    
    ### Methods

    </details>


    ### Enable Features


    <details>
    <summary>Autonomous Command of Mecanum drivetrain with encoders and field-centric layout enabled</summary>
    
    ### Imports
        
    ### Construction
                    
    ### Methods

    </details>


    ### Advanced Use


    <details>
    <summary>TeleOp Control of Mecanum drivetrain with Command Sequence</summary>

    ### Imports

    ### Additional
        
    ### Construction

    ### Methods

    ### Notes 
    * Sequence is initiated by pressing dpad_right and can be interrupted with dpad_left
    * Holding down dpad_left works best since the loop used by command() is thread-blocking, meaning its value is only read between sequence states

    </details>