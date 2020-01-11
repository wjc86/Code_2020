package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Robot extends TimedRobot {
  Joystick leftStick;
  Joystick rightStick;
  TalonFX leftMotor;
  TalonFX rightMotor;
  TalonFXFeedbackDevice leftEncoder;

  @Override
  public void robotInit() {
    leftStick = new Joystick(0);
    rightStick = new Joystick(1);
    leftMotor = new TalonFX(1);
    rightMotor = new TalonFX(2);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopPeriodic() {
    leftMotor.set(TalonFXControlMode.PercentOutput, leftStick.getY());
    rightMotor.set(TalonFXControlMode.PercentOutput, rightStick.getY());
    System.out.println(leftMotor.getSelectedSensorVelocity() + " | " + rightMotor.getSelectedSensorVelocity());
  }

  @Override
  public void testPeriodic() {
  }
}
