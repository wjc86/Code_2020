package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode; //Importing what we need from ctre in the Robot class

import frc.robot.MotorTest; //Imports our other files
import frc.robot.motorTestJoystick;
import frc.robot.ourpid;

public class Robot extends TimedRobot {
  //2nd number is the CAN ID and its creating them under the MotorTest Class
  private MotorTest spar1 = new MotorTest(true, 1);
  private MotorTest spar2 = new MotorTest(true, 4);
  private MotorTest tal1 = new MotorTest(false, 11);

  private MotorTest[] motors = new MotorTest[3];
  
  //Creates the Joystick for motorTestJoystick to use
  public motorTestJoystick Joy = new motorTestJoystick(1,3);

  //Creates the controllers in ourpid class
  public ourpid controller1 = new ourpid();
  public ourpid controller0 = new ourpid(); 
  public ourpid controller2 = new ourpid(); 
  
  private ourpid[] controllers = new ourpid[3];

  private double Mpct;
  
  //Takes the slider value on the Joystick and gets a speed
  public double throtpct2speed(double throtpct){
    return 5775.6*(throtpct) + 5.1504; 
  }

  //Takes the speed and returns the percent for the motor to run
  public double speed2throt_pct(double speed){
    return 0.0002*(speed); 
  }

  @Override
  public void robotInit() {
    //Adding the sparks and talon to the motor list
    motors[0] = spar1;
    motors[1] = spar2;
    motors[2] = tal1;

    //Sets the Encoder Velocity Conversion to 1.0
    motors[0].getSpark().getEncoder().setVelocityConversionFactor(1.0);
    motors[1].getSpark().getEncoder().setVelocityConversionFactor(1.0);

    //Adds the motor controllers to the controllers list
    controllers[0] = controller0;
    controllers[1] = controller1;
    controllers[2] = controller2;

    //Sets what the PID Loop Values are going to be
    double KP=0.01;
    double KI=0.02;
    double KD=0.0002;
    for(int i=0;i<3;i++){
      controllers[i].setGains(KP, KI, KD);
    }
  }
  
  @Override
  public void teleopPeriodic() {

    //Switches the controlled motor by pressing the trigger
    Joy.switchMotor();
    
    //Switches if the slider will change the speed of the motor
    Joy.EnableThrottle();
      
    //Sets Mpct to the slider position
    Mpct=Joy.ThrotAsPct();

    //Checks if the Throttle is enabled and if it is it sets the motor speed
    if(Joy.enable){
      controllers[Joy.motor_id].setTargetOutput(throtpct2speed(Mpct));
      
    }
    controllers[0].setMeasuredOutput(motors[0].getSpark().getEncoder().getVelocity());
    controllers[1].setMeasuredOutput(motors[1].getSpark().getEncoder().getVelocity());
    
    
    motors[0].getSpark().set(controllers[0].calcControleEffort()+speed2throt_pct(controllers[0].target_output));
    motors[2].getTalon().set(ControlMode.PercentOutput,speed2throt_pct(controllers[2].target_output)); //set SIMS motor to target speed as pct.
    
    //Shows the Flywheel Values on the Smart Dashboard
    SmartDashboard.putNumber("Flywheel Target",controllers[0].target_output);
    SmartDashboard.putNumber("Flywheel Speed", controllers[0].measured_output);
    SmartDashboard.putNumber("Flywheel Error", controllers[0].error);
    SmartDashboard.putNumber("Flywheel Error", controllers[0].error_sum);
    SmartDashboard.putNumber("Flywheel Error", controllers[0].error_difference);
    
    SmartDashboard.putNumber("Current Motor: ", Joy.motor_id);
    SmartDashboard.putBoolean("CTRL ENABLE", Joy.enable);    
  }
}

