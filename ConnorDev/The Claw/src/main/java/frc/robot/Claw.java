package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Claw {
    DoubleSolenoid firstdSolenoid = new DoubleSolenoid(1,4,3);
    DoubleSolenoid seconddSolenoid = new DoubleSolenoid(0,7,0);
    public void onStart() {
        firstdSolenoid.set(DoubleSolenoid.Value.kOff);
        seconddSolenoid.set(DoubleSolenoid.Value.kOff);
    }
    public void up(){
        firstdSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void down(){
        firstdSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void grab(){
        seconddSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public void release(){
        seconddSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    /*
    DoubleSolenoid firstdSolenoid = new DoubleSolenoid(1,4,3);
    DoubleSolenoid seconddSolenoid = new DoubleSolenoid(0,7,0);
     Joystick controller = new Joystick(1);
     firstdSolenoid.set(DoubleSolenoid.Value.kOff);
     seconddSolenoid.set(DoubleSolenoid.Value.kOff);
     boolean buttonValue1;
     boolean buttonValue2;
     boolean buttonValue3;
     boolean buttonValue4;
      buttonValue1 = controller.getRawButton(1);
      buttonValue2 = controller.getRawButton(2);
      buttonValue3 = controller.getRawButton(3);
      buttonValue4 = controller.getRawButton(4);
    */
      
}
