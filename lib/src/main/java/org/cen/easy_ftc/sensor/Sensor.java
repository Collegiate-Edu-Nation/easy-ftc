package org.cen.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Blueprints an abstract sensor, providing basic functionalities, options, and objects common to all sensors.
 * Cannot be instantiated, only extended by actual sensor classes (see
 * {@link Touch} and {@link Distance}).
 * <p>
 * @Methods
 * {@link #state()} (used by subclasses)
 */
public abstract class Sensor {
  protected HardwareMap hardwareMap;
  protected boolean reverseState;
  protected double calibrationValue;

  public Sensor(HardwareMap hardwareMap) {this(hardwareMap, false);}
  public Sensor(HardwareMap hardwareMap, boolean reverseState) {this(hardwareMap, reverseState, 0);}
  public Sensor(HardwareMap hardwareMap, double calibrationValue) {this(hardwareMap, false, calibrationValue);}
  public Sensor(HardwareMap hardwareMap, boolean reverseState, double calibrationValue) {
    this.hardwareMap = hardwareMap;
    this.reverseState = reverseState;
    this.calibrationValue = calibrationValue;
    hardwareInit();
  }

  protected abstract void hardwareInit();
  public abstract boolean state();
}