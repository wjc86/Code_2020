package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.tof.*;
import frc.robot.tof.I2CUpdatableAddress.NACKException;
import frc.robot.tof.I2CUpdatableAddress.Port;
import frc.robot.tof.VL53L0XSensor.SingleNotInitalizedException;
import java.io.IOException;

public class TOFSensors{
    private VL53L0XSensor sensor0;
    private VL53L0XSensor sensor1;

    public double[] readDistance(){
        double[] readings = new double[2];
        try{
            readings[0] = sensor0.readRangeSingleMillimeters();
        } catch(NACKException nack){
            System.out.println("NACK EXCEPTION " + nack.getStackTrace());
            readings[0] = 500;
        } catch(SingleNotInitalizedException notInit){
            System.out.println("SINGLE NOT INIT EXCEPTION " + notInit.getStackTrace());
            readings[0] = 500;
        } catch(Exception e){
            e.printStackTrace();
            readings[0] = 500;
        }

        try{
            readings[1] = sensor1.readRangeSingleMillimeters();
        } catch(NACKException nack){
            System.out.println("NACK EXCEPTION " + nack.getStackTrace());
            readings[1] = 500;
        } catch(SingleNotInitalizedException notInit){
            System.out.println("SINGLE NOT INIT EXCEPTION " + notInit.getStackTrace());
            readings[1] = 500;
        } catch(Exception e){
            e.printStackTrace();
            readings[1] = 500;
        }
        return readings;
    }

    public void setup(int address0, int address1){
        sensor0 = new VL53L0XSensor(Port.kOnboard, 0, 0);
        sensor1 = new VL53L0XSensor(Port.kOnboard, 1, 1);
    }

    public class SensorNotInitialized extends IOException{
        private final static long serialVersionUID = 1L;
        
        private String message;

        public SensorNotInitialized(){
            message = "Sensor is not initialized";
        }
        
        public SensorNotInitialized(String message){
            this.message = message;
        }

        public String toString(){ return message; }

        public void print(){ SmartDashboard.putString("Sensor Not Initialized", message); }
    }
}