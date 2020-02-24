/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.constants;

/**
 * Add your docs here.
 */
public class DrivetrainConstants {
    //IDs for Drivetrain motors and solenoids
    public static final int LEFT_MASTER_ID = 1;
    public static final int LEFT_FOLLOWER_ID = 11;
    public static final int RIGHT_MASTER_ID = 2;
    public static final int RIGHT_FOLLOWER_ID = 22;
    public static final int SHIFTER_ID_1 = 0;
    public static final int SHIFTER_ID_2 = 1;

    //Tuning values for Drivetrain
    public static final double[][] LEFT_PID = {{0,0,0}, {0,0,0}};
    public static final double[][] LEFT_FF = {{0,0,0}, {0.142, 2.47, 0.205}};
    public static final double[][] RIGHT_PID = {{0,0,0}, {0,0,0}};
    public static final double[][] RIGHT_FF = {{0,0,0}, {0.142, 2.47, 0.205}};

    //Encoder Conversion Ratios
    public static final double VELOCITY_RATIO = (7.1631/10000.0)*0.3048;
    public static final double POSITION_RATIO = (7.1631/100000.0)*0.3048;

    //Drivetrain width for kinematics and odometry
    public static final double TRACK_WIDTH = 0.6096;

    //Speed limits
    public static final double MAX_LINEAR_SPEED = 4;
    public static final double MAX_ROT_SPEED = 2.0 * Math.PI;
}
