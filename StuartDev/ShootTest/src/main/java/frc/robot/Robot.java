/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  private TalonFX flywheel = new TalonFX(5);
  private VictorSP booster = new VictorSP(0);
  private TalonSRX master = new TalonSRX(3);
  private TalonSRX follower = new TalonSRX(33);
  private Joystick stick = new Joystick(0);

  @Override
  public void robotInit() {
    SmartDashboard.putNumber("Conveyor Percent", 0);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    if (stick.getRawButton(1)){
      flywheel.set(ControlMode.PercentOutput, 1);
      booster.set(1);
    } else {
      flywheel.set(ControlMode.PercentOutput, 0);
      booster.set(0);
    }
    if (stick.getRawButton(2)) {
      master.set(ControlMode.PercentOutput, stick.getY());
    } else {
      master.set(ControlMode.PercentOutput, 0);
    }
    SmartDashboard.putNumber("Conveyor Percent", stick.getY());
    follower.follow(master);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
