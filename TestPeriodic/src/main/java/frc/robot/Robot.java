/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Compressor;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
  private Joystick Joy = new Joystick(1);
  private VictorSP intake = new VictorSP(1);
  private TalonFX rightMotorMaster = new TalonFX(1);
  private TalonFX rightMotorFollower = new TalonFX(2);
  private TalonFX leftMotorMaster = new TalonFX(3);
  private TalonFX leftMotorFollower = new TalonFX(4);
  private TalonSRX conveyorMaster = new TalonSRX(1);
  private TalonSRX conveyorFollower = new TalonSRX(2);
  private TalonSRX turret = new TalonSRX(3);
  private TalonFX flywheel = new TalonFX(5);
  private TalonSRX speedShooter = new TalonSRX(3);
  private Compressor compressor = new Compressor();
  private DoubleSolenoid leftGearboxPiston = new DoubleSolenoid(1, 2);
  private DoubleSolenoid rightGearboxPiston= new DoubleSolenoid(3, 4);
  private DoubleSolenoid leftIntakePiston = new DoubleSolenoid(1, 2);
  private DoubleSolenoid rightIntakePiston= new DoubleSolenoid(3, 4);
  private CANSparkMax winch1 = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax winch2 = new CANSparkMax(1, MotorType.kBrushless);
  private DigitalInput topConveyorLinebreak = new DigitalInput(1);
  private DigitalInput bottomConveyorLinebreak = new DigitalInput(2);
  private DigitalInput hallEffect = new DigitalInput(3);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    leftMotorFollower.follow(leftMotorMaster);
    rightMotorFollower.follow(rightMotorMaster);
    conveyorFollower.follow(conveyorMaster);

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
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    SmartDashboard.putBoolean("Top Linebreak", topConveyorLinebreak.get());
    SmartDashboard.putBoolean("Bottom Linebreak", bottomConveyorLinebreak.get());
    SmartDashboard.putBoolean("Hal Effect Sensor", hallEffect.get());
    SmartDashboard.putNumber("Encoder Value for Turret", turret.getSelectedSensorPosition());
    SmartDashboard.putNumber("Encoder Value for Conveyor", conveyorMaster.getSelectedSensorPosition());
    if (Joy.getRawButton(1)) {
      leftMotorMaster.set(ControlMode.PercentOutput, 0);
      rightMotorMaster.set(ControlMode.PercentOutput, 0);
      leftGearboxPiston.set(Value.kReverse);
      rightGearboxPiston.set(Value.kReverse);
      compressor.stop();
      leftIntakePiston.set(Value.kReverse);
      rightIntakePiston.set(Value.kReverse);
      intake.set(0);
      conveyorMaster.set(ControlMode.PercentOutput, 0);
      turret.set(ControlMode.PercentOutput, 0);
      flywheel.set(ControlMode.PercentOutput, 0);
      speedShooter.set(ControlMode.PercentOutput, 0);
      winch1.set(0);
      winch2.set(0);
    }
    
    if (Joy.getRawButton(2)) {
      leftGearboxPiston.set(Value.kForward);
      rightGearboxPiston.set(Value.kForward);
    }
    
    if (Joy.getRawButton(3)) {
      compressor.start();
    }
    if (Joy.getRawButton(4)){
      leftMotorMaster.set(ControlMode.PercentOutput, 0.2);
      rightMotorMaster.set(ControlMode.PercentOutput, -0.2);
    }
    if (Joy.getRawButton(5)){
      leftIntakePiston.set(Value.kForward);
      rightIntakePiston.set(Value.kForward);
    }
    if (Joy.getRawButton(6)) {
      intake.set(0.2);
    }
    if(Joy.getRawButton(7)) {
      conveyorMaster.set(ControlMode.PercentOutput, 0.2);
    }
    if(Joy.getRawButton(8)) {
      turret.set(ControlMode.PercentOutput, 0.2);
    }
    if(Joy.getRawButton(9)) {
      flywheel.set(ControlMode.PercentOutput, 0.2);
    }
    if(Joy.getRawButton(10)) {
      speedShooter.set(ControlMode.PercentOutput, 0.2);
    }
    if(Joy.getRawButton(11)) {
      winch1.set(0.2);
      winch2.set(0.2);
    }
  } 
}
