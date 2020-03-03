package frc.robot;

/* Normal Imports */

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.XboxController.Button;

/* Command Imports */

import frc.robot.commands.Climb;
import frc.robot.commands.ManualClimb;
import frc.robot.commands.ResetClimb;
import frc.robot.commands.ShooterSpinDown;
import frc.robot.commands.ShooterSpinUp;
import frc.robot.commands.extendIntake;
import frc.robot.commands.retractIntake;
import frc.robot.commands.reverseIntake;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ChaseBall;
import frc.robot.commands.HomeTurret;
import frc.robot.commands.LockOnTurret;
import frc.robot.commands.OscillateTurret;
import frc.robot.commands.ConveyorIntake;
import frc.robot.commands.ConveyorShoot;

  /* Subsystem Imports */

import frc.robot.subsystems.Drivetrain;
// import frc.robot.subsystems.Turret;
// import frc.robot.subsystems.ConveyorSubsystem;
// import frc.robot.subsystems.Intake;
// import frc.robot.subsystems.Shooter;

public class RobotContainer {

    /* Get Instances */

  private Drivetrain m_Drivetrain = Drivetrain.getInstance();
  private Controller m_Controller = Controller.getInstance();
  // private Turret turret = Turret.getInstance();
  // private ConveyorSubsystem m_ConveyorSubsystem = ConveyorSubsystem.getInstance();
  // private final Intake m_Intake = Intake.getInstance();
  // private final Shooter m_Shooter = Shooter.getInstance();

    /* Create Joysticks */

  private final Joystick leftStick = new Joystick(0);
  private final Joystick rightStick = new Joystick(1);
  private final Joystick XboxController = new Joystick(2);

    /* Creates the commands */

  private Command oscillateTurret = new OscillateTurret();
  private Command homeTurret = new HomeTurret();
  private Command lockOnTurret = new LockOnTurret();

  public RobotContainer() {
    configureButtonBindings();

    m_Drivetrain.setDefaultCommand(new ArcadeDrive(
      () -> m_Controller.getSpeed(), () -> m_Controller.getRot()));
  }

  private void configureButtonBindings() {

    /* Drivetrain Commands */

    System.out.println("1");
    m_Controller.getBallChaseButton().whenHeld(new ChaseBall()); //Example of what to do

    /* Intake Commands */

    extendIntakeButton.whenPressed(new extendIntake());
    reverseIntakeButton.whenPressed(new reverseIntake());
    retractIntakeButton.whenPressed(new retractIntake());

    /* Conveyor Commands */

    shootButton.whenPressed(new ConveyorShoot());
    ciButton.whenPressed(new ConveyorIntake());

    /* Turret Commands */

    new JoystickButton(XboxController, Button.kA.value)
      .whenPressed(homeTurret);
    new JoystickButton(XboxController, Button.kX.value)
      .whenPressed(oscillateTurret);
    new JoystickButton(XboxController, Button.kB.value)
      .whenPressed(lockOnTurret);

    /* Shooter Commands */

    speedButton.whenHeld(new ShooterSpinUp());
    slowButton.whenHeld(new ShooterSpinDown());

    /* Climber Commands */
    
    resetButton.whenPressed(new ResetClimb());
    climbButton.whenPressed(new Climb());
    manualButton.whenHeld(new ManualClimb(() -> leftStick.getY()));

    m_Controller.getShootButton().whenPressed(
      new SequentialCommandGroup(
        new ParallelRaceGroup(
          new LockOnTurret(),
          new ArcadeDrive(() -> 0, () -> 0),
          new SequentialCommandGroup(
            new ShooterSpinUp(), //end: if at velocity
            new CheckInRange(), //end: if true
            new ConveyorShoot() //end: stop conveyor
          )
        ), new ParallelCommandGroup(
          new ShooterSpinDown(), //end: shooter is stopped
          new HomeTurret() //end: 
        )
      )
    );

    m_Controller.getBallChaseButton().whenHeld(
      new SequentialCommandGroup(
        new extendIntake(),
        new ChaseBall()
      )
    );
  }
}
