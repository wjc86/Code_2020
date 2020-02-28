package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ChaseBall;
import frc.robot.commands.Climb;
import frc.robot.constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private Compressor m_Compressor = new Compressor();
  private Controller m_Controller = Controller.getInstance();

  private Drivetrain m_Drivetrain = Drivetrain.getInstance();

  public RobotContainer() {
    configureButtonBindings();
    m_Compressor.start();
    m_Drivetrain.setDefaultCommand(new ArcadeDrive(() -> m_Controller.getArcadeDriveSpeed(), () -> m_Controller.getArcadeDriveRot()));
  }

  private void configureButtonBindings() {
    m_Controller.getBallChaseButton().whenHeld(new ChaseBall());
    m_Controller.getClimbButton().whenPressed(new Climb());
  }

  public Command getAutonomousCommand() throws IOException {
    Trajectory m_Trajectory;
    String trajectoryJSON = "paths/output/Unnamed.wpilib.json";
    Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
    m_Trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    RamseteCommand m_RamseteCommand = new RamseteCommand(m_Trajectory, () -> m_Drivetrain.getCurrentPose(), new RamseteController(), m_Drivetrain.getKinematics(), m_Drivetrain::setSpeedsDouble, m_Drivetrain);
    return m_RamseteCommand;
  }
}
