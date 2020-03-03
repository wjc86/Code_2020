package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ShootConveyor extends CommandBase {
  private Conveyor m_Conveyor = Conveyor.getInstance();

  public ShootConveyor() {
    addRequirements(m_Conveyor);
  }

  @Override
  public void initialize() {
    m_Conveyor.setPercentControl(1);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    m_Conveyor.setPercentControl(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
