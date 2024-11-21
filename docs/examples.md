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
                .type(Drive.Type.MECANUM)
                .build();
            
            waitForStart();
            while (opModeIsActive()) {
                drive.control();
            }
        }
    }