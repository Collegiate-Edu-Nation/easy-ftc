// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.junit.Test;

public class TestTrigger {
    LinearOpMode mockedOpMode = mock(LinearOpMode.class);
    HardwareMap mockedHardwareMap = mock(HardwareMap.class);
    Gamepad mockedGamepad = mock(Gamepad.class);
    Servo mockedTrigger = mock(Servo.class);

    private void mockInit() {
        when(mockedHardwareMap.get(Servo.class, "triggerLeft")).thenReturn(mockedTrigger);
        when(mockedHardwareMap.get(Servo.class, "triggerRight")).thenReturn(mockedTrigger);
        when(mockedHardwareMap.get(Servo.class, "trigger")).thenReturn(mockedTrigger);
        when(mockedTrigger.getPosition()).thenReturn(0.0);
    }

    @Test
    public void Trigger_initializes() {
        mockInit();

        try {
            new Trigger.Builder(mockedOpMode, mockedHardwareMap).reverse("trigger").build();
            new Trigger.Builder(mockedOpMode, mockedHardwareMap)
                    .count(2)
                    .reverse(new String[] {"triggerLeft", "triggerRight"})
                    .build();
            new Trigger.Builder(mockedOpMode, mockedHardwareMap)
                    .reverse()
                    .smooth()
                    .increment(0.01)
                    .incrementDelay(0.02)
                    .delay(2.1)
                    .build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void whenNotSmooth_incrementThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).increment(0.02).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).smooth().increment(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementDelayThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).smooth().incrementDelay(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void delayThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).delay(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void countThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).count(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void openThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).open(1.1).build();
    }

    @Test(expected = IllegalStateException.class)
    public void whenOpenIsValidButLessThanClose_openThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).close(0.6).open(0.5).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void closeThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).close(-1).build();
    }

    @Test(expected = NullPointerException.class)
    public void namesThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).names(null).build();
    }

    @Test(expected = IllegalStateException.class)
    public void namesLengthThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap)
                .count(2)
                .names(new String[] {"abc"})
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void whenCloseIsValidButGreaterThanOpen_openThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).open(0.5).close(0.6).build();
    }

    @Test
    public void controlSolo_isCalled() {
        mockInit();

        try {
            Trigger trigger =
                    new Trigger.Builder(mockedOpMode, mockedHardwareMap)
                            .gamepad(mockedGamepad)
                            .build();
            Trigger triggerSmooth =
                    new Trigger.Builder(mockedOpMode, mockedHardwareMap)
                            .smooth()
                            .gamepad(mockedGamepad)
                            .build();
            trigger.control();
            triggerSmooth.control();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void controlDual_isCalled() {
        mockInit();

        try {
            Trigger trigger =
                    new Trigger.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .gamepad(mockedGamepad)
                            .build();
            Trigger triggerSmooth =
                    new Trigger.Builder(mockedOpMode, mockedHardwareMap)
                            .count(2)
                            .smooth()
                            .gamepad(mockedGamepad)
                            .build();
            trigger.control();
            triggerSmooth.control();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandSolo_isCalled() {
        mockInit();

        try {
            Trigger trigger = new Trigger.Builder(mockedOpMode, mockedHardwareMap).build();
            Trigger triggerSmooth =
                    new Trigger.Builder(mockedOpMode, mockedHardwareMap).smooth().build();
            trigger.command(Trigger.Direction.OPEN);
            triggerSmooth.command(Trigger.Direction.OPEN);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void commandDual_isCalled() {
        mockInit();

        try {
            Trigger trigger = new Trigger.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
            Trigger triggerSmooth =
                    new Trigger.Builder(mockedOpMode, mockedHardwareMap).count(2).smooth().build();
            trigger.command(Trigger.Direction.OPEN);
            triggerSmooth.command(Trigger.Direction.OPEN);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseDual_ThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("abc").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        new Trigger.Builder(mockedOpMode, mockedHardwareMap).reverse("abc").build();
    }

    @Test
    public void controlToDirection_isCorrect() {
        mockInit();

        Trigger trigger = new Trigger.Builder(mockedOpMode, mockedHardwareMap).build();

        // Test open
        double result = trigger.controlToDirection(0, true, false);
        assertEquals(1, result, 0.01);

        // Test close
        result = trigger.controlToDirection(1, false, true);
        assertEquals(0, result, 0.01);

        // Test doNothing (both false)
        result = trigger.controlToDirection(1, false, false);
        assertEquals(1, result, 0.01);

        // Test doNothing (both true)
        result = trigger.controlToDirection(1, true, true);
        assertEquals(1, result, 0.01);
    }

    @Test
    public void languageToDirection_isCorrect() {
        mockInit();

        Trigger trigger = new Trigger.Builder(mockedOpMode, mockedHardwareMap).build();

        // Test Direction.OPEN
        double result = trigger.languageToDirection(Trigger.Direction.OPEN);
        assertEquals(1, result, 0.01);

        // Test Direction.CLOSE
        result = trigger.languageToDirection(Trigger.Direction.CLOSE);
        assertEquals(0, result, 0.01);
    }

    @Test(expected = NullPointerException.class)
    public void command_nullThrowsException() {
        mockInit();

        Trigger trigger = new Trigger.Builder(mockedOpMode, mockedHardwareMap).build();
        trigger.command(null);
    }
}
