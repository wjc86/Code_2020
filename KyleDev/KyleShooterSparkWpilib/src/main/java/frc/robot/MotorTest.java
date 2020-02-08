package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MotorTest {
    CANSparkMax spark;
    TalonSRX talon;
    boolean myControllerType;

    //Sets the Talon and Sparks to the type of motor and gives them their CAN ID
    public MotorTest(boolean controllerType, int motorID) {
        this.myControllerType = controllerType;
        
        if (controllerType) {
            this.spark = new CANSparkMax(motorID, MotorType.kBrushless);
        } else {
            this.talon = new TalonSRX(motorID);
        }
    }

    public TalonSRX getTalon() {
        return this.talon;
    }

    public CANSparkMax getSpark() {
        return this.spark;
    }
    
}