package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;

public class Controller {
    private final Joystick speedStick = new Joystick(0);
    private final Joystick rotStick = new Joystick(1);

    public double getSpeed() {
        if(!speedStick.getRawButtonPressed(0)){
            if(Math.abs(speedStick.getY()) < Constants.controllerDeadband) {
                return 0.0;
            } else if (speedStick.getY() < 0) {
                return (speedStick.getY() + Constants.controllerDeadband) * Constants.slowdownSpeed * -1.0;
            } else {
                return (speedStick.getY() - Constants.controllerDeadband) * Constants.slowdownSpeed * -1.0;
            }
        } else {
            if(Math.abs(speedStick.getY()) < Constants.controllerDeadband) {
                return 0.0;
            } else if (speedStick.getY() < 0) {
                return (speedStick.getY() + Constants.controllerDeadband) * -1.0;
            } else {
                return (speedStick.getY() - Constants.controllerDeadband) * -1.0;
            }
        }
    }

    public double getRot() {
        if(Math.abs(rotStick.getX()) < Constants.controllerDeadband) {
            return 0.0;
        } else if (rotStick.getX() < 0) {
            return (rotStick.getX() + Constants.controllerDeadband) * Constants.slowdownRot * -1.0;
        } else {
            return (rotStick.getX() - Constants.controllerDeadband) * Constants.slowdownRot * -1.0;
        }
        
    }
}
