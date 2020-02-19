
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.Talon; //This import should work for 775pros
import edu.wpi.first.wpilibj.PIDContoller;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

    private Turret instance = new Turret();

    //Sensors
    private DigitalInput hallEffect = new DigitalInput(0);
    private CANEncoder encoder = new CANEncoder();
    private Talon rotateMotor = new Talon(0);
    
    //Measured in Rotations
    private double minPos = 0.0;
    private double midPos = 0.4;
    private double maxPos = 0.8;

    //AKA Ticks
    private int counts = encoder.getPosition() * encoder.getCountsPerRevolution();
    
    public static Turret getInstance() {
        return instance;
    }

    public void setTurretPosition(double wantedRotations) {
        PIDContoller pid = new PIDController(0.01, 0.01, 0.01);
        //For tuning in the smartDashboard
        //pid.setP(SmartDashboard.getNumber("kP", 0));
        //pid.setI(SmartDashboard.getNumber("kI", 0));
        //pid.setD(SmartDashboard.getNumber("kD", 0));
        rotateMotor.set(pid.calculate(encoder.getPosition(), wantedRotations) * Constants.TURRET_PERCENT_OUTPUT);
    }

    public void setTurretPercentOut(double percent) {
        rotateMotor.set(percent);
    }

    //Returns the angle offset where 0 degrees is where the turret and robot are facing the same direction
    public double getTurretAngle() {
        double angle = 360 * (encoder.getPosition() - midPos);
        return angle;
    }

    //Sets the encoder to where the hallEffect sensor should be (midPos)
    public void resetTurretEncoder() {
        encoder.setPosition(midPos);
    }

    public void getMinPos() { return minPos; }

    public void getMaxPos() { return maxPos; }

    //Hall effect sensors are true when the magnet is not near, so it's inverted to be intuitive again
    public boolean isOnHallEffect() { return !hallEffect.get(); }

    

    //High level methods - Oscillate, track, and Home
}