package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class moveIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();
  
  public moveIntake() {addRequirements(m_Intake);}

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(m_Intake.isDeployed == true) {
      if(m_Intake.speed == 0) {m_Intake.speed = Constants.intakeMaxPCT;}
      else if(m_Intake.speed == Constants.intakeMaxPCT) {m_Intake.speed = 0;}
    }

    m_Intake.StartIntake(m_Intake.speed);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {return false;}
}
