<div align="center">
<img src='docs/images/easy-ftc_color.png' height=96px>

![Static Badge](https://img.shields.io/badge/Version-1.0-blue)
![Static Badge](https://img.shields.io/badge/FTC_SDK-10.1.1-blue)
![Static Badge](https://img.shields.io/badge/Android_API-30-blue)
![Static Badge](https://img.shields.io/badge/OpenJDK-21.0.4-blue)
![Static Badge](https://img.shields.io/badge/Platforms-Linux,_macOS,_Windows-green)
![Static Badge](https://img.shields.io/badge/Coverage-96%25-green)
![Static Badge](https://img.shields.io/badge/Powered_by_Nix-grey?logo=nixOS&logoColor=white)
</div>

Library for easily leveraging in-the-box FTC mechanisms and sensors, including
* Arm
* Claw
* Drivetrain
* Lift
* Color Sensor
* Distance Sensor
* Touch Sensor

Docs deployed at https://collegiate-edu-nation.github.io/easy-ftc<br>
<i>Docs cover Getting Started, Examples, Controls, Diagrams, and the Javadoc</i>

This library greatly simplifies hardware initialization and control by abstracting away low-level decisions and operations, which implies it is highly opinionated. A consequence of this is that most users will only need to use a few methods (command(), control(), and state()) due to a reliance on sane defaults and the builder design-pattern.

<i>Encoders can be enabled for all motor-powered features. Both robot-centric and field-centric driving is supported for Mecanum, while tank and arcade are supported for Differential. Servo-powered features can optionally leverage smooth-servo control, enabling granular, multi-servo synchronization. Supported sensors are: color, distance, and touch</i>

## Install

### Download release archive
* Each option requires that you download this repo's latest Android archive
* Click on 'easy-ftc-release.aar' at https://github.com/collegiate-edu-nation/easy-ftc/releases

### Blockly
* While at the previous link, also click on 'myBlocks.zip'
* Upload the .aar and all relevant java files from myBlocks using OnBot Java's GUI
    * <i>Each java file in 'myBlocks' will control a specific mechanism/sensor. <b>Only add the files for which you'd like to control the associated mechanism/sensor in Blockly</b></i>
* Modify the myBlocks java files to use the correct easy-ftc class or to change behavior
    * <i>e.g. change Mecanum to Differential in Drive.java
    * e.g. use a different constructor, call drive.reverse(), etc.</i>
* Press the gear icon on the bottom right with the title 'Build Everything'
* A new menu option 'Java Classes' should be visible in the Blockly GUI now
* Each class listed there will provide several methods from easy-ftc, like control(), command(), and state()

### OnBot Java
* Upload the .aar using OnBot Java's GUI
* Press the gear icon on the bottom right with the title 'Build Everything'

### Android Studio
* Add the .aar to FtcRobotController/libs/
* Add implementation to TeamCode's buid.gradle  like so

        dependencies {
            implementation project(':FtcRobotController')
            implementation files('../libs/easy-ftc-release.aar')
        }

## Dev Setup
The development environment can be setup by either importing this project into Android Studio, or by using Nix with your IDE of choice

For either approach, gradlew builds are supported

### Nix
Nix is my preferred approach for setting up the development environment. Linux, MacOS, and WSL are supported

<b>Must install flake-enabled Nix before running</b>

Launch development environment

    nix develop github:collegiate-edu-nation/easy-ftc

### Non-Nix
The project can also be imported into Android Studio, where Windows is also supported

<b>Must install git before running</b>

    git clone https://github.com/collegiate-edu-nation/easy-ftc.git

Then import the project in Android Studio

To generate documentation, you must also install

* PlantUML
* mkdocs
* mkdocs-material

### Contributing
This project uses the Google Java Style Guide. I use the Red Hat Java Language Support extension in VSCode, which allows for this to be configured by adding the following to your settings.json

    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml"

Note that LightWeight and Standard modes do not give identical output when formatting

## ToDo
### Release
- [x] Add reverse(String[] names) wrapper
- [x] Use public enums for direction, type, layout
- [ ] Throw exceptions for invalid builder args
- [ ] Update uml
- [ ] Improve code comments (esp. command(), Builder)
- [ ] Add support for trigger (servo), intake (motor)
- [ ] Add hardware naming section
- [ ] Add more examples
- [ ] Add 'Common Issues' section

### Long-Term
- [ ] Support CRServo
- [ ] Flesh out Color
- [ ] Add graphics for blocks and onbot usage, controls
- [ ] Support mechanism + sensor integrations
- [ ] Investigate sequence abstraction + implementation
- [ ] Add telemetry for status indicators
- [ ] Add support for OpenCV, AprilTag
- [ ] Investigate options for synchronized, multi-system sequences
- [ ] Investigate further consolidation of builders (esp names, count, etc)
- [ ] Investigate instrumentation and/or manual hardware tests

## License
[GPLv3](COPYING)