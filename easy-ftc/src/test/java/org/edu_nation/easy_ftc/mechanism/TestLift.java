// SPDX-FileCopyrightText: 2024 Collegiate Edu-Nation
//
// SPDX-License-Identifier: GPL-3.0-or-later

package org.edu_nation.easy_ftc.mechanism;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class TestLift {
        LinearOpMode mockedOpMode = mock(LinearOpMode.class);
        HardwareMap mockedHardwareMap = mock(HardwareMap.class);
        Gamepad mockedGamepad = mock(Gamepad.class);
        DcMotor mockedMotor = mock(DcMotor.class);
        DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
        MotorConfigurationType motorType = new MotorConfigurationType();

        private void mockInit() {
                when(mockedHardwareMap.get(DcMotor.class, "liftLeft")).thenReturn(mockedMotor);
                when(mockedHardwareMap.get(DcMotor.class, "liftRight")).thenReturn(mockedMotor);
                when(mockedHardwareMap.get(DcMotorEx.class, "liftLeft")).thenReturn(mockedMotorEx);
                when(mockedHardwareMap.get(DcMotorEx.class, "liftRight")).thenReturn(mockedMotorEx);
                when(mockedHardwareMap.get(DcMotor.class, "lift")).thenReturn(mockedMotor);
                when(mockedHardwareMap.get(DcMotorEx.class, "lift")).thenReturn(mockedMotorEx);
                when(mockedMotorEx.getMotorType()).thenReturn(motorType);
                when(mockedMotorEx.isBusy()).thenReturn(true, false);
        }

        @Test
        public void Lift_initializes() {
                mockInit();

                try {
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).names(new String[]{"lift"}).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap)
                                        .behavior(DcMotor.ZeroPowerBehavior.FLOAT);
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).deadzone(0.1).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).reverse("lift").build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("lift")
                                        .build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse()
                                        .build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).reverse().build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                                        .build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                                        .build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                        .gamepad(mockedGamepad).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                                        .gamepad(mockedGamepad).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                                        .gearing(19.2).gamepad(mockedGamepad).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                                        .reverse().gamepad(mockedGamepad).build();

                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).deadzone(0.1)
                                        .build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse()
                                        .build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .reverse("liftLeft").build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .reverse().build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .reverse("liftLeft").build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .reverse("liftLeft").reverse("liftRight").build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .reverse("liftRight").build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .reverse("liftRight").build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .gamepad(mockedGamepad).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .diameter(4).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .gamepad(mockedGamepad).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .diameter(4).gamepad(mockedGamepad).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .diameter(4).gearing(19.2).gamepad(mockedGamepad).build();
                        new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .diameter(4).reverse().gamepad(mockedGamepad).build();
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void teleSolo_isCalled() {
                mockInit();

                try {
                        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap)
                                        .gamepad(mockedGamepad).build();
                        Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                        .gamepad(mockedGamepad).build();

                        lift.tele();
                        lift.tele(0.9);
                        liftEnc.tele();
                        liftEnc.tele(0.9);
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void teleDual_isCalled() {
                mockInit();

                try {
                        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .gamepad(mockedGamepad).build();
                        Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .encoder().gamepad(mockedGamepad).build();

                        lift.tele();
                        lift.tele(0.9);
                        liftEnc.tele();
                        liftEnc.tele(0.9);
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void moveSolo_isCalled() {
                mockInit();

                try {
                        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
                        Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                        .build();
                        Lift liftPos = new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                        .diameter(4).build();

                        lift.move(0.5, "up", 1);
                        liftEnc.move(0.5, "up", 1);
                        liftPos.move(0.5, "up", 12);
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void moveDual_isCalled() {
                mockInit();

                try {
                        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .build();
                        Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .encoder().build();
                        Lift liftPos = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .encoder().diameter(4).build();

                        lift.move(0.5, "up", 1);
                        liftEnc.move(0.5, "up", 1);
                        liftPos.move(0.5, "up", 12);
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test(expected = IllegalArgumentException.class)
        public void reverseSolo_ThrowsException() {
                mockInit();

                Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).reverse("abc")
                                .build();
                Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                .reverse("abc").build();
        }

        @Test(expected = IllegalArgumentException.class)
        public void reverseDual_ThrowsException() {
                mockInit();

                Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                .reverse("abc").build();
                Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                .reverse("abc").build();
        }

        @Test(expected = IllegalArgumentException.class)
        public void setGearingSolo_ThrowsException() {
                mockInit();

                Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                                .gearing(-1).build();
        }

        @Test(expected = IllegalArgumentException.class)
        public void setGearingDual_ThrowsException() {
                mockInit();

                Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                .diameter(4).gearing(-1).build();
        }

        @Test(expected = IllegalArgumentException.class)
        public void setdeadzoneSolo_ThrowsException() {
                mockInit();

                Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).encoder().diameter(4)
                                .deadzone(-1).build();
        }

        @Test(expected = IllegalArgumentException.class)
        public void setdeadzoneDual_ThrowsException() {
                mockInit();

                Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                .diameter(4).deadzone(-1).build();
        }
}
