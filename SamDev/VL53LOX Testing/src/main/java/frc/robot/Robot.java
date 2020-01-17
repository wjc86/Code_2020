/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Arrays;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
  }

  /**
   * This function is run once each time the robot enters autonomous mode.
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
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during teleoperated mode.
   */
  @Override
  public void teleopPeriodic() {
    int[] spam = new int[] { 0x00, 0x01, 0x03, 0x02 };
		System.out.println(Arrays.stream(spam)
						.boxed()
						.map(b -> String.format("0x%02X", b))
						.collect(Collectors.joining(" ")));
		try {
 			VL53L0X_v1 vl53l0x = new VL53L0X_v1();
       System.out.println(String.format("Revision %d, device ID %d ", vl53l0x.getRevision(), vl53l0x.getDeviceID()));
       SmartDashboard.putString("Description", String.format("Revision %d, device ID %d ", vl53l0x.getRevision(), vl53l0x.getDeviceID()));
 			System.out.println();
 			vl53l0x.startRanging(VL53L0X_v1.VL53L0X_BETTER_ACCURACY_MODE);
 			/*int howMany = 1_000;
 			for (int i=0; i<howMany; i++) {
 				int distance = vl53l0x.getDistance();
				 System.out.println(String.format("Distance:%d mm", distance));
				 SmartDashboard.putNumber("Distance", distance);
 				if (i < (howMany - 2)) {
 					try {
 						Thread.sleep(10); // 10ms
 					} catch (InterruptedException ie) {
 					}
 				}
 			}*/
 			System.out.println("Done.");
 		//} catch (UnsupportedBusNumberException e) {
 		//	e.printStackTrace();
 		} catch (Exception ex) {
       ex.printStackTrace();
       SmartDashboard.putNumber("Failed", 4);
     }

     SmartDashboard.putNumber("Test", 7);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
