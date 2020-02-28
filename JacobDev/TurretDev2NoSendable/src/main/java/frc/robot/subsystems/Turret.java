
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
import frc.robot.Constants;

public class Turret extends SubsystemBase {

    private static Turret instance = new Turret();

    //Sensors
    private DigitalInput hallEffect = new DigitalInput(0);
    private TalonSRX rotateMotor = new TalonSRX(11); //Might be (port) 12    

    //Measured in Degrees
    private double minPos = 0;
    private double midPos = 90;
    private double maxPos = 180;

    //Measurements
    private final double degrees2Rotations = (double)1 / 360;

    private PIDController pid = new PIDController(0.002, 0, 0);

    public Turret() {}

    @Override
    public void periodic() {
        isOnHallEffect();
    }


    public static Turret getInstance() {
        return instance;
    }

    public double getTurretPosition() {
        return rotateMotor.getSelectedSensorPosition(0);
    }

    public void setTurretPosition(double wantedDegrees) {
        //For tuning in the smartDashboard
        //pid.setP(SmartDashboard.getNumber("kP", 0));
        //pid.setI(SmartDashboard.getNumber("kI", 0));
        //pid.setD(SmartDashboard.getNumber("kD", 0));
        double currentDegrees = getTurretAngle();
        double output = pid.calculate(currentDegrees, wantedDegrees) * Constants.TURRET_PERCENT_OUTPUT;
        if(isOnHallEffect()) {
            System.out.println("Hall Effect" + isOnHallEffect());
        }
        SmartDashboard.putBoolean("Hall Effect", isOnHallEffect());
        //if(output > 0.1) { output = 0.1; }
        //else if (output < -0.1) { output = -0.1; }
        System.out.println(output);
        rotateMotor.set(ControlMode.PercentOutput, output);
    }

    public void setTurretPercentOut(double percent) {
        rotateMotor.set(ControlMode.PercentOutput, percent);
    }

    public void setTurretAngle(int angle) {
        rotateMotor.setSelectedSensorPosition(angle);
    }

    //Returns the angle offset where 0 degrees is where the turret and robot are facing the same direction
    public double getTurretAngle() {
        double degrees = (getTurretPosition() * Constants.MOTOR_TO_TURRET_GEAR_RATIO * Constants.REVOLUTIONS_PER_ENCODER_TICK * 360) % 360;
        return degrees - midPos;
    }

    //Sets the encoder to where the hallEffect sensor should be (midPos)
    public void resetTurretEncoder() {
        double position = midPos * degrees2Rotations; //In degrees
        rotateMotor.set(ControlMode.Position, position);
    }

    public double getMinPos() { return minPos; }
    public double getMidPos() { return midPos; }
    public double getMaxPos() { return maxPos; }

    //Hall effect sensors are true when the magnet is not near, so it's inverted to be intuitive again
    public boolean isOnHallEffect() { 
        SmartDashboard.putBoolean("Hall Effect", !hallEffect.get());
        return !hallEffect.get();
    }
}