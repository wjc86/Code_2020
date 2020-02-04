package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class turret {
    //LimitSwitch leftLimit = new LimitSwitch(1);
    public Boolean leftLimit = false;
    //LimitSwitch rightLimit = new LimitSwitch(2);
    public Boolean rightLimit = false;
    TalonFX spinMotor = new TalonFX(1);
    public int state = 0;

    public Boolean visionTracking = false;
    public Boolean visionLocked = false;


    public void limitReached() {}
    public void SlowDown() {}
    public void Oscillating() {}
    public void off() {}

    public void StateTransition(){
        if (this.visionTracking == true && this.visionLocked == true){
            this.state = 1;
        }
        else if (this.visionTracking == false && this.visionLocked == true) {
            this.state = 2;
        }
        else if (this.visionTracking == true && this.visionLocked == false) {
            this.state = 3;
        }
        else if (this.visionTracking == false && this.visionLocked == false) {
            this.state = 4;
        }    
    }
}
  


/*
2 Limit Switches
Spin Limit
Vision Integration
Motor (SparkMax or Falcon 500) with encoder value to determine angle
if reached enpoint speeed/pct = -speed/pct
switch case
eStop

*/