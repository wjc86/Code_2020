package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
public class SensorIntake extends CommandBase {
  private Intake m_Intake = Intake.getInstance();

  public SensorIntake() {addRequirements(m_Intake);}

  @Override
  public void initialize() {}

  @Override
  public void execute() {m_Intake.Linebreak();}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {return false;}
}
