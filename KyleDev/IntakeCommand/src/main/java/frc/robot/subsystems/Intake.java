package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class Intake extends SubsystemBase {
  private VictorSPX intakeMotor = new VictorSPX(Constants.intakeMotorCANID);
  private DigitalInput intakeSensor = new DigitalInput(Constants.intakeLineBreakID);
  private DoubleSolenoid intakePiston1 = new DoubleSolenoid(Constants.intakePiston1Forward, Constants.intakePiston1Reverse);
  private DoubleSolenoid intakePiston2 = new DoubleSolenoid(Constants.intakePiston2Forward, Constants.intakePiston2Reverse);
  public boolean isDeployed = false;
  public double speed = 0;

  public static Intake instance = new Intake();

  public static Intake getInstance() {return instance;}

  public Intake() {}

  public void startIntake(double speedPCT) {
    intakeMotor.set(ControlMode.PercentOutput, speedPCT);
  }

  public void pistonDeploy() {
    intakePiston1.set(Value.kForward);
    intakePiston2.set(Value.kForward);
  }

  public void pistonRetract() {
    intakePiston1.set(Value.kReverse);
    intakePiston2.set(Value.kReverse);
  }
  
  @Override
  public void periodic() {}
}
