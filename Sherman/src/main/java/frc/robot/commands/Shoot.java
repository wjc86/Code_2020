/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Shoot extends SequentialCommandGroup {
  /**
   * Creates a new Shoot.
   */
  public Shoot() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      SequentialCommandGroup(new LockOn, //Initial Lock and Ranging
      new ParallelDeadlineGroup(new LockOn, new SequentialCommandGroup(new ShootShooter,new ShootConveyor,new DisableShooter)));//since ShootShooter needs to be interrupted, will it ever exit to Shoot Conveyor? may need to have exit condition based on spin up
    }
}
