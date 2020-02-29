package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterSensor extends CommandBase {
  private Shooter m_Shooter = Shooter.getInstance();
  private boolean done = false;

  public ShooterSensor() {
    addRequirements(m_Shooter);
  }

  @Override
  public void initialize() {
    done = false;
  }

  @Override
  public void execute() {
    m_Shooter.shooterLineBreak();
    done = true;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if(done) {
      return true;
    } else {
      return false;
    }
  }
}
