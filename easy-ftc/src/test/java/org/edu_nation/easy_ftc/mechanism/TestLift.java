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
            new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).diameter(4).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true)
                    .gamepad(mockedGamepad).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).diameter(4)
                    .gamepad(mockedGamepad).build();

            new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).gamepad(mockedGamepad)
                    .build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true)
                    .diameter(4).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true)
                    .gamepad(mockedGamepad).build();
            new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true)
                    .diameter(4).gamepad(mockedGamepad).build();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleSolo_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).gamepad(mockedGamepad)
                    .build();
            Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true)
                    .gamepad(mockedGamepad).build();

            lift.tele();
            liftEnc.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleDual_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .gamepad(mockedGamepad).build();
            Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).gamepad(mockedGamepad).build();

            lift.tele();
            liftEnc.tele();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveSolo_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
            Lift liftEnc =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();
            Lift liftPos = new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true)
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
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).build();
            Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).build();
            Lift liftPos = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).diameter(4).build();

            lift.move(0.5, "up", 1);
            liftEnc.move(0.5, "up", 1);
            liftPos.move(0.5, "up", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverseSolo_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
            Lift liftEnc =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();

            lift.reverse();
            liftEnc.reverse();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void reverseSolo_isCalledWithValidInputs() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
            Lift liftEnc =
                    new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();

            lift.reverse();
            lift.reverse("lift");
            liftEnc.reverse();
            liftEnc.reverse("lift");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseSolo_ThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).build();
        Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).build();

        lift.reverse("abc");
        lift.reverse("");
        liftEnc.reverse("abc");
        liftEnc.reverse("");
    }

    @Test
    public void reverseDual_isCalledWithValidInputs() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).build();
            Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).build();

            lift.reverse();
            lift.reverse("liftLeft");
            lift.reverse("liftRight");
            liftEnc.reverse();
            liftEnc.reverse("liftLeft");
            liftEnc.reverse("liftRight");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void reverseDual_ThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).build();
        Lift liftEnc = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                .useEncoder(true).build();

        lift.reverse("abc");
        lift.reverse("");
        liftEnc.reverse("abc");
        liftEnc.reverse("");
    }

    @Test
    public void setGearingSolo_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true)
                    .diameter(4).build();

            lift.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingSolo_ThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).useEncoder(true).diameter(4)
                .build();

        lift.setGearing(0);
    }

    @Test
    public void setGearingDual_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2)
                    .useEncoder(true).diameter(4).build();

            lift.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDual_ThrowsException() {
        mockInit();

        Lift lift = new Lift.Builder(mockedOpMode, mockedHardwareMap).numMotors(2).useEncoder(true)
                .diameter(4).build();

        lift.setGearing(0);
    }
}
