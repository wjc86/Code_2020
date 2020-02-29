
package frc.robot.commands;

import frc.robot.VisionClient;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class LockOnTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();
  private OscillateTurret oscillateTurret;
  private VisionClient m_VisionClient = VisionClient.getInstance();

  private boolean seesTarget;
  private double visionAngle;

  public LockOnTurret() {
    addRequirements(turret);
    seesTarget = false;
    oscillateTurret = new OscillateTurret();
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // seesTarget = m_VisionClient.seesG2();
    // visionAngle = m_VisionClient.getAngle2G2();
    if(!seesTarget) {
      oscillateTurret.execute();
      seesTarget = SmartDashboard.getBoolean("seesAG2", false);
    }
    else {
      oscillateTurret.setFinished(true);
      double visionAngle = SmartDashboard.getNumber("angle", 0);
      turret.setTurretPosition(visionAngle + turret.getTurretAngle());
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
