package frc.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ColorSensorV3.ColorSensorMeasurementRate;
import com.revrobotics.ColorSensorV3.ColorSensorResolution;
import com.revrobotics.ColorSensorV3.GainFactor;

import java.util.ArrayList;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

public class ColorSensor{
    private CANSparkMax colorWheel = new CANSparkMax(10, MotorType.kBrushless);
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 mColorSensor = new ColorSensorV3(i2cPort);
    
    private state colorSearchState = state.NEUTRAL;

    private static final int YELLOW_CALIBRATION_LIGHT_OFF = 60;
    private static final int RED_CALIBRATION_LIGHT_OFF = 20;
    private static final int GREEN_CALIBRATION_LIGHT_OFF = 90;
    private static final int CYAN_CALIBRATION_LIGHT_OFF = 120;

    private static final int YELLOW_CALIBRATION_LIGHT_ON = 94;
    private static final int RED_CALIBRATION_LIGHT_ON = 29;
    private static final int GREEN_CALIBRATION_LIGHT_ON = 121;
    private static final int CYAN_CALIBRATION_LIGHT_ON = 182;

    private static final boolean isLightOn = true;

    private static ColorSensor.color mCurrentColor = ColorSensor.color.UNKNOWN;
    private ColorSensor.color lastColor = ColorSensor.color.UNKNOWN;
    private int colorCounter = 0;
    private static final int COLOR_LIMIT = 5;
    private ColorSensor.color lastValidColor = ColorSensor.color.UNKNOWN;

    private ColorSensor.color mWantedColor = ColorSensor.color.UNKNOWN;
    private ColorSensor.color mDetectedColor = ColorSensor.color.UNKNOWN;

    private ArrayList<ColorSensor.color> colors = new ArrayList<>();

    private final double MOTOR_SPEED = 0.15;
    private int colorReadBefore = 0;
    private int colorReadAfter = 0;

    private int mCounter = 0;
   // this bit of code right below might wreck something until we find a good spot to put it, we dont know where we should.
    public void configureColorSensor(){
        mColorSensor.configureColorSensor(ColorSensorResolution.kColorSensorRes16bit, ColorSensorMeasurementRate.kColorRate25ms, GainFactor.kGain3x);
    }

    public ColorSensor.color detectColor(){
        Color detectedColor = mColorSensor.getColor();
        
        double[] RGB = {detectedColor.red, detectedColor.green, detectedColor.blue};
        
        double min, max, delta, h;
        min = Math.min(Math.min(RGB[0], RGB[1]), RGB[2]);
        max = Math.max(Math.max(RGB[0], RGB[1]), RGB[2]);
        delta = max - min;

        colorReadBefore++;
        SmartDashboard.putNumber("Color Read Before", colorReadBefore);

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
        colorReadAfter++;
        if(colorCounter >= COLOR_LIMIT){
            if(isLightOn){
              if((h >= 0 && h <= ((RED_CALIBRATION_LIGHT_ON + YELLOW_CALIBRATION_LIGHT_ON)/2)) || (h >= 300 && h <= 360)){
                  mCurrentColor = ColorSensor.color.RED;
              } else if(h >= ((RED_CALIBRATION_LIGHT_ON + YELLOW_CALIBRATION_LIGHT_ON)/2) && h <= ((YELLOW_CALIBRATION_LIGHT_ON + GREEN_CALIBRATION_LIGHT_ON)/2)){
                  mCurrentColor = ColorSensor.color.YELLOW;
              } else if(h >= ((YELLOW_CALIBRATION_LIGHT_ON + GREEN_CALIBRATION_LIGHT_ON)/2) && h <= ((GREEN_CALIBRATION_LIGHT_ON + CYAN_CALIBRATION_LIGHT_ON)/2)){
                  mCurrentColor = ColorSensor.color.GREEN;
              } else if(h >= ((GREEN_CALIBRATION_LIGHT_ON + CYAN_CALIBRATION_LIGHT_ON)/2) && h <= 240){
                  mCurrentColor = ColorSensor.color.CYAN;
              } else {
                  mCurrentColor = ColorSensor.color.UNKNOWN;
              }
            } else{
              if((h >= 0 && h <= ((RED_CALIBRATION_LIGHT_OFF + YELLOW_CALIBRATION_LIGHT_OFF)/2)) || (h >= 300 && h <= 360)){
                  mCurrentColor = ColorSensor.color.RED;
              } else if(h >= ((RED_CALIBRATION_LIGHT_OFF + YELLOW_CALIBRATION_LIGHT_OFF)/2) && h <= ((YELLOW_CALIBRATION_LIGHT_OFF + GREEN_CALIBRATION_LIGHT_OFF)/2)){
                  mCurrentColor = ColorSensor.color.YELLOW;
              } else if(h >= ((YELLOW_CALIBRATION_LIGHT_OFF + GREEN_CALIBRATION_LIGHT_OFF)/2) && h <= ((GREEN_CALIBRATION_LIGHT_OFF + CYAN_CALIBRATION_LIGHT_OFF)/2)){
                  mCurrentColor = ColorSensor.color.GREEN;
              } else if(h >= ((GREEN_CALIBRATION_LIGHT_OFF + CYAN_CALIBRATION_LIGHT_OFF)/2) && h <= 240){
                  mCurrentColor = ColorSensor.color.CYAN;
              } else {
                  mCurrentColor = ColorSensor.color.UNKNOWN;
              }
            }
            lastValidColor = mCurrentColor;
            SmartDashboard.putNumber("Color Read After", colorReadAfter);
            SmartDashboard.putString("Detected Color", mCurrentColor.toString());
            return mCurrentColor;
        }
        SmartDashboard.putNumber("Color Read After", colorReadAfter);
        SmartDashboard.putString("Detected Color", lastValidColor.toString());
        return lastValidColor;
    }

     public void rotateToColor(){
        colorSearchState = ColorSensor.state.SPIN_TO_COLOR;


    //     SmartDashboard.putString("Wanted Color", "UNKNOWN");
    //     while(mWantedColor == ColorSensor.color.UNKNOWN){
    //         mWantedColor = toColor(SmartDashboard.getString("Wanted Color", "UNKNOWN")).getActualColor();
    //     }
    //     mDetectedColor = ColorSensor.color.UNKNOWN;
    //     while(mWantedColor != mDetectedColor){
    //         mDetectedColor = detectColor();
    //         colorWheel.set(MOTOR_SPEED);
    //     }
    //     colorWheel.set(0);
    } 

    public void colorSearch(){
        mWantedColor = toColor(SmartDashboard.getString("Wanted Color", "UNKNOWN")).getActualColor();
        mDetectedColor = detectColor();
        switch(colorSearchState){
            case NEUTRAL: 
                break;
            case SPIN_TO_COLOR:
                colorWheel.set(MOTOR_SPEED);
                if(mDetectedColor == mWantedColor){
                    colorSearchState = state.NEUTRAL;
                    colorWheel.set(0);
                    SmartDashboard.putString("FOUND COLOR", mDetectedColor.toString());
                }
                break;
            case SPIN_NUMBER_OF_TIMES:
                colorWheel.set(MOTOR_SPEED);
                if(mDetectedColor == mWantedColor && mCounter >= 7 && lastColor != mDetectedColor){
                    colorSearchState = state.NEUTRAL;
                    colorWheel.set(0);
                    SmartDashboard.putString("FOUND COLOR", mDetectedColor.toString());
                    mCounter = 0;
                } else if(mDetectedColor == mWantedColor && lastColor != mDetectedColor){
                    mCounter++;
                }
                break;
        }
    }


    public void rotateNumber(){
        colorSearchState = state.SPIN_NUMBER_OF_TIMES;

        /*ArrayList<ColorSensor.color> kColors = new ArrayList<>();
        kColors.add(ColorSensor.color.RED);
        kColors.add(ColorSensor.color.GREEN);
        kColors.add(ColorSensor.color.CYAN);
        kColors.add(ColorSensor.color.YELLOW);

        /*Timer timer = new Timer();
        timer.start();
        while(timer.get() <= 0.1){}

        mWantedColor = detectColor();
        while(mWantedColor == ColorSensor.color.UNKNOWN){
            mWantedColor = detectColor();
        }
        SmartDashboard.putString("Position Wanted Color", ColorSensor.color.RED.toString());
        colors.clear();
        ColorSensor.color firstColor = detectColor();
        colors.add(firstColor);
        int counter = 0;
        //ColorSensor.color lastColor = firstColor;
        ColorSensor.color currentColor;
        while(counter < 7){
            colorWheel.set(MOTOR_SPEED);
            currentColor = detectColor();
            if((kColors.indexOf(lastColor)+1 == kColors.indexOf(currentColor)) || (kColors.indexOf(lastColor) == 3 && kColors.indexOf(currentColor) == 0)){
                colors.add(currentColor);
                if(currentColor == mWantedColor){
                    counter++;
                }
            }
            if(currentColor == ColorSensor.color.RED && lastColor != ColorSensor.color.RED){
                counter++;
            }
            lastColor = currentColor;
            SmartDashboard.putNumber("Counter", counter);
            SmartDashboard.putString("ArrayList Values", iterate(colors));
            if(Robot.joystick.getRawButton(1)){
                break;
            }
        }
        colorWheel.set(0);*/
    }

    public void manualOverride(){
        colorWheel.set(0.1);
    }

    private String iterate(ArrayList<ColorSensor.color> colors){
        String fin = "";
        for(ColorSensor.color color : colors){
            fin += color.toString() + " ";
        }
        return fin;
    }

    public void deploy(){}

    public void retract(){}

    public void spin(){}

    public void approach(){}

    public ColorSensor.color getCurrentColor(){
        return lastValidColor;
    }

    public void outputToSmartDashboard(){
        SmartDashboard.putString("Detected Color", mCurrentColor.toString());
    }

    public void calibrate(){
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
        SmartDashboard.putNumber("H", h);

        String colorString = "";
        if(true){
            if(isLightOn){
              if((h >= 0 && h <= ((RED_CALIBRATION_LIGHT_ON + YELLOW_CALIBRATION_LIGHT_ON)/2)) || (h >= 300 && h <= 360)){
                  colorString = ColorSensor.color.RED.toString();
              } else if(h >= ((RED_CALIBRATION_LIGHT_ON + YELLOW_CALIBRATION_LIGHT_ON)/2) && h <= ((YELLOW_CALIBRATION_LIGHT_ON + GREEN_CALIBRATION_LIGHT_ON)/2)){
                colorString = ColorSensor.color.YELLOW.toString();
              } else if(h >= ((YELLOW_CALIBRATION_LIGHT_ON + GREEN_CALIBRATION_LIGHT_ON)/2) && h <= ((GREEN_CALIBRATION_LIGHT_ON + CYAN_CALIBRATION_LIGHT_ON)/2)){
                colorString = ColorSensor.color.GREEN.toString();
              } else if(h >= ((GREEN_CALIBRATION_LIGHT_ON + CYAN_CALIBRATION_LIGHT_ON)/2) && h <= 240){
                colorString = ColorSensor.color.CYAN.toString();
              } else {
                colorString = ColorSensor.color.UNKNOWN.toString();
              }
            } else{
              if((h >= 0 && h <= ((RED_CALIBRATION_LIGHT_OFF + YELLOW_CALIBRATION_LIGHT_OFF)/2)) || (h >= 300 && h <= 360)){
                colorString = ColorSensor.color.RED.toString();
              } else if(h >= ((RED_CALIBRATION_LIGHT_OFF + YELLOW_CALIBRATION_LIGHT_OFF)/2) && h <= ((YELLOW_CALIBRATION_LIGHT_OFF + GREEN_CALIBRATION_LIGHT_OFF)/2)){
                colorString = ColorSensor.color.YELLOW.toString();
              } else if(h >= ((YELLOW_CALIBRATION_LIGHT_OFF + GREEN_CALIBRATION_LIGHT_OFF)/2) && h <= ((GREEN_CALIBRATION_LIGHT_OFF + CYAN_CALIBRATION_LIGHT_OFF)/2)){
                colorString = ColorSensor.color.GREEN.toString();
              } else if(h >= ((GREEN_CALIBRATION_LIGHT_OFF + CYAN_CALIBRATION_LIGHT_OFF)/2) && h <= 240){
                colorString = ColorSensor.color.CYAN.toString();
              } else {
                colorString = ColorSensor.color.UNKNOWN.toString();
              }
            }
        }
        SmartDashboard.putString("Color", colorString);
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

    public enum state {
        NEUTRAL,
        SPIN_NUMBER_OF_TIMES,
        SPIN_TO_COLOR
    }
}