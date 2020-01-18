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
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public Joystick left = new Joystick(0);
  public Joystick right = new Joystick(1);
  public Joystick middle = new Joystick(1);
  private CANSparkMax LMotor = new CANSparkMax(4, MotorType.kBrushless);
  private CANSparkMax RMotor = new CANSparkMax(1, MotorType.kBrushless);
  private TalonSRX conveyor = new TalonSRX(44);
  private double lpct;
  private double rpct;
  private double lvelocity;
  private double rvelocity;
  private double convpct;
  private double convvelocity;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    RMotor.getEncoder().setVelocityConversionFactor(1.0);
    LMotor.getEncoder().setVelocityConversionFactor(1.0);
  }
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }
  
  @Override
  public void teleopPeriodic() {
    
    lpct = (left.getRawAxis(3)/-2.0+0.5);
    rpct = (right.getRawAxis(3)/-2.0+0.5);
    convpct = (middle.getRawAxis(3)/-2.0+0.5);
    RMotor.set(rpct);
    LMotor.set(lpct);
    conveyor.set(ControlMode.PercentOutput, convpct);
    
    
    
    lvelocity=LMotor.getEncoder().getVelocity();
    rvelocity=RMotor.getEncoder().getVelocity();

    SmartDashboard.putNumber("L%: ", lpct);
    SmartDashboard.putNumber("R%: ", rpct);
    SmartDashboard.putNumber("Tread %", convpct);
    SmartDashboard.putNumber("LVel: ", lvelocity);
    SmartDashboard.putNumber("RVel: ", rvelocity);
    SmartDashboard.putNumber("LVel Graph: ", lvelocity);
    SmartDashboard.putNumber("RVel Graph: ", rvelocity);
    
  }
}

