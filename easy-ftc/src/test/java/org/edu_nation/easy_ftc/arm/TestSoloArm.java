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

    @Test
    public void SoloArm_initializes() {
        when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "arm")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);

        try {
            new SoloArm(mockedOpMode, mockedHardwareMap);
            new SoloArm(mockedOpMode, mockedHardwareMap, true);
            new SoloArm(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new SoloArm(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void move_isCalled() {
        when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "arm")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        
        try {
            SoloArm arm = new SoloArm(mockedOpMode, mockedHardwareMap);
            SoloArm armEnc = new SoloArm(mockedOpMode, mockedHardwareMap, true);
            arm.move(0.5, "up", 1);
            armEnc.move(0.5, "up", 1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
