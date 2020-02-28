package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ChaseBall;
import frc.robot.commands.Climb;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private Compressor m_Compressor = new Compressor();
  private Controller m_Controller = Controller.getInstance();

  private Drivetrain m_Drivetrain = Drivetrain.getInstance();

  public RobotContainer() {
    configureButtonBindings();
    m_Compressor.start();
    m_Drivetrain.setDefaultCommand(new ArcadeDrive(() -> m_Controller.getArcadeDriveSpeed(), () -> m_Controller.getArcadeDriveRot()));
  }

  private void configureButtonBindings() {
    m_Controller.getBallChaseButton().whenHeld(new ChaseBall());
    m_Controller.getClimbButton().whenPressed(new Climb());
  }
}
