/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class ConveyorIntake extends CommandBase {
  private ConveyorSubsystem m_ConveyorSubsystem = ConveyorSubsystem.getInstance();
  private boolean lastSensorState = true;
  private double holdPosition = 0;

  /**
   * Creates a new ConveyorIntake.
   */
  public ConveyorIntake() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_ConveyorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    holdPosition = m_ConveyorSubsystem.getPosititon();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if(!lastSensorState && m_ConveyorSubsystem.bottomLine()){
    //   holdPosition = m_ConveyorSubsystem.getPosititon();
    // }
    // SmartDashboard.putNumber("Hold Position", holdPosition);
    // if (m_ConveyorSubsystem.topLine()){
    //   if (m_ConveyorSubsystem.bottomLine()){
    //     m_ConveyorSubsystem.setPosititon(holdPosition);
    //     SmartDashboard.putBoolean("Top and Bottom", true);
    //   }
    //   else {
    //     SmartDashboard.putBoolean("Top and Bottom", false);
    //     m_ConveyorSubsystem.move(-0.1);
    //   }
    // }
    // else {
    //   m_ConveyorSubsystem.move(0);
    // }
    //lastSensorState = m_ConveyorSubsystem.bottomLine();
    //m_ConveyorSubsystem.setPosition()
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
