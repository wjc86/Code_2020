package frc.robot;

public class Constants {
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

    public static final double ballChaseAngleP = 1.0;
    public static final double ballChaseDistanceP = 0.0;

    public static final int shifterIDOne = 0;
    public static final int shifterIDTwo = 1;
}
