package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

import java.sql.Timestamp;
import java.util.Date;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class ConveyorIntake extends CommandBase {
  private ConveyorSubsystem m_ConveyorSubsystem = ConveyorSubsystem.getInstance();

  // private double intake_speed = 0.25;

  private boolean inletBallDetected = false;
  private boolean done = false;
  public Timestamp calltime;

  /**
   * Creates a new ConveyorIntake.
   */
  public ConveyorIntake() {
    addRequirements(m_ConveyorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    inletBallDetected = false;
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
    if (!m_ConveyorSubsystem.getIsFull()){
      if (!inletBallDetected) {
        if (!m_ConveyorSubsystem.isBottomLineClosed()) {
          inletBallDetected = true;
          m_ConveyorSubsystem.setMotionProfile(10);
        }
      }
      if (inletBallDetected) {
        if (m_ConveyorSubsystem.isBottomLineClosed()) {
          inletBallDetected = false;
          done = true;
          m_ConveyorSubsystem.setPercentControl(0);
        }
      }
    } else {
      m_ConveyorSubsystem.setPercentControl(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
