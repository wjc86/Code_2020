
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon; //This import should work for 775pros
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Combine extends SubsystemBase {

    private Combine instance = new Combine();

    private boolean isCollecting;
    private boolean isExtended;

    //Arguments: PCM Number, PCM Port, PCM Port
    private DoubleSolenoid extender = new DoubleSolenoid(0, 0, 1);
    private Talon collector = new Talon();

    public Combine() {
        isExtended = extender.get();
    }

    public static Combine getInstance() {
        return instance;
    }

    public void extendCombine() { 
        extender.set(1); 
    }

    public boolean isCombineExtended() { return isExtended; }

    public void retractCombine() { extender.set(0); }
    
    public void combineCollect() { collector.set(0.5); }

    public boolean isCombineCollecting() { return isCollecting; }

    public boolean isCombineExtended() { return extender.get(); }
}