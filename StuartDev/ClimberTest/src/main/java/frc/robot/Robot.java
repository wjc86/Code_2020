package frc.robot;

import com.revrobotics.CANSparkMax;
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
    motor.setSmartCurrentLimit(120);
    motor.setSmartCurrentLimit(120, 120);
    motor.burnFlash();
    stick = new Joystick(0);
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
    motor.set(-stick.getThrottle());
    SmartDashboard.putNumber("Output", -stick.getThrottle());
    SmartDashboard.putNumber("Amps", motor.getOutputCurrent());
    SmartDashboard.putNumber("bus voltage", motor.getBusVoltage());
  }

  @Override
  public void testPeriodic() {
  }
}
