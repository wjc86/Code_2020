/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;



public class ConveyorShoot extends CommandBase {
  private ConveyorSubsystem m_ConveyorSubsystem = ConveyorSubsystem.getInstance();
  boolean shooting;

  public ConveyorShoot() {
    addRequirements(m_ConveyorSubsystem);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooting = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!shooting) {
      m_ConveyorSubsystem.conveyorMotor.set(ControlMode.Velocity, 1);
      shooting = true;
      
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
