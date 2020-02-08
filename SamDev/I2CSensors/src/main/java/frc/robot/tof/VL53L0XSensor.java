package frc.robot.tof;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VL53L0XSensor {
    private boolean initialized = false;
    private VL53L0X sensor;
    private Timer distanceTimer = null;

    public VL53L0XSensor(){
        try{
            sensor = new VL53L0X();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean init(){
        boolean result = false;
        try{
            Thread.sleep(10);
        } catch(InterruptedException e){
            e.printStackTrace();
            SmartDashboard.putString("IE", e.toString());
        }
        try{
            result = sensor.init(true);
        } catch(I2CUpdatableAddress.NACKException e){
            e.printStackTrace();
            SmartDashboard.putString("NE", e.toString());
        }
        initialized = result;
        if(initialized){
            distanceTimer = new Timer();
            System.out.println("---!!!Starting VL53L0X Timer!!!---");
        }
        return result;
    }

    public int readRangeSingleMillimeters() throws I2CUpdatableAddress.NACKException, SingleNotInitalizedException{
        if(!initialized){
            throw new SingleNotInitalizedException();
        }
        return sensor.readRangeSingleMillimeters();
    }

    public boolean changeAddress(int address){
        return sensor.changeAddress(address);
    }

    public class SingleNotInitalizedException extends IOException {}
}
