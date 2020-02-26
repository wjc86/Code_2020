/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;;


public class Intake extends SubsystemBase {
  private VictorSPX intakeMotor = new VictorSPX(1);
  private DigitalInput intakeSensor = new DigitalInput(1);
  private DoubleSolenoid intakePiston1 = new DoubleSolenoid(1, 2);
  private DoubleSolenoid intakePiston2 = new DoubleSolenoid(3, 4);

  public static Intake instance = new Intake();

  public static Intake getInstance() {
    return instance;
  }

  public Intake() {}
  
  public void Linebreak() {
    SmartDashboard.putBoolean("Line Touching", intakeSensor.get());
  }
  public void StartIntake(double speedPCT) {
    intakeMotor.set(ControlMode.PercentOutput, speedPCT);

      }
  public void PistonDeploy() {
    intakePiston1.set(Value.kForward);
    intakePiston2.set(Value.kForward);
  }
  public void PistonRetract() {
    intakePiston1.set(Value.kReverse);
    intakePiston2.set(Value.kReverse);
  }
  

  @Override
  public void periodic() {
   
  }
}
