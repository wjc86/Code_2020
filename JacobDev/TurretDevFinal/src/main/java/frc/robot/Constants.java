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
    
    //OUTPUT CONSTANTS
    public static final double TURRET_MAX_ROTATION_OUTPUT = 0.1;
    public static final double TURRET_MAX_BOOSTER_OUTPUT = 0.1; //1.0
    public static final double TURRET_MAX_FLYWHEEL_OUTPUT = 0.08; //0,8

    //RATIOS
    public static final double TURRET_TO_MOTOR_GEAR_RATIO = (double)120/16;
    public static final double MOTOR_TO_TURRET_GEAR_RATIO = (double)16/120;

    //ENCODDER CONSTANTS
    public static final double ENCODER_TICKS_PER_REVOLUTION = 4096.0;
    public static final double REVOLUTIONS_PER_ENCODER_TICK = 1.0 / 4096.0;
}
