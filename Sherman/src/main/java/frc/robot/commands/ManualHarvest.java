/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Harvester;

public class ManualHarvest extends CommandBase {
  private Harvester m_Harvester = Harvester.getInstance();

  DoubleSupplier motorSupply;
  BooleanSupplier actuateSupply;
  Boolean deployed = false;

  public ManualHarvest(DoubleSupplier motorSupply, BooleanSupplier actuateSupply) {
    this.motorSupply = motorSupply;
    this.actuateSupply = actuateSupply;
    addRequirements(m_Harvester);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Harvester.setPercentControl(motorSupply.getAsDouble());
    if(actuateSupply.getAsBoolean() && deployed) {
      m_Harvester.retract();
      deployed = false;
    }
    if(actuateSupply.getAsBoolean() && !deployed) {
      m_Harvester.deploy();
      deployed = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Harvester.setPercentControl(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
