/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  TalonFX flywheel = new TalonFX(20);

  @Override
  public void robotInit() {
    flywheel.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 20);
    SmartDashboard.putNumber("P", 0);
    SmartDashboard.putNumber("I", 0);
    SmartDashboard.putNumber("D", 0);
    SmartDashboard.putNumber("Wanted RPM", 0);
    SmartDashboard.putNumber("Current RPM", 0);
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
    updatePID();
    SmartDashboard.putNumber("Current RPM", flywheel.getSelectedSensorVelocity());
    flywheel.set(ControlMode.Velocity, SmartDashboard.getNumber("Wanted RPM", 0));
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  public void updatePID() {
    // flywheel.config_kP(0, SmartDashboard.getNumber("P", 0));
    flywheel.config_kP(0, SmartDashboard.getNumber("P", 0));
    flywheel.config_kI(0, SmartDashboard.getNumber("I", 0));
    flywheel.config_kD(0, SmartDashboard.getNumber("D", 0));
  }

}
