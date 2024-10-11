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

    private void mockInit() {
        when(mockedHardwareMap.get(Servo.class, "claw")).thenReturn(mockedClaw);
        when(mockedClaw.getPosition()).thenReturn(0.0);
    }

    @Test
    public void SoloClaw_initializes() {
        mockInit();

        try {
            new SoloClaw(mockedOpMode, mockedHardwareMap);
            new SoloClaw(mockedOpMode, mockedHardwareMap, true);
            new SoloClaw(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new SoloClaw(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        mockInit();

        try {
            SoloClaw claw = new SoloClaw(mockedOpMode, mockedHardwareMap, mockedGamepad);
            SoloClaw clawNotSmooth =
                    new SoloClaw(mockedOpMode, mockedHardwareMap, false, mockedGamepad);
            claw.tele();
            clawNotSmooth.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void move_isCalled() {
        mockInit();

        try {
            SoloClaw claw = new SoloClaw(mockedOpMode, mockedHardwareMap);
            SoloClaw clawNotSmooth = new SoloClaw(mockedOpMode, mockedHardwareMap, false);
            claw.move("open");
            clawNotSmooth.move("open");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverse_isCalledWithValidInputs() {
        mockInit();

        try {
            SoloClaw claw = new SoloClaw(mockedOpMode, mockedHardwareMap);
            claw.reverse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
