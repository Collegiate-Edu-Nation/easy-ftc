package org.edu_nation.easy_ftc.drive;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class TestMecanum {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
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
    public void Mecanum_initializes() {
        mockInit();

        try {
            new Mecanum(mockedOpMode, mockedHardwareMap);
            new Mecanum(mockedOpMode, mockedHardwareMap, true);
            new Mecanum(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new Mecanum(mockedOpMode, mockedHardwareMap, "");
            // new Mecanum(mockedOpMode, mockedHardwareMap, "field");
            new Mecanum(mockedOpMode, mockedHardwareMap, true, 4);
            new Mecanum(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new Mecanum(mockedOpMode, mockedHardwareMap, true, "");
            new Mecanum(mockedOpMode, mockedHardwareMap, mockedGamepad, "");
            new Mecanum(mockedOpMode, mockedHardwareMap, true, 4, "");
            new Mecanum(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad);
            new Mecanum(mockedOpMode, mockedHardwareMap, true, mockedGamepad, "");
            new Mecanum(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad, "");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        mockInit();

        try {
            Mecanum drive = new Mecanum(mockedOpMode, mockedHardwareMap, mockedGamepad);
            Mecanum driveEnc = new Mecanum(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            drive.tele();
            driveEnc.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void move_isCalled() {
        mockInit();

        try {
            Mecanum drive = new Mecanum(mockedOpMode, mockedHardwareMap);
            Mecanum driveEnc = new Mecanum(mockedOpMode, mockedHardwareMap, true);
            Mecanum drivePos = new Mecanum(mockedOpMode, mockedHardwareMap, true, 4);
            drive.move(0.5, "forward", 1);
            driveEnc.move(0.5, "forward", 1);
            drivePos.move(0.5, "forward", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverse_isCalledWithValidInputs() {
        mockInit();

        try {
            Mecanum drive = new Mecanum(mockedOpMode, mockedHardwareMap);
            Mecanum driveEnc = new Mecanum(mockedOpMode, mockedHardwareMap, true);
            drive.reverse();
            drive.reverse("frontLeft");
            drive.reverse("frontRight");
            drive.reverse("backLeft");
            drive.reverse("backRight");
            driveEnc.reverse();
            driveEnc.reverse("frontLeft");
            driveEnc.reverse("frontRight");
            driveEnc.reverse("backLeft");
            driveEnc.reverse("backRight");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverse_ThrowsException() {
        mockInit();

        Mecanum drive = new Mecanum(mockedOpMode, mockedHardwareMap, mockedGamepad);
        Mecanum driveEnc = new Mecanum(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        drive.reverse("abc");
        drive.reverse("");
        driveEnc.reverse("abc");
        driveEnc.reverse("");
    }
}
