// SPDX-FileCopyrightText: Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.LogoFacingDirection;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.edu_nation.easy_ftc.mechanism.Drive.Direction;
import org.edu_nation.easy_ftc.mechanism.Drive.Layout;
import org.edu_nation.easy_ftc.mechanism.Drive.Type;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.junit.Test;

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
            new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).count(4).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap)
                    .names(new String[] {"driveLeft", "driveRight"})
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap)
                    .behavior(DcMotor.ZeroPowerBehavior.FLOAT)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).logo(LogoFacingDirection.UP).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap)
                    .usb(UsbFacingDirection.FORWARD)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).deadzone(0.1).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse().build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).reverse("driveLeft").build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap)
                    .reverse("driveLeft")
                    .reverse("driveRight")
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap)
                    .encoder()
                    .reverse(new String[] {"driveLeft", "driveRight"})
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).layout(Layout.ARCADE).build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap)
                    .encoder()
                    .diameter(4)
                    .gearing(19.2)
                    .build();
            new Drive.Builder(mockedOpMode, mockedHardwareMap).count(4).type(Type.MECANUM).build();
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

        new Drive.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .names(new String[] {"abc"})
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void diameterNoEncoderThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap).diameter(1).build();
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
            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .type(Type.MECANUM)
                            .build();
            Drive driveEnc =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .gamepad(mockedGamepad)
                            .type(Type.MECANUM)
                            .build();

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
            Drive drive =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            Drive driveEnc =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .gamepad(mockedGamepad)
                            .build();

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
            Drive drivePos =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .diameter(4)
                            .build();

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
            Drive driveEnc =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .type(Type.MECANUM)
                            .build();
            Drive drivePos =
                    new Drive.Builder(mockedOpMode, mockedHardwareMap)
                            .encoder()
                            .diameter(4)
                            .type(Type.MECANUM)
                            .build();

            drive.command(Direction.FORWARD, 1, 0.5);
            driveEnc.command(Direction.FORWARD, 1, 0.5);
            drivePos.command(Direction.FORWARD, 12, 0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void commandGyro_throwsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.command(Direction.FORWARD, 90, 0.5, AngleUnit.DEGREES);
    }

    @Test
    public void moveForMeasurementGyro_isCalled() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        Drive mockedDrive = spy(drive);
        doReturn(false).when(mockedDrive).gyroIsBusy(90);
        double[] movements = {1, 0};

        try {
            mockedDrive.moveForMeasurement(movements, 90, 0.5, AngleUnit.DEGREES, false);
            mockedDrive.moveForMeasurement(movements, Math.PI / 2, 0.5, AngleUnit.RADIANS, false);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateDeg_throwsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();
        drive.validateDeg(-1);
    }

    @Test
    public void trimDegrees_isCorrect() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).build();

        double[] result = drive.trimDegrees(5);
        assertEquals(5, result[0], 0.01);

        result = drive.trimDegrees(360);
        double[] expected = {180, 0};
        for (int i = 0; i < 2; i++) {
            assertEquals(expected[i], result[i], 0.01);
        }

        result = drive.trimDegrees(365);
        double[] expected2 = {180, 0, 5};
        for (int i = 0; i < 3; i++) {
            assertEquals(expected2[i], result[i], 0.01);
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

        new Drive.Builder(mockedOpMode, mockedHardwareMap)
                .reverse("abc")
                .type(Type.MECANUM)
                .build();
        new Drive.Builder(mockedOpMode, mockedHardwareMap)
                .encoder()
                .reverse("abc")
                .type(Type.MECANUM)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDif_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap)
                .encoder()
                .diameter(4)
                .gearing(-1)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingMec_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap)
                .encoder()
                .diameter(4)
                .gearing(-1)
                .type(Type.MECANUM)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDeadzoneDif_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap)
                .encoder()
                .diameter(4)
                .deadzone(-1)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDeadzoneMec_ThrowsException() {
        mockInit();

        new Drive.Builder(mockedOpMode, mockedHardwareMap)
                .encoder()
                .diameter(4)
                .deadzone(-1)
                .type(Type.MECANUM)
                .build();
    }

    @Test
    public void whenTank_controlToDirection_isCorrect() {
        mockInit();

        Drive drive =
                new Drive.Builder(mockedOpMode, mockedHardwareMap)
                        .type(Type.DIFFERENTIAL)
                        .layout(Layout.TANK)
                        .build();
        double heading = 0;

        // Test no movement
        double[] result = drive.controlToDirection(heading, 0, 0, 0, 0);
        for (double v : result) {
            assertEquals(0, v, 0.01);
        }

        // Test forward
        result = drive.controlToDirection(heading, -1, 0, -1, 0);
        for (double v : result) {
            assertEquals(1, v, 0.01);
        }

        // Test backward
        result = drive.controlToDirection(heading, 1, 0, 1, 0);
        for (double v : result) {
            assertEquals(-1, v, 0.01);
        }
    }

    @Test
    public void whenArcade_controlToDirection_isCorrect() {
        mockInit();

        Drive drive =
                new Drive.Builder(mockedOpMode, mockedHardwareMap)
                        .type(Type.DIFFERENTIAL)
                        .layout(Layout.ARCADE)
                        .build();
        double heading = 0;

        // Test no movement
        double[] result = drive.controlToDirection(heading, 0, 0, 0, 0);
        for (double v : result) {
            assertEquals(0, v, 0.01);
        }

        // Test forward
        result = drive.controlToDirection(heading, -1, 0, 0, 0);
        for (double v : result) {
            assertEquals(1, v, 0.01);
        }

        // Test backward
        result = drive.controlToDirection(heading, 1, 0, 0, 0);
        for (double v : result) {
            assertEquals(-1, v, 0.01);
        }
    }

    @Test
    public void whenRobotCentric_controlToDirection_isCorrect() {
        mockInit();

        Drive drive =
                new Drive.Builder(mockedOpMode, mockedHardwareMap)
                        .type(Type.MECANUM)
                        .layout(Layout.ROBOT)
                        .build();
        final double heading = 0;

        // Test no movement
        double[] result = drive.controlToDirection(heading, 0, 0, 0, 0);
        for (double v : result) {
            assertEquals(0, v, 0.01);
        }

        // Test forward
        result = drive.controlToDirection(heading, -1, 0, 0, 0);
        for (double v : result) {
            assertEquals(1, v, 0.01);
        }

        // Test backward
        result = drive.controlToDirection(heading, 1, 0, 0, 0);
        for (double v : result) {
            assertEquals(-1, v, 0.01);
        }
    }

    @Test
    public void whenFieldCentric_controlToDirection_isCorrect() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM).build();
        final double heading = Math.PI / 2; // equals 90 degrees
        final double[][] expectedValues = {{0, 0, 0, 0}, {1, -1, -1, 1}, {-1, 1, 1, -1}};
        try {
            FieldUtils.writeField(drive, "layout", Layout.FIELD, true);
        } catch (Exception ignored) {
        }

        // Test no movement
        double[] result = drive.controlToDirection(heading, 0, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test forward
        result = drive.controlToDirection(heading, -1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test backward
        result = drive.controlToDirection(heading, 1, 0, 0, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void controlToDirectionDif_garbageThrowsException() {
        mockInit();

        Drive drive =
                new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.DIFFERENTIAL).build();
        try {
            FieldUtils.writeField(drive, "layout", Layout.ROBOT, true);
        } catch (Exception ignored) {
        }

        // Test Layout.ROBOT
        drive.controlToDirection(0, 0, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void controlToDirectionMec_garbageThrowsException() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM).build();
        try {
            FieldUtils.writeField(drive, "layout", Layout.ARCADE, true);
        } catch (Exception ignored) {
        }

        // Test Layout.ARCADE
        drive.controlToDirection(0, 0, 0, 0, 0);
    }

    @Test
    public void languageToDirectionDif_isCorrect() {
        mockInit();

        Drive drive =
                new Drive.Builder(mockedOpMode, mockedHardwareMap)
                        .type(Type.DIFFERENTIAL)
                        .layout(Layout.TANK)
                        .build();

        // Test Direction.FORWARD
        double[] result = drive.languageToDirection(Direction.FORWARD, 0);
        for (double value : result) {
            assertEquals(1, value, 0.01);
        }

        // Test Direction.BACKWARD
        result = drive.languageToDirection(Direction.BACKWARD, 0);
        for (double v : result) {
            assertEquals(-1, v, 0.01);
        }

        // Test Direction.ROTATE_LEFT
        double[] expectedRotateLeft = {-1, 1};
        result = drive.languageToDirection(Direction.ROTATE_LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_RIGHT
        double[] expectedRotateRight = {1, -1};
        result = drive.languageToDirection(Direction.ROTATE_RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }
    }

    @Test
    public void languageToDirectionMec_isCorrect() {
        mockInit();

        Drive drive =
                new Drive.Builder(mockedOpMode, mockedHardwareMap)
                        .type(Type.MECANUM)
                        .layout(Layout.ROBOT)
                        .build();

        // Test Direction.FORWARD
        double[] result = drive.languageToDirection(Direction.FORWARD, 0);
        for (double value : result) {
            assertEquals(1, value, 0.01);
        }

        // Test Direction.BACKWARD
        result = drive.languageToDirection(Direction.BACKWARD, 0);
        for (double v : result) {
            assertEquals(-1, v, 0.01);
        }

        // Test Direction.LEFT
        double[] expectedLeft = {-1, 1, 1, -1};
        result = drive.languageToDirection(Direction.LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedLeft[i], result[i], 0.01);
        }

        // Test Direction.RIGHT
        double[] expectedRight = {1, -1, -1, 1};
        result = drive.languageToDirection(Direction.RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRight[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_LEFT
        double[] expectedRotateLeft = {-1, 1, -1, 1};
        result = drive.languageToDirection(Direction.ROTATE_LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_RIGHT
        double[] expectedRotateRight = {1, -1, 1, -1};
        result = drive.languageToDirection(Direction.ROTATE_RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }

        // Test Direction.FORWARD_LEFT
        double[] expectedForwardLeft = {0, 1, 1, 0};
        result = drive.languageToDirection(Direction.FORWARD_LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardLeft[i], result[i], 0.01);
        }

        // Test Direction.FORWARD_RIGHT
        double[] expectedForwardRight = {1, 0, 0, 1};
        result = drive.languageToDirection(Direction.FORWARD_RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardRight[i], result[i], 0.01);
        }

        // Test Direction.BACKWARD_LEFT
        double[] expectedBackwardLeft = {-1, 0, 0, -1};
        result = drive.languageToDirection(Direction.BACKWARD_LEFT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardLeft[i], result[i], 0.01);
        }

        // Test Direction.BACKWARD_RIGHT
        double[] expectedBackwardRight = {0, -1, -1, 0};
        result = drive.languageToDirection(Direction.BACKWARD_RIGHT, 0);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardRight[i], result[i], 0.01);
        }
    }

    @Test
    public void whenFieldCentric_languageToDirection_isCorrect() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM).build();
        final double heading = Math.PI / 2; // equals 90 degrees
        final double[][] expectedValues = {{1, -1, -1, 1}, {-1, 1, 1, -1}};
        try {
            FieldUtils.writeField(drive, "layout", Layout.FIELD, true);
        } catch (Exception ignored) {
        }

        // Test forward
        double[] result = drive.languageToDirection(Direction.FORWARD, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test backward
        result = drive.languageToDirection(Direction.BACKWARD, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test Direction.LEFT
        double[] expectedLeft = {1, 1, 1, 1};
        result = drive.languageToDirection(Direction.LEFT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedLeft[i], result[i], 0.01);
        }

        // Test Direction.RIGHT
        double[] expectedRight = {-1, -1, -1, -1};
        result = drive.languageToDirection(Direction.RIGHT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRight[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_LEFT
        double[] expectedRotateLeft = {-1, 1, -1, 1};
        result = drive.languageToDirection(Direction.ROTATE_LEFT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateLeft[i], result[i], 0.01);
        }

        // Test Direction.ROTATE_RIGHT
        double[] expectedRotateRight = {1, -1, 1, -1};
        result = drive.languageToDirection(Direction.ROTATE_RIGHT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedRotateRight[i], result[i], 0.01);
        }

        // Test Direction.FORWARD_LEFT
        double[] expectedForwardLeft = {1, 0, 0, 1};
        result = drive.languageToDirection(Direction.FORWARD_LEFT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardLeft[i], result[i], 0.01);
        }

        // Test Direction.FORWARD_RIGHT
        double[] expectedForwardRight = {0, -1, -1, 0};
        result = drive.languageToDirection(Direction.FORWARD_RIGHT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedForwardRight[i], result[i], 0.01);
        }

        // Test Direction.BACKWARD_LEFT
        double[] expectedBackwardLeft = {0, 1, 1, 0};
        result = drive.languageToDirection(Direction.BACKWARD_LEFT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardLeft[i], result[i], 0.01);
        }

        // Test Direction.BACKWARD_RIGHT
        double[] expectedBackwardRight = {-1, 0, 0, -1};
        result = drive.languageToDirection(Direction.BACKWARD_RIGHT, heading);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedBackwardRight[i], result[i], 0.01);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirectionDif_garbageThrowsException() {
        mockInit();

        Drive drive =
                new Drive.Builder(mockedOpMode, mockedHardwareMap)
                        .type(Type.DIFFERENTIAL)
                        .layout(Layout.TANK)
                        .build();

        // Test FORWARD_LEFT
        drive.languageToDirection(Direction.FORWARD_LEFT, 0);
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

    @Test
    public void whenFourMotor_scaleDirections_isCorrect() {
        mockInit();

        Drive drive = new Drive.Builder(mockedOpMode, mockedHardwareMap).type(Type.MECANUM).build();
        final double[] powers = {0, 0.5, 1};
        final double[][] motorDirections = {{0, 0, 0, 0}, {1, 1, 1, 1}, {-1, -1, -1, -1}};
        final double[][][] expectedValues = {
            {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {0.5, 0.5, 0.5, 0.5}, {-0.5, -0.5, -0.5, -0.5}},
            {{0, 0, 0, 0}, {1, 1, 1, 1}, {-1, -1, -1, -1}}
        };

        // Test power zero
        for (int i = 0; i < 3; i++) {
            double[] movements = drive.scaleDirections(motorDirections[i], powers[0]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[0][i][j], movements[j], 0.01);
            }
        }

        // Test power half
        for (int i = 0; i < 3; i++) {
            double[] movements = drive.scaleDirections(motorDirections[i], powers[1]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[1][i][j], movements[j], 0.01);
            }
        }

        // Test power full
        for (int i = 0; i < 3; i++) {
            double[] movements = drive.scaleDirections(motorDirections[i], powers[2]);
            for (int j = 0; j < 4; j++) {
                assertEquals(expectedValues[2][i][j], movements[j], 0.01);
            }
        }
    }

    @Test
    public void whenFourMotor_calculatePositions_isCorrect() {
        mockInit();

        Drive drive =
                new Drive.Builder(mockedOpMode, mockedHardwareMap)
                        .type(Type.MECANUM)
                        .encoder()
                        .diameter(4)
                        .build();
        final double[] movements = {1, 1, 1, 1};
        final double[] movementsStrafe = {1, 0, 0, 1};
        final int[][] expectedValues = {
            {400, 400, 400, 400},
            {0, 0, 0, 0},
            {382, 382, 382, 382},
            {414, 414, 414, 414},
            {400, 0, 0, 400}
        };
        try {
            FieldUtils.writeField(drive, "distanceMultiplier", 400.0, true);
        } catch (Exception ignored) {
        }

        // Test distance = circumference
        int[] result = drive.calculatePositions(Math.PI * 4, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[0][i], result[i], 0.01);
        }

        // Test distance = 0
        result = drive.calculatePositions(0, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[1][i], result[i], 0.01);
        }

        // Test distance < circumference
        result = drive.calculatePositions(12, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[2][i], result[i], 0.01);
        }

        // Test distance > circumference
        result = drive.calculatePositions(13, movements);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[3][i], result[i], 0.01);
        }

        // Test distance = circumference, strafe
        result = drive.calculatePositions(Math.PI * 4, movementsStrafe);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expectedValues[4][i], result[i], 0.01);
        }
    }
}
