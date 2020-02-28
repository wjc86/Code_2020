/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ShooterSpinUp extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Shooter m_subsystem = Shooter.getInstance();
  boolean motorset= false;
  boolean flyready=false;
  boolean boosterready=false;
  double flywheel_motor_speed=1;//max speed as pct output
  double booster_motor_speed=1;//max speed as pct output


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShooterSpinUp() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    motorset= false;
    flyready=false;
    boosterready=false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(motorset==false){
      m_subsystem.flywheelmotor.set(flywheel_motor_speed); //Start speed control at to target speed wit motor appropriate commands
      m_subsystem.boostermotor.set(booster_motor_speed); //Start speed control at to target speed wit motor appropriate commands
      motorset=true;
    }
    if(flywheelmotor.get_encoder().get_velocity()>=flywheel_motor_speed){
      flyready = true;
    }
    if(boostermotor.get_encoder().get_velocity()>=booster_motor_speed){
      boosterready = true;
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (flyready && boosterready){
      return true;
    }
    return false;
  }
}
