package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MotorTest {
    CANSparkMax spark;
    TalonSRX talon;
    VictorSP victor;
    int myControllerType;

    //Sets the Talon and Sparks to the type of motor and gives them their CAN ID
    public MotorTest(int controllerType, int motorID) {
        this.myControllerType = controllerType;
        
        switch(controllerType) {
            case 0:
                this.spark = new CANSparkMax(motorID, MotorType.kBrushless);
                break;
            case 1:
                this.talon = new TalonSRX(motorID);
                break;
            case 2:
                this.victor = new VictorSP(motorID);
                break;
        }
    }

    public TalonSRX getTalon() {
        return this.talon;
    }

    public CANSparkMax getSpark() {
        return this.spark;
    }

    public VictorSP getVictor() {
        return this.victor;
    }
    
}