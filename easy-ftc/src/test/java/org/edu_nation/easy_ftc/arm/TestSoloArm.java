package org.edu_nation.easy_ftc.arm;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class TestSoloArm {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "arm")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void SoloArm_initializes() {
        mockInit();

        try {
            new SoloArm(mockedOpMode, mockedHardwareMap);
            new SoloArm(mockedOpMode, mockedHardwareMap, true);
            new SoloArm(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new SoloArm(mockedOpMode, mockedHardwareMap, true, 4);
            new SoloArm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new SoloArm(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        mockInit();

        try {
            SoloArm arm = new SoloArm(mockedOpMode, mockedHardwareMap, mockedGamepad);
            SoloArm armEnc = new SoloArm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            arm.tele();
            arm.tele(0.5);
            armEnc.tele();
            armEnc.tele(0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void move_isCalled() {
        mockInit();

        try {
            SoloArm arm = new SoloArm(mockedOpMode, mockedHardwareMap);
            SoloArm armEnc = new SoloArm(mockedOpMode, mockedHardwareMap, true);
            SoloArm armPos = new SoloArm(mockedOpMode, mockedHardwareMap, true, 4);
            arm.move(0.5, "up", 1);
            armEnc.move(0.5, "up", 1);
            armPos.move(0.5, "up", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverse_isCalled() {
        mockInit();

        try {
            SoloArm arm = new SoloArm(mockedOpMode, mockedHardwareMap);
            SoloArm armEnc = new SoloArm(mockedOpMode, mockedHardwareMap, true);
            arm.reverse();
            armEnc.reverse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setGearing_isCalled() {
        mockInit();

        try {
            SoloArm arm = new SoloArm(mockedOpMode, mockedHardwareMap, true, 4);
            arm.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearing_ThrowsException() {
        mockInit();

        SoloArm arm = new SoloArm(mockedOpMode, mockedHardwareMap, true, 4);
        arm.setGearing(0);
    }
}
