package frc.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;

import java.lang.ProcessBuilder.Redirect;

import com.revrobotics.ColorMatch;

public class ColorSensor{
    private Talon mMotor = new Talon(500);
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 mColorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch mColorMatch = new ColorMatch();

    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private static final int YELLOW_CALIBRATION = 90;
    private static final int RED_CALIBRATION = 30;
    private static final int GREEN_CALIBRATION = 130;
    private static final int CYAN_CALIBRATION = 184;

    public void setup(){}

    public void override(){
        mMotor.set(0.5);
    }

    private String detectColor(){
        return "";
    }

    public void rotateToColor(String color){}

    public void deploy(){}

    public void retract(){}

    public void spin(){}

    public void approach(){}

    public void rotateNumber(){}

    public enum colors{}
}