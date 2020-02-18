package frc.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class BatteryMonitoring {
    private static BatteryMonitoring instance = new BatteryMonitoring();
    PowerDistributionPanel m_pdp = new PowerDistributionPanel();

    public static BatteryMonitoring getInstance() {
        return instance;
    }    
    public void pdpMonitoring() {
        SmartDashboard.putNumber("Total Current", m_pdp.getTotalCurrent());
        SmartDashboard.putNumber("Total Voltage", m_pdp.getVoltage());
        SmartDashboard.putNumber("Left pdp current", m_pdp.getCurrent(Constants.leftMasterPDPChannel));
        SmartDashboard.putNumber("Right pdp current", m_pdp.getCurrent(Constants.rightMasterPDPChannel));
    }

    public void overallMonitoring(Drivetrain m_drive) {
        pdpMonitoring();
        m_drive.motorMonitoring();
    }
}
