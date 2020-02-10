/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Joystick;
import java.lang.Math;

public class Robot extends TimedRobot {
	private static Robot myRobot = new Robot(); 
  private elevator mElevator = elevator.getInstance();
  public Joystick Joy = new Joystick(1);

	double startTime = 0; 
	double endTime = 0; 


	public static Robot getInstance(){
		return myRobot; 
	}

	// @Override
	// public void robotInit() {
	// 	mElevator.registerLoop();
	// }

	public void autonomousPeriodic() {
		driverControl();
	}

	@Override
	public void teleopPeriodic() {
		try { 
			driverControl();
		} catch (Exception e) {

		}
	}

	public void driverControl() {
		if (wantedElevatorHeight() != -1 || elevator.wantedHeight != wantedElevatorHeight()) {
			mElevator.setWantedElevatorHeight(elevator.wantedHeight);
		 	mElevator.setWantedState(elevator.state.POSITION_CONTROL);
		} else if (wantedElevatorVelocity() != 0) {
			mElevator.setWantedState(elevator.state.MANUAL_CONTROL);
			mElevator.setWantedElevatorHeight(mElevator.getHeight() + wantedElevatorVelocity());
		} else {
		  	mElevator.setWantedState(elevator.state.HOLDING);
		}
  }
  public double wantedElevatorVelocityMain() {
    if(Math.abs(Joy.getRawAxis(1)) > 0.045) {
      return -Joy.getRawAxis(1) * 450;
    } else {
      return 0;
    }
  }

  public double wantedElevatorHeightMain() {
    if(Joy.getPOV() == 0) {
      return 27.75;
    } else if (Joy.getPOV() == 90) {
      return 15.25;
    } else if (Joy.getPOV() == 180) {
      return 0;
    } else if (Joy.getPOV() == 270) {
      return 8.0;
    } else {
      return -1;
    }
  }

  public double wantedElevatorOpenLoop() {
    if(Math.abs(Joy.getRawAxis(1)) > 0.045) {
      return -Joy.getRawAxis(1);
    } else {
      return 0;
    }
  }

  public double wantedElevatorVelocity() {
    SmartDashboard.putNumber("Joystick Velocity", wantedElevatorVelocityMain());
    if(wantedElevatorVelocityMain() != 0) {
      return wantedElevatorVelocityMain();
    } else {
      return 0;
    }
  }

  public double wantedElevatorHeight() {
    SmartDashboard.putNumber("Wanted Elevator Joystick", wantedElevatorHeightMain());
    if(wantedElevatorHeightMain() != -1) {
      return wantedElevatorHeightMain();
    } else {
      return -1;
    }
  }







}