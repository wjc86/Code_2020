package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;

public class Controller {
    private static Controller instance = new Controller();

    private final Joystick speedStick = new Joystick(0);
    private final Joystick rotStick = new Joystick(1);

    private final JoystickButton resetButton = new JoystickButton(speedStick, Constants.resetButtonPort); // 1
    private final JoystickButton climbButton = new JoystickButton(speedStick, Constants.climbButtonPort); // 2
    private final JoystickButton manualButton = new JoystickButton(speedStick, Constants.manualButtonPort); // 3
    private final JoystickButton ciButton = new JoystickButton(speedStick, 4);
    private final JoystickButton ballChaseButton = new JoystickButton(speedStick, 5);
    private final JoystickButton shootButton = new JoystickButton(speedStick, 6);
    
    private final JoystickButton extendIntakeButton = new JoystickButton(rotStick, 1);
    private final JoystickButton reverseIntakeButton = new JoystickButton(rotStick, 2);
    private final JoystickButton retractIntakeButton = new JoystickButton(rotStick, 3);
    private final JoystickButton speedButton = new JoystickButton(rotStick, 4);
    private final JoystickButton slowButton = new JoystickButton(rotStick, 5);

    public static Controller getInstance() {
        return instance;
    }

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

    public JoystickButton getBallChaseButton() {
        System.out.println("2");
        return ballChaseButton;
    } 
    
    public JoystickButton getClimberResetButton() {
        return resetButton;
    }

    public JoystickButton getClimberClimbButton() {
        return climbButton;
    }

    public JoystickButton getClimberManualButton() {
        return manualButton;
    }

    public JoystickButton getConveyorIntakebutton() {
        return ciButton;
    }

    public JoystickButton getShootButton() {
        return shootButton;
    }

    public JoystickButton getIntakeExtendButton() {
        return extendIntakeButton;
    }
}
