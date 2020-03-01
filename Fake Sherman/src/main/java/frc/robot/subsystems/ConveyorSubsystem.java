package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;

public class ConveyorSubsystem extends SubsystemBase {
  private TalonSRX conveyorMotor = new TalonSRX(1);
  private TalonSRX conveyorMotorFollower = new TalonSRX(2);
  private DigitalInput topSensor = new DigitalInput(0);
  private DigitalInput bottomSensor = new DigitalInput(2);
  private DigitalInput shooterSensor = new DigitalInput(1);
  private DigitalInput intakeSensor = new DigitalInput(3);
  private static ConveyorSubsystem instance = new ConveyorSubsystem();
  // private PIDController conveyorPID = new PIDController(0.01, 0.001, 0.002);
  // private CANEncoder encoder;
  private int ballCount = 0;
  private boolean shooterLineWasTripped = false;
  private boolean intakeLineWasTripped = false;
  private boolean isFull = false;

  public ConveyorSubsystem() {
    conveyorMotor.config_kP(0, Constants.kP);
    conveyorMotor.config_kP(0, Constants.kI);
    conveyorMotor.config_kP(0, Constants.kD);
    conveyorMotor.configMotionCruiseVelocity(Constants.maxConveyorVelocity);
    conveyorMotor.configMotionAcceleration(Constants.maxConveyorAccel);
    conveyorMotorFollower.follow(conveyorMotor);
    // encoder.setPosition(0);
    // encoder.setPositionConversionFactor(1.0);
  }
  public static ConveyorSubsystem getInstance(){
    return instance;
  }

  public void setPercentControl(double percent) {
    conveyorMotor.set(ControlMode.PercentOutput, percent);
  }

  public void setMotionProfile(double pos) {
    conveyorMotor.set(ControlMode.MotionMagic, pos);
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

  public int getBallCount() {
    return ballCount;
  }

  public boolean getIsFull() {
    return isFull;
  }

  public void setIsFull(boolean isFull) {
    this.isFull = isFull;
  }

  public void updateIsFull() {
    isFull = !isTopLineClosed();
  }

  public void putToDashboard() {
    SmartDashboard.putBoolean("Top", isTopLineClosed());
    SmartDashboard.putBoolean("Bottom", isBottomLineClosed());
    SmartDashboard.putBoolean("Shooter Line", isShooterLineClosed());
    SmartDashboard.putBoolean("Intake Line", isIntakeLineClosed());
    SmartDashboard.putNumber("Ball Count", ballCount);
  }

  @Override
  public void periodic() {
    putToDashboard();
    ballCounting();
    updateIsFull();
  }
}
