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

public class TestMecanum {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    DcMotor mockedMotor = mock(DcMotor.class);
    DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
    MotorConfigurationType motorType = new MotorConfigurationType();

    @Test
    public void Mecanum_initializes() {
        when(mockedHardwareMap.get(DcMotor.class, "frontLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "frontRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "backLeft")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotor.class, "backRight")).thenReturn(mockedMotor);
        when(mockedHardwareMap.get(DcMotorEx.class, "frontLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "frontRight")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "backLeft")).thenReturn(mockedMotorEx);
        when(mockedHardwareMap.get(DcMotorEx.class, "backRight")).thenReturn(mockedMotorEx);
        when(mockedMotorEx.getMotorType()).thenReturn(motorType);

        try {
            new Mecanum(mockedOpMode, mockedHardwareMap);
            new Mecanum(mockedOpMode, mockedHardwareMap, true);
            new Mecanum(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new Mecanum(mockedOpMode, mockedHardwareMap, "");
            // new Mecanum(mockedOpMode, mockedHardwareMap, "field");
            new Mecanum(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new Mecanum(mockedOpMode, mockedHardwareMap, true, "");
            new Mecanum(mockedOpMode, mockedHardwareMap, mockedGamepad, "");
            new Mecanum(mockedOpMode, mockedHardwareMap, true, mockedGamepad, "");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
