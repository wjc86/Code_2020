package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private Drivetrain m_drive;
  private Controller m_controller;
  private BatteryMonitoring m_batteryMonitor;
  private TrajectoryFolower m_trajectoryFollower;
  /*
  true = joystick/driver controller
  false = tajectory
  */
  private Boolean currentDriveState = true;
  private Boolean lastDriveState = true;
  private VisionClient m_VisionClient;
  public static double startTrajectoryTime = 0;
  
  @Override
  public void robotInit() {
    m_drive = new Drivetrain();
    m_controller = new Controller();
    m_batteryMonitor = new BatteryMonitoring();
    m_trajectoryFollower = new TrajectoryFolower();
    putDashboard();
    m_drive.resetOdometry();
    m_VisionClient = new VisionClient();
  }

  @Override
  public void autonomousInit() {
    m_trajectoryFollower.generateTrajectory();
    m_drive.setPose(new Pose2d(10, -10, new Rotation2d(Math.PI/2.0)));
    startTrajectoryTime = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    trajectoryMode();
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    if(m_controller.ballChaseMode()){
      m_drive.ballChase(m_VisionClient.getBallDistance(), m_VisionClient.getBallAngle(), true);
    } else {
      m_drive.drive(m_controller.getSpeed(), m_controller.getRot());
    }
    m_batteryMonitor.overallMonitoring(m_drive);
    if(m_controller.resetOdometry()) {
      m_drive.resetOdometry();
    }
  }

  public void trajectoryMode(){
    m_drive.drive(m_trajectoryFollower.calculateCurrentTrajectory(m_drive.getCurrentPose()));
  }

  public void putDashboard() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
