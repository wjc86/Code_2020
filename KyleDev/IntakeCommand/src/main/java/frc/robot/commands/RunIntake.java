package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
public class RunIntake extends CommandBase {
 private Intake m_Intake = Intake.getInstance();
  public RunIntake() {addRequirements(m_Intake);}

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(m_Intake.isDeployed == true) {
      m_Intake.StartIntake(Constants.intakeMaxPct);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {return false;}
}
