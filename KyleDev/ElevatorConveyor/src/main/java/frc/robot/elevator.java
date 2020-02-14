package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Joystick;

public class elevator {
    private static elevator instance = new elevator();
    private Joystick localJoy = new Joystick(1);

    private CANSparkMax mWinchLeft;

    private CANPIDController mPositionController;
    private CANEncoder mEncoder; 

    private PowerDistributionPanel powerPanel = new PowerDistributionPanel();

    public static double wantedHeight;
    private double lastWantedHeight = 0; 
    private double wantedSpeed; 

    private Boolean isAtBottom = false;

    public enum state {
        HOLDING,
        OPEN_LOOP,
        MANUAL_CONTROL,
        POSITION_CONTROL
    }

    private state mCurrentState;
    private state mWantedState;
    private state mLastState;

    public elevator() {
        mWinchLeft = new CANSparkMax(1, MotorType.kBrushless);
        mWinchLeft.restoreFactoryDefaults();
        mEncoder = mWinchLeft.getEncoder();
        mPositionController = mWinchLeft.getPIDController();

        mPositionController.setP(0.3);
        mPositionController.setI(0.0);
        mPositionController.setD(0.0);
        mPositionController.setFF(0.002);
        mPositionController.setIZone(0.0);

        mPositionController.setOutputRange(-0.1, 1.0);


        mWinchLeft.setSmartCurrentLimit(75);

        mWinchLeft.setClosedLoopRampRate(0.3);


       

        mEncoder.setPosition(0);
    }

    public static elevator getInstance() {
        return instance;
    }

    private void setHeight(double wantedHeight) {
        if(wantedHeight == -1) {
            wantedHeight = 0;
        }

        

        
    }


    private void setSpeed(double wantedSpeed) {
    }

    public boolean inPosition() {
        if(Math.abs(mEncoder.getVelocity())  < .1){
            return true; 
        } else {
            return false; 
        }
    }

    public void setWantedElevatorHeight(double wantedHeight){
        this.wantedHeight = wantedHeight;
    }

    public double getHeight() {
        return mEncoder.getPosition();
    }

    public double getLastWantedHeight(){
        return lastWantedHeight;
    }

    public void setWantedState(state wantedState) {
        this.mWantedState = wantedState;
    }

    public state getState() {
        return mCurrentState;
    }

    public void checkState() {
        if (mCurrentState != mWantedState) {
            mCurrentState = mWantedState;
        }
    }

    // public void registerLoop() {
    //     loopmanager.getInstance().addLoop(new loop()
    //     {
    //         @Override
    //         public void onStart() {
    //             mCurrentState = state.HOLDING;
    //             mWantedState = state.HOLDING;
    //         }

    //         @Override
    //         public void onLoop() {
    //             switch(mCurrentState) {
    //                 case HOLDING:
    //                     if(mLastState == state.MANUAL_CONTROL && mClaw.isExtended()){
    //                         lastWantedHeight += 4.5;
    //                     }
    //                     setHeight(lastWantedHeight);
                        
    //                     break; 
    //                 case MANUAL_CONTROL:
    //                     wantedSpeed = mController.wantedElevatorVelocity();
    //                     if(isAtBottom && wantedSpeed < 0){
    //                         setSpeed(0.0);
    //                     } else {
    //                         setSpeed(wantedSpeed);
    //                     }
    //                     lastWantedHeight = getHeight();
    //                     break;
    //                 case POSITION_CONTROL:
    //                     setHeight(wantedHeight);
    //                     lastWantedHeight = wantedHeight;
	// 					break;     
    //             }
    //         checkLimits();
    //         mLastState = mCurrentState;
    //         checkState();
    //         }
    //         public void onStop(){}
    //     });
    // }

    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("Left Output", mWinchLeft.getOutputCurrent());
        SmartDashboard.putNumber("Right Current", powerPanel.getCurrent(2));
        SmartDashboard.putNumber("Left Current", powerPanel.getCurrent(1));
        SmartDashboard.putNumber("Current Height", mEncoder.getPosition());
        SmartDashboard.putNumber("Last Height", lastWantedHeight);
        SmartDashboard.putNumber("Wanted Height", wantedHeight);
        SmartDashboard.putNumber("Current Velocity", mEncoder.getVelocity());
        SmartDashboard.putString("Elevator state", mCurrentState.toString());
        SmartDashboard.putNumber("Speed", wantedSpeed);
    }
}
