/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Constants {
    public static final double kHighMaxSpeed = 12.0;
    public static final double kLowMaxSpeed = 0;
    public static final double kMaxAngularSpeed = 2.0 * Math.PI;

    public static final double kTrackWidth = 2.0;
    public static final double kWheelRadius = 0.5;

    public static final int leftMasterID = 1;
    public static final int leftFollowerID = 11;
    public static final int rightMasterID = 2;
    public static final int rightFollowerID = 22;

    public static final double[] leftPID = {0,0,0};
    public static final double[] leftFF = {0,0,0};
    public static final double[] rightPID = {0,0,0};
    public static final double[] rightFF = {0,0,0};
}
