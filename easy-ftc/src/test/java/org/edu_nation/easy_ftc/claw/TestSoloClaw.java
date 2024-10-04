package org.edu_nation.easy_ftc.claw;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class TestSoloClaw {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    Servo mockedClaw = mock(Servo.class);

    @Test
    public void SoloClaw_initializes() {
        when(mockedHardwareMap.get(Servo.class, "claw")).thenReturn(mockedClaw);
        try {
            new SoloClaw(mockedOpMode, mockedHardwareMap);
            new SoloClaw(mockedOpMode, mockedHardwareMap, true);
            new SoloClaw(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new SoloClaw(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
