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
    private static VisionClient instance = new VisionClient();
    double[] defaultArray = { 0.0, 0.0, 0.0, 0.0, 0.0};
    NetworkTableInstance tableInstance;
    NetworkTable ballTable;
    NetworkTableEntry ballTableEntry;
    NetworkTableEntry returnData;

    public VisionClient() {
        tableInstance = NetworkTableInstance.getDefault();
        ballTable = tableInstance.getTable("ballVision");
        ballTableEntry = ballTable.getEntry("target_data");
        returnData = ballTable.getEntry("return_data");
    }

    public static VisionClient getInstance() {
        return instance;
    }

    public double[] getVisionArray() {
        return ballTableEntry.getDoubleArray(defaultArray);
    }

    public double getTimestamp() {
        return ballTableEntry.getDoubleArray(defaultArray)[0];
    }

    public Boolean isBallTargetAvail() {
        if (ballTableEntry.getDoubleArray(defaultArray)[1] == 1.0) {
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

    public void pushToTable(double timestamp) {
        returnData.setNumber(timestamp);
    }
}
