
package frc.robot.commands;

import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HomeTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();
  private OscillateTurret oscillateTurret = new OscillateTurret();

  private boolean isHomed;

  public HomeTurret() {
    isHomed = false;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    oscillateTurret.execute();
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if(turret.isOnHallEffect()) {
        return true;
    }
    return false;
  }
}
