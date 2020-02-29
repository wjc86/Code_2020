package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class reverseIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();
  boolean done = false;
  
  public reverseIntake() {addRequirements(m_Intake);}

  @Override
  public void initialize() {
    done = false;
  }

  @Override
  public void execute() {
    m_Intake.startIntake(m_Intake.speed * -1);
    done = true;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    if(done) {
      return true;
    } else {
      return false;
    }
  }
}