package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;

public class ConveyorSubsystem extends SubsystemBase {
  public TalonSRX conveyorMotor = new TalonSRX(1);
  public TalonSRX conveyorMotorFollower = new TalonSRX(2);
  public DigitalInput topSensor = new DigitalInput(0);
  public DigitalInput bottomSensor = new DigitalInput(2);
  public DigitalInput shooterSensor = new DigitalInput(1);
  public DigitalInput intakeSensor = new DigitalInput(3);
  public static ConveyorSubsystem instance = new ConveyorSubsystem();
  public PIDController conveyorPID = new PIDController(0.01, 0.001, 0.002);
  public CANEncoder encoder;
  public int ballCount = 0;
  public boolean shooterLineWasTripped = false;
  public boolean intakeLineWasTripped = false;

  public ConveyorSubsystem() {
    conveyorMotorFollower.follow(conveyorMotor);
    encoder.setPosition(0);
    encoder.setPositionConversionFactor(1.0);
  }
  public static ConveyorSubsystem getInstance(){
    return instance;
  }

  public void ballCounting() {
    if(checkForEntry()){
      ballCount++;
    }
    if(checkForExit()){
      if(ballCount > 0){
        ballCount--;
      }
    }
  }

  public boolean checkForExit() {
    if(shooterLineWasTripped) {
      if(isShooterLineClosed()) {
        shooterLineWasTripped = false;
        return true;
      } 
      else if (!isShooterLineClosed()) {
        shooterLineWasTripped = true;
      }

    }
    return false;
  }

  public boolean checkForEntry() {
    if(intakeLineWasTripped) {
      if(isIntakeLineClosed()) {
        intakeLineWasTripped = false;
        return true;
      } 
      else if (!isIntakeLineClosed()) {
        intakeLineWasTripped = true;
      }

    }
    return false;
  }

  public boolean isTopLineClosed() {
    return topSensor.get();
  }

  public boolean isBottomLineClosed() {
    return bottomSensor.get();
  }

  public boolean isShooterLineClosed() {
    return shooterSensor.get();
  }

  public boolean isIntakeLineClosed() {
    return intakeSensor.get(); 
  }

  public void putToDashboard() {
    SmartDashboard.putBoolean("Top", isTopLineClosed());
    SmartDashboard.putBoolean("Bottom", isBottomLineClosed());
    SmartDashboard.putBoolean("Shooter Line", isShooterLineClosed());
    SmartDashboard.putBoolean("Intake Line", isIntakeLineClosed());
  }

  @Override
  public void periodic() {
    putToDashboard();
  }
}
