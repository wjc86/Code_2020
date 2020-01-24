/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.Constants;


/**
 * Add your docs here.
 */
public class Drivetrain {
    private final WPI_TalonFX m_leftMaster = new WPI_TalonFX(Constants.leftMasterID);
    private final WPI_TalonFX m_leftFollower = new WPI_TalonFX(Constants.leftFollowerID);
    private final WPI_TalonFX m_rightMaster = new WPI_TalonFX(Constants.rightMasterID);
    private final WPI_TalonFX m_rightFollower = new WPI_TalonFX(Constants.rightFollowerID);

    private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(ks, kv)

}
