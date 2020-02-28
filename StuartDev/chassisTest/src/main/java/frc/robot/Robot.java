package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


public class Robot extends TimedRobot {
  private WPI_TalonFX leftMaster;
  private WPI_TalonFX leftFollower;
  private WPI_TalonFX rightMaster;
  private WPI_TalonFX rightFollower;
  private DoubleSolenoid shifter;
  private Compressor compressor;
  private Joystick rightStick;
  private Joystick leftStick;

  private int leftMasterID = 0;
  private int leftFollowerID = 0;
  private int rightMasterID = 0;
  private int rightFollowerID = 0;
  private int shifterID1 = 0;
  private int shifterID2 = 0;
  private int leftStickID = 0;
  private int rightStickID = 0;

  @Override
  public void robotInit() {
    leftMaster = new WPI_TalonFX(leftMasterID);
    leftFollower = new WPI_TalonFX(leftFollowerID);
    rightMaster = new WPI_TalonFX(rightMasterID);
    rightFollower = new WPI_TalonFX(rightFollowerID);
    shifter = new DoubleSolenoid(shifterID1, shifterID2);
    compressor = new Compressor();
    leftStick = new Joystick(leftStickID);
    rightStick = new Joystick(rightStickID);
    compressor.start();
    shifter.set(Value.kForward);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopPeriodic() {
    drive();
    shift();
  }

  public void drive() {
    leftMaster.set(leftStick.getY());
    leftFollower.follow(leftMaster);
    rightMaster.set(rightStick.getY());
    rightFollower.follow(rightMaster);
  }

  public void shift() {
    if(shifter.get() == Value.kForward && leftStick.getRawButtonPressed(1)) {
      shifter.set(Value.kReverse);
    }
    if(shifter.get() == Value.kReverse && leftStick.getRawButtonPressed(1)) {
      shifter.set(Value.kForward);
    }
  }

  @Override
  public void testPeriodic() {
  }
}
