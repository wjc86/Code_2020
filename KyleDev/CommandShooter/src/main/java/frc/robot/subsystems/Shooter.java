package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Shooter extends SubsystemBase {
  private static Shooter instance = new Shooter();
  public TalonFX Flywheel = new TalonFX(Constants.FlywheelCANID);
  public TalonSRX shooterSpeedUpMotor = new TalonSRX(Constants.ShooterTalonCANID);
  private DigitalInput shooterLine = new DigitalInput(Constants.ShooterLinebreakID);

  public Shooter() {
    Flywheel.config_kP(0, shooterKP);
    Flywheel.config_kI(0, shooterKI);
    Flywheel.config_kD(0, shooterKD);
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

  public void shooterLineBreak() {
    SmartDashboard.putBoolean("Shooter Line", shooterLine.get());
  }

  @Override
  public void periodic() {}
}