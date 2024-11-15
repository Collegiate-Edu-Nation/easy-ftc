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

    @Test
    public void teleMec_isCalled() {
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
    public void teleDif_isCalled() {
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
    public void moveDif_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            Drive drivePos = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .diameter(4).build();

            drive.command(0.5, Direction.FORWARD, 1);
            driveEnc.command(0.5, Direction.FORWARD, 1);
            drivePos.command(0.5, Direction.FORWARD, 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveMec_isCalled() {
        mockInit();

        try {
            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM).build();
            Drive driveEnc = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .type(Type.MECANUM).build();
            Drive drivePos = new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder()
                    .diameter(4).type(Type.MECANUM).build();

            drive.command(0.5, Direction.FORWARD, 1);
            driveEnc.command(0.5, Direction.FORWARD, 1);
            drivePos.command(0.5, Direction.FORWARD, 12);
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

        // Test "", no movement
        double[] result = Drive.controlToDirection(2, type, layout, deadzone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test "", forward
        result = Drive.controlToDirection(2, type, layout, deadzone, heading, -1, 0, -1, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "", backward
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

        // Test "", no movement
        double[] result = Drive.controlToDirection(2, type, layout, deadzone, heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(0, result[i], 0.01);
        }

        // Test "", forward
        result = Drive.controlToDirection(2, type, layout, deadzone, heading, -1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test "", backward
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

    @Test(expected = NullPointerException.class)
    public void controlToDirection_nullTypeThrowsException() {
        final Type type = null;
        final Layout layout = Layout.ARCADE;

        // Test Layout.ARCADE
        Drive.controlToDirection(4, type, layout, 0.1, 0, 0, 0, 0, 0);
    }

    @Test(expected = NullPointerException.class)
    public void controlToDirection_nullLayoutThrowsException() {
        final Type type = Type.MECANUM;
        final Layout layout = null;

        Drive.controlToDirection(4, type, layout, 0.1, 0, 0, 0, 0, 0);
    }

    @Test
    public void languageToDirectionDif_isCorrect() {
        final Type type = Type.DIFFERENTIAL;

        // Test Direction.FORWARD
        double[] result = Drive.languageToDirection(2, type, Direction.FORWARD);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test Direction.BACKWARD
        result = Drive.languageToDirection(2, type, Direction.BACKWARD);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }

        // Test "rotateLeft"
        double[] expectedRotateLeft = {-1, 1};
        result = Drive.languageToDirection(2, type, Direction.ROTATE_LEFT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test "rotateRight"
        double[] expectedRotateRight = {1, -1};
        result = Drive.languageToDirection(2, type, Direction.ROTATE_RIGHT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }
    }

    @Test
    public void languageToDirectionMec_isCorrect() {
        final Type type = Type.MECANUM;

        // Test Direction.FORWARD
        double[] result = Drive.languageToDirection(4, type, Direction.FORWARD);
        for (int i = 0; i < result.length; i++) {
            assertEquals(1, result[i], 0.01);
        }

        // Test Direction.BACKWARD
        result = Drive.languageToDirection(4, type, Direction.BACKWARD);
        for (int i = 0; i < result.length; i++) {
            assertEquals(-1, result[i], 0.01);
        }

        // Test "left"
        double[] expectedLeft = {-1, 1, 1, -1};
        result = Drive.languageToDirection(4, type, Direction.LEFT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedLeft[i], result[i], 0.01);
        }

        // Test "right"
        double[] expectedRight = {1, -1, -1, 1};
        result = Drive.languageToDirection(4, type, Direction.RIGHT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRight[i], result[i], 0.01);
        }

        // Test "rotateLeft"
        double[] expectedRotateLeft = {-1, 1, -1, 1};
        result = Drive.languageToDirection(4, type, Direction.ROTATE_LEFT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test "rotateRight"
        double[] expectedRotateRight = {1, -1, 1, -1};
        result = Drive.languageToDirection(4, type, Direction.ROTATE_RIGHT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }

        // Test "forwardLeft"
        double[] expectedForwardLeft = {0, 1, 1, 0};
        result = Drive.languageToDirection(4, type, Direction.FORWARD_LEFT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardLeft[i], result[i], 0.01);
        }

        // Test "forwardRight"
        double[] expectedForwardRight = {1, 0, 0, 1};
        result = Drive.languageToDirection(4, type, Direction.FORWARD_RIGHT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardRight[i], result[i], 0.01);
        }

        // Test "backwardLeft"
        double[] expectedBackwardLeft = {-1, 0, 0, -1};
        result = Drive.languageToDirection(4, type, Direction.BACKWARD_LEFT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardLeft[i], result[i], 0.01);
        }

        // Test "backwardRight"
        double[] expectedBackwardRight = {0, -1, -1, 0};
        result = Drive.languageToDirection(4, type, Direction.BACKWARD_RIGHT);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardRight[i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirectionDif_garbageThrowsException() {
        final Type type = Type.DIFFERENTIAL;

        // Test FORWARD_LEFT
        Drive.languageToDirection(2, type, Direction.FORWARD_LEFT);
    }

    @Test(expected = NullPointerException.class)
    public void languageToDirection_nullDirectionThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.command(1, null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void languageToDirection_nullTypeThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).type(null).build();
        drive.command(1, Direction.FORWARD, 1);
    }
}
