package org.cen.easy_ftc.sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Implements a touch sensor by extending the functionality of {@link Sensor}.
 * <p>
 * @param HardwareMap hardwareMap (required)
 * @param Boolean reverseState (true or false)
 * <p>
 * @Methods
 * {@link #state()}
 */
public class Touch extends Sensor {
  private TouchSensor sensor;

  public Touch(HardwareMap hardwareMap) {super(hardwareMap);}
  public Touch(HardwareMap hardwareMap, boolean reverseState) {super(hardwareMap, reverseState);}

  @Override
  protected void hardwareInit() {
    sensor = hardwareMap.get(TouchSensor.class,"touchSensor");
  }

  @Override
  public boolean state() {
    if(reverseState) {
      return !(sensor.isPressed());
    }
    else {
      return sensor.isPressed();
    }
  }
}