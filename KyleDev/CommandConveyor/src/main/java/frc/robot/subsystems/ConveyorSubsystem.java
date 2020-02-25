/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {
  private CANSparkMax conveyorMotor = new CANSparkMax(1, MotorType.kBrushless);
  private DigitalInput topSensor = new DigitalInput(2);
  private DigitalInput bottomSensor = new DigitalInput(0);
  private CANPIDController conveyorPID = conveyorMotor.getPIDController();
  private static ConveyorSubsystem instance = new ConveyorSubsystem();
  /**
   * Creates a new ConveyorSubsystem.
   */
  public ConveyorSubsystem() {
    conveyorMotor.restoreFactoryDefaults();
    conveyorPID.setP(0.01);
    conveyorPID.setI(0);
    conveyorPID.setD(0);
    conveyorMotor.getEncoder().setPosition(0.0);
    conveyorMotor.getEncoder().setPositionConversionFactor(1.0);
  }
  public static ConveyorSubsystem getInstance(){
    return instance;
  }

  public void move(double speed) { 
    conveyorMotor.set(speed);
  }

  public boolean topLine() {
    return topSensor.get();
  }

  public boolean bottomLine() {
    return bottomSensor.get();
    
  }

  public void putToDashboard() {
    SmartDashboard.putBoolean("Top", topLine());
    SmartDashboard.putBoolean("Bottom", bottomLine());
    SmartDashboard.putNumber("Pos", getPosititon());

  }

  public double getPosititon() {
    return conveyorMotor.getEncoder().getPosition();
  }

  public void advPosititon(double pos) {
    conveyorMotor.getEncoder().setPositionConversionFactor(1.0);
    conveyorPID.setP(0.01);
    conveyorPID.setI(0);
    conveyorPID.setD(0);
    // conveyorPID.setReference(pos/7.0037 + getPosititon(), ControlType.kPosition);
    conveyorPID.setReference(1.0, ControlType.kPosition);
    System.out.println("Reference set" + pos/7.0037 + getPosititon());
  }

  public void setPosition(double pos){
    conveyorPID.setReference(pos, ControlType.kPosition);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    putToDashboard();
  }
}
