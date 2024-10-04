package org.edu_nation.easy_ftc.claw;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class TestDualClaw {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    Servo mockedClaw = mock(Servo.class);

    @Test
    public void DualClaw_initializes() {
        when(mockedHardwareMap.get(Servo.class, "left_claw")).thenReturn(mockedClaw);
        when(mockedHardwareMap.get(Servo.class, "right_claw")).thenReturn(mockedClaw);
        try {
            new DualClaw(mockedOpMode, mockedHardwareMap);
            new DualClaw(mockedOpMode, mockedHardwareMap, true);
            new DualClaw(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new DualClaw(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
