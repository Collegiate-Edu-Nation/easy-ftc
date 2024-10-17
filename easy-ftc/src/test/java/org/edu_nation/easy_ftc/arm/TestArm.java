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

public class TestArm {
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
        when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "arm")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void Arm_initializes() {
        mockInit();

        try {
            new Arm(mockedOpMode, mockedHardwareMap);
            new Arm(mockedOpMode, mockedHardwareMap, true);
            new Arm(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new Arm(mockedOpMode, mockedHardwareMap, true, 4);
            new Arm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new Arm(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad);
            new Arm(mockedOpMode, mockedHardwareMap, 2);
            new Arm(mockedOpMode, mockedHardwareMap, 2, true);
            new Arm(mockedOpMode, mockedHardwareMap, 2, mockedGamepad);
            new Arm(mockedOpMode, mockedHardwareMap, 2, true, 4);
            new Arm(mockedOpMode, mockedHardwareMap, 2, true, mockedGamepad);
            new Arm(mockedOpMode, mockedHardwareMap, 2, true, 4, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleSolo_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap, mockedGamepad);
            Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            arm.tele();
            arm.tele(0.5);
            armEnc.tele();
            armEnc.tele(0.5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleDual_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap, 2, mockedGamepad);
            Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, 2, true, mockedGamepad);
            arm.tele();
            arm.tele(5);
            armEnc.tele();
            armEnc.tele(5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveSolo_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap);
            Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, true);
            Arm armPos = new Arm(mockedOpMode, mockedHardwareMap, true, 4);
            arm.move(0.5, "up", 1);
            armEnc.move(0.5, "up", 1);
            armPos.move(0.5, "up", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveDual_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap, 2);
            Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, 2, true);
            Arm armPos = new Arm(mockedOpMode, mockedHardwareMap, 2, true, 4);
            arm.move(0.5, "up", 1);
            armEnc.move(0.5, "up", 1);
            armPos.move(0.5, "up", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverseSolo_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap);
            Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, true);
            arm.reverse();
            armEnc.reverse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverseSolo_isCalledWithValidInputs() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap);
            Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, true);
            arm.reverse();
            arm.reverse("arm");
            armEnc.reverse();
            armEnc.reverse("arm");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        Arm arm = new Arm(mockedOpMode, mockedHardwareMap, mockedGamepad);
        Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        arm.reverse("abc");
        arm.reverse("");
        armEnc.reverse("abc");
        armEnc.reverse("");
    }

    @Test
    public void reverseDual_isCalledWithValidInputs() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap, 2);
            Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, 2, true);
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
    public void reverseDual_ThrowsException() {
        mockInit();

        Arm arm = new Arm(mockedOpMode, mockedHardwareMap, 2, mockedGamepad);
        Arm armEnc = new Arm(mockedOpMode, mockedHardwareMap, 2, true, mockedGamepad);
        arm.reverse("abc");
        arm.reverse("");
        armEnc.reverse("abc");
        armEnc.reverse("");
    }

    @Test
    public void setGearingSolo_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap, true, 4);
            arm.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingSolo_ThrowsException() {
        mockInit();

        Arm arm = new Arm(mockedOpMode, mockedHardwareMap, true, 4);
        arm.setGearing(0);
    }

    @Test
    public void setGearingDual_isCalled() {
        mockInit();

        try {
            Arm arm = new Arm(mockedOpMode, mockedHardwareMap, 2, true, 4);
            arm.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDual_ThrowsException() {
        mockInit();

        Arm arm = new Arm(mockedOpMode, mockedHardwareMap, 2, true, 4);
        arm.setGearing(0);
    }
}
