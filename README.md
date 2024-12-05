<div align="center">
<img src='docs/img/logo/easy-ftc_color.png' height=96px>

![Static Badge](https://img.shields.io/badge/Version-1.0-blue)
![Static Badge](https://img.shields.io/badge/FTC_SDK-10.1.1-blue)
![Static Badge](https://img.shields.io/badge/Android_API-30-blue)
![Static Badge](https://img.shields.io/badge/OpenJDK-21.0.4-blue)
![Static Badge](https://img.shields.io/badge/Platforms-Linux,_macOS,_Windows-green)
![Static Badge](https://img.shields.io/badge/Coverage-97%25-green)
![Static Badge](https://img.shields.io/badge/Powered_by_Nix-grey?logo=nixOS&logoColor=white)

Library for easily leveraging in-the-box FTC mechanisms and sensors, including

| Name     | Type            |
| -------- | --------------- |
| Arm      | Motor Mechanism |
| Claw     | Servo Mechanism |
| Drive    | Motor Mechanism |
| Lift     | Motor Mechanism |
| Color    | Sensor          |
| Distance | Sensor          |
| Touch    | Sensor          |
</div>

## Overview

This library greatly simplifies hardware initialization and control by abstracting away low-level decisions and operations. As such, users will only need to use a few methods (command(), control(), and state()) due to a reliance on sane defaults and the builder design pattern 

Additionally, several useful quality-of-life features are included
* Encoders can be used with minimal setup for either time- or distance-based commands
* Arm and Lift optionally feature encoder limits on range-of-motion
* Robot and field-centric driving are supported for Mecanum
* Tank and arcade are supported for Differential
* Servo-powered mechanisms can optionally leverage smooth-servo control
* Lift and Drive optionally feature gamepad deadzones, mitigating stick/trigger drift

Docs deployed at https://collegiate-edu-nation.github.io/easy-ftc<br>
<i>Docs cover Getting Started, Examples, Controls, Naming, Diagrams, and the Javadoc</i>

## Install

### Download release archive
* Each option requires that you download this repo's latest Android archive
* Click on 'easy-ftc-release.aar' at https://github.com/collegiate-edu-nation/easy-ftc/releases

### Blockly
* While at the previous link, also click on 'myBlocks.zip'
* Upload the .aar and all relevant Java files from myBlocks using OnBot Java's GUI
    * <i>Each Java file in 'myBlocks' will control a specific mechanism/sensor</i>
* Modify the myBlocks Java files to change behavior
    * <i>e.g. add .type(Drive.Type.MECANUM) to Drive.Builder in Drive.java</i>
* Press the gear icon on the bottom right with the title 'Build Everything'
* A new menu option 'Java Classes' should be visible in the Blockly GUI now
* Each class listed there will provide relevant methods from easy-ftc, like control(), command(), and state()

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
The development environment can be setup by either using Nix with your IDE of choice or by importing this project into Android Studio

### Nix
Nix is my preferred approach for setting up the development environment. Linux and macOS are supported

<b>Must install flake-enabled Nix before running</b>

Launch development environment

    nix develop github:collegiate-edu-nation/easy-ftc

### Non-Nix
The project can also be imported into Android Studio, where Windows is supported as well

<b>Must install git before running</b>

    git clone https://github.com/collegiate-edu-nation/easy-ftc.git

Then import the project in Android Studio

To generate documentation, you must also install

* PlantUML
* mkdocs
* mkdocs-material

## ToDo
### Release
- [x] Add reverse(String[] names) wrapper
- [x] Use public enums for direction, type, layout
- [x] Throw exceptions for invalid builder and method args
- [x] Support FIELD in command()
- [x] Support positional limits in command()
- [x] Update uml
- [x] Improve code comments (esp. command(), Builder)
- [ ] Add support for trigger (servo), intake (motor)
- [x] Add naming section
- [x] Add more examples
- [ ] Add graphics for blocks examples, gettingstarted
- [ ] Add 'Common Issues' section

### Long-Term
- [ ] Support gyro-based turning for drive in cmd
- [ ] Support CRServo
- [ ] Flesh out Color
- [ ] Add graphics for blocks and onbot usage
- [ ] Add graphic for controls
- [ ] Look into RACE layout for differential (triggers instead of joystick for axial)
- [ ] Support mechanism + sensor integrations
- [ ] Investigate sequence abstraction + implementation
- [ ] Add telemetry for status indicators
- [ ] Add support for OpenCV, AprilTag
- [ ] Investigate options for synchronized, multi-system sequences
- [ ] Investigate further consolidation of builders (esp names, count, etc)
- [ ] Investigate instrumentation and/or manual hardware tests

## License
[GPLv3](COPYING)