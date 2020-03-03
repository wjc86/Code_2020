package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private static Shooter instance = new Shooter();

  public TalonFX flywheel = new TalonFX(ShooterConstants.FLYWHEEL_ID);
  public TalonSRX booster = new TalonSRX(ShooterConstants.BOOSTER_ID);

  public Shooter() {
    flywheel.config_kP(0, ShooterConstants.FLYWHEEL_P);
    flywheel.config_kI(0, ShooterConstants.FLYWHEEL_I);
    flywheel.config_kD(0, ShooterConstants.FLYWHEEL_D);
  }

  public static Shooter getInstance() {
    return instance;
  }

  public void setFlywheelVelocityControl(double velocity) {
    flywheel.set(ControlMode.Velocity, velocity);
  }

  public void setFlywheelPercentControl(double percent) {
    flywheel.set(ControlMode.PercentOutput, percent);
  }

  public void setBoosterPercentControl(double percent) {
    flywheel.set(ControlMode.PercentOutput, percent);
  }
}
