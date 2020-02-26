package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.*;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final Intake m_Intake = Intake.getInstance();
  private final Joystick Joy = new Joystick(1);
  private final JoystickButton DeployIntakeButton = new JoystickButton(Joy, 1);
  private final JoystickButton RetractIntakeButton = new JoystickButton(Joy, 2);
  private final JoystickButton RunIntakeButton = new JoystickButton(Joy, 3);
  
  public RobotContainer() {
    configureButtonBindings();
    m_Intake.setDefaultCommand(new SensorIntake()); 
  }

  private void configureButtonBindings() {
    RunIntakeButton.whenPressed(new RunIntake());
    RunIntakeButton.whenReleased(new StopIntake());
    DeployIntakeButton.whenPressed(new DeployIntake());
    RetractIntakeButton.whenPressed(new RetractIntake());
  } 
}