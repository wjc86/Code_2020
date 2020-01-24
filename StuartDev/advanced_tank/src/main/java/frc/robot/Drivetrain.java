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

    private final SpeedControllerGroup m_leftGroup
        = new SpeedControllerGroup(m_leftMaster, m_leftFollower);
    private final SpeedControllerGroup m_rightGroup
        = new SpeedControllerGroup(m_rightMaster, m_rightFollower);

    private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

    private final SimpleMotorFeedforward leftFeedforward
        = new SimpleMotorFeedforward(Constants.leftFF[0], Constants.leftFF[1], Constants.leftFF[2]);
    private final SimpleMotorFeedforward rightFeedforward
        = new SimpleMotorFeedforward(Constants.rightFF[0], Constants.rightFF[1], Constants.rightFF[2]);
    private final PIDController m_leftPIDController = new PIDController(
        Constants.leftPID[0], Constants.leftPID[1], Constants.leftPID[2]);
    private final PIDController m_rightPIDController = new PIDController(
        Constants.rightPID[0], Constants.rightPID[1], Constants.rightPID[2]);

    private final DifferentialDriveKinematics m_kinematics
        = new DifferentialDriveKinematics(Constants.kTrackWidth);
    private final DifferentialDriveOdometry m_odometry;

    public Drivetrain() {
        m_gyro.reset();
    }
}
