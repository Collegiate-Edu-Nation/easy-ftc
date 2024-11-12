// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestServoMechanism {
    @Test
    public void controlToDirection_isCorrect() {
        // Test open
        double result = ServoMechanism.controlToDirection(1, 0, 0, true, false);
        assertEquals(1, result, 0.01);

        // Test close
        result = ServoMechanism.controlToDirection(1, 0, 1, false, true);
        assertEquals(0, result, 0.01);

        // Test doNothing (both false)
        result = ServoMechanism.controlToDirection(1, 0, 1, false, false);
        assertEquals(1, result, 0.01);

        // Test doNothing (both true)
        result = ServoMechanism.controlToDirection(1, 0, 1, true, true);
        assertEquals(1, result, 0.01);
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "open"
        double result = ServoMechanism.languageToDirection("open", 1, 0, "Claw");
        assertEquals(1, result, 0.01);

        // Test "close"
        result = ServoMechanism.languageToDirection("close", 1, 0, "Claw");
        assertEquals(0, result, 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_garbageThrowsException() {
        // Test "abc"
        ServoMechanism.languageToDirection("abc", 1, 0, "Claw");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_emptyThrowsException() {
        // Test ""
        ServoMechanism.languageToDirection("", 1, 0, "Claw");
    }
}
