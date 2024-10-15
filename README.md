# easy-ftc
![Static Badge](https://img.shields.io/badge/Version-1.0-blue)
![Static Badge](https://img.shields.io/badge/FTC_SDK-10.1.0-blue)
![Static Badge](https://img.shields.io/badge/Android_API-29-blue)
![Static Badge](https://img.shields.io/badge/OpenJDK-17.0.10-blue)
![Static Badge](https://img.shields.io/badge/Platforms-Linux,_macOS,_Windows-green)
![Static Badge](https://img.shields.io/badge/Coverage-95%25-green)
![Static Badge](https://img.shields.io/badge/Powered_by_Nix-grey?logo=nixOS&logoColor=white)

Library for easily leveraging in-the-box FTC mechanisms and features, including
* Arm
* Claw
* Drivetrain
* Lift
* Sensors

Docs deployed at https://camdenboren.github.io/easy-ftc<br>
<i>Docs cover examples, controls, diagrams, and API reference</i>

This library greatly simplifies hardware initialization and control by abstracting away low-level decisions and operations, which implies it is highly opinionated. A consequence of this is that most users will only need to use a few methods (move(), tele(), reverse(), and state()) due to a reliance on sane defaults and constructor-enabled features.

<i>Encoders can be enabled for all motor-powered features. Both robot-centric and field-centric driving is supported for Mecanum, while tank and arcade are supported for Differential. Servo-powered features leverage smooth-servo control by default, enabling multi-servo synchronization. This causes blocking when used in TeleOp, however, so it can be disabled if that causes issues (e.g. the robot must be able to drive while the servos are moving to the desired position). Supported sensors are: AprilTag, color, distance, and touch</i>

## Usage

### Download release archive
* Each option requires that you download this repo's latest Android archive
* Click on 'easy-ftc-release.aar' at https://github.com/camdenboren/easy-ftc/releases

### Blockly
* While at the previous link, also click on 'myBlocks.zip'
* Upload the .aar and all relevant java files from myBlocks using OnBot Java's GUI
    * <i>Each java file in 'myBlocks' will control a specific mechanism/sensor. <b>Only add the files for which you'd like to control the associated mechanism/sensor in Blockly</b></i>
* Modify the myBlocks java files to use the correct easy-ftc class or to change behavior
    * <i>e.g. change Mecanum to Differential in Drive.java
    * e.g. use a different constructor, call drive.reverse(), etc.</i>
* Press the gear icon on the bottom right with the title 'Build Everything'
* A new menu option 'Java Classes' should be visible in the Blockly GUI now
* Each class listed there will provide several methods from easy-ftc, like tele(), move(), and state()

### OnBot Java
* Upload the .aar using OnBot Java's GUI
* Press the gear icon on the bottom right with the title 'Build Everything'

### Android Studio
* Add the .aar to FtcRobotController/libs/
* Add libs/ to your root build.gradle like so

        allprojects {
            repositories {
                mavenCentral()
                google()
                flatDir {
                    dirs("libs")
                }
            }
        }
* Add implementation to TeamCode's buid.gradle  like so

        dependencies {
            implementation project(':FtcRobotController')
            implementation(name:'easy-ftc-release', ext:'aar')
        }

## Dev Setup
Nix is my preferred approach for setting up the development environment. Linux, MacOS, and WSL are supported

<b>Must install flake-enabled Nix before running</b>

Launch development environment

    nix develop github:camdenboren/easy-ftc

The project can also be imported into Android Studio, where Windows is also supported

<b>Must install git and Android Studio before running</b>

    git clone https://github.com/camdenboren/easy-ftc.git
    Import project in Android Studio

For either approach, gradlew builds are supported

If contributing, this project uses the Google Java Style Guide. I use the Red Hat Java Language Support extension in VSCode, which allows for this to be configured by adding the following to your settings.json

    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml"

Note that LightWeight and Standard modes do not give identical output when formatting.

## ToDo
### Update
- [x] Move to FTC SDK v10.1 (update javadoc cmd as well)
- [ ] Clean up unused deps

### Features
- [x] Unit Tests (motors)
- [x] Unit Tests (servos, sensors)
- [x] Add reverse()
- [ ] Add support for trigger (servo), intake (motor)
- [ ] Add support for OpenCV
- [ ] Flesh out AprilTag
- [x] Support RUN_TO_POSITION for encoders
- [ ] Support moving until sensor says otherwise
- [ ] Add telemetry for status indicators
- [x] Add support for Blockly

### Chores
- [ ] Use camelCase for hardware naming
- [ ] Make smoothServo opt-in
- [ ] Investigate layout approach for lift, arm, claw
- [ ] Investigate multi-threading for smoothServo control
- [ ] Investigate instrumentation and/or manual hardware tests

### Documentation
- [ ] Add hardware naming section
- [ ] Improve usage instructions
- [ ] Add more examples
- [ ] Add 'Common Issues' section
- [ ] Add graphics for blocks and onbot usage, controls
- [x] Flesh out controls for different drive configurations
- [ ] Create logo