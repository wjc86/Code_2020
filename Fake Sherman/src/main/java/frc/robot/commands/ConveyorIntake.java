package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

import java.sql.Timestamp;
import java.util.Date;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ConveyorIntake extends CommandBase {
  private ConveyorSubsystem m_ConveyorSubsystem = ConveyorSubsystem.getInstance();

  private double intake_speed = 0.25;

  private boolean inletBallDetected = false;
  private boolean fullyLoaded = false;
  private boolean done = false;
  public Timestamp calltime;

  /**
   * Creates a new ConveyorIntake.
   */
  public ConveyorIntake() {
    // Use addRequirements() here to declare subsystem dependencies.
    Date date = new Date();
    long time = date.getTime();
    calltime = new Timestamp(time);
    addRequirements(m_ConveyorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    inletBallDetected = false;
    fullyLoaded = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean topLine = m_ConveyorSubsystem.isTopLineClosed();
    boolean bottomLine = m_ConveyorSubsystem.isBottomLineClosed();
    System.out.print("Conveyor Intake | ");
    System.out.print(calltime + " | ");
    System.out.print("Top: " + topLine + " | ");
    System.out.println("Bottom: " + bottomLine + " | ");
    if (!m_ConveyorSubsystem.isTopLineClosed()) {
      fullyLoaded = true;
      done = true;
      m_ConveyorSubsystem.conveyorMotor.set(ControlMode.Velocity, 0);
    }
    if (!fullyLoaded) {
      if (!inletBallDetected) {
        if (!m_ConveyorSubsystem.isBottomLineClosed()) {
          inletBallDetected = true;
          m_ConveyorSubsystem.conveyorMotor.set(ControlMode.MotionProfile, 10);
        }
      }
      if (inletBallDetected) {
        if (m_ConveyorSubsystem.isBottomLineClosed()) {
          inletBallDetected = false;
          done = true;
          m_ConveyorSubsystem.conveyorMotor.set(ControlMode.Velocity, 0);
        }
      }
    }

    // if(!lastSensorState && m_ConveyorSubsystem.bottomLine()){
    // holdPosition = m_ConveyorSubsystem.getPosititon();
    // }
    // SmartDashboard.putNumber("Hold Position", holdPosition);
    // if (m_ConveyorSubsystem.topLine()){
    // if (m_ConveyorSubsystem.bottomLine()){
    // m_ConveyorSubsystem.setPosititon(holdPosition);
    // SmartDashboard.putBoolean("Top and Bottom", true);
    // }
    // else {
    // SmartDashboard.putBoolean("Top and Bottom", false);
    // m_ConveyorSubsystem.move(-0.1);
    // }
    // }
    // else {
    // m_ConveyorSubsystem.move(0);
    // }
    // lastSensorState = m_ConveyorSubsystem.bottomLine();
    // m_ConveyorSubsystem.setPosition()
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (done) {
      return true;
    }
    return false;
  }
}
