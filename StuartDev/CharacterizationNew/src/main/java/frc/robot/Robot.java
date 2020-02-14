/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  static private int PIDIDX = 0;

  Joystick stick;
  DifferentialDrive drive;

  WPI_TalonFX leftMaster;
  WPI_TalonFX rightMaster;

  Supplier<Double> leftEncoderPosition;
  Supplier<Double> leftEncoderRate;
  Supplier<Double> rightEncoderPosition;
  Supplier<Double> rightEncoderRate;
  Supplier<Double> gyroAngleRadians;

  NetworkTableEntry autoSpeedEntry =
      NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
  NetworkTableEntry telemetryEntry =
      NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
  NetworkTableEntry rotateEntry =
    NetworkTableInstance.getDefault().getEntry("/robot/rotate");

  double priorAutospeed = 0;
  Number[] numberArray = new Number[10];
  
  @Override
  public void robotInit() {if (!isReal()) SmartDashboard.putData(new SimEnabler());

    stick = new Joystick(0);

    leftMaster = new WPI_TalonFX(1);
    leftMaster.setInverted(false);
    leftMaster.setSensorPhase(false);
    leftMaster.setNeutralMode(NeutralMode.Brake);

    rightMaster = new WPI_TalonFX(2);
    rightMaster.setInverted(false);
    rightMaster.setSensorPhase(false);
    rightMaster.setNeutralMode(NeutralMode.Brake);



    //
    // Configure gyro
    //

    // Note that the angle from the NavX and all implementors of wpilib Gyro
    // must be negated because getAngle returns a clockwise positive angle
    AHRS navx = new AHRS(SPI.Port.kMXP);
    gyroAngleRadians = () -> -1 * Math.toRadians(navx.getAngle());

    //
    // Configure drivetrain movement
    //

    drive = new DifferentialDrive(leftMaster, rightMaster);

    drive.setDeadband(0);

    //
    // Configure encoder related functions -- getDistance and getrate should
    // return units and units/s
    //

    double encoderConstant = (7.1631/100000.0)*0.3048;

    leftMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                                                PIDIDX, 10);
    leftEncoderPosition = ()
        -> leftMaster.getSelectedSensorPosition(PIDIDX) * encoderConstant;
    leftEncoderRate = ()
        -> leftMaster.getSelectedSensorVelocity(PIDIDX) * encoderConstant *
               10;

    rightMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                                                 PIDIDX, 10);
    rightEncoderPosition = ()
        -> -rightMaster.getSelectedSensorPosition(PIDIDX) * encoderConstant;
    rightEncoderRate = ()
        -> -rightMaster.getSelectedSensorVelocity(PIDIDX) * encoderConstant * 10.0;

    // Reset encoders
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);

    // Set the update rate instead of using flush because of a ntcore bug
    // -> probably don't want to do this on a robot in competition
    NetworkTableInstance.getDefault().setUpdateRate(0.010);
  }

  @Override
  public void disabledInit() {
    System.out.println("Robot disabled");
    drive.tankDrive(0, 0);
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void robotPeriodic() {
    // feedback for users, but not used by the control program
    SmartDashboard.putNumber("l_encoder_pos", leftEncoderPosition.get());
    SmartDashboard.putNumber("l_encoder_rate", leftEncoderRate.get());
    SmartDashboard.putNumber("r_encoder_pos", rightEncoderPosition.get());
    SmartDashboard.putNumber("r_encoder_rate", rightEncoderRate.get());
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    
    // Retrieve values to send back before telling the motors to do something
    double now = Timer.getFPGATimestamp();

    double leftPosition = leftEncoderPosition.get();
    double leftRate = leftEncoderRate.get();

    double rightPosition = rightEncoderPosition.get();
    double rightRate = rightEncoderRate.get();

    double battery = RobotController.getBatteryVoltage();

    double leftMotorVolts = leftMaster.getMotorOutputVoltage();
    double rightMotorVolts = rightMaster.getMotorOutputVoltage();

    // Retrieve the commanded speed from NetworkTables
    double autospeed = autoSpeedEntry.getDouble(0);
    priorAutospeed = autospeed;

    // command motors to do things
    drive.tankDrive(
      (rotateEntry.getBoolean(false) ? -1 : 1) * autospeed, autospeed,
      false
    );

    // send telemetry data array back to NT
    numberArray[0] = now;
    numberArray[1] = battery;
    numberArray[2] = autospeed;
    numberArray[3] = leftMotorVolts;
    numberArray[4] = rightMotorVolts;
    numberArray[5] = leftPosition;
    numberArray[6] = rightPosition;
    numberArray[7] = leftRate;
    numberArray[8] = rightRate;
    numberArray[9] = gyroAngleRadians.get();

    telemetryEntry.setNumberArray(numberArray);
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
