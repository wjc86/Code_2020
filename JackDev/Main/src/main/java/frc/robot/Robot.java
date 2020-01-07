/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 
/* The big DS (double solenoid) has the claw up against the robot when off (retracted) and pushes the claw
in front of the robot when on (extended). The small piston has the claw closed when off, and openes it when on.
*/

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

 // solP is the piston that pivots the whole claw, solG opens and closes the arms.
public class Robot extends IterativeRobot {

  Joystick control = new Joystick(0);
  Claw claw = new Claw();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
  
  /* The first piston has one button to extend and one to release, same for the second one. These button
  numbers correspond to different buttons on the Xbox controller, and we'll find out which ones to use
  later. Our idea is to use l1 and l2 for one piston and r1 and r2 for the other one.
  */
  @Override
  public void teleopPeriodic() {
    if(control.getRawButton(1)) {
      claw.down();
    }
    if(control.getRawButton(2)) {
      claw.up();
    }
    if(control.getRawButton(3)) {
      claw.grab();
    }
    if(control.getRawButton(4)) {
      claw.shoot();
    }
    if(control.getRawButton(6)) {
      claw.prepare();
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
