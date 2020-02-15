// package frc.robot;

// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.revrobotics.CANEncoder;
// import com.revrobotics.CANPIDController;
// import com.revrobotics.CANSparkMax;

// import edu.wpi.first.wpilibj.Joystick;

// public class Conveyor {
//     public TalonSRX lowerConveyor = new TalonSRX(1);
//     public TalonSRX upperConveyor = new TalonSRX(2);
//     public Joystick controller = new Joystick(0);

//     public Joystick Joy;
//     public CANSparkMax cMotor;
//     public CANPIDController cPID;
//     public CANEncoder cEncoder;
//     public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
//     public double distancePerRot;
    
//     public Boolean isStaged = false;
//     public int state = 0;


//     cMotor = new CANSparkMax(1, MotorType.kBrushless);
//     cMotor.restoreFactoryDefaults();
//     cPID = cMotor.getPIDController();
//     cEncoder = cMotor.getEncoder();
//     cEncoder.setPosition(0);
//     Joy = new Joystick(1);
//     distancePerRot = 7.0037; // in/rev
//     cEncoder.setPositionConversionFactor(1);

//     kP = 0;
//     kI = 0;
//     kD = 0;
//     kIz = 0;
//     kFF = 0;
//     kMaxOutput = 1;
//     kMinOutput = -1;
  
//     cPID.setP(kP);
//     cPID.setI(kI);
//     cPID.setD(kD);
//     cPID.setIZone(kIz);
//     cPID.setFF(kFF);
//     cPID.setOutputRange(kMinOutput, kMaxOutput);

//     SmartDashboard.putNumber("P Gain", kP);
//     SmartDashboard.putNumber("I Gain", kI);
//     SmartDashboard.putNumber("D Gain", kD);
//     SmartDashboard.putNumber("I Zone", kIz);
//     SmartDashboard.putNumber("Feed Forward", kFF);
//     SmartDashboard.putNumber("Max Output", kMaxOutput);
//     SmartDashboard.putNumber("Min Output", kMinOutput);
//     SmartDashboard.putNumber("Set Belt Delta", 0);

//     double p = SmartDashboard.getNumber("P Gain", 0);
//     double i = SmartDashboard.getNumber("I Gain", 0);
//     double d = SmartDashboard.getNumber("D Gain", 0);
//     double iz = SmartDashboard.getNumber("I Zone", 0);
//     double ff = SmartDashboard.getNumber("Feed Forward", 0);
//     double max = SmartDashboard.getNumber("Max Output", 0);
//     double min = SmartDashboard.getNumber("Min Output", 0);
//     double deltaPosition = SmartDashboard.getNumber("Set Belt Delta", 0);
//     double beltPosition = 0;

//     beltPosition += deltaPosition;
//     cPID.setReference(beltPosition/distancePerRot, ControlType.kPosition);

//     SmartDashboard.putNumber("SetPoint", beltPosition);
//     SmartDashboard.putNumber("ProcessVariable", cEncoder.getPosition());
//   }

//     public void stateTransfer() {
//          if (controller.getRawButton(1) && isStaged == false) {
//             this.state = 1;
//         }
//         else if(controller.getRawButton(1) && isStaged == true){
//             this.state = 2;
//         }
//         else if (controller.getRawButton(2) && isStaged == false){
//             this.state = 3;
                   
//         }
//         else if (controller.getRawButton(2) && isStaged == true){
//             this.state = 4;
//         }
//     }
//     if((p != kP)) {cPID.setP(p); kP = p;}
//     if((i != kI)) {cPID.setI(i); kI = i;}
//     if((d != kD)) {cPID.setD(d); kD = d;}
//     if((iz != kIz)) {cPID.setIZone(iz); kIz = iz;}
//     if((ff != kFF)) {cPID.setFF(ff); kFF = ff;}
//     if((max != kMaxOutput) || (min != kMinOutput)) {
//       cPID.setOutputRange(min, max);
//       kMinOutput = min; kMaxOutput = max;
//     }
// }
