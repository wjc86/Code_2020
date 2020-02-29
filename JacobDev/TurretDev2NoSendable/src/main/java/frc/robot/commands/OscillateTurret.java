
package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OscillateTurret extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Turret turret = Turret.getInstance();

  private boolean isTurningRight;

  public OscillateTurret() {
    addRequirements(turret);
    isTurningRight = true;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(isTurningRight) {
        System.out.println("RIGHT");
        //Go to the minPos
        turret.setTurretPosition(turret.getMinPos());
        //Turret is less than the minPos so turn around
        if(Math.abs(turret.getTurretAngle() - turret.getMinPos()) < 5) {
            isTurningRight = false;
        }  
    } else {
        System.out.println("LEFT");       
        //Go to the minPos
        turret.setTurretPosition(turret.getMaxPos());
        //Turret is greater than the maxPos so turn around
        if(Math.abs(turret.getTurretAngle() - turret.getMaxPos()) < 5) {
            isTurningRight = true;
        }
    }
    SmartDashboard.putNumber("Angle", turret.getTurretAngle());
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
