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
// import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.MotorTest;
import frc.robot.motorTestJoystick;
import frc.robot.ourpid;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private MotorTest spar1 = new MotorTest(true, 1);
  private MotorTest spar2 = new MotorTest(true, 4);
  private MotorTest tal1 = new MotorTest(false, 11);

  private MotorTest[] motors = new MotorTest[3];



  // public Joystick left = new Joystick(0);
  // public Joystick right = new Joystick(1);
  // public Joystick middle = new Joystick(2);
  
  public motorTestJoystick Joy = new motorTestJoystick(1,3);

  public ourpid controller1 = new ourpid();
  public ourpid controller0 = new ourpid(); 
  public ourpid controller2 = new ourpid(); 
  
  private ourpid[] controllers = new ourpid[3];

  private double pert;
  private double Mpct;
  
  
  public double throtpct2speed(double throtpct){
    return 5775.6*(throtpct) + 5.1504; 
  }

  public double speed2throt_pct(double speed){
    return 0.0002*(speed); 
  }

  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    motors[0] = spar1;
    motors[1] = spar2;
    motors[2] = tal1;

    motors[0].getSpark().getEncoder().setVelocityConversionFactor(1.0);
    motors[1].getSpark().getEncoder().setVelocityConversionFactor(1.0);

    controllers[0] = controller0;
    controllers[1] = controller1;
    controllers[2] = controller2;
    double KP=0;
    double KI=0;
    double KD=0;
    for(int i=0;i<3;i++){
      controllers[i].setGains(KP, KI, KD);
    }



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
  
  
    pert+=Joy.ButtonAsPerturbation();
    Mpct=Joy.ThrotAsPct();
    if(Joy.enable){
      // controllers[Joy.motor_id].setTargetOutput(throtpct2speed(Mpct+pert));
      if (Joy.motor_id==2){
        motors[Joy.motor_id].getTalon().set(ControlMode.PercentOutput,Mpct+pert);
      }
      else{
        motors[Joy.motor_id].getSpark().set(Mpct);
      }
    }
    controllers[0].setMeasuredOutput(motors[0].getSpark().getEncoder().getVelocity());
    controllers[1].setMeasuredOutput(motors[1].getSpark().getEncoder().getVelocity());
    
    // for (int i=0;i<2;i++){
    //   motors[i].getSpark().set(controllers[i].calcControleEffort()+controllers[i].target_output);
    // }
    // motors[2].getTalon().set(ControlMode.PercentOutput, controllers[2].target_output); //set SIMS motor to target speed as pct.
        
    SmartDashboard.putNumber("M0:target",-(controllers[0].target_output));
    SmartDashboard.putNumber("M0:speed", -(controllers[0].measured_output));
    SmartDashboard.putNumber("M1:target",-(controllers[1].target_output));
    SmartDashboard.putNumber("M1:speed", -(controllers[1].measured_output));
    SmartDashboard.putNumber("M2:target",-(controllers[2].target_output));

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

