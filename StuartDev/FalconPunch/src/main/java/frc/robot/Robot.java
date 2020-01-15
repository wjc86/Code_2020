package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
  PowerDistributionPanel pdp;
  double conversionFactor = (14.0/50.0)*0.5*Math.PI;

  @Override
  public void robotInit() {
    leftStick = new Joystick(0);
    rightStick = new Joystick(1);
    leftMotor = new TalonFX(1);
    rightMotor = new TalonFX(2);
    pdp = new PowerDistributionPanel();
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
  public void teleopInit() {
    super.teleopInit();
    // SmartDashboard.putNumber("leftK", 0.0);
    // SmartDashboard.putNumber("leftP", 0.0);
    // SmartDashboard.putNumber("leftI", 0.0);
    // SmartDashboard.putNumber("leftD", 0.0);
    // SmartDashboard.putNumber("rightK", 0.0);
    // SmartDashboard.putNumber("rightP", 0.0);
    // SmartDashboard.putNumber("rightI", 0.0);
    // SmartDashboard.putNumber("rightD", 0.0);
  }

  @Override
  public void teleopPeriodic() {
    double motorMultiplier = 1.0;
    double leftSetpoint;
    double rightSetpoint;
    if(Math.abs(leftStick.getY())>.075) {
      leftSetpoint = leftStick.getY()*motorMultiplier*-1;
    } else {
      leftSetpoint = 0.0;
    }
    if(Math.abs(rightStick.getY())>.075) {
      rightSetpoint = rightStick.getY()*motorMultiplier;
    } else {
      rightSetpoint = 0.0;
    }

    leftMotor.set(TalonFXControlMode.PercentOutput, leftSetpoint);
    rightMotor.set(TalonFXControlMode.PercentOutput, rightSetpoint);

    SmartDashboard.putNumber("Left Motor Supply Current", leftMotor.getSupplyCurrent());
    SmartDashboard.putNumber("Right Motor Supply Current", rightMotor.getSupplyCurrent());
    SmartDashboard.putNumber("Left Motor Output Current", leftMotor.getStatorCurrent());
    SmartDashboard.putNumber("Right Motor Output Current ", rightMotor.getStatorCurrent());
    SmartDashboard.putNumber("PDP Current Output", pdp.getTotalCurrent());
    SmartDashboard.putNumber("Left Velocity", leftMotor.getSelectedSensorVelocity()*conversionFactor);
    SmartDashboard.putNumber("Right Velocity", rightMotor.getSelectedSensorVelocity()*conversionFactor);

    /*
    leftMotor.config_kF(0 ,SmartDashboard.getNumber("leftK", 0.0));
    leftMotor.config_kP(0, SmartDashboard.getNumber("leftP", 0.0));
    leftMotor.config_kI(0, SmartDashboard.getNumber("leftI", 0.0));
    leftMotor.config_kD(0, SmartDashboard.getNumber("leftD", 0.0));
    rightMotor.config_kF(0 ,SmartDashboard.getNumber("rightK", 0.0));
    rightMotor.config_kP(0, SmartDashboard.getNumber("rightP", 0.0));
    rightMotor.config_kI(0, SmartDashboard.getNumber("rightI", 0.0));
    rightMotor.config_kD(0, SmartDashboard.getNumber("rightD", 0.0));

    leftMotor.set(TalonFXControlMode.Velocity, leftSetpoint);
    rightMotor.set(TalonFXControlMode.Velocity, rightSetpoint);

    SmartDashboard.putNumber("Left Target", leftSetpoint);
    SmartDashboard.putNumber("Right Target", rightSetpoint);
    SmartDashboard.putNumber("Left Actual", leftMotor.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Right Actual", rightMotor.getSelectedSensorVelocity());
    */
  }

  @Override
  public void testPeriodic() {
  }
}
