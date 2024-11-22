# Examples

=== "Java"

    ## Autonomous Command of Mecanum drivetrain with encoders and field-centric layout enabled

        package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import org.edu_nation.easy_ftc.mechanism.Drive;
        
        @Autonomous(name="Auto", group="dev")
        public class Auto extends LinearOpMode {
        
            @Override
            public void runOpMode() {
                // Hardware init
                Drive drive = new Drive.Builder(this, hardwareMap)
                    .encoder()
                    .type(Drive.Type.MECANUM)
                    .layout(Drive.Layout.FIELD)
                    .build();
                
                waitForStart();
                if (opModeIsActive()) {
                    // Move drivetrain forward for 2 seconds at half power
                    drive.command(Drive.Direction.FORWARD, 2, 0.5);
                }
            }
        }

    ## TeleOp Control of Mecanum drivetrain using gamepad1

        package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import org.edu_nation.easy_ftc.mechanism.Drive;

        @TeleOp(name="Tele", group="dev")
        public class Tele extends LinearOpMode {
            @Override
            public void runOpMode() {
                // Hardware init
                Drive drive = new Drive.Builder(this, hardwareMap)
                    .gamepad(gamepad1)
                    .type(Drive.Type.MECANUM)
                    .build();
                
                waitForStart();
                while (opModeIsActive()) {
                    drive.control();
                }
            }
        }

    ## TeleOp Control of Mecanum drivetrain with Command Sequence
    <i>Sequence is initiated by pressing dpad_right and can be interrupted with dpad_left. Holding down dpad_left works best since the loop used by command() is thread-blocking, meaning its value is only read between sequence states</i>

        package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import org.edu_nation.easy_ftc.mechanism.Drive;

        @TeleOp(name="Tele", group="dev")
        public class Tele extends LinearOpMode {
            public enum State {
                ROTATE_LEFT, ROTATE_RIGHT
            }
        
            @Override
            public void runOpMode() {
                // Hardware init
                Drive drive = new Drive.Builder(this, hardwareMap)
                    .gamepad(gamepad1)
                    .type(Drive.Type.MECANUM)
                    .build();
                
                // define starting state
                State state = State.ROTATE_LEFT;

                waitForStart();
                while (opModeIsActive()) {
                    // Define the sequence with a Finite State Machine
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
                }
            }
        }

=== "Blockly"