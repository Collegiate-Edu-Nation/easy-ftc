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

public class TestDualArm {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "armLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "armRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "armLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "armRight")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void DualArm_initializes() {
        mockInit();

        try {
            new DualArm(mockedOpMode, mockedHardwareMap);
            new DualArm(mockedOpMode, mockedHardwareMap, true);
            new DualArm(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new DualArm(mockedOpMode, mockedHardwareMap, true, 4);
            new DualArm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new DualArm(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        mockInit();

        try {
            DualArm arm = new DualArm(mockedOpMode, mockedHardwareMap, mockedGamepad);
            DualArm armEnc = new DualArm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            arm.tele();
            arm.tele(5);
            armEnc.tele();
            armEnc.tele(5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void move_isCalled() {
        mockInit();

        try {
            DualArm arm = new DualArm(mockedOpMode, mockedHardwareMap);
            DualArm armEnc = new DualArm(mockedOpMode, mockedHardwareMap, true);
            DualArm armPos = new DualArm(mockedOpMode, mockedHardwareMap, true, 4);
            arm.move(0.5, "up", 1);
            armEnc.move(0.5, "up", 1);
            armPos.move(0.5, "up", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverse_isCalledWithValidInputs() {
        mockInit();

        try {
            DualArm arm = new DualArm(mockedOpMode, mockedHardwareMap);
            DualArm armEnc = new DualArm(mockedOpMode, mockedHardwareMap, true);
            arm.reverse();
            arm.reverse("armLeft");
            arm.reverse("armRight");
            armEnc.reverse();
            armEnc.reverse("armLeft");
            armEnc.reverse("armRight");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverse_ThrowsException() {
        mockInit();

        DualArm arm = new DualArm(mockedOpMode, mockedHardwareMap, mockedGamepad);
        DualArm armEnc = new DualArm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        arm.reverse("abc");
        arm.reverse("");
        armEnc.reverse("abc");
        armEnc.reverse("");
    }

    @Test
    public void setGearing_isCalled() {
        mockInit();

        try {
            DualArm arm = new DualArm(mockedOpMode, mockedHardwareMap, true, 4);
            arm.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearing_ThrowsException() {
        mockInit();

        DualArm arm = new DualArm(mockedOpMode, mockedHardwareMap, true, 4);
        arm.setGearing(0);
    }
}
