/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Claw {

    // solP is the big piston that pivots the claw up and down. solG is the opening and closing of it.
    
    DoubleSolenoid solP = new DoubleSolenoid(1,4,3);
    DoubleSolenoid solG = new DoubleSolenoid(0,7,0);

    // Prepares to pick up a ball by pivoting down, opening the claw and then letting the claw rest.
    public void prepare() {
        solP.set(DoubleSolenoid.Value.kForward);
        solG.set(DoubleSolenoid.Value.kForward);
        solG.set(DoubleSolenoid.Value.kOff);   
    }
    // Opens the claw, pushing a ball out if there is one held.
    public void shoot() {
        solG.set(DoubleSolenoid.Value.kForward);
    }
    // Pivots the claw up against the robot.
    public void up() {
        solP.set(DoubleSolenoid.Value.kReverse);
    }
    // Pivots the claw down, pointing it outward.
    public void down() {
        solP.set(DoubleSolenoid.Value.kForward);
    }
    // Closes the claw to grab a ball.
    public void grab() {
        solG.set(DoubleSolenoid.Value.kReverse);
    }
}