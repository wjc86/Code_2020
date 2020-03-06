package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Until extends CommandBase {
  BooleanSupplier supply;
  public Until(BooleanSupplier supply) {
    this.supply = supply;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished()  {
    if(supply.getAsBoolean()) {
      return true;
    } else {
      return false;
    }
  }
}
