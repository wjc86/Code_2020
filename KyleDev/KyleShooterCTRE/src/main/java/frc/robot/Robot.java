package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public Joystick XboxController = new Joystick(0);
  private TalonSRX Bottom = new TalonSRX(1);
  private TalonSRX Top = new TalonSRX(2);



  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  @Override
  public void teleopPeriodic() {
    /*
    double speedMultiplier = 1;
    if(X.getRawButtonPressed(1)) {
      if(speedMultiplier == 1) {
        speedMultiplier = 2;
      } else if(speedMultiplier == 2) {
        speedMultiplier = 1;
      }
    }
    */
    if(XboxController.getY() >= 0.1 || XboxController.getY() <= -0.1) {
      Top.set(ControlMode.PercentOutput, XboxController.getY());
      Bottom.set(ControlMode.PercentOutput, XboxController.getY());    
    }
    
  }

  @Override
  public void testPeriodic() {
  }
}
