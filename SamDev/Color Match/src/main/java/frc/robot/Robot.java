/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;

import java.lang.ProcessBuilder.Redirect;

import com.revrobotics.ColorMatch;

/**
 * This is a simple example to show how the REV Color Sensor V3 can be used to
 * detect pre-configured colors.
 */
public class Robot extends TimedRobot {
  /**
   * Change the I2C port below to match the connection of your color sensor
   */
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a 
   * parameter. The device will be automatically initialized with default 
   * parameters.
   */
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  /**
   * A Rev Color Match object is used to register and detect known colors. This can 
   * be calibrated ahead of time or during operation.
   * 
   * This object uses a simple euclidian distance to estimate the closest match
   * with given confidence range.
   */
  private final ColorMatch m_colorMatcher = new ColorMatch();

  /**
   * Note: Any example colors should be calibrated as the user needs, these
   * are here as a basic example.
   */
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  private static final int YELLOW_CALIBRATION = 90;
  private static final int RED_CALIBRATION = 30;
  private static final int GREEN_CALIBRATION = 130;
  private static final int CYAN_CALIBRATION = 184;

  @Override
  public void robotInit() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);    
  }

  @Override
  public void robotPeriodic() {
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

    /*if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }*/

    /**
     * Open Smart Dashboard or Shuffleboard to see the color detected by the 
     * sensor.
     */
    /*SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);*/
  }
}
