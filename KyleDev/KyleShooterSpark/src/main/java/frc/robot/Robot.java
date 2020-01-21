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

import frc.robot.MotorTest;
import frc.robot.motorTestJoystick;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private MotorTest spar1 = new MotorTest(true, 1);
  private MotorTest spar2 = new MotorTest(true, 4);
  private MotorTest tal1 = new MotorTest(false, 2);

  private MotorTest[] motors = new MotorTest[3];

  public Joystick left = new Joystick(0);
  public Joystick right = new Joystick(1);
  public Joystick middle = new Joystick(2);
  
  public motorTestJoystick Joy = new motorTestJoystick(1,3);

  private final CANSparkMax Motor0 = new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax Motor1 = new CANSparkMax(1, MotorType.kBrushless);
  private final TalonSRX Motor2 = new TalonSRX(44);
  private double pct0;
  private double pct1;
  private double pct2;
  private double pert0;
  private double pert1;
  private double pert2;
  private double M0speed;
  private double M1speed;
  private double M2speed = 0;
  private double M0pct;
  private double M1pct;
  private double M2pct;
  
  


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    Motor0.getEncoder().setVelocityConversionFactor(1.0);
    Motor1.getEncoder().setVelocityConversionFactor(1.0);
    motors[0] = spar1;
    motors[1] = spar2;
    motors[2] = tal1;
  }
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }
  
  @Override
  public void teleopPeriodic() {

    Joy.switchMotor();
    Joy.EnableThrottle();
    System.out.println(Joy.motor_id+"|"+Joy.ThrotAsPct()+"|"+Joy.ButtonAsPerturbation()+"|"+Joy.enable);
  
    if (Joy.motor_id==0){
      pert0+=Joy.ButtonAsPerturbation();
      M0pct=Joy.ThrotAsPct();
      if(Joy.enable){
        Motor0.set(M0pct+pert0);
      }
    }
    else if (Joy.motor_id==1){
      pert1+=Joy.ButtonAsPerturbation();
      M1pct=Joy.ThrotAsPct();
      if(Joy.enable){
        Motor1.set(M1pct+pert1);
      }
    }
    else{
      pert2+=Joy.ButtonAsPerturbation();
      M2pct=Joy.ThrotAsPct();
      if(Joy.enable){
        Motor2.set(ControlMode.PercentOutput,M2pct+pert2);
      }
    }
    
    SmartDashboard.putNumber("M0:pct",-(M0pct+pert0));
    SmartDashboard.putNumber("M0:speed", -(Motor0.getEncoder().getVelocity()));
    SmartDashboard.putNumber("M1:pct",-(M1pct+pert1));
    SmartDashboard.putNumber("M1:speed", -(Motor1.getEncoder().getVelocity()));
    SmartDashboard.putNumber("M2:pct",-(M2pct+pert2));
    SmartDashboard.putNumber("M2:speed", -(M2speed));
    SmartDashboard.putNumber("Current Motor: ", Joy.motor_id);
    SmartDashboard.putBoolean("CTRL ENABLE", Joy.enable);
    /*
    lpct = (left.getRawAxis(3)/-2.0+0.5);
    rpct = (right.getRawAxis(3)/-2.0+0.5);
    convpct = (middle.getRawAxis(3)/-2.0+0.5);
    RMotor.set(rpct);
    LMotor.set(lpct);
    conveyor.set(ControlMode.PercentOutput, convpct);
    */
    
    
    
    // lvelocity=LMotor.getEncoder().getVelocity();
    // rvelocity=RMotor.getEncoder().getVelocity();

    // SmartDashboard.putNumber("L%: ", lpct);
    // SmartDashboard.putNumber("R%: ", rpct);
    // SmartDashboard.putNumber("Tread %", convpct);
    // SmartDashboard.putNumber("LVel: ", lvelocity);
    // SmartDashboard.putNumber("RVel: ", rvelocity);
    // SmartDashboard.putNumber("LVel Graph: ", lvelocity);
    // SmartDashboard.putNumber("RVel Graph: ", rvelocity);

    
  }
}

