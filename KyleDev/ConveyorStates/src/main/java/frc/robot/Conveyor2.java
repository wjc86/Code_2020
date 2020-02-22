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
    
    // double[] K = new double[3]; //Array of gains
    // double[] K_UI = new double[3];
    double beltDelta; //length in inches
    double beltPosition;
    double distancePerRot = 7.0037; // inches per revolution
    double p, i, d;

    DigitalInput sensor1 = new DigitalInput(0);
    DigitalInput sensor2 = new DigitalInput(2);
    
    public Conveyor2(){
        conveyorMotor = new CANSparkMax(1, MotorType.kBrushless);
        conveyorMotor.restoreFactoryDefaults();
        conveyorPID = conveyorMotor.getPIDController();
        conveyorEncoder = conveyorMotor.getEncoder();
        conveyorEncoder.setPosition(0);
        conveyorEncoder.setPositionConversionFactor(1.0);
        this.getData();
        this.pushData();
        conveyorPID.setP(0);
        conveyorPID.setI(0);
        conveyorPID.setD(0);
        conveyorPID.setIZone(0);
        conveyorPID.setFF(0);
        conveyorPID.setOutputRange(-1, 1);

        SmartDashboard.putNumber("P Gain", 0);
        SmartDashboard.putNumber("I Gain", 0);
        SmartDashboard.putNumber("D Gain", 0);
        SmartDashboard.putNumber("Set Belt Delta", 0);
    }
    
    public void getData() {
        this.p = SmartDashboard.getNumber("P Gain", 0);
        this.i = SmartDashboard.getNumber("I Gain", 0);
        this.d = SmartDashboard.getNumber("D Gain", 0);
        this.beltDelta = SmartDashboard.getNumber("Set Belt Delta", 0);
        // this.K_UI[0] = p;
        // this.K_UI[1] = i;
        // this.K_UI[2] = d;
        conveyorMotor.set(0);
    }

    public void home(){
        conveyorPID.setReference(0, ControlType.kPosition);
    }

    public void setData() {
        // this.K = this.K_UI;
        conveyorPID.setP(p);
        conveyorPID.setI(i);
        conveyorPID.setD(d);
        // conveyorPID.setP(this.K[0]);
        // conveyorPID.setI(this.K[1]);
        // conveyorPID.setD(this.K[2]);
    }

    public void pushData() {
        double measure = this.conveyorMotor.getEncoder().getPosition();
        this.beltPosition = conveyorMotor.getEncoder().getPosition();

        SmartDashboard.putNumber("Measured Value ", measure);
        SmartDashboard.putNumber("Target Value ", 0);
        SmartDashboard.putBoolean("Top Line ", sensor1.get()); 
        SmartDashboard.putBoolean("Bottom Line ", sensor2.get());
    }

    public void advanceBelt(){
        conveyorPID.setReference(this.beltDelta/this.distancePerRot, ControlType.kPosition);
        // this.beltDelta = 0;
    }
}