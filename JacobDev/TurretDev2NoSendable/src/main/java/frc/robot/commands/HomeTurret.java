
package frc.robot.commands;

import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HomeTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();
  private OscillateTurret oscillateTurret = new OscillateTurret();

  public HomeTurret() { }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(turret.getTurretAngle() < 90) {
      //turret.setTurretPosition();
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if(turret.isOnHallEffect()) {
        turret.setTurretAngle(0);
        return true;
    }
    return false;
  }
}
