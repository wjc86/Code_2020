package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Conveyor2 {
    public CANSparkMax conveyorMotor;
    public CANPIDController conveyorPID;
    public CANEncoder conveyorEncoder;
    double beltDelta; //length in inches
    double beltPosition, beltDelta_ui;
    double distancePerRot = 7.0037; // inches per revolution
    double p = 0.08;
    double i = 0.075;
    double d = 0.07;

    DigitalInput sensor1 = new DigitalInput(0);
    DigitalInput sensor2 = new DigitalInput(2);
    
    public Conveyor2(){
        conveyorMotor = new CANSparkMax(1, MotorType.kBrushless);
        conveyorMotor.restoreFactoryDefaults();
        conveyorPID = conveyorMotor.getPIDController();
        conveyorEncoder = conveyorMotor.getEncoder();
        conveyorEncoder.setPosition(0.0);
        conveyorEncoder.setPositionConversionFactor(1.0);
        this.getData();
        this.pushData();
        conveyorPID.setP(p);
        conveyorPID.setI(i);
        conveyorPID.setD(d);
    }
    
    public void getData() {
        this.beltDelta_ui = SmartDashboard.getNumber("Set Belt Delta", 0);
    }

    public void setData() {
        this.beltDelta = this.beltDelta_ui;
    }

    public void pushData() {
        double measure = this.conveyorMotor.getEncoder().getPosition();
        this.beltPosition = conveyorMotor.getEncoder().getPosition();

        SmartDashboard.putNumber("Measured Value ", measure);
        SmartDashboard.putNumber("Target Value ", 0);
        SmartDashboard.putBoolean("Top Line ", sensor1.get()); 
        SmartDashboard.putBoolean("Bottom Line ", sensor2.get());
        SmartDashboard.putNumber("Set Belt Delta", 0);
    }

    public void advanceBelt(){
        conveyorPID.setReference(this.beltDelta/this.distancePerRot+this.beltPosition, ControlType.kPosition);
        double reference = this.beltDelta/this.distancePerRot;
        SmartDashboard.putNumber("Set Reference", reference);
        SmartDashboard.putNumber("Encoder Position", conveyorEncoder.getPosition());
        this.beltDelta = 0;
    }
}