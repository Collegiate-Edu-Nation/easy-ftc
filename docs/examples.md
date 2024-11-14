# Examples

## Autonomous control of Mecanum drivetrain with encoders and field-centric layout enabled

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
                .type("mecanum")
                .layout("field")
                .build();
            
            waitForStart();
            if (opModeIsActive()) {
                // Move drivetrain forward at half power for 2 seconds
                drive.command(0.5, Drive.Direction.FORWARD, 2);
            }
        }
    }

## TeleOp control of Mecanum drivetrain using gamepad1

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
                .build();
            
            waitForStart();
            while (opModeIsActive()) {
                drive.control();
            }
        }
    }