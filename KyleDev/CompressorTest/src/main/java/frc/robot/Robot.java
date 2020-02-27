package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
  private Compressor gen = new Compressor();
  Joystick Joy = new Joystick(0);
  DoubleSolenoid piston1 = new DoubleSolenoid(1, 2);
  // DoubleSolenoid piston2 = new DoubleSolenoid(3, 4);
  // DoubleSolenoid piston3 = new DoubleSolenoid(5, 6);

  @Override
  public void robotInit() {
    gen.start();
  }

  @Override
  public void teleopPeriodic() {
    if(Joy.getRawButton(3)) {gen.start();}

    if(Joy.getRawButton(4)) {gen.stop();}

    if(Joy.getRawButton(5)) {piston1.set(Value.kForward);}

    if(Joy.getRawButton(6)) {piston1.set(Value.kReverse);}
  }
}
