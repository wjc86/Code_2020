package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake {
    CANSparkMax motor;
    double speed;
    public Intake(int port, double iSpeed) {
        this.motor = new CANSparkMax(port, MotorType.kBrushless);
        this.speed = iSpeed;
    }
    public void start() {
        motor.set(speed);
    }
    public void stop() {
        motor.set(0);
    }
    public void reverse() {
        this.speed = -1 * this.speed;
    }
}