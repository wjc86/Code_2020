/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ColorSensor;

public class RotateToColor extends CommandBase {
  private ColorSensor mColorSensor;
  private CANSparkMax mMotor;

  /**
   * Creates a new RotateToColor.
   */
  public RotateToColor(ColorSensor colorSensor, CANSparkMax motor) {
    mColorSensor = colorSensor;
    mMotor = motor;
    addRequirements(colorSensor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putString("Wanted Color", "UNKNOWN");
    while(mColorSensor.wantedColor == ColorSensor.color.UNKNOWN){
        mColorSensor.wantedColor = ColorSensor.toColor(SmartDashboard.getString("Wanted Color", "UNKNOWN")).getActualColor();
    }
    mColorSensor.detectedColor = ColorSensor.color.UNKNOWN;
    while(mColorSensor.wantedColor != mColorSensor.detectedColor){
      mColorSensor.detectedColor = mColorSensor.detectColor();
      mMotor.set(Constants.DEFAULT_MOTOR_SPEED);
    }
    mMotor.set(0);
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
