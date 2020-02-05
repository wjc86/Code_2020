package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class shooter{

    private int State;
    private boolean systemHot;
    void init(){
        State = 0;
        systemHot = false;

    }
    public boolean checkSystemHot(){
        return true;
    }
    void stateTransition(){
        checkSystemHot();
        systemHot = checkSystemHot;
        if(this.systemHot == true){
            this.State = 1
        }
        else {
            this.State = 2
        }
 
    }
    private void act(){
        switch (State) {
            case 1:
                
                break;
           
            default:
                break;
        }

        }

    }
}