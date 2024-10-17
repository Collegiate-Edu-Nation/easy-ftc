package org.edu_nation.easy_ftc.claw;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

public class TestClaw {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    Servo mockedClaw = mock(Servo.class);

    private void mockInit() {
        when(mockedHardwareMap.get(Servo.class, "clawLeft")).thenReturn(mockedClaw);
        when(mockedHardwareMap.get(Servo.class, "clawRight")).thenReturn(mockedClaw);
        when(mockedHardwareMap.get(Servo.class, "claw")).thenReturn(mockedClaw);
        when(mockedClaw.getPosition()).thenReturn(0.0);
    }

    @Test
    public void Claw_initializes() {
        mockInit();

        try {
            new Claw(mockedOpMode, mockedHardwareMap);
            new Claw(mockedOpMode, mockedHardwareMap, 2);
            new Claw(mockedOpMode, mockedHardwareMap, true);
            new Claw(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new Claw(mockedOpMode, mockedHardwareMap, 2, true);
            new Claw(mockedOpMode, mockedHardwareMap, 2, mockedGamepad);
            new Claw(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new Claw(mockedOpMode, mockedHardwareMap, 2, true, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleSolo_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw(mockedOpMode, mockedHardwareMap, mockedGamepad);
            Claw clawSmooth =
                    new Claw(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            claw.tele();
            clawSmooth.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleDual_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw(mockedOpMode, mockedHardwareMap, 2, mockedGamepad);
            Claw clawSmooth =
                    new Claw(mockedOpMode, mockedHardwareMap, 2, true, mockedGamepad);
            claw.tele();
            clawSmooth.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveSolo_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw(mockedOpMode, mockedHardwareMap);
            Claw clawSmooth = new Claw(mockedOpMode, mockedHardwareMap, true);
            claw.move("open");
            clawSmooth.move("open");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveDual_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw(mockedOpMode, mockedHardwareMap, 2);
            Claw clawSmooth = new Claw(mockedOpMode, mockedHardwareMap, 2, true);
            claw.move("open");
            clawSmooth.move("open");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverseSolo_isCalledWithValidInputs() {
        mockInit();

        try {
            Claw claw = new Claw(mockedOpMode, mockedHardwareMap);
            claw.reverse();
            claw.reverse("claw");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverseDual_isCalledWithValidInputs() {
        mockInit();

        try {
            Claw claw = new Claw(mockedOpMode, mockedHardwareMap, 2);
            claw.reverse();
            claw.reverse("clawLeft");
            claw.reverse("clawRight");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseDual_ThrowsException() {
        mockInit();

        Claw claw = new Claw(mockedOpMode, mockedHardwareMap, 2);
        claw.reverse("abc");
        claw.reverse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        Claw claw = new Claw(mockedOpMode, mockedHardwareMap);
        claw.reverse("abc");
        claw.reverse("");
    }
}
