/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Flywheel extends SubsystemBase {
  /**
   * Creates a new Flywheel.
   */
  TalonFX flywheelMotor = new TalonFX(Constants.k_FLYWHEEL_PORT);

  public Flywheel() {
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void startFlywheel(double speed) {
    speed = Constants.k_FLYWHEEL_SPEED;
    flywheelMotor.set(TalonFXControlMode.PercentOutput, speed);
  }

  public void stopFlywheel() {
    flywheelMotor.set(TalonFXControlMode.PercentOutput, 0);
  }
}
