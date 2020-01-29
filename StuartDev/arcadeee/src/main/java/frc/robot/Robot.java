/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * This is a demo program showing the use of the DifferentialDrive class.
 * Runs the motors with arcade steering.
 */
public class Robot extends TimedRobot {
  private final WPI_TalonFX m_leftMotor = new WPI_TalonFX(1);
  private final WPI_TalonFX m_rightMotor = new WPI_TalonFX(2);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
  private final Joystick m_stickLeft = new Joystick(0);
  private final Joystick m_stickRight = new Joystick(1);
  private double velocityConversion = 7.1631/10000.0;

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    if(m_stickRight.getRawButton(1)){
      m_robotDrive.arcadeDrive(-m_stickLeft.getY(), m_stickRight.getX());
    } else {
      m_robotDrive.arcadeDrive(-m_stickLeft.getY(), m_stickRight.getX());
    }
    SmartDashboard.putNumber("left velocity", m_leftMotor.getSelectedSensorVelocity()*velocityConversion);
    SmartDashboard.putNumber("right velocity", -m_rightMotor.getSelectedSensorVelocity()*velocityConversion);
  }
}