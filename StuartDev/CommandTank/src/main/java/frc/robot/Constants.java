/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double kHighMaxSpeed = 4;
    public static final double kLowMaxSpeed = 0;
    public static final double kMaxAngularSpeed = 2.0 * Math.PI;
    public static final double kSpeedMultiplier = 8;
    public static final double kRotationMultiplier = 4.0 * Math.PI;
    public static final double slowdownSpeed = .5;
    public static final double slowdownRot = .5;

    public static final double kTrackWidth = 0.6096;
    public static final double kWheelRadius = .1524;

    public static final int leftMasterID = 1;
    public static final int leftFollowerID = 11;
    public static final int rightMasterID = 2;
    public static final int rightFollowerID = 22;
    public static final int leftMasterPDPChannel = 14;
    public static final int rightMasterPDPChannel = 1;

    public static final double[][] leftPID = {{0,0,0}, {0,0,0}};
    public static final double[][] leftFF = {{0,0,0}, {0.142, 2.47, 0.205}};
    public static final double[][] rightPID = {{0,0,0}, {0,0,0}};
    public static final double[][] rightFF = {{0,0,0}, {0.142, 2.47, 0.205}};

    public static final double velocityConversion = (7.1631/10000.0)*0.3048;
    public static final double postitionConversion = (7.1631/100000.0)*0.3048;

    public static final double controllerDeadband = .1;

    public static final double driveToSetpointAngleP = 0;
    public static final double driveToSetpointDistanceP = 0;

    public static final int shifterIDOne = 0;
    public static final int shifterIDTwo = 1;
}
