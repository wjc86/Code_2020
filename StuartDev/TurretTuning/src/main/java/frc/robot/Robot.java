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
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;

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
    private double minPos = -88.0;
    private double midPos = 4.0;
    private double maxPos = 94.0;

    //Measurements
    private final double degrees2Rotations = (double)1 / 360;

    private PIDController pid = new PIDController(0.0175, 0.0001, 0.001);

    private Joystick leftStick = new Joystick(0);
    private Joystick rightStick = new Joystick(1);

    public static final double TURRET_PERCENT_OUTPUT = 0.1; //As a decimal
    public static final double MOTOR_TO_TURRET_GEAR_RATIO = 16.0/120.0;
    public static final double ENCODER_TICKS_PER_REVOLUTION = 4096.0;
    public static final double REVOLUTIONS_PER_ENCODER_TICK = 1.0 / 4096.0;

    public double offset = 0;

    double wantedAngle = 0;
    double currentAngle = 0;
    double lastAngle = 0;
    boolean currentHall = false;
    boolean lastHall = false;



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
    SmartDashboard.putNumber("limit", .3);
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
    SmartDashboard.putBoolean("Hall Effect", hallEffect.get());
    calibratePosition();
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

  @Override
  public void teleopInit() {
    // TODO Auto-generated method stub
    super.teleopInit();
    SmartDashboard.putNumber("p", 0);
    SmartDashboard.putNumber("i", 0);
    SmartDashboard.putNumber("d", 0);
    SmartDashboard.putNumber("limit", 0.3);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    updatePID();
    double output = 0;
    double limit = SmartDashboard.getNumber("limit", 0.3);
    if (leftStick.getRawButton(1)){
      wantedAngle = minPos;
      SmartDashboard.putNumber("PID Out", -output);
    } else if (leftStick.getRawButton(2)) {
      wantedAngle = minPos / 2.0;
      SmartDashboard.putNumber("PID Out", -output);
    } else if (leftStick.getRawButton(3)) {
      wantedAngle = midPos;
      SmartDashboard.putNumber("PID Out", -output);
    } else if (leftStick.getRawButton(4)) {
      wantedAngle = maxPos / 2.0;
      SmartDashboard.putNumber("PID Out", -output);
    } else if (leftStick.getRawButton(5)) {
      wantedAngle = maxPos;
      SmartDashboard.putNumber("PID Out", -output);
    }
    if(Math.abs(rightStick.getY()) > .25) {
      wantedAngle += rightStick.getY();
    }
    if (wantedAngle > maxPos) {
      wantedAngle = maxPos;
    } else if (wantedAngle < minPos) {
      wantedAngle = minPos;
    }
    output = pid.calculate(getTurretAngle(), wantedAngle);
    double error = wantedAngle - getTurretAngle();
    // if (Math.abs(error) > 90) {
      if (output > limit) {
        output = limit;
      } else if (output < -limit) {
        output = -limit;
      }
    // } else if (Math.abs(error) < 90 && Math.abs(error) > 60) {
    //   if (output > .2) {
    //     output = .2;
    //   } else if (output < -.2) {
    //     output = -.2;
    //   }
    // } else if (Math.abs(error) < 60 && Math.abs(error) > 30) {
    //   if (output > .1) {
    //     output = .1;
    //   } else if (output < -.1) {
    //     output = -.1;
    //   }
    // } else if (Math.abs(error) < 30) {
    //   if (output > .05) {
    //     output = .05;
    //   } else if (output < -.05) {
    //     output = -.05;
    //   }
    // }
    if (leftStick.getRawButton(1)){
      SmartDashboard.putNumber("PID Out", -output);
    } else if (leftStick.getRawButton(2)) {
      SmartDashboard.putNumber("PID Out", -output);
    } else if (leftStick.getRawButton(3)) {
      SmartDashboard.putNumber("PID Out", -output);
    } else if (leftStick.getRawButton(4)) {
      SmartDashboard.putNumber("PID Out", -output);
    } else if (leftStick.getRawButton(5)) {
      SmartDashboard.putNumber("PID Out", -output);
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
    //pid.setP(SmartDashboard.getNumber("p", 0));
    //pid.setI(SmartDashboard.getNumber("i", 0));
    //pid.setD(SmartDashboard.getNumber("d", 0));
  }

  public double getTurretAngle() {
    double degrees = (getTurretPosition() * MOTOR_TO_TURRET_GEAR_RATIO * REVOLUTIONS_PER_ENCODER_TICK * 360.0 * -1.0) - offset;
    return degrees;// - midPos;
  }

  public double getTurretPosition() {
    return rotateMotor.getSelectedSensorPosition(0);
  }

  public boolean getHallEffect() {
    return !hallEffect.get();
  }

  public void calibratePosition() {
    currentAngle = getTurretAngle();
    currentHall = getHallEffect();

    if (((currentAngle - lastAngle) > 0 && (lastHall == false && currentHall == true)) || ((currentAngle - lastAngle) < 0 && (lastHall == true && currentHall == false))){
      rotateMotor.setSelectedSensorPosition(0);
      System.out.println("true");
    }

    lastAngle = currentAngle;
    lastHall = currentHall;
  }
}
