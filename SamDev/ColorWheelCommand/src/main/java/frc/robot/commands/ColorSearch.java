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
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.subsystems.ColorSensor;

public class ColorSearch extends CommandBase {
  private ColorSensor mColorSensor;
  private CANSparkMax mMotor;
  private Joystick mJoystick;
  private state mState;
  private ColorSensor.color mDetectedColor = ColorSensor.color.UNKNOWN;
  private ColorSensor.color mWantedColor = ColorSensor.color.UNKNOWN;
  private int mCounter = 0;
  
  public ColorSearch(ColorSensor colorSensor, CANSparkMax motor, Joystick joystick) {
    mColorSensor = colorSensor;
    mMotor = motor;
    mJoystick = joystick;

    addRequirements(colorSensor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  public void setState(){
    if(mJoystick.getRawButton(3)){
      mState = state.SPIN_TO_COLOR;
    } else if(mJoystick.getRawButton(4)){
      mState = state.SPIN_NUMBER_OF_TIMES;
    } else{
      mState = state.NEUTRAL;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mDetectedColor = mColorSensor.detectColor();
    setState();
    switch(mState){
      case NEUTRAL:
        break;
      case SPIN_NUMBER_OF_TIMES:
        mMotor.set(Constants.DEFAULT_MOTOR_SPEED);
        if(mDetectedColor == mWantedColor){
          mState = state.NEUTRAL;
          mMotor.set(0);
          SmartDashboard.putString("FOUND COLOR", mDetectedColor.toString());
        }
        break;
      case SPIN_TO_COLOR:
        mMotor.set(Constants.DEFAULT_MOTOR_SPEED);
        if(mDetectedColor == mWantedColor && mCounter >= 7 && mColorSensor.lastColor != mDetectedColor){
          mState = state.NEUTRAL;
          mMotor.set(0);
          SmartDashboard.putString("FOUND COLOR", mDetectedColor.toString());
          mCounter = 0;
        } else if(mDetectedColor == mWantedColor && mColorSensor.lastColor != mDetectedColor){
          mCounter++;
        }
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mMotor.set(0);
    mState = state.NEUTRAL;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(mMotor.get() != 0 && mState != state.NEUTRAL){
      return false;
    } else{
      return true;
    }
  }

  public enum state{
    NEUTRAL,
    SPIN_NUMBER_OF_TIMES,
    SPIN_TO_COLOR
  }
}
