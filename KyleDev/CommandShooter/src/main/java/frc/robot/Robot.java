/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }
  
  @Override
  public void testPeriodic() {
  }
}
/*
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
  private TalonFX Falcon = new TalonFX(1);
  private TalonSRX Talon = new TalonSRX(2);
  
  Joystick Joy = new Joystick(0);
  
  public int motor_id = 0;
  public int num_motors = 0;
  public boolean enable = false;

  TalonSRX talon;
  boolean myControllerType;
  
  public double throtpct2speed(double throtpct){
    return 5775.6*(throtpct) + 5.1504; 
  }

  public double speed2throt_pct(double speed){
    return 0.0002*(speed); 
  }

  @Override
  public void robotInit() {
    double shooterKP=0.01;
    double shooterKI=0.02;
    double shooterKD=0.0002;
    


    Falcon.config_kP(0, shooterKP);
    Falcon.config_kI(0, shooterKI);
    Falcon.config_kD(0, shooterKD);
  }
  
  @Override
  public void teleopPeriodic() {
    
    EnableThrottle();
      
    if(enable == true){
      if(motor_id == 0) {Falcon.set(ControlMode.PercentOutput, Joy.getRawAxis(3)/-2.0+1);} 
      else if (motor_id == 1) {Talon.set(ControlMode.PercentOutput, Joy.getRawAxis(3)/-2.0+1);}
    }
    SmartDashboard.putNumber("Current Motor: ", motor_id);
    SmartDashboard.putBoolean("CTRL ENABLE", enable);    
  }



  public void EnableThrottle(){
    if (Joy.getRawButtonPressed(2)){
      if(enable==true){
        enable=false;
      }
      else if(enable==false){
        enable=true;
      }
    }
  }
} */