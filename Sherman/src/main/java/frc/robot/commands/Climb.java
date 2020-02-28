package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class Climb extends CommandBase {
  private Climber m_Climber = Climber.getInstance();

  public Climb() {
    addRequirements(m_Climber);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_Climber.set(-1);
  }

  @Override
  public void end(boolean interrupted) {
    m_Climber.lock();
  }

  @Override
  public boolean isFinished() {
    if(m_Climber.getPosition() > ClimberConstants.CLIMB_HEIGHT) {
      return true;
    } else {
      return false;
    }
  }
}
