package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;

public class RobotContainer {
  private Joystick Joy = new Joystick(0);

  private JoystickButton speedButton = new JoystickButton(Joy, 1);
  private JoystickButton slowButton = new JoystickButton(Joy, 2);

  public RobotContainer() {
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    speedButton.whenHeld(new ShooterSpinUp());
    slowButton.whenHeld(new ShooterSpinDown());
  }
}
