package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class extendIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();
  boolean deployed = false;
  boolean done = false;
  
  public extendIntake() {addRequirements(m_Intake);}

  @Override
  public void initialize() {
    deployed=false;
    done= false;
  }

  @Override
  public void execute() {
    if (m_Intake.isDeployed == false){
      m_Intake.pistonDeploy();
      m_Intake.startIntake(Constants.intakeMaxPCT);
      m_Intake.speed = Constants.intakeMaxPCT;
      deployed = true;
    }
    done=true;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    if(done){
      return true;
    }
    return false;
  }
}