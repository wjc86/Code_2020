/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Filesystem;
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
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

/**
 * Add your docs here.
 */
public class TrajectoryFollower {
    private static TrajectoryFollower instance = new TrajectoryFollower();
    Trajectory mTrajectory;
    RamseteController mRamseteController = new RamseteController();
    String trajectoryJSON = "paths/output/Unnamed.wpilib.json";

    public static TrajectoryFollower getInstance() {
        return instance;
    }

    public void generateTrajectory() {
        Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
        try {
            mTrajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ChassisSpeeds calculateCurrentTrajectory(Pose2d currentPose){
        double passedTime = Timer.getFPGATimestamp()-Robot.startTrajectoryTime;
        System.out.println(Robot.startTrajectoryTime + " | " + Timer.getFPGATimestamp() + " | " + passedTime);
        Trajectory.State currentGoal = mTrajectory.sample(passedTime);
        return mRamseteController.calculate(currentPose, currentGoal);
    }
}
