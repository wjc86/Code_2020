/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final CANSparkMax colorMotor = new CANSparkMax(4, MotorType.kBrushless);
  private final Joystick joy1 = new Joystick(1);

  // The robot's subsystems and commands are defined here...
  private final ColorSensor mSensor = new ColorSensor();

  private final RotateNumberOfTimes rotateNumber = new RotateNumberOfTimes(mSensor, colorMotor, joy1);
  private final RotateToColor rotateColor = new RotateToColor(mSensor, colorMotor);
  private final ColorSearch colorSearch = new ColorSearch(mSensor, colorMotor, joy1);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    /*CommandScheduler.getInstance().onCommandExecute(rotateColor -> SmartDashboard.putBoolean("rotateColor", true));
    CommandScheduler.getInstance().onCommandFinish(rotateColor -> SmartDashboard.putString("Detected", mSensor.detectedColor.toString()));
    CommandScheduler.getInstance().onCommandExecute(rotateNumber -> SmartDashboard.putBoolean("rotateNumber", true));*/
    CommandScheduler.getInstance().onCommandExecute(colorSearch -> SmartDashboard.putBoolean("hasExecuted", true));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    /*new JoystickButton(joy1, 3).whenPressed(rotateColor);
    new JoystickButton(joy1, 4).whenPressed(rotateNumber);*/
    //new JoystickButton(joy1, 1).whenHeld(colorSearch);
    new JoystickButton(joy1, 1).whenPressed(colorSearch);
    new JoystickButton(joy1, 7).cancelWhenActive(colorSearch);
  }
}
