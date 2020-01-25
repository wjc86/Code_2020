package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
  private Drivetrain m_drive;
  private Controller m_controller;
  private BatteryMonitoring m_batteryMonitor;
  
  @Override
  public void robotInit() {
    m_drive = new Drivetrain();
    m_controller = new Controller();
    m_batteryMonitor = new BatteryMonitoring();
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
    m_drive.drive(m_controller.getSpeed(), m_controller.getRot());
    m_batteryMonitor.overallMonitoring(m_drive);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
