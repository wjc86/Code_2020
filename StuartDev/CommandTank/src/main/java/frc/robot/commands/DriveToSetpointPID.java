/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DriveToSetpointPID extends CommandBase {
  private Drivetrain m_Drivetrain = Drivetrain.getInstance();

  private DoubleSupplier wantedDistanceDoubleSupplier;
  private DoubleSupplier currentDistanceDoubleSupplier;
  private double distanceDeadband;
  private double angleDeadband;
  private DoubleSupplier wantedAngleDoubleSupplier;
  private DoubleSupplier currentAngleDoubleSupplier;

  private PIDController distanceController = new PIDController(Constants.driveToSetpointDistanceP, 0.0, 0.0);
  private PIDController angleController = new PIDController(Constants.driveToSetpointAngleP, 0.0, 0.0);

  public DriveToSetpointPID(DoubleSupplier wantedDistanceDoubleSupplier, DoubleSupplier currentDistanceDoubleSupplier, double distanceDeadband,
    DoubleSupplier wantedAngleDoubleSupplier, DoubleSupplier currentAngleDoubleSupplier, double angleDeadband) {
    this.wantedDistanceDoubleSupplier = wantedDistanceDoubleSupplier;
    this.currentDistanceDoubleSupplier = currentDistanceDoubleSupplier;
    this.distanceDeadband = distanceDeadband;
    this.angleDeadband = angleDeadband;
    this.wantedAngleDoubleSupplier = wantedAngleDoubleSupplier;
    this.currentAngleDoubleSupplier = currentAngleDoubleSupplier;
    addRequirements(m_Drivetrain);
    System.out.println("4");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    distanceController.setP(SmartDashboard.getNumber("Distance P", 0));
    angleController.setP(SmartDashboard.getNumber("Angle P", 0));
    angleController.setI(SmartDashboard.getNumber("Angle I", 0));
    angleController.setD(SmartDashboard.getNumber("Angle D", 0));
    m_Drivetrain.drive(new ChassisSpeeds(
      distanceController.calculate(
        currentDistanceDoubleSupplier.getAsDouble(),
        wantedAngleDoubleSupplier.getAsDouble()), 0.0,
      angleController.calculate(
        currentAngleDoubleSupplier.getAsDouble(),
        wantedAngleDoubleSupplier.getAsDouble())));
    System.out.println(currentDistanceDoubleSupplier.getAsDouble() + " | " + wantedDistanceDoubleSupplier.getAsDouble() + " | " + currentAngleDoubleSupplier.getAsDouble() + " | " + wantedAngleDoubleSupplier.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if ((Math.abs(wantedDistanceDoubleSupplier.getAsDouble()) < distanceDeadband) & (Math.abs(wantedAngleDoubleSupplier.getAsDouble()) < angleDeadband)) {
      return true;
    } else {
      return false;
    }
  }
}
