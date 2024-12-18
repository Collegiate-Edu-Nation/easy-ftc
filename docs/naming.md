---
hide:
  - toc
---

# Naming

These are the default hardware names. They can be modified via `Builder.names()`, if desired. Check the Javadoc for an object's `Builder` to see all default values

## Mechanisms

### Non-Drive

=== "1"

    | Mechanism | Name    |
    | --------- | ------- |
    | Arm       | arm     |
    | Claw      | claw    |
    | Intake    | intake  |
    | Lift      | lift    |
    | Trigger   | trigger |

=== "2"

    | Mechanism | Names                     |
    | --------- | ------------------------- |
    | Arm       | armLeft, armRight         |
    | Claw      | clawLeft, clawRight       |
    | Intake    | intakeLeft, intakeRight   |
    | Lift      | liftLeft, liftRight       |
    | Trigger   | triggerLeft, triggerRight |

### Drive

=== "2"

    | Names                 |
    | --------------------- |
    | driveLeft, driveRight |

=== "4"

    | Names                                      |
    | ------------------------------------------ |
    | frontLeft, frontRight, backLeft, backRight |

## Sensors

| Sensor   | Name     |
| -------- | -------- |
| Color    | color    |
| Distance | distance |
| Touch    | touch    |
