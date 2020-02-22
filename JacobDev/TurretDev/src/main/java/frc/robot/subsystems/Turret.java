
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {

    private static Turret instance = new Turret();

    //Sensors
    private DigitalInput hallEffect = new DigitalInput(0);
    private TalonSRX rotateMotor = new TalonSRX(11);
    private CANEncoder encoder;
    
    
    //Measured in Degrees
    private double minPos = 0;
    private double midPos = 144;
    private double maxPos = 288;

    //Measurements
    private int ticks = (int)(rotateMotor.getSelectedSensorPosition(0) * encoder.getCountsPerRevolution());
    private double degrees = (double)360 / encoder.getCountsPerRevolution();
    private final double degrees2Rotations = (double)1 / 360;

    private PIDController pid = new PIDController(0.01, 0.01, 0.01);


    public static Turret getInstance() {
        return instance;
    }

    public void setTurretPosition(double wantedDegrees) {
        //For tuning in the smartDashboard
        //pid.setP(SmartDashboard.getNumber("kP", 0));
        //pid.setI(SmartDashboard.getNumber("kI", 0));
        //pid.setD(SmartDashboard.getNumber("kD", 0));
        rotateMotor.set(ControlMode.Position, pid.calculate(degrees, wantedDegrees) * Constants.TURRET_PERCENT_OUTPUT * Constants.TURRET_TO_MOTOR_GEAR_RATIO);
    }

    public void setTurretPercentOut(double percent) {
        rotateMotor.set(ControlMode.PercentOutput, percent);
    }

    //Returns the angle offset where 0 degrees is where the turret and robot are facing the same direction
    public double getTurretAngle() {
        return degrees - midPos;
    }

    //Sets the encoder to where the hallEffect sensor should be (midPos)
    public void resetTurretEncoder() {
        encoder.setPosition(midPos * degrees2Rotations);
    }

    public double getMinPos() { return minPos; }

    public double getMaxPos() { return maxPos; }

    //Hall effect sensors are true when the magnet is not near, so it's inverted to be intuitive again
    public boolean isOnHallEffect() { return !hallEffect.get(); }
}