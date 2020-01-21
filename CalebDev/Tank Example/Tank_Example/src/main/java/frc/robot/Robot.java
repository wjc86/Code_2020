/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
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
  private AHRS m_gyro;
  private double positionConversion = 7.1631/100000.0;
  private double velocityConversion = 7.1631/10000.0;
  private double leftMotorTotal = 0;
  private double rightMotorTotal = 0;
  private double codeRunThru = 0;
  private double target = 7.0;
  private double currentVelocity = 0.0;
  private double kP = 0.0;
  private double kI = 0.0;
  private double kD = 0.0;
  private double error = 0.0;
  private double lastError = 0.0;
  private double feedForward = 0.0;
  private double derivative = 0.0;
  private double integral = 0.0;
  private double proportional = 0.0;
  private double currentPos = 0.0;
  private double lastPos = 0.0;
  private double changePos = 0.0;
  private double angle = 0.0;
  private double xAxis = 0.0;
  private double yAxis = 0.0;


  @Override
  public void robotInit() {
    leftMotor = new TalonFX(1);
    rightMotor = new TalonFX(2);
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
    m_gyro = new AHRS(SPI.Port.kMXP);
  }

  @Override
  public void teleopInit() {
    // TODO Auto-generated method stub
    super.teleopInit();
    SmartDashboard.putNumber("kP", 0);
    SmartDashboard.putNumber("kI", 0);
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
     SmartDashboard.putNumber("angle", angle);
     SmartDashboard.putNumber("x", xAxis);
     SmartDashboard.putNumber("y", yAxis);
     SmartDashboard.putNumber("pos", currentPos);
     SmartDashboard.putNumber("error", error);
     kP = SmartDashboard.getNumber("kP", 0);
     kI = SmartDashboard.getNumber("kI", 0);
     kD = SmartDashboard.getNumber("kD", 0);
     angle = ((-m_gyro.getAngle())/3.0)*Math.PI;
    // codeRunThru += 1.0;
    // if (codeRunThru>100){
    //   leftMotorTotal += leftMotor.getSelectedSensorVelocity()*velocityConversion;
    //   rightMotorTotal += rightMotor.getSelectedSensorVelocity()*velocityConversion;
    // }
    // System.out.println(leftMotorTotal/(codeRunThru-100) + " | " + rightMotorTotal/(codeRunThru-100));
    currentPos = leftMotor.getSelectedSensorPosition()*positionConversion; 
    changePos = currentPos - lastPos;
    xAxis += changePos * Math.sin(angle) * -1.0;
    yAxis += changePos * Math.cos(angle);
    error = target -  leftMotor.getSelectedSensorVelocity()*velocityConversion;
    feedForward = (target + .1) / 15.62;
    proportional = error * kP;
    integral = error * 0.02 * kI;
    derivative = ((error - lastError) / 0.02) * kD;
    
    if(m_leftStick.getRawButton(1)){
      leftMotor.set(TalonFXControlMode.PercentOutput, feedForward + proportional + integral - derivative);
      rightMotor.set(TalonFXControlMode.PercentOutput, -(feedForward + proportional + integral - derivative));
    } else if(!m_rightStick.getRawButton(2)) {
      leftMotor.set(TalonFXControlMode.PercentOutput, -m_leftStick.getY()*.5);
      rightMotor.set(TalonFXControlMode.PercentOutput, m_rightStick.getY()*.5);
    } else{ 
      leftMotor.set(TalonFXControlMode.PercentOutput, -m_leftStick.getY());
      rightMotor.set(TalonFXControlMode.PercentOutput, m_rightStick.getY());
    }
    lastError = error;
    lastPos = currentPos;

    if(m_rightStick.getRawButton(1)){
      m_gyro.reset();
      xAxis = 0.0;
      yAxis = 0.0;
    }
  }

  public void autonPeriodic(){


  }
  /*left=4.448 and right=11.909
  traveled=8' 8.5"
  left=4.744 and right=11.655
  left=12.684 and right-3.828 */
}
