/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.*;

/**
 * Add your docs here.
 */
public class VisionClient {
        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        NetworkTable ballTable = instance.getTable("ballTable");
        NetworkTableEntry ballDistanceEntry = ballTable.getEntry("ballDistance");
        NetworkTableEntry ballAngleEntry = ballTable.getEntry("ballAngle");

        public double getBallDistance() {
            return ballDistanceEntry.getDouble(-1);
        }

        public double getBallAngle() {
            return ballAngleEntry.getDouble(-1);
        }
}
