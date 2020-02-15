/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import frc.robot.Loop;
/*
 * Add your docs here.
 */
public class LoopManager {
    private static LoopManager instance = new LoopManager();
    private ArrayList<Loop> subsystems = new ArrayList<Loop>();

    public LoopManager() {}

    public static LoopManager getInstance(){
        return instance; 
    }

    public void addLoop(Loop l){
        subsystems.add(l);
    }

    public void startLoops(){
        for(Loop l : subsystems){
            l.onStart();
        }
    }

    public void runLoops(){
        for(Loop l : subsystems){
            l.onLoop();
        }
    }

    public void stopLoops(){
        for(Loop l : subsystems){
            l.onStop();
        }
    }
}
