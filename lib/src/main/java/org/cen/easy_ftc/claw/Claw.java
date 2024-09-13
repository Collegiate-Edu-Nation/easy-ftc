package org.cen.easy_ftc.claw;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Blueprints an abstract claw, providing basic functionalities, options, and objects common to all claws.
 * Cannot be instantiated, only extended by actual lift classes (see {@link SoloClaw} and {@link DualClaw}).
 */
public abstract class Claw {
  protected HardwareMap hardwareMap;
  protected double open, close;
  protected Gamepad gamepad;

  /**
   * Constructor
   * @Defaults 
   * reverseState = false
   * <li>gamepad = null
   */
  public Claw(HardwareMap hardwareMap) {this(hardwareMap, false);}
  /**
   * Constructor
   * @Defaults
   * gamepad = null
   */
  public Claw(HardwareMap hardwareMap, boolean reverseState) {this(hardwareMap, reverseState, null);}
  /**
   * Constructor
   * @Defaults
   * reverseState = false
   */
  public Claw(HardwareMap hardwareMap, Gamepad gamepad) {this(hardwareMap, false, gamepad);}
  /**
   * Constructor
   */
  public Claw(HardwareMap hardwareMap, boolean reverseState, Gamepad gamepad) {
    this.hardwareMap = hardwareMap;
    if(reverseState) {
      this.open = 1.0;
      this.close = 0.0;
    }
    else {
      this.open = 0.0;
      this.close = 1.0;
    }
    this.gamepad = gamepad;
    hardwareInit();
  }

  protected abstract void hardwareInit();
  public abstract void tele();
  public abstract void move(String direction);
}