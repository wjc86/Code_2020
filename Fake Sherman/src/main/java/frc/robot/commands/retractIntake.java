package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class retractIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();
  boolean deployed = true;
  boolean done = false;
  public retractIntake() {
    addRequirements(m_Intake);
  }

  @Override
  public void initialize() {
    deployed=true;
    done=false;
  }

  @Override
  public void execute() {
    if (m_Intake.isDeployed == true){
      m_Intake.pistonRetract();
      m_Intake.startIntake(0);
      m_Intake.speed = 0;
      deployed = false;
    }
    done = true;
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (done){
      return true;
    }
    return false;
  }
}