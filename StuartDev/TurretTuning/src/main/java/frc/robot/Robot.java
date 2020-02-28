/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

    //Sensors
    private DigitalInput hallEffect = new DigitalInput(0);
    private TalonSRX rotateMotor = new TalonSRX(11);    
    
    //Measured in Degrees
    private double minPos = 0;
    private double midPos = 90;
    private double maxPos = 180;

    //Measurements
    private final double degrees2Rotations = (double)1 / 360;

    private PIDController pid = new PIDController(0.0, 0.0, 0.0);

    private Joystick stick = new Joystick(0);

    public static final double TURRET_PERCENT_OUTPUT = 0.1; //As a decimal
    public static final double MOTOR_TO_TURRET_GEAR_RATIO = 16.0/120.0;
    public static final double ENCODER_TICKS_PER_REVOLUTION = 4096.0;
    public static final double REVOLUTIONS_PER_ENCODER_TICK = 1.0 / 4096.0;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    SmartDashboard.putNumber("p", 0);
    SmartDashboard.putNumber("i", 0);
    SmartDashboard.putNumber("d", 0);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Actual Angle", getTurretAngle());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    updatePID();
    double output = 0;
    double wantedAngle = 0;
    if (stick.getRawButton(1)){
      wantedAngle = 45;
      output = pid.calculate(getTurretAngle(), wantedAngle);
    } else if (stick.getRawButton(2)) {
      wantedAngle = -45;
      output = pid.calculate(getTurretAngle(), wantedAngle);
    }
    rotateMotor.set(ControlMode.PercentOutput, -output);
    SmartDashboard.putNumber("Wanted Angle", wantedAngle);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void updatePID() {
    pid.setP(SmartDashboard.getNumber("p", 0));
    pid.setI(SmartDashboard.getNumber("i", 0));
    pid.setD(SmartDashboard.getNumber("d", 0));
  }

  public double getTurretAngle() {
    double degrees = getTurretPosition() * MOTOR_TO_TURRET_GEAR_RATIO * REVOLUTIONS_PER_ENCODER_TICK * 360.0 * -1.0;
    return degrees - midPos;
  }

  public double getTurretPosition() {
    return rotateMotor.getSelectedSensorPosition(0);
  }
}
