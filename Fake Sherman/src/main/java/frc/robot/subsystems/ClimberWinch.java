package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberWinch extends SubsystemBase {
  private static ClimberWinch instance = new ClimberWinch();
  private CANSparkMax winchMotor = new CANSparkMax(Constants.winchMotorID, MotorType.kBrushless);
  private CANEncoder winchEncoder = new CANEncoder(winchMotor);
  private CANPIDController winchController = new CANPIDController(winchMotor);

  public ClimberWinch() {
    winchController.setFeedbackDevice(winchEncoder);
  }

  @Override
  public void periodic() {
  }

  public static ClimberWinch getInstance() {
    return instance;
  }

  public void setClimberPostition(double pos) {
    winchController.setReference(pos, ControlType.kPosition);
  }

  public void setManualClimb(double percent) {
    winchMotor.set(percent);
  }

  public double getClimberPosition() {
    return winchEncoder.getPosition();
  }
}