package frc.robot.tof;

import java.io.IOException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.tof.I2CUpdatableAddress.NACKException;
import frc.robot.tof.I2CUpdatableAddress.Port;

public class VL53L0XSensor {
    private boolean initialized = false;
    private VL53L0X sensor;

    public VL53L0XSensor(){
        try{
            sensor = new VL53L0X();
        } catch(NACKException nack){
            System.out.println("NACK EXCEPTION at Line 21 of VS");
        } catch(Exception e){
            System.out.println("EXCEPTION at Line 23 of VS");
        }
    }

    public VL53L0XSensor(Port port, int defaultAddress, int deviceAddress){
        try{
            sensor = new VL53L0X(port, defaultAddress, defaultAddress);
        } catch(NACKException nack){
            System.out.println("NACK EXCEPTION at Sensor ID " + defaultAddress);
        } catch(Exception e){
            System.out.println("EXCEPTION at Line 33 of VS");
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

    public class SingleNotInitalizedException extends IOException {
        private static final long serialVersionUID = 1L;
    }
}
