package frc.robot;

import edu.wpi.first.wpilibj.SPI;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        = new SimpleMotorFeedforward(Constants.leftFF[0],
        Constants.leftFF[1], Constants.leftFF[2]);
    private final SimpleMotorFeedforward rightFeedforward
        = new SimpleMotorFeedforward(Constants.rightFF[0],
        Constants.rightFF[1], Constants.rightFF[2]);
    private final PIDController m_leftPIDController = new PIDController(
        Constants.leftPID[0], Constants.leftPID[1], Constants.leftPID[2]);
    private final PIDController m_rightPIDController = new PIDController(
        Constants.rightPID[0], Constants.rightPID[1], Constants.rightPID[2]);

    private final DifferentialDriveKinematics m_kinematics
        = new DifferentialDriveKinematics(Constants.kTrackWidth);
    private final DifferentialDriveOdometry m_odometry;

    public Drivetrain() {
        m_gyro.reset();
        m_odometry = new DifferentialDriveOdometry(getAngle());
        m_rightMaster.setInverted(true);
    }

    public Rotation2d getAngle() {
       return Rotation2d.fromDegrees(-m_gyro.getAngle());
    }

    public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
        double leftOutput = m_leftPIDController.calculate(m_leftMaster.getSelectedSensorVelocity()*Constants.velocityConversion, speeds.leftMetersPerSecond)
            + leftFeedforward.calculate(speeds.leftMetersPerSecond);
        double rightOutput = m_rightPIDController.calculate(m_rightMaster.getSelectedSensorVelocity()*Constants.velocityConversion, speeds.rightMetersPerSecond)
            + rightFeedforward.calculate(speeds.rightMetersPerSecond);
        m_leftGroup.set(leftOutput);
        m_rightGroup.set(rightOutput);
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
    }

    public void updateOdometry() {
        m_odometry.update(getAngle(),
            m_leftMaster.getSelectedSensorPosition()*Constants.postitionConversion,
            m_rightMaster.getSelectedSensorPosition()*Constants.postitionConversion);
    }

    public void motorMonitoring() {
        SmartDashboard.putNumber("left draw", m_leftMaster.getSupplyCurrent());
        SmartDashboard.putNumber("left out", m_leftMaster.getStatorCurrent());
        SmartDashboard.putNumber("right draw", m_rightMaster.getSupplyCurrent());
        SmartDashboard.putNumber("right out", m_rightMaster.getStatorCurrent());
    }
}
