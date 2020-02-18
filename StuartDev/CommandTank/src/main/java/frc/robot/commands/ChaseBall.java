/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.VisionClient;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;


public class ChaseBall extends CommandBase {
  private VisionClient m_VisionClient = VisionClient.getInstance();
  private DriveToSetpointPID m_DriveToSetpointPID;
  private Drivetrain m_Drivetrain = Drivetrain.getInstance();

  public ChaseBall() {
    System.out.println("3");
    m_DriveToSetpointPID = new DriveToSetpointPID(() -> m_VisionClient.getBallDistance(), () -> 0.0, .5, () -> (m_VisionClient.getBallAngle()*-1.0), () -> 0.0, 3.0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // m_DriveToSetpointPID.execute();
    if(m_VisionClient.isBallTargetAvail()){
      m_DriveToSetpointPID.execute();
    } else if (m_VisionClient.getBallAngle() < 0) {
      m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 2.0*Math.PI));
    } else {      
      m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, -2.0*Math.PI));
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_DriveToSetpointPID.isFinished();
  }
}
