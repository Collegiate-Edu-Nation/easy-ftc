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

    private void mockInit() {
        when(mockedHardwareMap.get(Servo.class, "left_claw")).thenReturn(mockedClaw);
        when(mockedHardwareMap.get(Servo.class, "right_claw")).thenReturn(mockedClaw);
        when(mockedClaw.getPosition()).thenReturn(0.0);
    }

    @Test
    public void DualClaw_initializes() {
        mockInit();

        try {
            new DualClaw(mockedOpMode, mockedHardwareMap);
            new DualClaw(mockedOpMode, mockedHardwareMap, true);
            new DualClaw(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new DualClaw(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void tele_isCalled() {
        mockInit();

        try {
            DualClaw claw = new DualClaw(mockedOpMode, mockedHardwareMap, mockedGamepad);
            DualClaw clawNotSmooth =
                    new DualClaw(mockedOpMode, mockedHardwareMap, false, mockedGamepad);
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
            DualClaw claw = new DualClaw(mockedOpMode, mockedHardwareMap);
            DualClaw clawNotSmooth = new DualClaw(mockedOpMode, mockedHardwareMap, false);
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
            DualClaw claw = new DualClaw(mockedOpMode, mockedHardwareMap);
            claw.reverse();
            claw.reverse("left_claw");
            claw.reverse("right_claw");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverse_ThrowsException() {
        mockInit();

        DualClaw claw = new DualClaw(mockedOpMode, mockedHardwareMap);
        claw.reverse("abc");
        claw.reverse("");
    }
}
