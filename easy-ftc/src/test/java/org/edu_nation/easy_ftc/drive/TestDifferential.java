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

public class TestDifferential {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "left_drive")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "right_drive")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "left_drive")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "right_drive")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void Differential_initializes() {
        mockInit();

        try {
            new Differential(mockedOpMode, mockedHardwareMap);
            new Differential(mockedOpMode, mockedHardwareMap, true);
            new Differential(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new Differential(mockedOpMode, mockedHardwareMap, "");
            new Differential(mockedOpMode, mockedHardwareMap, "arcade");
            new Differential(mockedOpMode, mockedHardwareMap, true, 4);
            new Differential(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new Differential(mockedOpMode, mockedHardwareMap, true, "");
            new Differential(mockedOpMode, mockedHardwareMap, mockedGamepad, "");
            new Differential(mockedOpMode, mockedHardwareMap, true, 4, "");
            new Differential(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad);
            new Differential(mockedOpMode, mockedHardwareMap, true, mockedGamepad, "");
            new Differential(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad, "");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        mockInit();

        try {
            Differential drive = new Differential(mockedOpMode, mockedHardwareMap, mockedGamepad);
            Differential driveEnc =
                    new Differential(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
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
            Differential drive = new Differential(mockedOpMode, mockedHardwareMap);
            Differential driveEnc = new Differential(mockedOpMode, mockedHardwareMap, true);
            Differential drivePos = new Differential(mockedOpMode, mockedHardwareMap, true, 4);
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
            Differential drive = new Differential(mockedOpMode, mockedHardwareMap);
            Differential driveEnc = new Differential(mockedOpMode, mockedHardwareMap, true);
            drive.reverse();
            drive.reverse("left_drive");
            drive.reverse("right_drive");
            driveEnc.reverse();
            driveEnc.reverse("left_drive");
            driveEnc.reverse("right_drive");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverse_ThrowsException() {
        mockInit();

        Differential drive = new Differential(mockedOpMode, mockedHardwareMap, mockedGamepad);
        Differential driveEnc =
                new Differential(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        drive.reverse("abc");
        drive.reverse("");
        driveEnc.reverse("abc");
        driveEnc.reverse("");
    }

    @Test
    public void setGearing_isCalled() {
        mockInit();

        try {
            Differential drive = new Differential(mockedOpMode, mockedHardwareMap, true, 4);
            drive.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearing_ThrowsException() {
        mockInit();

        Differential drive = new Differential(mockedOpMode, mockedHardwareMap, true, 4);
        drive.setGearing(0);
    }
}
