# easy-ftc

Facade-pattern API for easily leveraging in-the-box FTC features, including:
* Arm (Solo and Dual motor)
* Claw (Solo and Dual servo)
* Drive (Differential and Mecanum)
* Lift (Solo and Dual motor)
* Sensors (Distance, Touch, AprilTag)

<i>Note: Encoders can be enabled for all motor-powered features. Both Robot-centric and Field-centric driving is supported for Mecanum, while tank and arcade are supported for Differential</i>

API reference docs deployed at: 

Powered by Nix ❄️

<details>
<summary><b>Usage</b></summary>

Download release archive:
* Download 'easy-ftc-release.aar' (the Android archive for this library) at: https://github.com/camdenboren/easy-ftc/releases

OnBot Java:
* Upload the .aar using OnBot Java's GUI

Blocks:
* asdf

Android Studio:
* asdf

</details>

<details>
<summary><b>Examples</b></summary>
Autonomous control of Mecanum drivetrain with encoders and field-centric layout enabled

    package org.firstinspires.ftc.teamcode.auto;

    import org.cen.easy_ftc.*;
    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

    @Autonomous(name = "Auto")
    public class Auto extends LinearOpMode {
        /**
        * This function is executed when this OpMode is selected from the Driver Station.
        */
        @Override
        public void runOpMode() {
            // Hardware init
            Mecanum mecanum = new Mecanum(this, hardwareMap, true, "field");

            waitForStart();
            if (opModeIsActive()) {
                // Move drivetrain forward at half power for 2s
                mecanum.move(0.5, "forward", 2);
            }
        }
    }
</details>

<details>
<summary><b>Dev Setup</b></summary>
Nix is my preferred approach for setting up the development environment. Linux, MacOS, and WSL are supported. 

<b>Must install flake-enabled Nix before running.</b>

Launch development environment

    nix develop github:camdenboren/easy-ftc

The project can also be imported into Android Studio, where Windows is also supported

    git clone https://github.com/camdenboren/easy-ftc.git

For either approach, gradlew builds are supported
</details>

<details>
<summary><b>Advanced Usage</b></summary>
Generate javadoc

    javadoc -d docs -classpath easy-ftc/build/aarLibraries/org.firstinspires.ftc-RobotCore-10.0.0.jar:easy-ftc/build/aarLibraries/org.firstinspires.ftc-Vision-10.0.0.jar:easy-ftc/build/aarLibraries/org.firstinspires.ftc-Hardware-10.0.0.jar -sourcepath easy-ftc/src/main/java/ org.cen.easy_ftc.arm org.cen.easy_ftc.claw org.cen.easy_ftc.drive org.cen.easy_ftc.lift org.cen.easy_ftc.sensor
</details>