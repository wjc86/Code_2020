package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ConveyorConstants;
import frc.robot.subsystems.Conveyor;

public class ShootConveyor extends CommandBase {
  private Conveyor m_Conveyor = Conveyor.getInstance();
  private double startTime;

  public ShootConveyor() {
    addRequirements(m_Conveyor);
  }

  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
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
    if(Timer.getFPGATimestamp() > startTime + ConveyorConstants.SHOOT_TIME) {
      return true;
    } else {
      return false;
    }
  }
}
