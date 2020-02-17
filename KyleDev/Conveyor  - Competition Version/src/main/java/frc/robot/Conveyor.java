package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Conveyor {
    VictorSPX conveyorMotor = new VictorSPX(1);
    VictorSPX conveyorFollower = new VictorSPX(2);

    double[] K = new double[3]; //Array of gains

    public final PIDController conveyorPID = new PIDController(this.K[0], this.K[1], this.K[2]);

    double[] K_UI = new double[3];
    double beltDelta /*length in inches*/, beltDelta_ui, beltPosition;
    double distancePerRot = 7.0037; // inches per revolution
    
    public Conveyor(){
        this.getData();
        SmartDashboard.putNumber("P Gain", 0);
        SmartDashboard.putNumber("I Gain", 0);
        SmartDashboard.putNumber("D Gain", 0);
        SmartDashboard.putNumber("Set Belt Delta", 0);
        conveyorFollower.follow(conveyorMotor);
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

    public void setData() {
        this.K = this.K_UI;
        this.beltDelta += this.beltDelta_ui;
        conveyorPID.setP(this.K[0]);
        conveyorPID.setI(this.K[1]);
        conveyorPID.setD(this.K[2]);
        if(this.beltDelta > 0) {this.advanceBelt();}
    }

    public void advanceBelt(){
        conveyorMotor.set(ControlMode.Position, this.beltPosition/this.distancePerRot + this.beltDelta);
        this.beltDelta = 0;
    }
}