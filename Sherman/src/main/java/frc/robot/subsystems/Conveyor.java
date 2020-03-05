package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ConveyorConstants;

public class Conveyor extends SubsystemBase {
  private static Conveyor instance = new Conveyor();

  private TalonSRX master = new TalonSRX(ConveyorConstants.MASTER_ID);
  private TalonSRX follower = new TalonSRX(ConveyorConstants.FOLLOWER_ID);

  // private DigitalInput shooterSensor = new DigitalInput(ConveyorConstants.SHOOTER_SENSOR_PORT);
  private DigitalInput topSensor = new DigitalInput(ConveyorConstants.TOP_SENSOR_PORT);
  private DigitalInput bottomSensor = new DigitalInput(ConveyorConstants.BOTTOM_SENSOR_PORT);
  // private DigitalInput intakeSensor = new DigitalInput(ConveyorConstants.INTAKE_SENSOR_PORT);

  private int ballCount = 0;
  private boolean shooterLineWasTripped = false;
  private boolean intakeLineWasTripped = false;
  private boolean isFull = false;

  public Conveyor() {
    master.config_kP(0, ConveyorConstants.MOTION_MAGIC_P);
    master.config_kI(0, ConveyorConstants.MOTION_MAGIC_I);
    master.config_kD(0, ConveyorConstants.MOTION_MAGIC_D);
    master.configMotionCruiseVelocity(ConveyorConstants.MOTION_MAGIC_VELOCITY);
    master.configMotionAcceleration(ConveyorConstants.MOTION_MAGIC_ACCELERATION);
    follower.follow(master);//Does the follow set the PID to match/ just match output so it doesnt matter
  }

  @Override
  public void periodic() {
    putToDashboard();
    ballCounting();
    updateIsFull();
  }

  public static Conveyor getInstance(){
    return instance;
  }

  public void setPercentControl(double percent) {
    master.set(ControlMode.PercentOutput, percent);
    follower.follow(master);
  }

  public void setMotionMagic(double pos) {
    master.set(ControlMode.MotionMagic, pos);
    follower.follow(master);
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
      if(isTopLineClosed()) {
        shooterLineWasTripped = false;
        return true;
      } 
      else if (!isTopLineClosed()) {
        shooterLineWasTripped = true;
      }

    }
    return false;
  }

  public boolean checkForEntry() {
    if(intakeLineWasTripped) {
      if(isBottomLineClosed()) {
        intakeLineWasTripped = false;
        return true;
      } 
      else if (!isBottomLineClosed()) {
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

  // public boolean isShooterLineClosed() {
  //   return shooterSensor.get();
  // }

  // public boolean isIntakeLineClosed() {
  //   return intakeSensor.get(); 
  // }

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
    // SmartDashboard.putBoolean("Shooter Line", isShooterLineClosed());
    // SmartDashboard.putBoolean("Intake Line", isIntakeLineClosed());
    SmartDashboard.putNumber("Ball Count", ballCount);
  }
}
