// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
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
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import org.edu_nation.easy_ftc.mechanism.Drive.Type;
import org.edu_nation.easy_ftc.mechanism.Drive.Layout;
import org.edu_nation.easy_ftc.mechanism.Drive.Direction;

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
                    .behavior(DcMotor.ZeroPowerBehavior.FLOAT).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).logo(LogoFacingDirection.UP).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).usb(UsbFacingDirection.FORWARD)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).deadzone(0.1).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse().build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("driveLeft").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("driveRight").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("driveLeft")
                    .reverse("driveRight").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("driveLeft")
                    .reverse("driveRight").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .reverse(new String[] {"driveLeft", "driveRight"}).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).layout(Layout.ARCADE).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().gamepad(mockedGamepad)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().layout(Layout.ARCADE)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .layout(Layout.ARCADE).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .layout(Layout.ARCADE).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .gamepad(mockedGamepad).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().gamepad(mockedGamepad)
                    .layout(Layout.ARCADE).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .gamepad(mockedGamepad).layout(Layout.ARCADE).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).gearing(19.2)
                    .gamepad(mockedGamepad).layout(Layout.ARCADE).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4).reverse()
                    .gamepad(mockedGamepad).layout(Layout.ARCADE).build();

            // mecanum
            new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM).deadzone(0.1)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse().type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("frontLeft")
                    .type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("frontRight")
                    .type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("backLeft")
                    .type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("backRight")
                    .type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("backRight")
                    .reverse("frontLeft").type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("backRight")
                    .reverse("frontLeft").reverse("frontRight").reverse("backLeft")
                    .type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM)
                    .layout(Layout.ROBOT).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().gamepad(mockedGamepad)
                    .type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().type(Type.MECANUM)
                    .layout(Layout.ROBOT).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .type(Type.MECANUM).layout(Layout.ROBOT).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .type(Type.MECANUM).layout(Layout.ROBOT).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .gamepad(mockedGamepad).type(Type.MECANUM).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().gamepad(mockedGamepad)
                    .type(Type.MECANUM).layout(Layout.ROBOT).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                    .gamepad(mockedGamepad).type(Type.MECANUM).layout(Layout.ROBOT).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).count(4).encoder().diameter(4)
                    .gamepad(mockedGamepad).type(Type.MECANUM).layout(Layout.ROBOT).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).count(4).encoder().diameter(4)
                    .gearing(19.2).gamepad(mockedGamepad).type(Type.MECANUM).layout(Layout.ROBOT)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).count(4).encoder().diameter(4)
                    .reverse().gamepad(mockedGamepad).type(Type.MECANUM).layout(Layout.ROBOT)
                    .build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void countThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).count(3).build();
    }

    @Test(expected = NullPointerException.class)
    public void namesThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).names(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void namesLengthThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).count(2).names(new String[] {"abc"})
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void behaviorThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).behavior(null).build();
    }

    @Test(expected = NullPointerException.class)
    public void type_nullTypeThrowsException() {
        mockInit();

        final Type type = null;
        final Layout layout = Layout.ARCADE;
        new Drive.Builder(mockedOpMode, mockedHardwareMap).type(type).layout(layout).build();
    }

    @Test(expected = NullPointerException.class)
    public void layout_nullLayoutThrowsException() {
        mockInit();

        final Type type = Type.MECANUM;
        final Layout layout = null;
        new Drive.Builder(mockedOpMode, mockedHardwareMap).type(type).layout(layout).build();
    }

    @Test(expected = NullPointerException.class)
    public void logoThrowsException() {
        mockInit();

        final LogoFacingDirection logo = null;
        new Drive.Builder(mockedOpMode, mockedHardwareMap).logo(logo).build();
    }

    @Test(expected = NullPointerException.class)
    public void usbThrowsException() {
        mockInit();

        final UsbFacingDirection usb = null;
        new Drive.Builder(mockedOpMode, mockedHardwareMap).usb(usb).build();
    }

    @Test(expected = IllegalStateException.class)
    public void mecType_layoutStateThrowsException() {
        mockInit();

        final Type type = Type.MECANUM;
        final Layout layout = Layout.ARCADE;
        new Drive.Builder(mockedOpMode, mockedHardwareMap).type(type).layout(layout).build();
    }

    @Test(expected = IllegalStateException.class)
    public void mecType_countStateThrowsException() {
        mockInit();

        final Type type = Type.MECANUM;
        final int count = 2;
        new Drive.Builder(mockedOpMode, mockedHardwareMap).type(type).count(count).build();
    }

    @Test(expected = IllegalStateException.class)
    public void difType_layoutStateThrowsException() {
        mockInit();

        final Type type = Type.DIFFERENTIAL;
        final Layout layout = Layout.ROBOT;
        new Drive.Builder(mockedOpMode, mockedHardwareMap).type(type).layout(layout).build();
    }

    @Test
    public void controlMec_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .type(Type.MECANUM).build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .gamepad(mockedGamepad).type(Type.MECANUM).build();

            drive.control();
            drive.control(0.9);
            driveEnc.control();
            driveEnc.control(0.9);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void controlDif_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .gamepad(mockedGamepad).build();

            drive.control();
            drive.control(0.9);
            driveEnc.control();
            driveEnc.control(0.9);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandDif_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            Drive drivePos = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .diameter(4).build();

            drive.command(Direction.FORWARD, 1, 0.5);
            driveEnc.command(Direction.FORWARD, 1, 0.5);
            drivePos.command(Direction.FORWARD, 12, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandMec_isCalled() {
        mockInit();

        try {
            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM).build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .type(Type.MECANUM).build();
            Drive drivePos = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .diameter(4).type(Type.MECANUM).build();

            drive.command(Direction.FORWARD, 1, 0.5);
            driveEnc.command(Direction.FORWARD, 1, 0.5);
            drivePos.command(Direction.FORWARD, 12, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLargeMultiplier_controlThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.control(1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativeMultiplier_controlThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.control(-0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLargePower_commandThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.command(Drive.Direction.FORWARD, 1, 1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNegativePower_commandThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.command(Drive.Direction.FORWARD, 1, -0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidMeasurment_commandThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.command(Drive.Direction.FORWARD, -1, 0.5);
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

        new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("abc").type(Type.MECANUM)
                .build();
        new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("abc")
                .type(Type.MECANUM).build();
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
                .type(Type.MECANUM).build();
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
                .type(Type.MECANUM).build();
    }

    @Test
    public void whenTank_controlToDirection_isCorrect() {
        double deadzone = 0.1;
        double heading = 0;
        final Type type = Type.DIFFERENTIAL;
        final Layout layout = Layout.TANK;

        // Test no movement
        double[] result = Drive.controlToDirection(2, type, layout, deadzone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test forward
        result = Drive.controlToDirection(2, type, layout, deadzone, heading, -1, 0, -1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test backward
        result = Drive.controlToDirection(2, type, layout, deadzone, heading, 1, 0, 1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenArcade_controlToDirection_isCorrect() {
        final Type type = Type.DIFFERENTIAL;
        final Layout layout = Layout.ARCADE;
        double deadzone = 0.1;
        double heading = 0;

        // Test no movement
        double[] result = Drive.controlToDirection(2, type, layout, deadzone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test forward
        result = Drive.controlToDirection(2, type, layout, deadzone, heading, -1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test backward
        result = Drive.controlToDirection(2, type, layout, deadzone, heading, 1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenRobotCentric_controlToDirection_isCorrect() {
        final double deadzone = 0.1;
        final double heading = 0;
        final Type type = Type.MECANUM;
        final Layout layout = Layout.ROBOT;

        // Test no movement
        double[] result = Drive.controlToDirection(4, type, layout, deadzone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test forward
        result = Drive.controlToDirection(4, type, layout, deadzone, heading, -1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test backward
        result = Drive.controlToDirection(4, type, layout, deadzone, heading, 1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }
    }

    @Test
    public void whenFieldCentric_controlToDirection_isCorrect() {
        final double deadzone = 0.1;
        final double heading = Math.PI / 2; // equals 90 degrees
        final double[][] expectedValues = {{0, 0, 0, 0}, {1, -1, -1, 1}, {-1, 1, 1, -1}};
        final Type type = Type.MECANUM;
        final Layout layout = Layout.FIELD;

        // Test no movement
        double[] result = Drive.controlToDirection(4, type, layout, deadzone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test forward
        result = Drive.controlToDirection(4, type, layout, deadzone, heading, -1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test backward
        result = Drive.controlToDirection(4, type, layout, deadzone, heading, 1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void controlToDirectionDif_garbageThrowsException() {
        final Type type = Type.DIFFERENTIAL;
        final Layout layout = Layout.ROBOT;

        // Test Layout.ROBOT
        Drive.controlToDirection(2, type, layout, 0.1, 0, 0, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void controlToDirectionMec_garbageThrowsException() {
        final Type type = Type.MECANUM;
        final Layout layout = Layout.ARCADE;

        // Test Layout.ARCADE
        Drive.controlToDirection(4, type, layout, 0.1, 0, 0, 0, 0, 0);
    }

    @Test
    public void languageToDirectionDif_isCorrect() {
        final Type type = Type.DIFFERENTIAL;
        final Layout layout = Layout.TANK;

        // Test Direction.FORWARD
        double[] result = Drive.languageToDirection(2, type, layout, Direction.FORWARD, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test Direction.BACKWARD
        result = Drive.languageToDirection(2, type, layout, Direction.BACKWARD, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }

        // Test Direction.ROTATE_LEFT
        double[] expectedRotateLeft = {-1, 1};
        result = Drive.languageToDirection(2, type, layout, Direction.ROTATE_LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_RIGHT
        double[] expectedRotateRight = {1, -1};
        result = Drive.languageToDirection(2, type, layout, Direction.ROTATE_RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }
    }

    @Test
    public void languageToDirectionMec_isCorrect() {
        final Type type = Type.MECANUM;
        final Layout layout = Layout.ROBOT;

        // Test Direction.FORWARD
        double[] result = Drive.languageToDirection(4, type, layout, Direction.FORWARD, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test Direction.BACKWARD
        result = Drive.languageToDirection(4, type, layout, Direction.BACKWARD, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }

        // Test Direction.LEFT
        double[] expectedLeft = {-1, 1, 1, -1};
        result = Drive.languageToDirection(4, type, layout, Direction.LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedLeft[i], result[i], 0.01);
        }

        // Test Direction.RIGHT
        double[] expectedRight = {1, -1, -1, 1};
        result = Drive.languageToDirection(4, type, layout, Direction.RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRight[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_LEFT
        double[] expectedRotateLeft = {-1, 1, -1, 1};
        result = Drive.languageToDirection(4, type, layout, Direction.ROTATE_LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_RIGHT
        double[] expectedRotateRight = {1, -1, 1, -1};
        result = Drive.languageToDirection(4, type, layout, Direction.ROTATE_RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }

        // Test Direction.FORWARD_LEFT
        double[] expectedForwardLeft = {0, 1, 1, 0};
        result = Drive.languageToDirection(4, type, layout, Direction.FORWARD_LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardLeft[i], result[i], 0.01);
        }

        // Test Direction.FORWARD_RIGHT
        double[] expectedForwardRight = {1, 0, 0, 1};
        result = Drive.languageToDirection(4, type, layout, Direction.FORWARD_RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardRight[i], result[i], 0.01);
        }

        // Test Direction.BACKWARD_LEFT
        double[] expectedBackwardLeft = {-1, 0, 0, -1};
        result = Drive.languageToDirection(4, type, layout, Direction.BACKWARD_LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardLeft[i], result[i], 0.01);
        }

        // Test Direction.BACKWARD_RIGHT
        double[] expectedBackwardRight = {0, -1, -1, 0};
        result = Drive.languageToDirection(4, type, layout, Direction.BACKWARD_RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardRight[i], result[i], 0.01);
        }
    }

    @Test
    public void whenFieldCentric_languageToDirection_isCorrect() {
        final double heading = Math.PI / 2; // equals 90 degrees
        final double[][] expectedValues = {{1, -1, -1, 1}, {-1, 1, 1, -1}};
        final Type type = Type.MECANUM;
        final Layout layout = Layout.FIELD;

        // Test forward
        double[] result = Drive.languageToDirection(4, type, layout, Direction.FORWARD, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test backward
        result = Drive.languageToDirection(4, type, layout, Direction.BACKWARD, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test Direction.LEFT
        double[] expectedLeft = {1, 1, 1, 1};
        result = Drive.languageToDirection(4, type, layout, Direction.LEFT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedLeft[i], result[i], 0.01);
        }

        // Test Direction.RIGHT
        double[] expectedRight = {-1, -1, -1, -1};
        result = Drive.languageToDirection(4, type, layout, Direction.RIGHT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRight[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_LEFT
        double[] expectedRotateLeft = {-1, 1, -1, 1};
        result = Drive.languageToDirection(4, type, layout, Direction.ROTATE_LEFT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_RIGHT
        double[] expectedRotateRight = {1, -1, 1, -1};
        result = Drive.languageToDirection(4, type, layout, Direction.ROTATE_RIGHT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }

        // Test Direction.FORWARD_LEFT
        double[] expectedForwardLeft = {1, 0, 0, 1};
        result = Drive.languageToDirection(4, type, layout, Direction.FORWARD_LEFT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardLeft[i], result[i], 0.01);
        }

        // Test Direction.FORWARD_RIGHT
        double[] expectedForwardRight = {0, -1, -1, 0};
        result = Drive.languageToDirection(4, type, layout, Direction.FORWARD_RIGHT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardRight[i], result[i], 0.01);
        }

        // Test Direction.BACKWARD_LEFT
        double[] expectedBackwardLeft = {0, 1, 1, 0};
        result = Drive.languageToDirection(4, type, layout, Direction.BACKWARD_LEFT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardLeft[i], result[i], 0.01);
        }

        // Test Direction.BACKWARD_RIGHT
        double[] expectedBackwardRight = {-1, 0, 0, -1};
        result = Drive.languageToDirection(4, type, layout, Direction.BACKWARD_RIGHT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardRight[i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirectionDif_garbageThrowsException() {
        final Type type = Type.DIFFERENTIAL;
        final Layout layout = Layout.TANK;

        // Test FORWARD_LEFT
        Drive.languageToDirection(2, type, layout, Direction.FORWARD_LEFT, 0);
    }

    @Test(expected = NullPointerException.class)
    public void languageToDirection_nullDirectionThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.command(null, 1, 1);
    }

    @Test(expected = NullPointerException.class)
    public void languageToDirection_nullTypeThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).type(null).build();
        drive.command(Direction.FORWARD, 1, 1);
    }
}
