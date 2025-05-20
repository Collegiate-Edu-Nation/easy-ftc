package org.firstinspires.ftc.teamcode;

import org.edu_nation.easy_ftc.mechanism.Arm;
import org.edu_nation.easy_ftc.mechanism.Claw;
import org.edu_nation.easy_ftc.mechanism.Drive;
import org.edu_nation.easy_ftc.mechanism.Intake;
import org.edu_nation.easy_ftc.mechanism.Lift;
import org.edu_nation.easy_ftc.mechanism.Trigger;
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;

public class CommandSequence extends BlocksOpModeCompanion {
    @ExportToBlocks(
            comment = "Initiate an automated arm movement",
            parameterLabels = {"Direction", "Time", "Power"})
    public static void use(org.edu_nation.easy_ftc.mechanism.CommandSequence sequence) {
        sequence.use();
    }

    @ExportToBlocks()
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence CommandSequence() {
        return new org.edu_nation.easy_ftc.mechanism.CommandSequence();
    }

    @ExportToBlocks(
            comment = "Add an Arm command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Direction", "Time", "Power"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence commandArm(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Arm.Direction direction,
            double time,
            double power) {
        Arm arm = new Arm.Builder(linearOpMode, hardwareMap).gamepad(gamepad1).build();
        return sequence.command(arm, direction, time, power);
    }

    @ExportToBlocks(
            comment = "Add a Claw command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Direction"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence commandClaw(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence, Claw.Direction direction) {
        Claw claw = new Claw.Builder(linearOpMode, hardwareMap).gamepad(gamepad1).build();
        return sequence.command(claw, direction);
    }

    @ExportToBlocks(
            comment = "Add a Drive command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Direction", "Time", "Power"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence commandDrive(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Drive.Direction direction,
            double time,
            double power) {
        Drive drive = new Drive.Builder(linearOpMode, hardwareMap).gamepad(gamepad1).build();
        return sequence.command(drive, direction, time, power);
    }

    @ExportToBlocks(
            comment = "Add an Intake command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Direction", "Time", "Power"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence commandIntake(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Intake.Direction direction,
            double time,
            double power) {
        Intake intake = new Intake.Builder(linearOpMode, hardwareMap).gamepad(gamepad1).build();
        return sequence.command(intake, direction, time, power);
    }

    @ExportToBlocks(
            comment = "Add a Lift command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Direction", "Time", "Power"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence commandLift(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Lift.Direction direction,
            double time,
            double power) {
        Lift lift = new Lift.Builder(linearOpMode, hardwareMap).gamepad(gamepad1).build();
        return sequence.command(lift, direction, time, power);
    }

    @ExportToBlocks(
            comment = "Add a Trigger command to the sequence via method chaining",
            parameterLabels = {"CommandSequence", "Direction"})
    public static org.edu_nation.easy_ftc.mechanism.CommandSequence commandTrigger(
            org.edu_nation.easy_ftc.mechanism.CommandSequence sequence,
            Trigger.Direction direction) {
        Trigger trigger = new Trigger.Builder(linearOpMode, hardwareMap).gamepad(gamepad1).build();
        return sequence.command(trigger, direction);
    }
}
