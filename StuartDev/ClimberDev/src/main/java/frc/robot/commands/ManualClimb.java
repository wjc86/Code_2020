/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberWinch;

public class ManualClimb extends CommandBase {
  ClimberWinch m_ClimberWinch = ClimberWinch.getInstance();
  DoubleSupplier winchPercentDoubleSupplier;
  /**
   * Creates a new ManualClimb.
   */
  public ManualClimb(DoubleSupplier winchPercentDoubleSupplier) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.winchPercentDoubleSupplier = winchPercentDoubleSupplier;
    addRequirements(m_ClimberWinch);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ClimberWinch.setManualClimb(winchPercentDoubleSupplier.getAsDouble());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
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
