/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;

/**
 * Add your docs here.
 */
public class TrajectoryFolower {
    Trajectory mTrajectory;
    RamseteController mRamseteController = new RamseteController();
    double startTime = 0.0;

    public void generateTrajectory(Pose2d currentPose, double currentSpeed, 
                                   Pose2d finalPose, double finalSpeed,
                                   ArrayList<Translation2d> waypoints,
                                   double maxVelocity, double maxAcceleration, boolean reversed){
        TrajectoryConfig config = new TrajectoryConfig(maxVelocity, maxAcceleration);
        config.setStartVelocity(currentSpeed);
        config.setEndVelocity(finalSpeed);
        config.setReversed(reversed);
        waypoints.add(currentPose.getTranslation());
        waypoints.add(finalPose.getTranslation());
        mTrajectory = TrajectoryGenerator.generateTrajectory(currentPose, waypoints, finalPose, config);
        startTime = Timer.getFPGATimestamp();
        System.out.println("4");
    }

    public ChassisSpeeds calculateTrajectory(Pose2d currentPose){
        double passedTime = Timer.getFPGATimestamp()-startTime;
        Trajectory.State currentGoal = mTrajectory.sample(passedTime);
        System.out.println("5");
        return mRamseteController.calculate(currentPose, currentGoal);
    }
}
