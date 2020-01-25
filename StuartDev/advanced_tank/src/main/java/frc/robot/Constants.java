package frc.robot;

public class Constants {
    public static final double kHighMaxSpeed = 12.0;
    public static final double kLowMaxSpeed = 0;
    public static final double kMaxAngularSpeed = 2.0 * Math.PI;

    public static final double kTrackWidth = 2.0;
    public static final double kWheelRadius = 0.5;

    public static final int leftMasterID = 1;
    public static final int leftFollowerID = 11;
    public static final int rightMasterID = 2;
    public static final int rightFollowerID = 22;
    public static final int leftMasterPDPChannel = 14;
    public static final int rightMasterPDPChannel = 1;

    public static final double[] leftPID = {0,0,0};
    public static final double[] leftFF = {0.156, 0.747, 0.068};
    public static final double[] rightPID = {0,0,0};
    public static final double[] rightFF = {0.156, 0.747, 0.068};

    public static final double velocityConversion = 7.1631/100000.0;
    public static final double postitionConversion = 7.1631/10000.0;

    public static final double controllerDeadband = 0.1;
    public static final double slowdownSpeed = 1;
    public static final double slowdownRot = 1;
}
