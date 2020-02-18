/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Vector;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.tof.VL53L0XSensors;

public class Robot extends TimedRobot {
  //private ColorSensor mColorSensor = new ColorSensor();
  private ColorSensor.color mColor;
  private VL53L0XSensors mSensors = new VL53L0XSensors();

  @Override
  public void robotInit() {
    SmartDashboard.putString("Initializing...", "Please Wait");
    //mColorSensor.setup();
    mSensors.init();
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
    SmartDashboard.putBoolean("teleopPeriodic", true);
    //mColorSensor.detectColor();
    SmartDashboard.putBoolean("Got Color", true);

    /*Vector<Integer> distances = new Vector<>();
    try{
      distances = mSensors.readRangeSingleMillimeters();
    } catch(Exception e){
      e.printStackTrace();
    }
    
    if(distances != null && distances.capacity() > 0){
      for(int i = 0; i < distances.capacity(); i++){
        SmartDashboard.putNumber("Sensor " + i + " Distance" , distances.get(i));
      }
    }*/
  }

  @Override
  public void testPeriodic() {
  }
}