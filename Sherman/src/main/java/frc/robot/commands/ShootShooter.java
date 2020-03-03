package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class ShootShooter extends CommandBase {
  private Shooter m_Shooter = Shooter.getInstance();

  public ShootShooter() {
    addRequirements(m_Shooter);
  }

  @Override
  public void initialize() {
    m_Shooter.setFlywheelVelocityControl(ShooterConstants.FLYWHEEL_VELOCITY);
    m_Shooter.setBoosterPercentControl(1);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    m_Shooter.setFlywheelPercentControl(0);
    m_Shooter.setBoosterPercentControl(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
