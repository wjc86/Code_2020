package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

public class Controller {
    private final Joystick speedStick = new Joystick(0);
    private final Joystick rotStick = new Joystick(1);

    public double getSpeed() {
        if(!speedStick.getRawButton(1)){
            if(Math.abs(speedStick.getY()) < Constants.controllerDeadband) {
                return 0.0;
            } else if (speedStick.getY() < 0) {
                return (speedStick.getY() + Constants.controllerDeadband) * Constants.slowdownSpeed * Constants.kSpeedMultiplier * -1.0;
            } else {
                return (speedStick.getY() - Constants.controllerDeadband) * Constants.slowdownSpeed * Constants.kSpeedMultiplier * -1.0;
            }
        } else {
            if(Math.abs(speedStick.getY()) < Constants.controllerDeadband) {
                return 0.0;
            } else if (speedStick.getY() < 0) {
                return (speedStick.getY() + Constants.controllerDeadband) * Constants.kSpeedMultiplier * -1.0;
            } else {
                return (speedStick.getY() - Constants.controllerDeadband) * Constants.kSpeedMultiplier * -1.0;
            }
        }
    }

    public double getRot() {
        if(!speedStick.getRawButton(1)){
            if(Math.abs(rotStick.getX()) < Constants.controllerDeadband) {
                return 0.0;
            } else if (rotStick.getX() < 0) {
                return (rotStick.getX() + Constants.controllerDeadband) * Constants.slowdownRot * Constants.kRotationMultiplier * -1.0;
            } else {
                return (rotStick.getX() - Constants.controllerDeadband) * Constants.slowdownRot * Constants.kRotationMultiplier * -1.0;
            }
        } else {
            if(Math.abs(rotStick.getX()) < Constants.controllerDeadband) {
                return 0.0;
            } else if (rotStick.getX() < 0) {
                return (rotStick.getX() + Constants.controllerDeadband) * Constants.kRotationMultiplier * -1.0;
            } else {
                return (rotStick.getX() - Constants.controllerDeadband) * Constants.kRotationMultiplier * -1.0;
            }
        }
    }

    public Boolean resetOdometry() {
        return speedStick.getRawButton(6);
    }

    public Boolean trajectoryMode() {
        if(rotStick.getRawButtonPressed(1)){
            Robot.startTrajectoryTime = Timer.getFPGATimestamp();
        }
        if(rotStick.getRawButton(1)){
            return true;
        } else {
            return false;
        }
    }

    public Boolean ballChaseMode() {
        if(speedStick.getRawButton(5)){
            return true;
        } else {
            return false;
        }
    }
}
