package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

public class DriveToSetpointPID extends CommandBase {
  private Drivetrain m_Drivetrain = Drivetrain.getInstance();

  private DoubleSupplier wantedDistanceDoubleSupplier;
  private DoubleSupplier currentDistanceDoubleSupplier;
  private double distanceDeadband;
  private double angleDeadband;
  private DoubleSupplier wantedAngleDoubleSupplier;
  private DoubleSupplier currentAngleDoubleSupplier;

  private PIDController distanceController = new PIDController(
    DrivetrainConstants.DRIVETOSETPOINT_DISTANCE_P, DrivetrainConstants.DRIVETOSETPOINT_DISTANCE_I, DrivetrainConstants.DRIVETOSETPOINT_DISTANCE_D);
  private PIDController angleController = new PIDController(
    DrivetrainConstants.DRIVETOSETPOINT_ANGLE_P, DrivetrainConstants.DRIVETOSETPOINT_ANGLE_I, DrivetrainConstants.DRIVETOSETPOINT_ANGLE_D);

  public DriveToSetpointPID(DoubleSupplier wantedDistanceDoubleSupplier, DoubleSupplier currentDistanceDoubleSupplier, double distanceDeadband,
    DoubleSupplier wantedAngleDoubleSupplier, DoubleSupplier currentAngleDoubleSupplier, double angleDeadband) {
    this.wantedDistanceDoubleSupplier = wantedDistanceDoubleSupplier;
    this.currentDistanceDoubleSupplier = currentDistanceDoubleSupplier;
    this.distanceDeadband = distanceDeadband;
    this.angleDeadband = angleDeadband;
    this.wantedAngleDoubleSupplier = wantedAngleDoubleSupplier;
    this.currentAngleDoubleSupplier = currentAngleDoubleSupplier;
    addRequirements(m_Drivetrain);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_Drivetrain.drive(new ChassisSpeeds(
      distanceController.calculate(
        currentDistanceDoubleSupplier.getAsDouble(),
        wantedAngleDoubleSupplier.getAsDouble()), 0.0,
      angleController.calculate(
        currentAngleDoubleSupplier.getAsDouble(),
        wantedAngleDoubleSupplier.getAsDouble())));
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if ((Math.abs(wantedDistanceDoubleSupplier.getAsDouble()) < distanceDeadband) & (Math.abs(wantedAngleDoubleSupplier.getAsDouble()) < angleDeadband)) {
      return true;
    } else {
      return false;
    }
  }
}
