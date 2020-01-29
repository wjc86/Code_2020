package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

class motorTestJoystick extends Joystick  {
    public int motor_id = 0;
    public int num_motors = 0;
    public boolean enable = false;
    public motorTestJoystick(int port,int num_motors) {
        super(port);
        this.num_motors=num_motors;
        
    }

    public void switchMotor(){
        if(this.getRawButtonPressed(1)){
            this.enable = false;
            if(this.motor_id < num_motors-1){
                this.motor_id += 1;
            }
            else{
                this.motor_id = 0;
            }
        }
    }

    public void EnableThrottle(){
        if (this.getRawButtonPressed(2)){
            if(this.enable==true){
                this.enable=false;
            }
            else if(this.enable==false){
                this.enable=true;
            }
        }
    }

    public double ThrotAsPct(){
        return -(this.getRawAxis(3)/-2.0+0.5);
    }

    public double ButtonAsPerturbation(){
        if (this.getRawButtonPressed(5)) {
            return 0.05;
        } else if(this.getRawButtonPressed(3)) {
            return -0.05;
        } else if(this.getRawButtonPressed(6)) {
            return 0.01;
        } else if(this.getRawButtonPressed(4)) {
            return -0.01;
        }
        else{
            return 0;
        }
    }
}