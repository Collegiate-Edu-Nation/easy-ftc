# easy-ftc

Facade-pattern API for easily leveraging in-the-box FTC mechanisms and features, including:
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
<summary><b>Controls</b></summary>

Arm:
* Bumpers
    * RB to raise
    * LB to lower


Claw:
* Buttons: A, B
    * A to open
    * B to close

Drive:
* Joysticks
* Option (resets gyro, optional)

Lift:
* Triggers
    * RT to lift
    * LT to lower
</details>

<details>
<summary><b>Examples</b></summary>
Autonomous control of Mecanum drivetrain with encoders and field-centric layout enabled

    package org.firstinspires.ftc.teamcode;

    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import org.cen.easy_ftc.drive.*;

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
Nix is my preferred approach for setting up the development environment. Linux, MacOS, and WSL are supported

<b>Must install flake-enabled Nix before running</b>

Launch development environment

    nix develop github:camdenboren/easy-ftc

The project can also be imported into Android Studio, where Windows is also supported

<b>Must install git and Android Studio before running</b>

    git clone https://github.com/camdenboren/easy-ftc.git
    Import project in Android Studio

For either approach, gradlew builds are supported
</details>

<details>
<summary><b>Advanced Usage</b></summary>
Generate javadoc

    javadoc -d docs -classpath easy-ftc/build/aarLibraries/org.firstinspires.ftc-RobotCore-10.0.0.jar:easy-ftc/build/aarLibraries/org.firstinspires.ftc-Vision-10.0.0.jar:easy-ftc/build/aarLibraries/org.firstinspires.ftc-Hardware-10.0.0.jar -sourcepath easy-ftc/src/main/java/ org.cen.easy_ftc.arm org.cen.easy_ftc.claw org.cen.easy_ftc.drive org.cen.easy_ftc.lift org.cen.easy_ftc.sensor
</details>

<details>
<summary><b>Todo</b></summary>

Features
- [ ] Add setters for directionality
- [ ] OpenCV
- [ ] Flesh out AprilTag
- [ ] Support RUN_TO_POSITION for encoders via moveTo
- [ ] Support moving until sensor says otherwise via moveUntil
- [ ] Tests

Documentation
- [ ] Add more examples
- [ ] Improve usage instructions
- [ ] Add 'Common Issues' section
- [ ] Add graphics for usage, controls
- [ ] Flesh out controls for different drive configurations
- [ ] Create logo
</details>