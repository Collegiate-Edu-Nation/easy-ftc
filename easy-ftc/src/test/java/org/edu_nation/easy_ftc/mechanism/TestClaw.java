// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

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
            new Claw.Builder(mockedOpMode, mockedHardwareMap).names(new String[] {"claw"}).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).reverse("claw").build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("clawLeft")
                    .reverse("clawRight").build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2)
                    .reverse(new String[] {"clawLeft", "clawRight"}).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).smooth().build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).smooth().build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).gamepad(mockedGamepad)
                    .build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).smooth().gamepad(mockedGamepad)
                    .build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).smooth()
                    .gamepad(mockedGamepad);
            new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).smooth().open(0.9).close(0.1)
                    .increment(0.01).incrementDelay(0.02).delay(2.1).gamepad(mockedGamepad).build();
            new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).smooth().reverse().open(0.9)
                    .close(0.1).increment(0.01).incrementDelay(0.02).delay(2.1)
                    .gamepad(mockedGamepad).build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void whenNotSmooth_incrementThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).increment(0.02).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).smooth().increment(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementDelayThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).smooth().incrementDelay(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void delayThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).delay(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void countThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).count(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void openThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).open(1.1).build();
    }

    @Test(expected = IllegalStateException.class)
    public void whenOpenIsValidButLessThanClose_openThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).close(0.6).open(0.5).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void closeThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).close(-1).build();
    }

    @Test(expected = NullPointerException.class)
    public void namesThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).names(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void namesLengthThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).names(new String[] {"abc"})
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void whenCloseIsValidButGreaterThanOpen_openThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).open(0.5).close(0.6).build();
    }

    @Test
    public void controlSolo_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .build();
            Claw clawSmooth = new Claw.Builder(mockedOpMode, mockedHardwareMap).smooth()
                    .gamepad(mockedGamepad).build();
            claw.control();
            clawSmooth.control();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void controlDual_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2)
                    .gamepad(mockedGamepad).build();
            Claw clawSmooth = new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).smooth()
                    .gamepad(mockedGamepad).build();
            claw.control();
            clawSmooth.control();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandSolo_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).build();
            Claw clawSmooth = new Claw.Builder(mockedOpMode, mockedHardwareMap).smooth().build();
            claw.command(Claw.Direction.OPEN);
            clawSmooth.command(Claw.Direction.OPEN);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandDual_isCalled() {
        mockInit();

        try {
            Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
            Claw clawSmooth =
                    new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).smooth().build();
            claw.command(Claw.Direction.OPEN);
            clawSmooth.command(Claw.Direction.OPEN);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseDual_ThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("abc").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        new Claw.Builder(mockedOpMode, mockedHardwareMap).reverse("abc").build();
    }

    @Test
    public void controlToDirection_isCorrect() {
        // Test open
        double result = Claw.controlToDirection(1, 0, 0, true, false);
        assertEquals(1, result, 0.01);

        // Test close
        result = Claw.controlToDirection(1, 0, 1, false, true);
        assertEquals(0, result, 0.01);

        // Test doNothing (both false)
        result = Claw.controlToDirection(1, 0, 1, false, false);
        assertEquals(1, result, 0.01);

        // Test doNothing (both true)
        result = Claw.controlToDirection(1, 0, 1, true, true);
        assertEquals(1, result, 0.01);
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test Direction.OPEN
        double result = Claw.languageToDirection(Claw.Direction.OPEN, 1, 0);
        assertEquals(1, result, 0.01);

        // Test Direction.CLOSE
        result = Claw.languageToDirection(Claw.Direction.CLOSE, 1, 0);
        assertEquals(0, result, 0.01);
    }

    @Test(expected = NullPointerException.class)
    public void command_nullThrowsException() {
        mockInit();

        Claw claw = new Claw.Builder(mockedOpMode, mockedHardwareMap).build();
        claw.command(null);
    }
}
