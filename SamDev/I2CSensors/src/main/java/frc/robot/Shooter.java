package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shooter{
    private TalonSRX lMotor = new TalonSRX(1);
    private CANSparkMax rMotor = new CANSparkMax(2, MotorType.kBrushless);

    public void manualOverride(){}

    public void stage(){}

    public void fToggle(){}

    public void intake(){}

    public void conveyor(){}
}