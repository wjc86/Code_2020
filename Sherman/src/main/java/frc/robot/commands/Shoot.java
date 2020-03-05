/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Shoot extends SequentialCommandGroup {
  /**
   * Creates a new Shoot.
   */
  public Shoot() {
    super(
    //   new ParallelRaceGroup(
    //     new LockOn(),
    //     new ArcadeDrive(() -> 0, () -> 0),
    //     new SequentialCommandGroup(
    //       new ShootShooter(), // end: if at velocity
    //       new CheckInRange(), //end: if true
    //       new ShootConveyor() //end: stop conveyor
    //     )
    //   ), new ParallelCommandGroup(
    //     new ShooterSpinDown(), //end: shooter is stopped
    //     new HomeTurret() //end: 
    // )
    );
  }
}
