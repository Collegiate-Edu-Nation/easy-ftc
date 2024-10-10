package org.edu_nation.easy_ftc.lift;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class TestSoloLift {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "lift")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
    }

    @Test
    public void SoloLift_initializes() {
        mockInit();

        try {
            new SoloLift(mockedOpMode, mockedHardwareMap);
            new SoloLift(mockedOpMode, mockedHardwareMap, true);
            new SoloLift(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new SoloLift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        mockInit();

        try {
            SoloLift lift = new SoloLift(mockedOpMode, mockedHardwareMap, mockedGamepad);
            SoloLift liftEnc = new SoloLift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            lift.tele();
            liftEnc.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void move_isCalled() {
        mockInit();

        try {
            SoloLift drive = new SoloLift(mockedOpMode, mockedHardwareMap);
            SoloLift driveEnc = new SoloLift(mockedOpMode, mockedHardwareMap, true);
            drive.move(0.5, "up", 1);
            driveEnc.move(0.5, "up", 1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverse_isCalled() {
        mockInit();

        try {
            SoloLift lift = new SoloLift(mockedOpMode, mockedHardwareMap);
            SoloLift liftEnc = new SoloLift(mockedOpMode, mockedHardwareMap, true);
            lift.reverse();
            liftEnc.reverse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}