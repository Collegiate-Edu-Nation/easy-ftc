<div align="center">
<img src='docs/img/logo/easy-ftc_color.png' alt='easy-ftc' height=auto width=400px>

![Static Badge](https://img.shields.io/badge/Version-1.1.0-mediumblue?style=for-the-badge&labelColor=8C8F8F)
![Static Badge](https://img.shields.io/badge/FTC_SDK-10.1.1-mediumblue?style=for-the-badge&labelColor=8C8F8F)
![Static Badge](https://img.shields.io/badge/Android_API-30-mediumblue?style=for-the-badge&labelColor=8C8F8F)
![Static Badge](https://img.shields.io/badge/OpenJDK-21.0.4-mediumblue?style=for-the-badge&labelColor=8C8F8F)\
![Dynamic Badge](https://img.shields.io/github/actions/workflow/status/collegiate-edu-nation/easy-ftc/build.yaml?branch=main&style=for-the-badge&labelColor=8C8F8F&color=forestgreen)
[![Dynamic Badge](https://img.shields.io/endpoint.svg?url=https%3A%2F%2Fgarnix.io%2Fapi%2Fbadges%2Fcollegiate-edu-nation%2Feasy-ftc%3Fbranch%3Dmain&style=for-the-badge&color=8C8F8F&labelColor=8C8F8F)](https://garnix.io/repo/collegiate-edu-nation/easy-ftc)
![Static Badge](https://img.shields.io/badge/Powered_by_Nix-grey?logo=nixOS&logoColor=white&logoSize=auto&style=for-the-badge&color=8C8F8F)

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

### What does this do?

This library greatly simplifies the implementation of common FTC robotics subsystems and features by abstracting away low-level decisions and providing a simplified, façade pattern API. As such, users will only need to use a few methods (`command()`, `control()`, and `state()`) due to a reliance on sane defaults and the builder design pattern

### Who is this for?

The target audience is beginner-level FTC teams and hobbyists, but even advanced users can elect to utilize easy-ftc à la carte (e.g. leverage easy-ftc for peripheral devices so you can focus on more complex features like motion planning)

### Features

- Encoders can be used with minimal setup for either time- or distance-based commands
- The IMU's Gyro can be leveraged with minimal setup for angle-based commands
- `Arm`, `Intake`, and `Lift` optionally feature encoder limits on range-of-motion
- `ROBOT` and `FIELD`-centric driving are supported for `MECANUM`
- `TANK` and `ARCADE` are supported for `DIFFERENTIAL`
- Servo-powered mechanisms can optionally leverage smooth-servo control
- `Lift` and `Drive` optionally feature gamepad deadzones, mitigating stick/trigger drift
- Routine tasks in the TeleOp phase (like a scoring sequence) can be easily modelled via `CommandSequence`

### Docs

Deployed at https://collegiate-edu-nation.github.io/easy-ftc<br>
_Cover Getting Started, Examples, Controls, Naming, Diagrams, and the Javadoc_

## Install

### Download release archive

- Each option requires that you download this repo's latest Android archive
- Click on `easy-ftc-release.aar` at https://github.com/collegiate-edu-nation/easy-ftc/releases

### OnBot Java

- Upload the .aar using OnBot Java's GUI
- Press the gear icon on the bottom right with the title 'Build Everything'

### Android Studio

- Add the .aar to `FtcRobotController/libs/`
- Add implementation to TeamCode's `build.gradle` like so

  ```groovy
  dependencies {
      implementation project(':FtcRobotController')
      implementation files('../libs/easy-ftc-release.aar')
  }
  ```

### Blockly

- While at the previous link, also click on `myBlocks.zip`
- Upload the .aar and all relevant Java files from myBlocks using OnBot Java's GUI
  - _Each Java file in `myBlocks/` will control a specific mechanism/sensor_
- Modify the myBlocks Java files to change behavior
  - _e.g. add `.layout(Layout.ARCADE)` to `Drive.Builder` in `Drive.java`_
- Press the gear icon on the bottom right with the title 'Build Everything'
- A new menu option 'Java Classes' should be visible in the Blockly GUI now
- Each class listed there will provide relevant methods from easy-ftc, like `control()`, `command()`, and `state()`

## Contributing

[CONTRIBUTING.md](.github/CONTRIBUTING.md)

## License

[GPLv3](COPYING)
