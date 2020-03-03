package frc.robot;

public final class Constants {

    /* Climber */

    public static final int winchMotorID = 1;
    public static final double winchP = 0;
    public static final double winchI = 0;
    public static final double winchD = 0;

    public static final double climbHeight = 12;
    
    public static final int resetButtonPort = 1;
    public static final int climbButtonPort = 2;
    public static final int manualButtonPort = 3;

    /* Drivetrain */

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

    public static final double driveToSetpointAngleP = 0;
    public static final double driveToSetpointDistanceP = 0;

    public static final int shifterIDOne = 0;
    public static final int shifterIDTwo = 1;

    /* Turret Constants */

    public static final double TURRET_PERCENT_OUTPUT = 1.0; //As a decimal
    public static final double TURRET_MAX_OUTPUT = 0.1;
    public static final double TURRET_TO_MOTOR_GEAR_RATIO = (double)120/16;
    public static final double MOTOR_TO_TURRET_GEAR_RATIO = (double)16/120;

    public static final double ENCODER_TICKS_PER_REVOLUTION = 4096.0;
    public static final double REVOLUTIONS_PER_ENCODER_TICK = 1.0 / 4096.0;

    /* Conveyor Constants */
    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static int maxConveyorVelocity = 0;
    public static int maxConveyorAccel = 0;


    /* Intake Constants */

    public static int intakeMotorCANID = 1;
    public static int intakeLineBreakID = 0;
    public static int intakePiston1Forward = 1;
    public static int intakePiston1Reverse = 2;
    public static int intakePiston2Forward = 3;
    public static int intakePiston2Reverse = 4;
    public static double intakeMaxPCT = 0.4;

    /* Shooter Constants */

    public static double shooterSpeedUpPCT = 0.6;
    public static double shooterFlywheelPCT = 0.7;
    public static int FlywheelCANID = 1;
    public static int ShooterTalonCANID = 2;
    public static int ShooterLinebreakID = 0;

    // Shooter Flywheel PID Values
    public static double flywheelP = 0.01;
    public static double flywheelI = 0.02;
    public static double flywheelD = 0.0002;
}
