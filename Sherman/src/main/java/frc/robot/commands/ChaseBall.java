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
    m_DriveToSetpointPID = new DriveToSetpointPID(() -> m_VisionClient.getBallDistance(), () -> 0.0, .5, () -> (m_VisionClient.getBallAngle()*-1.0), () -> 0.0, 3.0);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(m_VisionClient.isBallTargetAvail()){
      m_DriveToSetpointPID.execute();
    } else if (m_VisionClient.getBallAngle() < 0) {
      m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 2.0*Math.PI));
    } else {      
      m_Drivetrain.drive(new ChassisSpeeds(0.0, 0.0, -2.0*Math.PI));
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return m_DriveToSetpointPID.isFinished();
  }
}
