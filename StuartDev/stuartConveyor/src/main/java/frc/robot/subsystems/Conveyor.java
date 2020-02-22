/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Conveyor extends SubsystemBase {
  public static Conveyor instance = new Conveyor();
  public DigitalInput topSensor = new DigitalInput(Constants.topConveyorSensorPort);
  public DigitalInput bottomSensor = new DigitalInput(Constants.bottomConveyorSensorPort);
  public CANSparkMax conveyorMotor = new CANSparkMax(Constants.conveyorMotorID, MotorType.kBrushless);

  public Conveyor() {}

  public static Conveyor getInstance() {
    return instance;
  }

  @Override
  public void periodic() {
    getTopSensor();
    getBottomSensor();
    // This method will be called once per scheduler run
  }

  public Boolean getTopSensor() {
    System.out.println("Top: " + topSensor.get());
    SmartDashboard.putBoolean("Top", topSensor.get());
    return topSensor.get();
  }

  public Boolean getBottomSensor() {
    System.out.println("Bottom: " + bottomSensor.get());
    SmartDashboard.putBoolean("Bottom", bottomSensor.get());
    return bottomSensor.get();
  }

  public void driveMotor(double percent) {
    conveyorMotor.set(percent);
  }
}
