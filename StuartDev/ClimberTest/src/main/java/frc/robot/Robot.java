package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  CANSparkMax motor;
  Joystick stick;
  
  @Override
  public void robotInit() {
    motor = new CANSparkMax(2, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
    motor.setSmartCurrentLimit(100);
    motor.setIdleMode(IdleMode.kBrake);
    motor.burnFlash();
    stick = new Joystick(0);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Amps", motor.getOutputCurrent());
    SmartDashboard.putNumber("bus voltage", motor.getBusVoltage());
  }

  @Override
  public void autonomousInit() {
  }
  
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopPeriodic() {
    motor.set(-stick.getY());
    SmartDashboard.putNumber("Output", -stick.getY());
    SmartDashboard.putNumber("Amps", motor.getOutputCurrent());
    SmartDashboard.putNumber("bus voltage", motor.getBusVoltage());
  }

  @Override
  public void testPeriodic() {
  }
}
