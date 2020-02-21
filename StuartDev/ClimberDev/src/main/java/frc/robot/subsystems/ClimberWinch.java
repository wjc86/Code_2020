/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberWinch extends SubsystemBase {
  /**
   * Creates a new ClimberWinch.
   */
  private static ClimberWinch instance = new ClimberWinch();

  private Encoder winchEncoder = new Encoder(Constants.winchEncoderChannel1, Constants.winchEncoderChannel2);
  // private TalonSRX winchMotor = new TalonSRX(Constants.winchMotorID);
  private SparkMax winchMotor;

  // private PIDController winchPID = new PIDController(Constants.winchP, Constants.winchI, Constants.winchD);

  public ClimberWinch() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public static ClimberWinch getInstance() {
    return instance;
  }

  public void setClimberPostition(double pos) {
    winchMotor.set(ControlMode.Position, pos);
    // winchMotor.configSelectedFeedbackSensor(winchEncoder);
  }

  public void setManualClimb(double percent) {
    winchMotor.set(ControlMode.PercentOutput, percent);
  }

  public double getClimberPosition() {
    return winchEncoder.get();
  }
}