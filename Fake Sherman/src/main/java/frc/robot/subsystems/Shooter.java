package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Shooter extends SubsystemBase {
  private static Shooter instance = new Shooter();
  public TalonFX Flywheel = new TalonFX(Constants.FlywheelCANID);
  public TalonSRX shooterSpeedUpMotor = new TalonSRX(Constants.ShooterTalonCANID);

  public Shooter() {
    Flywheel.config_kP(0, Constants.flywheelP);
    Flywheel.config_kI(0, Constants.flywheelI);
    Flywheel.config_kD(0, Constants.flywheelD);
  }

  public static Shooter getInstance() {
    return instance;
  }

  public void runShooter(int x) {
    if(x == 1) { 
      Flywheel.set(ControlMode.PercentOutput, Constants.shooterFlywheelPCT);
      shooterSpeedUpMotor.set(ControlMode.PercentOutput, Constants.shooterSpeedUpPCT);
    } else {
      Flywheel.set(ControlMode.PercentOutput, 0);
      shooterSpeedUpMotor.set(ControlMode.PercentOutput, 0);
    }
  }

  @Override
  public void periodic() {}
}