package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

public class Intake {
    public Joystick controller = new Joystick(2);
    public CANSparkMax extendMotor = new CANSparkMax(1, MotorType.kBrushless);    
    public CANSparkMax retrieveMotor = new CANSparkMax(2, MotorType.kBrushless);

    public int state = 0;
    public Boolean isDeployed = false;


    public void stateTransfer() {
       if (controller.getRawButton(1)) {
           this.state = 1;//Intake starts to deploy
       }
       else if (isDeployed == true) {
           this.state = 2;//Intake retracted completely
       }
       else if (controller.getRawButton(2)) {
           this.state = 3;//Turn off either if intake/retract failed or retract succeded
       }
       else if (isDeployed == false) {
           this.state = 0;//Intake completely off
       }
    }
}
