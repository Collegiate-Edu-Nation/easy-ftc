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

    @Test
    public void Differential_initializes() {
        when(mockedHardwareMap.get(DcMotor.class, "left_drive")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "right_drive")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "left_drive")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "right_drive")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);

        try {
            new Differential(mockedOpMode, mockedHardwareMap);
            new Differential(mockedOpMode, mockedHardwareMap, true);
            new Differential(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new Differential(mockedOpMode, mockedHardwareMap, "");
            new Differential(mockedOpMode, mockedHardwareMap, "arcade");
            new Differential(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new Differential(mockedOpMode, mockedHardwareMap, true, "");
            new Differential(mockedOpMode, mockedHardwareMap, mockedGamepad, "");
            new Differential(mockedOpMode, mockedHardwareMap, true, mockedGamepad, "");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void move_isCalled() {
        when(mockedHardwareMap.get(DcMotor.class, "left_drive")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "right_drive")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "left_drive")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "right_drive")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);
        
        try {
            Differential drive = new Differential(mockedOpMode, mockedHardwareMap);
            Differential driveEnc = new Differential(mockedOpMode, mockedHardwareMap, true);
            drive.move(0.5, "forward", 1);
            driveEnc.move(0.5, "forward", 1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
