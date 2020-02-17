package frc.robot;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Conveyor2 {
    CANSparkMax conveyorMotor = new CANSparkMax(1, MotorType.kBrushless);
    CANPIDController conveyorPID = conveyorMotor.getPIDController();
    double[] K = new double[3]; //Array of gains
    double[] K_UI = new double[3];
    double beltDelta; //length in inches
    double beltDelta_ui;
    double beltPosition;
    double distancePerRot = 7.0037; // inches per revolution
    // Boolean isStaged = false;
    // int state = 0;
    
    public Conveyor2(){
        this.getData();
        this.pushData();
        SmartDashboard.putNumber("P Gain", 0);
        SmartDashboard.putNumber("I Gain", 0);
        SmartDashboard.putNumber("D Gain", 0);
        SmartDashboard.putNumber("Set Belt Delta", 0);
    }
    
    public void getData() {
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double dX = SmartDashboard.getNumber("Set Belt Delta", 0);
        this.K_UI[0] = p;
        this.K_UI[1] = i;
        this.K_UI[2] = d;
        this.beltDelta_ui = dX;
    }

    public void home(){
        conveyorPID.setReference(0, ControlType.kPosition);
    }

    public void setData() {
        this.K = this.K_UI;
        this.beltDelta += this.beltDelta_ui;
        conveyorPID.setP(this.K[0]);
        conveyorPID.setI(this.K[1]);
        conveyorPID.setD(this.K[2]);
        if(this.beltDelta > 0) {
            this.advanceBelt();
        }
    }

    public void pushData() {
        double measure = this.conveyorMotor.getEncoder().getPosition();
        SmartDashboard.putNumber("Measured Value ", measure);
        SmartDashboard.putNumber("Target Value ", 0);
        
    }

    public void advanceBelt(){
        conveyorPID.setReference(this.beltPosition/this.distancePerRot + this.beltDelta, ControlType.kPosition);
        this.beltDelta = 0;
    }
    /*public void stateTransfer() {
         if (controller.getRawButton(1) && isStaged == false) {this.state = 1;}
        else if(controller.getRawButton(1) && isStaged == true){this.state = 2;}
        else if (controller.getRawButton(2) && isStaged == false){this.state = 3;}
        else if (controller.getRawButton(2) && isStaged == true){this.state = 4;}
    }*/
}