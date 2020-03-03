package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.*;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final Intake m_Intake = Intake.getInstance();
  private final Joystick Joy = new Joystick(1);

  private final JoystickButton extendIntakeButton = new JoystickButton(Joy, 1);
  private final JoystickButton reverseIntakeButton = new JoystickButton(Joy, 2);
  private final JoystickButton retractIntakeButton = new JoystickButton(Joy, 3);

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    extendIntakeButton.whenPressed(new extendIntake());
    reverseIntakeButton.whenPressed(new reverseIntake());
    retractIntakeButton.whenPressed(new retractIntake());
  }
}