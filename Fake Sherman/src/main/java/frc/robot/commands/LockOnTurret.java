
package frc.robot.commands;

import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class LockOnTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();
  private OscillateTurret oscillateTurret;

  private boolean seesTarget;

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
    if(!seesTarget) {
      oscillateTurret.execute();
      seesTarget = SmartDashboard.getBoolean("seesAG2", false);
    }
    else {
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
