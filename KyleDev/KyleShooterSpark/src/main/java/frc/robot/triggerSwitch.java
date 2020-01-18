package frc.robot;
import edu.wpi.first.wpilibj.Joystick;


class motorTestJoystick extends Joystick  {
    public int motor_id = 0;
    public int num_motors = 0;

    public motorTestJoystick(int port,int num_motors) {
        super(port);
        this.num_motors=num_motors;
        // TODO Auto-generated constructor stub
    }

    public void switchMotor(){
        boolean triggerPressed = this.getRawButton(1);
        if(triggerPressed){
            if(this.motor_id < num_motors-1){
                this.motor_id += 1;
            }
            else{
                this.motor_id = 0;
            }
    
        }
    }

    public double ThrotAsPct(){
        return this.getRawAxis(3)/-2.0+0.5;
    }

    public double ButtonAsPerturbation(){
        if (this.getRawButton(5)) {
            return 0.05;
        } else if(this.getRawButton(3)) {
            return -0.05;
        } else if(this.getRawButton(6)) {
            return 0.01;
        } else if(this.getRawButton(4)) {
            return -0.01;
        }
        else{
            return 0;
        }
    }

  
    
}