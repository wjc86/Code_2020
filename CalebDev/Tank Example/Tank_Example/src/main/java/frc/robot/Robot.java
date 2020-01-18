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
  private double positionConversion = 7.1631/100000.0;
  private double velocityConversion = 7.1631/10000.0;
  private double leftMotorTotal = 0;
  private double rightMotorTotal = 0;
  private double codeRunThru = 0;
  private double target = 7.0;
  private double currentVelocity = 0.0;
  private double kP = 0.0;
  private double kD = 0.0;
  private double feedForward = 0.0;
  private double error = 0.0;
  private double derivative = 0.0;
  private double lastError = 0.0;
  private double proportional = 0.0;

  @Override
  public void robotInit() {
    leftMotor = new TalonFX(1);
    rightMotor = new TalonFX(2);    
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
  }

  @Override
  public void teleopInit() {
    // TODO Auto-generated method stub
    super.teleopInit();
    SmartDashboard.putNumber("kP", 0);
    SmartDashboard.putNumber("kD", 0);
  }

  @Override
  public void teleopPeriodic() {
    // leftMotor.set(TalonFXControlMode.PercentOutput, .9);
    // rightMotor.set(TalonFXControlMode.PercentOutput, -.9 );
    // SmartDashboard.putNumber("left position", leftMotor.getSelectedSensorPosition()*positionConversion);
    // SmartDashboard.putNumber("right position", rightMotor.getSelectedSensorPosition()*positionConversion);
     SmartDashboard.putNumber("left velocity", leftMotor.getSelectedSensorVelocity()*velocityConversion);
     SmartDashboard.putNumber("right velocity", -rightMotor.getSelectedSensorVelocity()*velocityConversion);
     SmartDashboard.putNumber("target", target);
     kP = SmartDashboard.getNumber("kP", 0);
     kD = SmartDashboard.getNumber("kD", 0);
    // codeRunThru += 1.0;
    // if (codeRunThru>100){
    //   leftMotorTotal += leftMotor.getSelectedSensorVelocity()*velocityConversion;
    //   rightMotorTotal += rightMotor.getSelectedSensorVelocity()*velocityConversion;
    // }
    // System.out.println(leftMotorTotal/(codeRunThru-100) + " | " + rightMotorTotal/(codeRunThru-100));

    error = target -  leftMotor.getSelectedSensorVelocity()*velocityConversion;
    feedForward = (target + .1) / 15.62;
    proportional = error * kP;
    derivative = ((error - lastError) / 0.02) * kD;
    if(m_leftStick.getRawButton(1)){
      leftMotor.set(TalonFXControlMode.PercentOutput, feedForward + proportional - derivative);
      rightMotor.set(TalonFXControlMode.PercentOutput, -(feedForward + proportional - derivative));
    } else {
      leftMotor.set(TalonFXControlMode.PercentOutput, -m_leftStick.getY()*.33);
      rightMotor.set(TalonFXControlMode.PercentOutput, m_rightStick.getY()*.33);
    }
    lastError = error;

  }

  public void autonPeriodic(){


  }
  /*left=4.448 and right=11.909
  traveled=8' 8.5"
  left=4.744 and right=11.655
  left=12.684 and right-3.828 */
}
