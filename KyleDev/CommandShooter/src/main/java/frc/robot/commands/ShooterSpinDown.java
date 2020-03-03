package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterSpinDown extends CommandBase {
  private Shooter m_Shooter = Shooter.getInstance();
  boolean motorset = false;
  boolean ready = false;

  public ShooterSpinDown() {
    addRequirements(m_Shooter);
  }

  @Override
  public void initialize() {
    motorset = false;
    ready = false;
  }

  @Override
  public void execute() {
    if(motorset == false){
      m_Shooter.runShooter(0);
      motorset = true;
    }
    if(m_Shooter.Flywheel.getSelectedSensorVelocity() <= 0) {
      ready = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if (ready){
      return true;
    }
    return false;
  }
}