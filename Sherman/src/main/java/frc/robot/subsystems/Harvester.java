package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.HarvesterConstants;

public class Harvester extends SubsystemBase {
  private static Harvester instance = new Harvester();

  private VictorSPX motor = new VictorSPX(HarvesterConstants.MOTOR_ID);
  private DoubleSolenoid leftPiston = new DoubleSolenoid(HarvesterConstants.LEFT_PISTON_ID_1, HarvesterConstants.LEFT_PISTON_ID_2);
  private DoubleSolenoid rightPiston = new DoubleSolenoid(HarvesterConstants.RIGHT_PISTON_ID_1, HarvesterConstants.RIGHT_PISTON_ID_2);

  public static Harvester getInstance() {
    return instance;
  }

  public void setPercentControl(double percent) {
    motor.set(ControlMode.PercentOutput, percent);
  }

  public void deploy() {
    leftPiston.set(Value.kForward);
    rightPiston.set(Value.kForward);
  }

  public void retract() {
    leftPiston.set(Value.kReverse);
    rightPiston.set(Value.kReverse);
  }
}
