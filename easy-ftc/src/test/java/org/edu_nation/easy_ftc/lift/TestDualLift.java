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

    @Test
    public void DualLift_initializes() {
        when(mockedHardwareMap.get(DcMotor.class, "left_lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "right_lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "left_lift")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "right_lift")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);

        try {
            new DualLift(mockedOpMode, mockedHardwareMap);
            new DualLift(mockedOpMode, mockedHardwareMap, true);
            new DualLift(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new DualLift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        when(mockedHardwareMap.get(DcMotor.class, "left_lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "right_lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "left_lift")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "right_lift")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);

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
        when(mockedHardwareMap.get(DcMotor.class, "left_lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "right_lift")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "left_lift")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "right_lift")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        
        try {
            DualLift drive = new DualLift(mockedOpMode, mockedHardwareMap);
            DualLift driveEnc = new DualLift(mockedOpMode, mockedHardwareMap, true);
            drive.move(0.5, "up", 1);
            driveEnc.move(0.5, "up", 1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
