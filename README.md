# easy-ftc
![Static Badge](https://img.shields.io/badge/Version-1.0-blue)
![Static Badge](https://img.shields.io/badge/Platforms-Linux,_macOS,_Windows-red)
![Static Badge](https://img.shields.io/badge/Powered_by_Nix-grey?logo=nixOS&logoColor=white)

Library for easily leveraging in-the-box FTC mechanisms and features, including
* Arm (Solo and Dual motor)
* Claw (Solo and Dual servo)
* Drive (Differential and Mecanum)
* Lift (Solo and Dual motor)
* Sensors (Distance, Touch, AprilTag)

<i>Encoders can be enabled for all motor-powered features. Both robot-centric and field-centric driving is supported for Mecanum, while tank and arcade are supported for Differential</i>

Docs deployed at https://camdenboren.github.io/easy-ftc<br>
<i>Docs cover examples, controls, and API reference</i>

## Usage

### Download release archive
* Each option requires that you download this repo's latest Android archive
* Click on 'easy-ftc-release.aar' at https://github.com/camdenboren/easy-ftc/releases

### OnBot Java
* Upload the .aar using OnBot Java's GUI

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

## Advanced Usage
Generate javadoc

    javadoc -d docs -classpath easy-ftc/build/aarLibraries/org.firstinspires.ftc-RobotCore-10.0.0.jar:easy-ftc/build/aarLibraries/org.firstinspires.ftc-Vision-10.0.0.jar:easy-ftc/build/aarLibraries/org.firstinspires.ftc-Hardware-10.0.0.jar -sourcepath easy-ftc/src/main/java/ org.cen.easy_ftc.arm org.cen.easy_ftc.claw org.cen.easy_ftc.drive org.cen.easy_ftc.lift org.cen.easy_ftc.sensor -public -overview docs/overview.html

## ToDo
### Features
- [ ] Add support for Blockly
- [ ] Add setters for directionality
- [ ] OpenCV
- [ ] Flesh out AprilTag
- [ ] Support RUN_TO_POSITION for encoders via moveTo
- [ ] Support moving until sensor says otherwise via moveUntil
- [ ] Tests

### Documentation
- [ ] Add more examples
- [x] Improve usage instructions
- [ ] Add 'Common Issues' section
- [ ] Add graphics for blocks and onbot usage, controls
- [x] Flesh out controls for different drive configurations
- [ ] Create logo