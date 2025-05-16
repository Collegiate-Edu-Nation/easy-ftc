// SPDX-FileCopyrightText: 2025 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.edu_nation.easy_ftc.mechanism.Drive.Direction;
import org.junit.Test;

public class TestCommandSequence {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "driveLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "driveRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "driveLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "driveRight")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotor.class, "frontLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "frontRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "backLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "backRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "frontLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "frontRight")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "backLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "backRight")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void CommandSequence_multipleCommands() {
        mockInit();

        try {
            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            CommandSequence sequence =
                    new CommandSequence(drive)
                            .command(Direction.FORWARD, 2, 0.5)
                            .command(Direction.BACKWARD, 2, 0.5);

            FieldUtils.writeField(mockedGamepad, "dpad_left", false);
            FieldUtils.writeField(mockedGamepad, "dpad_right", true);

            sequence.use();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void CommandSequence_nullThrowsException() {
        CommandSequence sequence =
                new CommandSequence(null)
                        .command(Direction.FORWARD, 2, 0.5)
                        .command(Direction.BACKWARD, 2, 0.5);
    }
}
