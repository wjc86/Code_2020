package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class Conveyor {
    public TalonSRX lowerConveyor = new TalonSRX(1);
    public TalonSRX upperConveyor = new TalonSRX(2);
    public Joystick controller = new Joystick(0);
    

    public Boolean isStaged = false;
    public int state = 0;


    public void stateTransfer() {
         if (controller.getRawButton(1) && isStaged == false) {
            this.state = 1;
        }
        else if(controller.getRawButton(1) && isStaged == true){
            this.state = 2;
        }
        else if (controller.getRawButton(2) && isStaged == false){
            this.state = 3;
                   
        }
        else if (controller.getRawButton(2) && isStaged == true){
            this.state = 4;
        }
    }
}
