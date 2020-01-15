/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private TalonFX leftMotor;
  private TalonFX rightMotor;
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  @Override
  public void robotInit() {
    leftMotor = new TalonFX(1);
    rightMotor = new TalonFX(2);    
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
  }

  @Override
  public void teleopPeriodic() {
    leftMotor.set(TalonFXControlMode.PercentOutput, -m_leftStick.getY());
    rightMotor.set(TalonFXControlMode.PercentOutput, m_rightStick.getY());
    SmartDashboard.putNumber("left position", leftMotor.getSelectedSensorPosition());
    SmartDashboard.putNumber("right position", rightMotor.getSelectedSensorPosition());
  }

  public void autonPeriodic(){


  }
}
