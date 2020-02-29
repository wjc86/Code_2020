
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
    private double minPos = -88.0;
    private double midPos = 4.0;
    private double maxPos = 94.0;

    //Measurements
    private final double degrees2Rotations = (double)1 / 360; 

    private PIDController pid = new PIDController(0.0175, 0.0001, 0.001);

    private double currentAngle = 0.0;
    private double lastAngle = 0.0;
    private boolean currentHall = false;
    private boolean lastHall = false;

    public Turret() {}

    @Override
    public void periodic() {
        isOnHallEffect();
        getTurretAngle();
        calibratePosition();
    }


    public static Turret getInstance() {
        return instance;
    }

    public double getTurretPosition() {
        return rotateMotor.getSelectedSensorPosition(0);
    }

    public void setTurretPosition(double wantedDegrees) {
        if(wantedDegrees > maxPos) {
            wantedDegrees = maxPos;
        } else if(wantedDegrees < minPos) {
            wantedDegrees = minPos;
        }
        double currentDegrees = getTurretAngle();
        double output = -1 * pid.calculate(currentDegrees, wantedDegrees);
        double error = wantedDegrees - getTurretAngle();
        // if (Math.abs(error) > 90) {
          if (output > 0.1) {
            output = 0.1;
          } else if (output < -0.1) {
            output = -0.1;
          }
        // } else if (Math.abs(error) < 90 && Math.abs(error) > 60) {
        //   if (output > .15) {
        //     output = .15;
        //   } else if (output < -.15) {
        //     output = -.15;
        //   }
        // } else if (Math.abs(error) < 60 && Math.abs(error) > 30) {
        //   if (output > .1) {
        //     output = .1;
        //   } else if (output < -.1) {
        //     output = -.1;
        //   }
        // } else if (Math.abs(error) < 30) {
        //   if (output > .05) {
        //     output = .05;
        //   } else if (output < -.05) {
        //     output = -.05;
        //   }
        SmartDashboard.putNumber("PID Out", output);
        SmartDashboard.putNumber("Actual Angle", currentDegrees);
        SmartDashboard.putNumber("Wanted Angle", wantedDegrees);
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
        double angle = -1.0 * getTurretPosition() * Constants.MOTOR_TO_TURRET_GEAR_RATIO * Constants.REVOLUTIONS_PER_ENCODER_TICK * 360.0;
        SmartDashboard.putNumber("Angle", angle);
        return angle;
    }

    public double getMinPos() { return minPos; }
    public double getMidPos() { return midPos; }
    public double getMaxPos() { return maxPos; }

    //Hall effect sensors are true when the magnet is not near, so it's inverted to be intuitive again
    public boolean isOnHallEffect() { 
        SmartDashboard.putBoolean("Hall Effect", !hallEffect.get());
        return !hallEffect.get();
    }

    public void calibratePosition() {
        currentAngle = getTurretAngle();
        currentHall = isOnHallEffect();

        if(((currentAngle - lastAngle) > 0 && (lastHall == false && currentHall == true)) || ((currentAngle - lastAngle) < 0 && (lastHall == true && currentHall == false))) { 
            rotateMotor.setSelectedSensorPosition(0);
            System.out.println("true");
        }

        lastAngle = currentAngle;
        lastHall = currentHall;
    }
}