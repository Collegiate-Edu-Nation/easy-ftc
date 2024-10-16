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

public class TestDualLift {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    private void mockInit() {
        when(mockedHardwareMap.get(DcMotor.class, "liftLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "liftRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "liftLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "liftRight")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        when(mockedMotorEx.isBusy()).thenReturn(true, false);
    }

    @Test
    public void DualLift_initializes() {
        mockInit();

        try {
            new DualLift(mockedOpMode, mockedHardwareMap);
            new DualLift(mockedOpMode, mockedHardwareMap, true);
            new DualLift(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new DualLift(mockedOpMode, mockedHardwareMap, true, 4);
            new DualLift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new DualLift(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        mockInit();

        try {
            DualLift lift = new DualLift(mockedOpMode, mockedHardwareMap, mockedGamepad);
            DualLift liftEnc = new DualLift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
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
            DualLift lift = new DualLift(mockedOpMode, mockedHardwareMap);
            DualLift liftEnc = new DualLift(mockedOpMode, mockedHardwareMap, true);
            DualLift liftPos = new DualLift(mockedOpMode, mockedHardwareMap, true, 4);
            lift.move(0.5, "up", 1);
            liftEnc.move(0.5, "up", 1);
            liftPos.move(0.5, "up", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverse_isCalledWithValidInputs() {
        mockInit();

        try {
            DualLift lift = new DualLift(mockedOpMode, mockedHardwareMap);
            DualLift liftEnc = new DualLift(mockedOpMode, mockedHardwareMap, true);
            lift.reverse();
            lift.reverse("liftLeft");
            lift.reverse("liftRight");
            liftEnc.reverse();
            liftEnc.reverse("liftLeft");
            liftEnc.reverse("liftRight");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverse_ThrowsException() {
        mockInit();

        DualLift lift = new DualLift(mockedOpMode, mockedHardwareMap, mockedGamepad);
        DualLift liftEnc = new DualLift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        lift.reverse("abc");
        lift.reverse("");
        liftEnc.reverse("abc");
        liftEnc.reverse("");
    }

    @Test
    public void setGearing_isCalled() {
        mockInit();

        try {
            DualLift lift = new DualLift(mockedOpMode, mockedHardwareMap, true, 4);
            lift.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearing_ThrowsException() {
        mockInit();

        DualLift lift = new DualLift(mockedOpMode, mockedHardwareMap, true, 4);
        lift.setGearing(0);
    }
}
