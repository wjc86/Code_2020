/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  CANSparkMax colorWheel;
  Joystick joystick;
  ColorSensor mColorSensor;
  ColorSensor.color mDetectedColor;
  ColorSensor.color mWantedColor;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    colorWheel = new CANSparkMax(4, MotorType.kBrushless);
    joystick = new Joystick(1);
    mColorSensor = new ColorSensor();
    mDetectedColor = ColorSensor.color.UNKNOWN;
    mWantedColor = ColorSensor.color.UNKNOWN;
    SmartDashboard.putString("Wanted Color", "UNKNOWN");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    mWantedColor = mColorSensor.toColor(SmartDashboard.getString("Wanted Color", "UNKNOWN")).getActualColor();
    mDetectedColor = mColorSensor.detectColor();
    if(joystick.getRawButton(1) && mDetectedColor != mWantedColor){
      colorWheel.set(0.1);
    } else{
      colorWheel.set(0);
    }
    SmartDashboard.putBoolean("Joystick Button", joystick.getRawButton(1));
    SmartDashboard.putBoolean("Color Equals", mDetectedColor == mWantedColor);
    SmartDashboard.putString("Detected Color", mDetectedColor.toString());
    SmartDashboard.putNumber("Detected Color", mDetectedColor.toInt());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    if(joystick.getRawButton(1)){
      colorWheel.set(0.1);
    } else{
      colorWheel.set(0);
    }
  }
}