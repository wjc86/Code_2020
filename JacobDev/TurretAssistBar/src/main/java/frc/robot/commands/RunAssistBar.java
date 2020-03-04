/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RunAssistBar extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private Turret turret = Turret.getInstance();

  private Joystick stick = new Joystick(0);

  private double output = 0.0;

  public RunAssistBar() {
    addRequirements(turret);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    turret.setAssistBarOutput(0.0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    SmartDashboard.putNumber("output", output);
    System.out.println("output: " + output);
    if(Math.abs(stick.getY()) > 0.1) {
      output -= stick.getY() / 100;
    }
    if(output < 0) {
      output = 0;
    } else if (output > 1) {
      output = 1;
    }
    turret.setAssistBarOutput(output);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
  }
}
