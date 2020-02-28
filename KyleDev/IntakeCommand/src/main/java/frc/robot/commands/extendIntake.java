package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class extendIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();
  
  public extendIntake() {
    addRequirements(m_Intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(m_Intake.isDeployed == false) {
      m_Intake.PistonDeploy();
      m_Intake.isDeployed = true;
    } else if (m_Intake.isDeployed == true) {
      m_Intake.PistonRetract();
      m_Intake.isDeployed = false;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}