/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
  private Joystick joy = new Joystick(0);
  private ColorSensor mColorSensor = new ColorSensor();
  private ColorSensor.color mColor;

  @Override
  public void robotInit() {
    mColorSensor.setup();
  }
  
  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    if(joy.getRawButtonPressed(8)){
      mColor = mColorSensor.detectColor();
    }
    SmartDashboard.putString("Robot's Detected Color", mColor.toString());
  }

  @Override
  public void testPeriodic() {
  }
}
