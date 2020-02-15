package frc.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

public class ColorSensor{
    private Talon mMotor = new Talon(4);
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 mColorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch mColorMatcher = new ColorMatch();

    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private static final int YELLOW_CALIBRATION = 90;
    private static final int RED_CALIBRATION = 30;
    private static final int GREEN_CALIBRATION = 130;
    private static final int CYAN_CALIBRATION = 184;

    private static ColorSensor.color mCurrentColor = ColorSensor.color.UNKNOWN;

    public void setup(){
        mColorMatcher.addColorMatch(kBlueTarget);
        mColorMatcher.addColorMatch(kGreenTarget);
        mColorMatcher.addColorMatch(kRedTarget);
        mColorMatcher.addColorMatch(kYellowTarget);
    }

    public void manualOverride(){
        mMotor.set(0.5);
    }

    /*public ColorSensor.color detectColor(){
        Color detectedColor = mColorSensor.getColor();

        double[] RGB = {detectedColor.red, detectedColor.green, detectedColor.blue};
        
        double min, max, delta, h;
        min = Math.min(Math.min(RGB[0], RGB[1]), RGB[2]);
        max = Math.max(Math.max(RGB[0], RGB[1]), RGB[2]);
        delta = max - min;
        while(true){
            if(max == 0){
                h = -1;
                break;
            }
            if(RGB[0] == max){
                h = (RGB[1] - RGB[2])/delta;
            } else if(RGB[1] == max){
                h = 2 + (RGB[2] - RGB[0])/delta;
            } else {
                h = 4 + (RGB[0] - RGB[1])/delta;
            }
            h *= 60;
            if(h < 0){
                h += 360;
            }
        }

        if((h >= 0 && h <= ((RED_CALIBRATION + YELLOW_CALIBRATION)/2)) || (h >= 300 && h <= 360)){
            mCurrentColor = ColorSensor.color.RED;
          } else if(h >= ((RED_CALIBRATION + YELLOW_CALIBRATION)/2) && h <= ((YELLOW_CALIBRATION + GREEN_CALIBRATION)/2)){
            mCurrentColor = ColorSensor.color.YELLOW;
          } else if(h >= ((YELLOW_CALIBRATION + GREEN_CALIBRATION)/2) && h <= ((GREEN_CALIBRATION + CYAN_CALIBRATION)/2)){
            mCurrentColor = ColorSensor.color.GREEN;
          } else if(h >= ((GREEN_CALIBRATION + CYAN_CALIBRATION)/2) && h <= 240){
            mCurrentColor = ColorSensor.color.CYAN;
          } else {
            mCurrentColor = ColorSensor.color.UNKNOWN;
          }
          return mCurrentColor;
    }*/

    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();
    private int Last_Color = 0;
    private int Color_Counter = 0;
    private static final int COLOR_LIMIT = 5;
    
    public void detectColor(){
        /**
         * The method GetColor() returns a normalized color value from the sensor and can be
         * useful if outputting the color to an RGB LED or similar. To
         * read the raw color, use GetRawColor().
         * 
         * The color sensor works best when within a few inches from an object in
         * well lit conditions (the built in LED is a big help here!). The farther
         * an object is the more light from the surroundings will bleed into the 
         * measurements and make it difficult to accurately determine its color.
         */
        Color detectedColor = m_colorSensor.getColor();
    
        /**
         * Run the color match algorithm on our detected color
         */
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
        int color;
    
        double[] perRGB = {detectedColor.red, detectedColor.green, detectedColor.blue};
        double[] RGB = {perRGB[0], perRGB[1], perRGB[2]};
    
        double min, max, delta, h, s, v;
        min = Math.min(Math.min(RGB[0], RGB[1]), RGB[2]);
        max = Math.max(Math.max(RGB[0], RGB[1]), RGB[2]);
        v = max;
        delta = max - min;
        while(true){
          if(max != 0){
            s = delta/max;
          } else {
            s = 0;
            h = -1;
            break;
          }
          if(RGB[0] == max){
            h = (RGB[1] - RGB[2])/delta;
          } else if(RGB[1] == max){
            h = 2 + (RGB[2] - RGB[0])/delta;
          } else {
            h = 4 + (RGB[0] - RGB[1])/delta;
          }
          h *= 60;
          if(h < 0){
            h += 360;
          }
          break;
        }
    
        if((h >= 0 && h <= ((RED_CALIBRATION + YELLOW_CALIBRATION)/2)) || (h >= 300 && h <= 360)){
          colorString = "Red";
          color = 3;
        } else if(h >= ((RED_CALIBRATION + YELLOW_CALIBRATION)/2) && h <= ((YELLOW_CALIBRATION + GREEN_CALIBRATION)/2)){
          colorString = "Yellow";
          color = 4;
        } else if(h >= ((YELLOW_CALIBRATION + GREEN_CALIBRATION)/2) && h <= ((GREEN_CALIBRATION + CYAN_CALIBRATION)/2)){
          colorString = "Green";
          color = 2;
        } else if(h >= ((GREEN_CALIBRATION + CYAN_CALIBRATION)/2) && h <= 240){
          colorString = "Cyan";
          color = 1;
        } else {
          colorString = "Unknown";
          color = 0;
        }
        if (color == Last_Color) {
          Color_Counter++;
        }
        else {
          Color_Counter = 0;
        }
        Last_Color = color;
        if (Color_Counter >= COLOR_LIMIT){
          SmartDashboard.putNumber("Color Graph", color);
          SmartDashboard.putString("Color", colorString);
          SmartDashboard.putNumber("H", h);
          SmartDashboard.putNumber("S", s);
          SmartDashboard.putNumber("V", v);
        }
    }

    public void rotateToColor(String color){}

    public void deploy(){}

    public void retract(){}

    public void spin(){}

    public void approach(){}

    public void rotateNumber(){}

    public ColorSensor.color getCurrentColor(){
        return mCurrentColor;
    }

    public void outputToSmartDashboard(){
        SmartDashboard.putString("Detected Color", mCurrentColor.toString());
    }

    public enum color{
        RED,
        GREEN,
        CYAN,
        YELLOW,
        UNKNOWN
    }
}