package frc.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

public class ColorSensor{
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 mColorSensor = new ColorSensorV3(i2cPort);

    private static final int YELLOW_CALIBRATION = 90;
    private static final int RED_CALIBRATION = 30;
    private static final int GREEN_CALIBRATION = 130;
    private static final int CYAN_CALIBRATION = 184;

    private static ColorSensor.color mCurrentColor = ColorSensor.color.UNKNOWN;
    private ColorSensor.color lastColor = ColorSensor.color.UNKNOWN;
    private int colorCounter = 0;
    private static final int COLOR_LIMIT = 5;
    private ColorSensor.color lastValidColor = ColorSensor.color.UNKNOWN;

    public ColorSensor.color detectColor(){
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
            break;
        }

        if (mCurrentColor == lastColor) {
            colorCounter++;
        }
        else {
          colorCounter = 0;
        }
        lastColor = mCurrentColor;

        if(colorCounter >= COLOR_LIMIT){
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
            lastValidColor = mCurrentColor;
            return mCurrentColor;
        }
        return lastValidColor;
    }
    
    /*public void detectColor(){
        Color detectedColor = m_colorSensor.getColor();
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
        if (color == lastColor) {
          colorCounter++;
        }
        else {
          colorCounter = 0;
        }
        lastColor = color;
        if (colorCounter >= COLOR_LIMIT){
          SmartDashboard.putNumber("Color Graph", color);
          SmartDashboard.putString("Color", colorString);
          SmartDashboard.putNumber("H", h);
          SmartDashboard.putNumber("S", s);
          SmartDashboard.putNumber("V", v);
        }
    }*/

    public void rotateToColor(String color){}

    public void deploy(){}

    public void retract(){}

    public void spin(){}

    public void approach(){}

    public void rotateNumber(){}

    public ColorSensor.color getCurrentColor(){
        return lastValidColor;
    }

    public void outputToSmartDashboard(){
        SmartDashboard.putString("Detected Color", mCurrentColor.toString());
    }

    public ColorSensor.color toColor(String color){
        if(color.equals("RED")){
            return ColorSensor.color.RED;
        } else if(color.equals("GREEN")){
            return ColorSensor.color.GREEN;
        } else if(color.equals("CYAN")){
            return ColorSensor.color.CYAN;
        } else if(color.equals("YELLOW")){
            return ColorSensor.color.YELLOW;
        } else{
            return ColorSensor.color.UNKNOWN;
        }
    }

    public enum color{
        RED, GREEN, CYAN, YELLOW, UNKNOWN;

        public String toString(){
            if(this == ColorSensor.color.RED){
                return "RED";
            } else if(this == ColorSensor.color.GREEN){
                return "GREEN";
            } else if(this == ColorSensor.color.CYAN){
                return "CYAN";
            } else if(this == ColorSensor.color.YELLOW){
                return "YELLOW";
            } else{
                return "UNKNOWN";
            }
        }

        public int toInt(){
            if(this == ColorSensor.color.RED){
                return 1;
            } else if(this == ColorSensor.color.GREEN){
                return 2;
            } else if(this == ColorSensor.color.CYAN){
                return 3;
            } else if(this == ColorSensor.color.YELLOW){
                return 4;
            } else{
                return 0;
            }
        }

        public ColorSensor.color getActualColor(){
            if(this == ColorSensor.color.RED){
                return ColorSensor.color.CYAN;
            } else if(this == ColorSensor.color.GREEN){
                return ColorSensor.color.YELLOW;
            } else if(this == ColorSensor.color.CYAN){
                return ColorSensor.color.RED;
            } else if(this == ColorSensor.color.YELLOW){
                return ColorSensor.color.GREEN;
            } else{
                return ColorSensor.color.UNKNOWN;
            }
        }
    }
}