
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class Turret extends SubsystemBase {

    private static Turret instance = new Turret();

    //Sensors
    private DigitalInput hallEffect = new DigitalInput(0);

    //Motors (with CAN IDs)
    private TalonSRX rotateMotor = new TalonSRX(33); 
    private WPI_TalonFX flywheelMotor = new WPI_TalonFX(22);
    private TalonSRX boosterMotor = new TalonSRX(11);

    //Measured in Degrees
    private double minPos = -88.0; //-88
    private double midPos = 4.0; //Degrees have been reduced to avoid hardstops
    private double maxPos = 94.0; //92

    //Measurements
    private final double degrees2Rotations = (double)1 / 360; 

    private PIDController pid = new PIDController(0.0175, 0.0001, 0.001);

    private double wantedAngle = 0.0;
    private double currentAngle = 0.0;
    private double lastAngle = 0.0;
    private boolean currentHall = false;
    private boolean lastHall = false;

    public Turret() {
      setBoosterOutput(0.0);
      setFlywheelOutput(0.0);
      setTurretAngle((int)midPos);
    }

    @Override
    public void periodic() {
        getFlywheelOutput();
        isOnHallEffect();
        getTurretAngle();
    }

    public static Turret getInstance() {
        return instance;
    }

    public double getFlywheelOutput() {
      double output = flywheelMotor.getMotorOutputPercent();
      SmartDashboard.putNumber("flyWheel (%)Output", output);
      return output;
    }

    public void setFlywheelOutput(double percentOutput) {
      flywheelMotor.set(ControlMode.PercentOutput, percentOutput);
    }

    public void setBoosterOutput(double percentOutput) {
      boosterMotor.set(ControlMode.PercentOutput, percentOutput);
    }

    public double getTurretPosition() {
        return rotateMotor.getSelectedSensorPosition(0);
    }

    public void setTurretPosition(double wantedAngle) {
        this.wantedAngle = wantedAngle;
        if(wantedAngle > maxPos) {
            wantedAngle = maxPos;
        } else if(wantedAngle < minPos) {
            wantedAngle = minPos;
        }
        currentAngle = getTurretAngle();
        double output = -1 * pid.calculate(currentAngle, wantedAngle);
        double error = wantedAngle - currentAngle;
        // if (Math.abs(error) > 90) {
          if (output > Constants.TURRET_MAX_ROTATION_OUTPUT) {
            output = Constants.TURRET_MAX_ROTATION_OUTPUT;
          } else if (output < -Constants.TURRET_MAX_ROTATION_OUTPUT) {
            output = -Constants.TURRET_MAX_ROTATION_OUTPUT;
          }

        rotateMotor.set(ControlMode.PercentOutput, output);
    }

    public void setTurretPercentOut(double percent) {
        rotateMotor.set(ControlMode.PercentOutput, percent);
        calibratePosition();
    }

    public void setTurretAngle(int angle) {
        rotateMotor.setSelectedSensorPosition(angle);
    }

    //Returns the angle offset where 0 degrees is where the turret and robot are facing the same direction
    public double getTurretAngle() {
        double angle = -1.0 * getTurretPosition() * Constants.MOTOR_TO_TURRET_GEAR_RATIO * Constants.REVOLUTIONS_PER_ENCODER_TICK * 360.0;
        SmartDashboard.putNumber("Actual Angle", currentAngle);
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

      if(((currentAngle - lastAngle) > 0 && (lastHall == false && currentHall == true)) || 
      ((currentAngle - lastAngle) < 0 && (lastHall == true && currentHall == false))) { 
          rotateMotor.setSelectedSensorPosition(0);
          System.out.println("true");
      }
      lastAngle = currentAngle;
      lastHall = currentHall;
    }
}