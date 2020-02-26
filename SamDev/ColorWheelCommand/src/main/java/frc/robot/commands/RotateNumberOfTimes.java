/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ColorSensor;

public class RotateNumberOfTimes extends CommandBase {
  private ColorSensor mColorSensor;
  private CANSparkMax mMotor;
  private Joystick mJoystick;

  /**
   * Creates a new RotateNumberOfTimes.
   */
  public RotateNumberOfTimes(ColorSensor colorSensor, CANSparkMax motor, Joystick joystick) {
    mColorSensor = colorSensor;
    mMotor = motor;
    mJoystick = joystick;
    addRequirements(colorSensor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mColorSensor.wantedColor = mColorSensor.detectColor();
    while(mColorSensor.wantedColor == ColorSensor.color.UNKNOWN){
      mColorSensor.wantedColor = mColorSensor.detectColor();
    }
    SmartDashboard.putString("Position Wanted Color", ColorSensor.color.RED.toString());
    int counter = 0;
    ColorSensor.color firstColor = ColorSensor.color.RED;
    ColorSensor.color lastColor = firstColor;
    ColorSensor.color currentColor;
    while(counter < 7){
      mMotor.set(Constants.DEFAULT_MOTOR_SPEED);
      currentColor = mColorSensor.detectColor();
      /*if((kColors.indexOf(lastColor)+1 == kColors.indexOf(currentColor)) || (kColors.indexOf(lastColor) == 3 && kColors.indexOf(currentColor) == 0)){
          colors.add(currentColor);
          if(currentColor == wantedColor){
            counter++;
          }
      }*/
      if(currentColor == ColorSensor.color.RED && lastColor != ColorSensor.color.RED){
        counter++;
      }
      lastColor = currentColor;
      SmartDashboard.putNumber("Counter", counter);
      if(mJoystick.getRawButton(1)){
        break;
      }
    }
    //colorWheel.set(0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
