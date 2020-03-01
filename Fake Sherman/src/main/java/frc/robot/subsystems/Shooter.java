package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Shooter extends SubsystemBase {
  private static Shooter instance = new Shooter();
  public TalonFX flywheel = new TalonFX(Constants.FlywheelCANID);
  public TalonSRX booster = new TalonSRX(Constants.ShooterTalonCANID);

  public Shooter() {
    flywheel.config_kP(0, Constants.flywheelP);
    flywheel.config_kI(0, Constants.flywheelI);
    flywheel.config_kD(0, Constants.flywheelD);
  }

  public static Shooter getInstance() {
    return instance;
  }

  public void runShooter(int x) {
    if(x == 1) { 
      flywheel.set(ControlMode.PercentOutput, Constants.shooterFlywheelPCT);
      booster.set(ControlMode.PercentOutput, Constants.shooterSpeedUpPCT);
    } else {
      flywheel.set(ControlMode.PercentOutput, 0);
      booster.set(ControlMode.PercentOutput, 0);
    }
  }

  public void shooterVelocityControl(double velocity) {
    flywheel.set(ControlMode.Velocity, velocity);
  }

  public void shooterPercentControl(double percent) {
    flywheel.set(ControlMode.PercentOutput, percent);
  }

  public void boosterPercentControl(double percent) {
    flywheel.set(ControlMode.PercentOutput, percent);
  }

  @Override
  public void periodic() {}
}