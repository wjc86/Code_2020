package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  public Joystick control;
  public CANSparkMax cMotor;
  public CANPIDController cPID;
  public CANEncoder cEncoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;


  public Timer time = new Timer();
  // public double timTim = 0.0;
  public double percentage = 0.5;
  public double speed = 0;
  

  @Override
  public void robotInit() {
    control = new Joystick(1);
    cMotor = new CANSparkMax(1, MotorType.kBrushless);
    cMotor.restoreFactoryDefaults();
    cPID = cMotor.getPIDController();
    cEncoder = cMotor.getEncoder();

    //PID coefficients
    kP = 6e-5;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0.000015;
    kMaxOutput = 1;
    kMinOutput = -1;
    maxRPM = 5700;

    cPID.setP(kP);
    cPID.setI(kI);
    cPID.setD(kD);
    cPID.setIZone(kIz);
    cPID.setFF(kFF);
    cPID.setOutputRange(kMinOutput, kMaxOutput);

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);

    // cMotor.getEncoder().setVelocityConversionFactor(1.0/*/60.0/8.75*Units.inchesToMeters(2)*Math.PI*/);
  }

  @Override
  public void teleopPeriodic() {
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);

    if((p != kP)) {cPID.setP(p); kP = p;}
    if((i != kI)) {cPID.setI(i); kI = i;}
    if((d != kD)) {cPID.setD(d); kD = d;}
    if((iz != kIz)) {cPID.setIZone(iz); kIz = iz;}
    if((ff != kFF)) {cPID.setFF(ff); kFF = ff;}
    if((max != kMaxOutput) || (min != kMinOutput)) {
      cPID.setOutputRange(min, max);
      kMinOutput = min; kMaxOutput = max;
    }

    double setPoint = control.getY() * maxRPM;
    cPID.setReference(setPoint , ControlType.kVelocity);

    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("ProcessVariable", cEncoder.getVelocity());

    // if (control.getRawButton(2) && time.get() <= 0.02) {
    //   cMotor.set(percentage);
    //   time.start();
    //   speed = cMotor.getEncoder().getVelocity();
    // }
    // if (time.get() > 0.02) {
    //    cMotor.set(0);
    //    time.stop();
    // }       
    // if (control.getRawButton(1)) {
    //   time.reset();
    // }
      
      
    // SmartDashboard.putNumber("speed", speed);
    // SmartDashboard.putNumber("time", time.get());
  }
}