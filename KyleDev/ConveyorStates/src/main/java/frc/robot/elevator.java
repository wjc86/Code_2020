package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.*;;

public class elevator {
    private static elevator instance = new elevator();

    private CANSparkMax mMotor;

    private CANPIDController mPositionController;
    private CANEncoder mEncoder; 

    private PowerDistributionPanel powerPanel = new PowerDistributionPanel();
 
    private double wantedSpeed; 

    public enum state {
        HOLDING,
        OPEN_LOOP,
        MANUAL_CONTROL,
        POSITION_CONTROL
    }

    private state mCurrentState;
    private state mWantedState;

    public elevator() {
        mMotor = new CANSparkMax(1, MotorType.kBrushless);
        mMotor.restoreFactoryDefaults();
        mEncoder = mMotor.getEncoder();
        mPositionController = mMotor.getPIDController();

        mPositionController.setP(0.07);
        mPositionController.setI(0.0);
        mPositionController.setD(0.0);
        mPositionController.setFF(0.002);
        mPositionController.setIZone(0.0);

        mPositionController.setOutputRange(-0.1, 1);

        mMotor.setSmartCurrentLimit(75);

        mMotor.setClosedLoopRampRate(0.3);

        mEncoder.setPosition(0);
    }

    public static elevator getInstance() {
        return instance;
    }

    public boolean inPosition() {
        if(Math.abs(mEncoder.getVelocity())  < .1){
            return true; 
        } else {
            return false; 
        }
    }


    public double getHeight() {
        return mEncoder.getPosition();
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

    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("Left Output", mMotor.getOutputCurrent());
        SmartDashboard.putNumber("Right Current", powerPanel.getCurrent(2));
        SmartDashboard.putNumber("Left Current", powerPanel.getCurrent(1));
        SmartDashboard.putNumber("Current Height", mEncoder.getPosition());
        SmartDashboard.putNumber("Current Velocity", mEncoder.getVelocity());
        SmartDashboard.putString("Elevator state", mCurrentState.toString());
        SmartDashboard.putNumber("Sswitchpeed", wantedSpeed);
    }
}