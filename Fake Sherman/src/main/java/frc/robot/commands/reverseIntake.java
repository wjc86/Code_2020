package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class reverseIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();
  
  public reverseIntake() {addRequirements(m_Intake);}

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_Intake.startIntake(-1);
  }

  @Override
  public void end(boolean interrupted) {
    m_Intake.startIntake(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}