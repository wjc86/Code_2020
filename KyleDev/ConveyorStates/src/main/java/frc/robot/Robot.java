
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.util.Units;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public Joystick controller = new Joystick(1);
  public CANSparkMax upperMotor = new CANSparkMax(1, MotorType.kBrushless);
  
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);

    upperMotor.getEncoder().setVelocityConversionFactor(1.0/60.0/8.75*Units.inchesToMeters(2)*Math.PI);
  }

  
  @Override
  public void robotPeriodic() {
  }


  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
   
    System.out.println("Auto selected: " + m_autoSelected);
  }

  
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
     
        break;
      case kDefaultAuto:
      default:
      
        break;
    }
  }

  
  @Override
  public void teleopPeriodic() {
      if (controller.getRawButton(1)) {
          upperMotor.set(0.5);
      }
      else {
        upperMotor.set(0);
         
      }
    SmartDashboard.putNumber("speed", upperMotor.getEncoder().getVelocity());
  }

 
  @Override
  public void testPeriodic() {
  }
}
