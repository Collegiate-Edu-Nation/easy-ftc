// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class TestDrive {
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
    public void Drive_initializes() {
        mockInit();

        try {
            // differential
            new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap)
                    .names(new String[] {"driveLeft", "driveRight"}).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap)
                    .behavior(DcMotor.ZeroPowerBehavior.FLOAT);
            new Drive.Builder(mockedOpMode, mockedHardwareMap).deadzone(0.1).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse().build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("driveLeft").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("driveRight").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("driveLeft")
                    .reverse("driveRight").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("driveLeft")
                    .reverse("driveRight").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).layout("arcade").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().gamepad(mockedGamepad)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().layout("arcade").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .layout("arcade").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .layout("arcade").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .gamepad(mockedGamepad).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().gamepad(mockedGamepad)
                    .layout("arcade").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .gamepad(mockedGamepad).layout("arcade").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).gearing(19.2)
                    .gamepad(mockedGamepad).layout("arcade").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).reverse()
                    .gamepad(mockedGamepad).layout("arcade").build();

            // mecanum
            new Drive.Builder(mockedOpMode, mockedHardwareMap).type("mecanum").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).type("mecanum").deadzone(0.1)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().type("mecanum").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse().type("mecanum").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("frontLeft").type("mecanum")
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("frontRight").type("mecanum")
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("backLeft").type("mecanum")
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("backRight").type("mecanum")
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("backRight")
                    .reverse("frontLeft").type("mecanum").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("backRight")
                    .reverse("frontLeft").reverse("frontRight").reverse("backLeft").type("mecanum")
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .type("mecanum").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).type("mecanum").layout("robot")
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).type("mecanum")
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().gamepad(mockedGamepad)
                    .type("mecanum").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().type("mecanum")
                    .layout("robot").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .type("mecanum").layout("robot").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).type("mecanum")
                    .layout("robot").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .gamepad(mockedGamepad).type("mecanum").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().gamepad(mockedGamepad)
                    .type("mecanum").layout("robot").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .gamepad(mockedGamepad).type("mecanum").layout("robot").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).count(4).encoder().diameter(4)
                    .gamepad(mockedGamepad).type("mecanum").layout("robot").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).count(4).encoder().diameter(4)
                    .gearing(19.2).gamepad(mockedGamepad).type("mecanum").layout("robot").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).count(4).encoder().diameter(4)
                    .reverse().gamepad(mockedGamepad).type("mecanum").layout("robot").build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleMec_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .type("mecanum").build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .gamepad(mockedGamepad).type("mecanum").build();

            drive.tele();
            drive.tele(0.9);
            driveEnc.tele();
            driveEnc.tele(0.9);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleDif_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .gamepad(mockedGamepad).build();

            drive.tele();
            drive.tele(0.9);
            driveEnc.tele();
            driveEnc.tele(0.9);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveDif_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            Drive drivePos = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .diameter(4).build();

            drive.move(0.5, "forward", 1);
            driveEnc.move(0.5, "forward", 1);
            drivePos.move(0.5, "forward", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveMec_isCalled() {
        mockInit();

        try {
            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap).type("mecanum").build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .type("mecanum").build();
            Drive drivePos = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .diameter(4).type("mecanum").build();

            drive.move(0.5, "forward", 1);
            driveEnc.move(0.5, "forward", 1);
            drivePos.move(0.5, "forward", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseDif_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("abc").build();
        new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("abc").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseMec_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("abc").type("mecanum").build();
        new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("abc").type("mecanum")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDif_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).gearing(-1)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingMec_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).gearing(-1)
                .type("mecanum").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setdeadzoneDif_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).deadzone(-1)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setdeadzoneMec_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).deadzone(-1)
                .type("mecanum").build();
    }
}
