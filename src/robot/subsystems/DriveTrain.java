package robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.OI;
import robot.RobotMap;

public class DriveTrain extends Subsystem {
	
	private TalonSRX trainRight;
	private TalonSRX trainRight2;
	private TalonSRX trainLeft;
	private TalonSRX trainLeft2;
	private Solenoid trans;
	
	public DriveTrain(){
		trainRight = new WPI_TalonSRX(RobotMap.DRIVETRAINRIGHT);
		trainRight2 = new WPI_TalonSRX(RobotMap.DRIVETRAINRIGHT2);
		trainLeft = new WPI_TalonSRX(RobotMap.DRIVETRAINLEFT);
		trainLeft2 = new WPI_TalonSRX(RobotMap.DRIVETRAINLEFT2);
		
		trainRight2.follow(trainRight);
		trainLeft2.follow(trainLeft);
		
		trainRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		trainLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		trainRight.config_kP(0, 0, 10);
		trainLeft.config_kP(0, 0, 10);
		
		trainRight.config_kI(0, 0, 10);
		trainLeft.config_kI(0, 0, 10);
		
		trainRight.config_kD(0, 0, 10);
		trainLeft.config_kD(0, 0, 10);
	}
	
	//main method for setting percent motor speeds
	public void setPOut(double right, double left){
		right = checkPOut(right);
		left = checkPOut(left);
		if(OI.driver.getBumper(Hand.kRight)){
			right = right * 0.25;
			left = left * 0.25;
		}
		trainRight.set(ControlMode.PercentOutput, checkPOut(right));
		trainLeft.set(ControlMode.PercentOutput, checkPOut(left));
	}
	
	//main method for setting output by any control
	public void setOut(ControlMode control, double right, double left){
		trainRight.set(control, right);
		trainLeft.set(control, left);
	}
	
	public void setTrans(boolean trans){
		this.trans.set(trans);
	}
	
	public void toggleTrans(){
		trans.set(!trans.get());
	}
	
	public int getEncoderLeft(){
		return trainLeft.getSensorCollection().getQuadraturePosition();
	}
	
	public int getEncoderRight(){
		return trainRight.getSensorCollection().getQuadraturePosition();
	}
	
	public double getEncoderLSpeed(){
		return trainLeft.getSensorCollection().getQuadratureVelocity();
	}
	
	public double getEncoderRSpeed(){
		return trainRight.getSensorCollection().getQuadratureVelocity();
	}
	
	//checks range of input for motors using percent output
	public double checkPOut(double in){
		in = in >= 1 ? in : 1;
		in = in <= -1 ? in : -1;
		return in;
	}
	
	protected void initDefaultCommand() {
		
	}
}