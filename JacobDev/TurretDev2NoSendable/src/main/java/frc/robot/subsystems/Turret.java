
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

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
    private DigitalInput clicker = new DigitalInput(1);
    private TalonSRX rotateMotor = new TalonSRX(11); //Might be (port) 12    

    //Measured in Degrees
    private double minPos = 0;
    private double midPos = 90;
    private double maxPos = 180;

    private int yesCount = 0;


    //Measurements
    private int ticks = (int)(rotateMotor.getSelectedSensorPosition(0) * Constants.ENCODER_TICKS_PER_REVOLUTION);
    private final double degrees2Rotations = (double)1 / 360;

    private PIDController pid = new PIDController(0.0001, 0.0001, 0.0001);

    public Turret() {}


    public static Turret getInstance() {
        return instance;
    }

    public double getTurretPosition() {
        return rotateMotor.getSelectedSensorPosition(0);
    }

    public void set45() {
        setTurretPosition(45);
    }

    public void setNeg45() {
        setTurretPosition(-45);
    }

    public void setTurretPosition(double wantedDegrees) {
        //For tuning in the smartDashboard
        //pid.setP(SmartDashboard.getNumber("kP", 0));
        //pid.setI(SmartDashboard.getNumber("kI", 0));
        //pid.setD(SmartDashboard.getNumber("kD", 0));
        if(isOnHallEffect()) {
            yesCount++;
        }
        double currentDegrees = getTurretAngle();
        double output = pid.calculate(currentDegrees, wantedDegrees) * Constants.TURRET_PERCENT_OUTPUT;
        System.out.println("Clicker: " + isOnHallEffect());
        System.out.println("HallEffect: " + isOnHallEffect());
        System.out.println("YAHHH: " + yesCount);
        System.out.println(output);
        rotateMotor.set(ControlMode.PercentOutput, output);
    }

    public void setTurretPercentOut(double percent) {
        rotateMotor.set(ControlMode.PercentOutput, percent);
    }

    //Returns the angle offset where 0 degrees is where the turret and robot are facing the same direction
    public double getTurretAngle() {
        double degrees = getTurretPosition() * Constants.MOTOR_TO_TURRET_GEAR_RATIO * Constants.REVOLUTIONS_PER_ENCODER_TICK * 360;
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
        return hallEffect.get();
    }

    public Boolean getClicker() { 
        return clicker.get();
    }
}