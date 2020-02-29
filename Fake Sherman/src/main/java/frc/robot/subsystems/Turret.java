
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
    private double minPos = -90;
    private double midPos = 0;
    private double maxPos = 90;

    //Measurements
    // private final double degrees2Rotations = (double)1 / 360;

    private PIDController pid = new PIDController(0.03, 0.0001, 0.001);

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
        double currentDegrees = getTurretAngle();
        double output = -1 * pid.calculate(currentDegrees, wantedDegrees) * Constants.TURRET_PERCENT_OUTPUT;
        if(output > Constants.TURRET_MAX_OUTPUT) { output = Constants.TURRET_MAX_OUTPUT; }
        else if (output < -Constants.TURRET_MAX_OUTPUT) { output = -Constants.TURRET_MAX_OUTPUT; }
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
        return -1.0 * getTurretPosition() * Constants.MOTOR_TO_TURRET_GEAR_RATIO * Constants.REVOLUTIONS_PER_ENCODER_TICK * 360.0;
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