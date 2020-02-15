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
        double[] defaultArray = {0.0,0.0,0.0,5.0,5.0};
        NetworkTableInstance instance;
        NetworkTable ballTable;
        NetworkTableEntry ballTableEntry;

        public VisionClient() {
            instance = NetworkTableInstance.getDefault();
            ballTable  = instance.getTable("ballVision");
            ballTableEntry = ballTable.getEntry("target_data");
        }

        public double getTimestamp() {
            return ballTableEntry.getDoubleArray(defaultArray)[0];
        }

        public Boolean isBallTargetAvail() {
            if(ballTableEntry.getDoubleArray(defaultArray)[1] == 1.0) {
                return true;
            } else {
                return false;
            }
        }

        public double getBallDistance() {
            return ballTableEntry.getDoubleArray(defaultArray)[3];
        }

        public double getBallAngle() {
            return ballTableEntry.getDoubleArray(defaultArray)[4];
        }
}
