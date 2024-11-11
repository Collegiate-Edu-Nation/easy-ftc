// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestArmUtil {
    @Test
    public void controlToDirection_iscorrect() {
        // Test no movement (both true)
        double result = ArmUtil.controlToDirection(true, true);
        assertEquals(0, result, 0.01);

        // Test no movement (both false)
        result = ArmUtil.controlToDirection(false, false);
        assertEquals(0, result, 0.01);

        // Test up
        result = ArmUtil.controlToDirection(false, true);
        assertEquals(1.0, result, 0.01);

        // Test down
        result = ArmUtil.controlToDirection(true, false);
        assertEquals(-1.0, result, 0.01);
    }

    @Test
    public void languageToDirection_isCorrect() {
        // Test "up"
        double result = ArmUtil.languageToDirection("up");
        assertEquals(1, result, 0.01);

        // Test "down"
        result = ArmUtil.languageToDirection("down");
        assertEquals(-1, result, 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_garbageThrowsException() {
        // Test "abc"
        ArmUtil.languageToDirection("abc");
    }

    @Test(expected = IllegalArgumentException.class)
    public void languageToDirection_emptyThrowsException() {
        // Test ""
        ArmUtil.languageToDirection("");
    }
}
