
package frc.robot.commands;



public class StowCombine extends CommandBase {

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private Combine combine = Combine.getInstance();

    public StowCombine() {

    }
  
    @Override
    public void initialize() {
        combine.retractCombine();
        if(combine.)
    }
  
    @Override
    public void execute() {}
  
    @Override
    public void end(boolean interrupted) {}
  
    @Override
    public boolean isFinished() {
        if(!combine.isCombineExtended()) {
            return true;
        }
        return false;
    }
  }
}