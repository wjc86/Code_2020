
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.robot.Constants;

public class Turret extends SubsystemBase {

    private static Turret instance = new Turret();

    private WPI_TalonFX flywheelMotor = new WPI_TalonFX(11);
    
    //Measurements
    private final double degrees2Rotations = 1.0 / 360.0; 
    private final double percentToVoltage = 3.0 / 25.0;

    private PIDController pid = new PIDController(0.0175, 0.0001, 0.001);

    private double wantedAngle = 0.0;
    private double currentAngle = 0.0;
    private double lastAngle = 0.0;
    private boolean currentHall = false;
    private boolean lastHall = false;

    public Turret() {}

    @Override
    public void periodic() {
        putOutput();
    }


    public static Turret getInstance() {
        return instance;
    }

    public void setFlywheelOutput(double percent) {
        flywheelMotor.setVoltage(percent * percentToVoltage);
    }

    public void changeOutput(double input) {
        flywheelMotor.setVoltage(flywheelMotor.get() + input);
    }

    public void putOutput() {
        SmartDashboard.putNumber("Flywheel Output", flywheelMotor.get());
    }
}