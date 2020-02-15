package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain {
    private final DoubleSolenoid m_shifter = new DoubleSolenoid(Constants.shifterIDOne, Constants.shifterIDTwo);

    private final WPI_TalonFX m_leftMaster = new WPI_TalonFX(Constants.leftMasterID);
    private final WPI_TalonFX m_leftFollower = new WPI_TalonFX(Constants.leftFollowerID);
    private final WPI_TalonFX m_rightMaster = new WPI_TalonFX(Constants.rightMasterID);
    private final WPI_TalonFX m_rightFollower = new WPI_TalonFX(Constants.rightFollowerID);

    private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

    private final SimpleMotorFeedforward leftFeedforwardHigh
        = new SimpleMotorFeedforward(Constants.leftFF[1][0],
        Constants.leftFF[1][1], Constants.leftFF[1][2]);
    private final SimpleMotorFeedforward rightFeedforwardHigh
        = new SimpleMotorFeedforward(Constants.rightFF[1][0],
        Constants.rightFF[1][1], Constants.rightFF[1][2]);
    private final PIDController m_leftPIDControllerHigh = new PIDController(
        Constants.leftPID[1][0], Constants.leftPID[1][1], Constants.leftPID[1][2]);
    private final PIDController m_rightPIDControllerHigh = new PIDController(
        Constants.rightPID[1][0], Constants.rightPID[1][1], Constants.rightPID[1][2]);

    private final SimpleMotorFeedforward leftFeedforwardLow
        = new SimpleMotorFeedforward(Constants.leftFF[0][0],
        Constants.leftFF[0][1], Constants.leftFF[0][2]);
    private final SimpleMotorFeedforward rightFeedforwardLow
        = new SimpleMotorFeedforward(Constants.rightFF[0][0],
        Constants.rightFF[0][1], Constants.rightFF[0][2]);
    private final PIDController m_leftPIDControllerLow = new PIDController(
        Constants.leftPID[0][0], Constants.leftPID[0][1], Constants.leftPID[0][2]);
    private final PIDController m_rightPIDControllerLow = new PIDController(
        Constants.rightPID[0][0], Constants.rightPID[0][1], Constants.rightPID[0][2]);

    private final DifferentialDriveKinematics m_kinematics
        = new DifferentialDriveKinematics(Constants.kTrackWidth);
    private final DifferentialDriveOdometry m_odometry;

    /*
    1=in high gear
    0=not in high gear
    */
    private int inHighGear = 1;

    public Drivetrain() {
        m_gyro.reset();
        m_odometry = new DifferentialDriveOdometry(getAngle());
        m_rightMaster.setInverted(true);
    }

    public Rotation2d getAngle() {
       return Rotation2d.fromDegrees(-m_gyro.getAngle());
    }

    public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
        double leftOutput;
        double rightOutput;
        if(inHighGear == 1){
            leftOutput = m_leftPIDControllerHigh.calculate(m_leftMaster.getSelectedSensorVelocity()*Constants.velocityConversion, speeds.leftMetersPerSecond)
                + leftFeedforwardHigh.calculate(speeds.leftMetersPerSecond);
            rightOutput = m_rightPIDControllerHigh.calculate(m_rightMaster.getSelectedSensorVelocity()*Constants.velocityConversion, speeds.rightMetersPerSecond)
                + rightFeedforwardHigh.calculate(speeds.rightMetersPerSecond);
        } else {
            leftOutput = m_leftPIDControllerLow.calculate(m_leftMaster.getSelectedSensorVelocity()*Constants.velocityConversion, speeds.leftMetersPerSecond)
                + leftFeedforwardLow.calculate(speeds.leftMetersPerSecond);
            rightOutput = m_rightPIDControllerLow.calculate(m_rightMaster.getSelectedSensorVelocity()*Constants.velocityConversion, speeds.rightMetersPerSecond)
                + rightFeedforwardLow.calculate(speeds.rightMetersPerSecond);
        }
        m_leftMaster.setVoltage(leftOutput);
        m_rightMaster.setVoltage(rightOutput);
        // m_leftFollower.follow(m_leftMaster);
        // m_rightFollower.follow(m_rightMaster);
        SmartDashboard.putNumber("right output", rightOutput);
        SmartDashboard.putNumber("left output", leftOutput);
        SmartDashboard.putNumber("right speed", m_rightMaster.getSelectedSensorVelocity()*Constants.velocityConversion);
        SmartDashboard.putNumber("left speed", m_leftMaster.getSelectedSensorVelocity()*Constants.velocityConversion);
    }

    @SuppressWarnings("ParameterName")
    public void drive(double xSpeed, double rot){
        var wheelSpeeds = m_kinematics.toWheelSpeeds(
            new ChassisSpeeds(xSpeed, 0.0, rot));
        setSpeeds(wheelSpeeds);
        SmartDashboard.putNumber("Speed", xSpeed);
        SmartDashboard.putNumber("Rot", rot);
        SmartDashboard.putNumber("left wheel speed", wheelSpeeds.leftMetersPerSecond);
        SmartDashboard.putNumber("right wheel speed", wheelSpeeds.rightMetersPerSecond);
        updateOdometry();
        printOdometry();
    }

    @SuppressWarnings("ParameterName")
    public void drive(ChassisSpeeds m_chassisSpeeds){
        var wheelSpeeds = m_kinematics.toWheelSpeeds(m_chassisSpeeds);
        setSpeeds(wheelSpeeds);
        SmartDashboard.putNumber("left wheel speed", wheelSpeeds.leftMetersPerSecond);
        SmartDashboard.putNumber("right wheel speed", wheelSpeeds.rightMetersPerSecond);
        updateOdometry();
        printOdometry();
    }

    public void ballChase(double distance, double angle, Boolean seesBall) {
        double xSpeed;
        double rot;
        if(seesBall){
            xSpeed = distance*Constants.ballChaseDistanceP;
            rot = angle*Constants.ballChaseAngleP;
        } else {
            xSpeed = 0;
            rot = 2.0 * Math.PI;
        }
        var wheelSpeeds = m_kinematics.toWheelSpeeds(
            new ChassisSpeeds(xSpeed, 0.0, rot));
        setSpeeds(wheelSpeeds);
        updateOdometry();
        printOdometry();
    }

    public void updateOdometry() {
        m_odometry.update(getAngle(),
            m_leftMaster.getSelectedSensorPosition()*Constants.postitionConversion,
            m_rightMaster.getSelectedSensorPosition()*Constants.postitionConversion);
    }

    public void printOdometry() {
        SmartDashboard.putNumber("X Pos", m_odometry.getPoseMeters().getTranslation().getX());
        SmartDashboard.putNumber("Y Pos", m_odometry.getPoseMeters().getTranslation().getY());
        SmartDashboard.putNumber("Rot Pos", m_odometry.getPoseMeters().getRotation().getDegrees());
    }

    public void resetOdometry() {
        m_odometry.resetPosition(new Pose2d(), getAngle());
        m_rightMaster.setSelectedSensorPosition(0);
        m_leftMaster.setSelectedSensorPosition(0);
    }

    public void setPose(Pose2d setPose) {
        m_odometry.resetPosition(setPose, getAngle());
        m_rightMaster.setSelectedSensorPosition(0);
        m_leftMaster.setSelectedSensorPosition(0);
    }

    public void motorMonitoring() {
        SmartDashboard.putNumber("left draw", m_leftMaster.getSupplyCurrent());
        SmartDashboard.putNumber("left out", m_leftMaster.getStatorCurrent());
        SmartDashboard.putNumber("right draw", m_rightMaster.getSupplyCurrent());
        SmartDashboard.putNumber("right out" , m_rightMaster.getStatorCurrent());
    }

    public Pose2d getCurrentPose() {
        return m_odometry.getPoseMeters();
    }

    public int getInHighGear() {
        return inHighGear;
    }

    public void setInHighGear(int inHighGear) {
        this.inHighGear = inHighGear;
    }
    public void updateShifter() {
        if(inHighGear == 1){
            if(m_shifter.get() == DoubleSolenoid.Value.kReverse){
                m_shifter.set(DoubleSolenoid.Value.kForward);
            }
        } else{
            if(m_shifter.get() == DoubleSolenoid.Value.kForward){
                m_shifter.set(DoubleSolenoid.Value.kReverse);
            }
        }
    }

    /*
    case closedLoopArcade
        speed = controller.getSpeed
        rot = controller.getRot
        wheelSpeeds = kinematics.toWheelSpeeds(speed, rot)
        setSpeeds(wheelSpeeds)
    case ballChase
        double xSpeed;
        double rot;
        if(seesBall){
            xSpeed = distance*Constants.ballChaseDistanceP;
            rot = angle*Constants.ballChaseAngleP;
        } else {
            xSpeed = 0;
            rot = 2.0 * Math.PI;
        }
        var wheelSpeeds = m_kinematics.toWheelSpeeds(
            new ChassisSpeeds(xSpeed, 0.0, rot));
        setSpeeds(wheelSpeeds);
    case pathFollow
        getT
        setSpeeds(getTrajectoryWheelSpeeds)
    */
}