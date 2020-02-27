package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.constants.ControllerConstants;

public class Controller {
    // Instantiating the instance of Controller to be used system-wide
    private static Controller instance = new Controller();

    // Instantiating necessary joysticks and buttons
    private final Joystick speedStick = new Joystick(ControllerConstants.SPEED_STICK_ID);
    private final Joystick rotStick = new Joystick(ControllerConstants.ROT_STICK_ID);

    private final JoystickButton ballChaseButton = new JoystickButton(speedStick, ControllerConstants.BALL_CHASE_BUTTON_ID);
    private final JoystickButton climbButton = new JoystickButton(speedStick, ControllerConstants.CLIMB_BUTTON_ID);

    // getInstance function for controller
    public static Controller getInstance() {
        return instance;
    }

    // Gets the joystick speed input for normal arcade drive
    public double getArcadeDriveSpeed() {
        // Switches between a fast and slow drive control by multiplying joystick output by a constant
        if (!speedStick.getRawButton(1)) {
            // Adds in deadband logic
            if (Math.abs(speedStick.getY()) < ControllerConstants.DEADBAND) {
                return 0.0;
            } else if (speedStick.getY() < 0) {
                return (speedStick.getY() + ControllerConstants.DEADBAND) * ControllerConstants.SLOW_SPEED_MULT * -1.0;
            } else {
                return (speedStick.getY() - ControllerConstants.DEADBAND) * ControllerConstants.SLOW_SPEED_MULT * -1.0;
            }
        } else {
            // Adds in deadband logic
            if (Math.abs(speedStick.getY()) < ControllerConstants.DEADBAND) {
                return 0.0;
            } else if (speedStick.getY() < 0) {
                return (speedStick.getY() + ControllerConstants.DEADBAND) * -1.0;
            } else {
                return (speedStick.getY() - ControllerConstants.DEADBAND) * -1.0;
            }
        }
    }

    // Gets the joystick rotation input for normal arcade drive
    public double getArcadeDriveRot() {
        if(!speedStick.getRawButton(1)){
            // Adds in deadband logic
            if (Math.abs(rotStick.getX()) < ControllerConstants.DEADBAND) {
                return 0.0;
            } else if (rotStick.getX() < 0) {
                return (rotStick.getX() + ControllerConstants.DEADBAND) * ControllerConstants.SLOW_ROT_MULT * -1.0;
            } else {
                return (rotStick.getX() - ControllerConstants.DEADBAND) * ControllerConstants.SLOW_ROT_MULT * -1.0;
            }
        } else {
            // Adds in deadband logic
            if (Math.abs(rotStick.getX()) < ControllerConstants.DEADBAND) {
                return 0.0;
            } else if (rotStick.getX() < 0) {
                return (rotStick.getX() + ControllerConstants.DEADBAND) * -1.0;
            } else {
                return (rotStick.getX() - ControllerConstants.DEADBAND) * -1.0;
            }
        }
    }

    public Boolean resetOdometry() {
        return speedStick.getRawButton(6);
    }

    // Getters for buttons to be passed through RobotContainer
    public JoystickButton getBallChaseButton() {
        return ballChaseButton;
    } 

    public JoystickButton getClimbButton() {
        return climbButton;
    }
}
