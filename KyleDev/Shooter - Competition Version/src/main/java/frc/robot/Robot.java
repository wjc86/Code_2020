package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
  private TalonFX Falcon = new TalonFX(1);
  private TalonSRX Talon = new TalonSRX(2);
  
  Joystick Joy = new Joystick(0);
  
  public int motor_id = 0;
  public int num_motors = 0;
  public boolean enable = false;

  TalonSRX talon;
  boolean myControllerType;
  
  public double throtpct2speed(double throtpct){
    return 5775.6*(throtpct) + 5.1504; 
  }

  public double speed2throt_pct(double speed){
    return 0.0002*(speed); 
  }

  @Override
  public void robotInit() {
    double shooterKP=0.01;
    double shooterKI=0.02;
    double shooterKD=0.0002;
    


    Falcon.config_kP(0, shooterKP);
    Falcon.config_kI(0, shooterKI);
    Falcon.config_kD(0, shooterKD);
  }
  
  @Override
  public void teleopPeriodic() {
    
    EnableThrottle();
      
    if(enable == true){
      if(motor_id == 0) {Falcon.set(ControlMode.PercentOutput, Joy.getRawAxis(3)/-2.0+1);} 
      else if (motor_id == 1) {Talon.set(ControlMode.PercentOutput, Joy.getRawAxis(3)/-2.0+1);}
    }
    SmartDashboard.putNumber("Current Motor: ", motor_id);
    SmartDashboard.putBoolean("CTRL ENABLE", enable);    
  }



  public void EnableThrottle(){
    if (Joy.getRawButtonPressed(2)){
      if(enable==true){
        enable=false;
      }
      else if(enable==false){
        enable=true;
      }
    }
  }
}

