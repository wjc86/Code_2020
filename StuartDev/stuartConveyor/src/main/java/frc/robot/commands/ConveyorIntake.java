/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Conveyor;

public class ConveyorIntake extends CommandBase {
  private Conveyor m_Conveyor = Conveyor.getInstance();
  /**
   * Creates a new ConveyorIntake.
   */
  public ConveyorIntake() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_Conveyor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_Conveyor.getTopSensor()){
      if(!m_Conveyor.getBottomSensor()){
        m_Conveyor.driveMotor(.25);
      } else {
        m_Conveyor.driveMotor(0);
      }
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
