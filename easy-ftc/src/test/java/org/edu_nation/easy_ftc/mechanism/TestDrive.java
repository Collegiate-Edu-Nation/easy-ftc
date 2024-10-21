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
            new Drive(mockedOpMode, mockedHardwareMap, "differential");
            new Drive(mockedOpMode, mockedHardwareMap, "differential", true);
            new Drive(mockedOpMode, mockedHardwareMap, "differential", mockedGamepad);
            new Drive(mockedOpMode, mockedHardwareMap, "differential", "");
            new Drive(mockedOpMode, mockedHardwareMap, "differential", "arcade");
            new Drive(mockedOpMode, mockedHardwareMap, "differential", true, 4);
            new Drive(mockedOpMode, mockedHardwareMap, "differential", true, mockedGamepad);
            new Drive(mockedOpMode, mockedHardwareMap, "differential", true, "");
            new Drive(mockedOpMode, mockedHardwareMap, "differential", mockedGamepad, "");
            new Drive(mockedOpMode, mockedHardwareMap, "differential", true, 4, "");
            new Drive(mockedOpMode, mockedHardwareMap, "differential", true, 4, mockedGamepad);
            new Drive(mockedOpMode, mockedHardwareMap, "differential", true, mockedGamepad, "");
            new Drive(mockedOpMode, mockedHardwareMap, "differential", true, 4, mockedGamepad, "");
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum");
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true);
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", mockedGamepad);
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", "");
            // new Drive(mockedOpMode, mockedHardwareMap, "mecanum", "field");
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, 4);
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, mockedGamepad);
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, "");
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", mockedGamepad, "");
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, 4, "");
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, 4, mockedGamepad);
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, mockedGamepad, "");
            new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, 4, mockedGamepad, "");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleMec_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", mockedGamepad);
            Drive driveEnc = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, mockedGamepad);
            drive.tele();
            driveEnc.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleDif_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "differential", mockedGamepad);
            Drive driveEnc =
                    new Drive(mockedOpMode, mockedHardwareMap, "differential", true, mockedGamepad);
            drive.tele();
            driveEnc.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveDif_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "differential");
            Drive driveEnc = new Drive(mockedOpMode, mockedHardwareMap, "differential", true);
            Drive drivePos = new Drive(mockedOpMode, mockedHardwareMap, "differential", true, 4);
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
            Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "mecanum");
            Drive driveEnc = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true);
            Drive drivePos = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, 4);
            drive.move(0.5, "forward", 1);
            driveEnc.move(0.5, "forward", 1);
            drivePos.move(0.5, "forward", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverseDif_isCalledWithValidInputs() {
        mockInit();

        try {
            Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "differential");
            Drive driveEnc = new Drive(mockedOpMode, mockedHardwareMap, "differential", true);
            drive.reverse();
            drive.reverse("driveLeft");
            drive.reverse("driveRight");
            driveEnc.reverse();
            driveEnc.reverse("driveLeft");
            driveEnc.reverse("driveRight");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverseMec_isCalledWithValidInputs() {
        mockInit();

        try {
            Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "mecanum");
            Drive driveEnc = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true);
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
    public void reverseDif_ThrowsException() {
        mockInit();

        Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "differential", mockedGamepad);
        Drive driveEnc =
                new Drive(mockedOpMode, mockedHardwareMap, "differential", true, mockedGamepad);
        drive.reverse("abc");
        drive.reverse("");
        driveEnc.reverse("abc");
        driveEnc.reverse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseMec_ThrowsException() {
        mockInit();

        Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", mockedGamepad);
        Drive driveEnc = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, mockedGamepad);
        drive.reverse("abc");
        drive.reverse("");
        driveEnc.reverse("abc");
        driveEnc.reverse("");
    }

    @Test
    public void setGearingDif_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "differential", true, 4);
            drive.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setGearingMec_isCalled() {
        mockInit();

        try {
            Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, 4);
            drive.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDif_ThrowsException() {
        mockInit();

        Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "differential", true, 4);
        drive.setGearing(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingMec_ThrowsException() {
        mockInit();

        Drive drive = new Drive(mockedOpMode, mockedHardwareMap, "mecanum", true, 4);
        drive.setGearing(0);
    }
}
