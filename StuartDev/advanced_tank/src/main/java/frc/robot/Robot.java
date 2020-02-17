package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  private LoopManager m_LoopManager = LoopManager.getInstance();
  private Drivetrain m_Drivetrain = Drivetrain.getInstance();
  private Controller m_Controller = Controller.getInstance();
  private BatteryMonitoring m_BatteryMonitoring = BatteryMonitoring.getInstance();
  private TrajectoryFollower m_TrajectoryFollower = TrajectoryFollower.getInstance();
  private VisionClient m_VisionClient = VisionClient.getInstance();
  public static double startTrajectoryTime = 0;
  
  @Override
  public void robotInit() {
    // m_Loo
    m_Drivetrain.resetOdometry();
    putDashboardInit();
  }

  @Override
  public void robotPeriodic() {
    // TODO Auto-generated method stub
    super.robotPeriodic();
    putDashboard();
  }

  @Override
  public void autonomousInit() {
    m_TrajectoryFollower.generateTrajectory();
    m_Drivetrain.setPose(new Pose2d(10, -10, new Rotation2d(Math.PI/2.0)));
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
    double[] visionData = m_VisionClient.getVisionArray();
    if(m_Controller.ballChaseMode()){m_Drivetrain.ballChase(visionData[3], visionData[4], visionData[1]);
    } else {
      m_Drivetrain.drive(m_Controller.getSpeed(), m_Controller.getRot());
    }
    m_BatteryMonitoring.overallMonitoring(m_Drivetrain);
    if(m_Controller.resetOdometry()) {
      m_Drivetrain.resetOdometry();
    }
    m_VisionClient.pushToTable(visionData[0]);
  }

  public void trajectoryMode(){
    m_Drivetrain.drive(m_TrajectoryFollower.calculateCurrentTrajectory(m_Drivetrain.getCurrentPose()));
  }

  public void putDashboard() {
    SmartDashboard.putNumber("Timestamp", m_VisionClient.getTimestamp());
    SmartDashboard.putBoolean("isBallTargetAvail", m_VisionClient.isBallTargetAvail());
    SmartDashboard.putNumber("Ball Distance", m_VisionClient.getBallDistance());
    SmartDashboard.putNumber("Ball Angle", m_VisionClient.getBallAngle());
  }

  public void putDashboardInit() {
    SmartDashboard.putNumber("Timestamp", m_VisionClient.getTimestamp());
    SmartDashboard.putBoolean("isBallTargetAvail", m_VisionClient.isBallTargetAvail());
    SmartDashboard.putNumber("Ball Distance", m_VisionClient.getBallDistance());
    SmartDashboard.putNumber("Ball Angle", m_VisionClient.getBallAngle());
    SmartDashboard.putNumber("Angle P", 0);
    SmartDashboard.putNumber("Angle I", 0);
    SmartDashboard.putNumber("Angle D", 0);
    SmartDashboard.putNumber("Distance P", 0);
    SmartDashboard.putNumber("Distance I", 0);
    SmartDashboard.putNumber("Distance D", 0);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
