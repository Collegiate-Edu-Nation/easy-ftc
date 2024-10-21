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
            new Lift(mockedOpMode, mockedHardwareMap);
            new Lift(mockedOpMode, mockedHardwareMap, true);
            new Lift(mockedOpMode, mockedHardwareMap, mockedGamepad);
            new Lift(mockedOpMode, mockedHardwareMap, true, 4);
            new Lift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
            new Lift(mockedOpMode, mockedHardwareMap, true, 4, mockedGamepad);
            new Lift(mockedOpMode, mockedHardwareMap, 2);
            new Lift(mockedOpMode, mockedHardwareMap, 2, true);
            new Lift(mockedOpMode, mockedHardwareMap, 2, mockedGamepad);
            new Lift(mockedOpMode, mockedHardwareMap, 2, true, 4);
            new Lift(mockedOpMode, mockedHardwareMap, 2, true, mockedGamepad);
            new Lift(mockedOpMode, mockedHardwareMap, 2, true, 4, mockedGamepad);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void teleSolo_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift(mockedOpMode, mockedHardwareMap, mockedGamepad);
            Lift liftEnc = new Lift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
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
            Lift lift = new Lift(mockedOpMode, mockedHardwareMap, 2, mockedGamepad);
            Lift liftEnc = new Lift(mockedOpMode, mockedHardwareMap, 2, true, mockedGamepad);
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
            Lift drive = new Lift(mockedOpMode, mockedHardwareMap);
            Lift driveEnc = new Lift(mockedOpMode, mockedHardwareMap, true);
            Lift drivePos = new Lift(mockedOpMode, mockedHardwareMap, true, 4);
            drive.move(0.5, "up", 1);
            driveEnc.move(0.5, "up", 1);
            drivePos.move(0.5, "up", 12);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void moveDual_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift(mockedOpMode, mockedHardwareMap, 2);
            Lift liftEnc = new Lift(mockedOpMode, mockedHardwareMap, 2, true);
            Lift liftPos = new Lift(mockedOpMode, mockedHardwareMap, 2, true, 4);
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
            Lift lift = new Lift(mockedOpMode, mockedHardwareMap);
            Lift liftEnc = new Lift(mockedOpMode, mockedHardwareMap, true);
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
            Lift lift = new Lift(mockedOpMode, mockedHardwareMap);
            Lift liftEnc = new Lift(mockedOpMode, mockedHardwareMap, true);
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

        Lift lift = new Lift(mockedOpMode, mockedHardwareMap, mockedGamepad);
        Lift liftEnc = new Lift(mockedOpMode, mockedHardwareMap, true, mockedGamepad);
        lift.reverse("abc");
        lift.reverse("");
        liftEnc.reverse("abc");
        liftEnc.reverse("");
    }

    @Test
    public void reverseDual_isCalledWithValidInputs() {
        mockInit();

        try {
            Lift lift = new Lift(mockedOpMode, mockedHardwareMap, 2);
            Lift liftEnc = new Lift(mockedOpMode, mockedHardwareMap, 2, true);
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

        Lift lift = new Lift(mockedOpMode, mockedHardwareMap, 2, mockedGamepad);
        Lift liftEnc = new Lift(mockedOpMode, mockedHardwareMap, 2, true, mockedGamepad);
        lift.reverse("abc");
        lift.reverse("");
        liftEnc.reverse("abc");
        liftEnc.reverse("");
    }

    @Test
    public void setGearingSolo_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift(mockedOpMode, mockedHardwareMap, true, 4);
            lift.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingSolo_ThrowsException() {
        mockInit();

        Lift lift = new Lift(mockedOpMode, mockedHardwareMap, true, 4);
        lift.setGearing(0);
    }

    @Test
    public void setGearingDual_isCalled() {
        mockInit();

        try {
            Lift lift = new Lift(mockedOpMode, mockedHardwareMap, 2, true, 4);
            lift.setGearing(19.2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGearingDual_ThrowsException() {
        mockInit();

        Lift lift = new Lift(mockedOpMode, mockedHardwareMap, 2, true, 4);
        lift.setGearing(0);
    }
}
