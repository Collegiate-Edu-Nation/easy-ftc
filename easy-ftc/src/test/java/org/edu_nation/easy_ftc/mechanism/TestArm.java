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
            new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).length(4).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).gamepad(mockedGamepad)
                    .build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).length(4)
                    .gamepad(mockedGamepad).build();

            new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).gamepad(mockedGamepad)
                    .build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true).length(4)
                    .build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true)
                    .gamepad(mockedGamepad).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true).length(4)
                    .gamepad(mockedGamepad).build();
            new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true).length(4)
                    .gearing(19.2).gamepad(mockedGamepad).build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleSolo_isCalled() {
        mockInit();

        try {
            Arm arm =
                    new Arm.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true)
                    .gamepad(mockedGamepad).build();

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
            Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .gamepad(mockedGamepad).build();
            Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).gamepad(mockedGamepad).build();

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
            Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
            Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();
            Arm armPos = new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).length(4)
                    .build();

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
            Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).build();
            Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).build();
            Arm armPos = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).length(4).build();

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
            Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
            Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();

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
            Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
            Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();

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

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
        Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true)
                .gamepad(mockedGamepad).build();

        arm.reverse("abc");
        arm.reverse("");
        armEnc.reverse("abc");
        armEnc.reverse("");
    }

    @Test
    public void reverseDual_isCalledWithValidInputs() {
        mockInit();

        try {
            Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).build();
            Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).build();

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

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).build();
        Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true)
                .build();

        arm.reverse("abc");
        arm.reverse("");
        armEnc.reverse("abc");
        armEnc.reverse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingSolo_ThrowsException() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).length(4)
                .gearing(-1).build();

        arm.setGearing(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDual_ThrowsException() {
        mockInit();

        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true)
                .length(4).gearing(-1).build();
    }
}
