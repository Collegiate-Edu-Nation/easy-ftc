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
| Intake   | Motor Mechanism |
| Lift     | Motor Mechanism |
| Trigger  | Servo Mechanism |
| Color    | Sensor          |
| Distance | Sensor          |
| Touch    | Sensor          |

</div>

## Overview

This library greatly simplifies the implementation of common FTC robotics subsystems by abstracting away low-level decisions and providing a simplified, façade pattern API. As such, users will only need to use a few methods (command(), control(), and state()) due to a reliance on sane defaults and the builder design pattern

Additionally, several useful quality-of-life features are included

- Encoders can be used with minimal setup for either time- or distance-based commands
- Arm, Intake, and Lift optionally feature encoder limits on range-of-motion
- Robot and field-centric driving are supported for Mecanum
- Tank and arcade are supported for Differential
- Servo-powered mechanisms can optionally leverage smooth-servo control
- Lift and Drive optionally feature gamepad deadzones, mitigating stick/trigger drift

Docs deployed at https://collegiate-edu-nation.github.io/easy-ftc<br>
<i>Docs cover Getting Started, Examples, Controls, Naming, Diagrams, and the Javadoc</i>

## Install

### Download release archive

- Each option requires that you download this repo's latest Android archive
- Click on 'easy-ftc-release.aar' at https://github.com/collegiate-edu-nation/easy-ftc/releases

### OnBot Java

- Upload the .aar using OnBot Java's GUI
- Press the gear icon on the bottom right with the title 'Build Everything'

### Android Studio

- Add the .aar to FtcRobotController/libs/
- Add implementation to TeamCode's buid.gradle like so

        dependencies {
            implementation project(':FtcRobotController')
            implementation files('../libs/easy-ftc-release.aar')
        }

### Blockly

- While at the previous link, also click on 'myBlocks.zip'
- Upload the .aar and all relevant Java files from myBlocks using OnBot Java's GUI
  - <i>Each Java file in 'myBlocks' will control a specific mechanism/sensor</i>
- Modify the myBlocks Java files to change behavior
  - <i>e.g. add .layout(Layout.ARCADE) to Drive.Builder in Drive.java</i>
- Press the gear icon on the bottom right with the title 'Build Everything'
- A new menu option 'Java Classes' should be visible in the Blockly GUI now
- Each class listed there will provide relevant methods from easy-ftc, like control(), command(), and state()

## License

[GPLv3](COPYING)
