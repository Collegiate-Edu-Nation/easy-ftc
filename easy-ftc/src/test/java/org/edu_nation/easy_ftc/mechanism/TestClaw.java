package org.edu_nation.easy_ftc.mechanism;

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
            new Claw.Builder(mockedOpMode, mockedHardwareMap).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).smoothServo(true).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2).smoothServo(true)
                    .build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2).gamepad(mockedGamepad)
                    .build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).smoothServo(true)
                    .gamepad(mockedGamepad).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2).smoothServo(true)
                    .gamepad(mockedGamepad);
            new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2).smoothServo(true)
                    .open(0.9).close(0.1).increment(0.01).incrementDelay(0.02).delay(2.1)
                    .gamepad(mockedGamepad).build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleSolo_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .build();
            Claw clawSmooth = new Claw.Builder(mockedOpMode, mockedHardwareMap).smoothServo(true)
                    .gamepad(mockedGamepad).build();
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
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2)
                    .gamepad(mockedGamepad).build();
            Claw clawSmooth = new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2)
                    .smoothServo(true).gamepad(mockedGamepad).build();
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
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).build();
            Claw clawSmooth =
                    new Claw.Builder(mockedOpMode, mockedHardwareMap).smoothServo(true).build();
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
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2).build();
            Claw clawSmooth = new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2)
                    .smoothServo(true).build();
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
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).build();
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
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2).build();
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

        Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).numServos(2).build();
        claw.reverse("abc");
        claw.reverse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).build();
        claw.reverse("abc");
        claw.reverse("");
    }
}