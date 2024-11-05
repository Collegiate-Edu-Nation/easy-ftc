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

public class TestArm {
        LinearOpMode mockedOpMode = mock(LinearOpMode.class);
        HardwareMap mockedHardwareMap = mock(HardwareMap.class);
        Gamepad mockedGamepad = mock(Gamepad.class);
        DcMotor mockedMotor = mock(DcMotor.class);
        DcMotorEx mockedMotorEx = mock(DcMotorEx.class);
        MotorConfigurationType motorType = new MotorConfigurationType();

        private void mockInit() {
                when(mockedHardwareMap.get(DcMotor.class, "armLeft")).thenReturn(mockedMotor);
                when(mockedHardwareMap.get(DcMotor.class, "armRight")).thenReturn(mockedMotor);
                when(mockedHardwareMap.get(DcMotorEx.class, "armLeft")).thenReturn(mockedMotorEx);
                when(mockedHardwareMap.get(DcMotorEx.class, "armRight")).thenReturn(mockedMotorEx);
                when(mockedHardwareMap.get(DcMotor.class, "arm")).thenReturn(mockedMotor);
                when(mockedHardwareMap.get(DcMotorEx.class, "arm")).thenReturn(mockedMotorEx);
                when(mockedMotorEx.getMotorType()).thenReturn(motorType);
                when(mockedMotorEx.isBusy()).thenReturn(true, false);
        }

        @Test
        public void Arm_initializes() {
                mockInit();

                try {
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).names(new String[]{"arm"}).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap)
                                        .behavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse()
                                        .build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse().build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse("arm").build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().reverse("arm")
                                        .build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                                        .build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4)
                                        .build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                        .gamepad(mockedGamepad).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4)
                                        .gamepad(mockedGamepad).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4)
                                        .gearing(19.2).gamepad(mockedGamepad).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4)
                                        .reverse().gamepad(mockedGamepad).build();

                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder().build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse().build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .reverse().build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("armLeft")
                                        .build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("armLeft")
                                        .reverse("armRight").build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .reverse("armRight").build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .reverse("armLeft").build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .reverse("armRight").build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .gamepad(mockedGamepad).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .length(4).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .gamepad(mockedGamepad).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .length(4).gamepad(mockedGamepad).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .length(4).gearing(19.2).gamepad(mockedGamepad).build();
                        new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                        .length(4).reverse().gamepad(mockedGamepad).build();
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void teleSolo_isCalled() {
                mockInit();

                try {
                        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap)
                                        .gamepad(mockedGamepad).build();
                        Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                        .gamepad(mockedGamepad).build();

                        arm.tele();
                        arm.tele(0.5);
                        armEnc.tele();
                        armEnc.tele(0.5);
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void teleDual_isCalled() {
                mockInit();

                try {
                        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .gamepad(mockedGamepad).build();
                        Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .encoder().gamepad(mockedGamepad).build();

                        arm.tele();
                        arm.tele(5);
                        armEnc.tele();
                        armEnc.tele(5);
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void moveSolo_isCalled() {
                mockInit();

                try {
                        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).build();
                        Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                        .build();
                        Arm armPos = new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder()
                                        .length(4).build();

                        arm.move(0.5, "up", 1);
                        armEnc.move(0.5, "up", 1);
                        armPos.move(0.5, "up", 12);
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test
        public void moveDual_isCalled() {
                mockInit();

                try {
                        Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).build();
                        Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .encoder().build();
                        Arm armPos = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                        .encoder().length(4).build();

                        arm.move(0.5, "up", 1);
                        armEnc.move(0.5, "up", 1);
                        armPos.move(0.5, "up", 12);
                } catch (Exception e) {
                        fail(e.getMessage());
                }
        }

        @Test(expected = IllegalArgumentException.class)
        public void reverseSolo_ThrowsException() {
                mockInit();

                Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse("abc")
                                .gamepad(mockedGamepad).build();
                Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).reverse("abc")
                                .encoder().gamepad(mockedGamepad).build();
        }

        @Test(expected = IllegalArgumentException.class)
        public void reverseDual_ThrowsException() {
                mockInit();

                Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).reverse("abc")
                                .build();
                Arm armEnc = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2)
                                .reverse("abc").encoder().build();
        }

        @Test(expected = IllegalArgumentException.class)
        public void setGearingSolo_ThrowsException() {
                mockInit();

                Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).encoder().length(4)
                                .gearing(-1).build();

                arm.setGearing(0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void setGearingDual_ThrowsException() {
                mockInit();

                Arm arm = new Arm.Builder(mockedOpMode, mockedHardwareMap).count(2).encoder()
                                .length(4).gearing(-1).build();
        }
}
