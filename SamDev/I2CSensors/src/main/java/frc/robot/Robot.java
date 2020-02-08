/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.TOFSensors.SensorNotInitialized;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
  private Joystick joy = new Joystick(0);
  private ColorSensor mColorSensor = new ColorSensor();
  private ColorSensor.color mColor;
  private TOFSensors mTOFSensors = new TOFSensors();

  @Override
  public void robotInit() {
    mColorSensor.setup();
    try{
      mTOFSensors.setup(0, 1);
    } catch(SensorNotInitialized notInit){
      notInit.print();
    }
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
    mColor = mColorSensor.detectColor();
    SmartDashboard.putString("Robot's Detected Color", mColor.toString());

    double[] values = mTOFSensors.readDistance();
    SmartDashboard.putString("Sensor 1 Distance", ((Double) values[0]).toString());
    SmartDashboard.putString("Sensor 2 Distance", ((Double) values[1]).toString());
  }

  @Override
  public void testPeriodic() {
  }
}
