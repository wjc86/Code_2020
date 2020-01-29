package frc.robot;

public class ourpid {
    //All the variables
    public double error = 0;
    public double previous_error = 0;
    public double error_sum = 0;
    public double k_p = 0;
    public double k_i = 0;
    public double k_d = 0;
    public double Timestep = 1;
    public double integrated_error;
    public double error_difference;
    public double differentiated_error;
    public double p_component;
    public double i_component;
    public double d_component;
    public double max_effort;
    public double min_effort;
    public double effort;

    //Lets us see the differance between the target output and the measured output
    public double target_output = 0;
    public double measured_output = 0;

    //Lets us find the error and set our pid components
    public double calcControleEffort() {
        this.error =this.target_output-this.measured_output;
        this.error_sum += this.error;
        integrated_error = this.error_sum * this.Timestep;

        error_difference = this.error - this.previous_error;
        differentiated_error = error_difference/this.Timestep;

        this.previous_error = this.error;

        p_component = k_p * this.error;
        i_component = k_i * integrated_error;
        d_component = k_d * differentiated_error;
        
        effort = p_component+i_component+d_component;
        if(effort < this.min_effort) {
            return this.min_effort;
        } else if (effort > this.max_effort) {
            return this.max_effort;
        } 
        return effort;
    }

    //Uses the parameters for the PID and sets them to the k_x 
    public void setGains(double Kp, double Ki, double Kd) {
        this.k_p = Kp;
        this.k_i = Ki;
        this.k_d = Kd;
    }
    
    //Updates the Timestep
    public void setTimestep(double Timestep) {
        this.Timestep = Timestep;
    }

    //Finds the max and min effort of the motor
    public void setMaxMinEffort(double Max, double Min) {
        this.max_effort = Max;
        this.min_effort = Min;
    }

    //gives the target input
    public void setTargetOutput(double target) {
        this.target_output = target;
    }

    //gives the measured input
    public void setMeasuredOutput(double measure) {
        this.measured_output = measure;
    }
}