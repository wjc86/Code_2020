package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
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
  
  @Override
  public void robotInit() {
    m_drive = new Drivetrain();
    m_controller = new Controller();
    m_batteryMonitor = new BatteryMonitoring();
    m_trajectoryFollower = new TrajectoryFolower();
    putDashboard();
    Rotation2d finalRot = new Rotation2d(0);
    Pose2d finalPose = new Pose2d(10, 0, finalRot);
    m_trajectoryFollower.generateTrajectory(m_drive.getCurrentPose(), 0.0, finalPose, 0.0, null, 10, 10, false);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    currentDriveState = SmartDashboard.getBoolean("drive mode", true);
    if(!currentDriveState){
      trajectoryMode();
      System.out.println("1");
    } else {
      m_drive.drive(m_controller.getSpeed(), m_controller.getRot());
    }
    m_batteryMonitor.overallMonitoring(m_drive);
    if(m_controller.resetOdometry()) {
      m_drive.resetOdometry();
    }
    lastDriveState = currentDriveState;
  }

  public void trajectoryMode(){
    // if(lastDriveState){
    //   Rotation2d finalRot = new Rotation2d(SmartDashboard.getNumber("final rot", 0) * (Math.PI/180.0));
    //   Pose2d finalPose = new Pose2d(SmartDashboard.getNumber("final x", 0), SmartDashboard.getNumber("final y", 0), finalRot);
    //   m_trajectoryFollower.generateTrajectory(m_drive.getCurrentPose(), 0.0, finalPose, SmartDashboard.getNumber("final speed", 0), new ArrayList<Translation2d>(), 10.0, 10.0, SmartDashboard.getBoolean("final reversed",false));
    //   SmartDashboard.putBoolean("entered 3", true);
    // }
    m_drive.drive(m_trajectoryFollower.calculateCurrentTrajectory(m_drive.getCurrentPose()));
    System.out.println("2");
  }

  public void putDashboard() {
    SmartDashboard.putNumber("final rot", 0);
    SmartDashboard.putNumber("final x", 0);
    SmartDashboard.putNumber("final y", 0);
    SmartDashboard.putNumber("final speed", 0);
    SmartDashboard.putBoolean("final reversed", false);
    SmartDashboard.putBoolean("drive mode", true);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
