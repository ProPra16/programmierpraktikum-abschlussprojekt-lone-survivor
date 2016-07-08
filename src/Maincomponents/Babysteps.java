package Maincomponents;
 /* 
 * @author Andreas Lüpertz
 * @version 1.1
 */
public class Babysteps {
	
	private boolean expired;
	private boolean running;
	private double start;
	private double end;
	private double milSec;
	public Babysteps(){}
	
	public Babysteps(double milSec){
		setExpired(false);
		setRunning(false);
		setMilSec(milSec);
	}

	public void startTimer(){
		if(!isRunning()){
			setExpired(false);
			setRunning(true);
			setStart(System.currentTimeMillis());
		}
	}	
	
	public void stopTimer(){
		if(isRunning()){
			setRunning(false);
			setEnd(System.currentTimeMillis());
		}
	}
	
	public double getTime(){
		if(!isRunning())
			return end-start;
		else return System.currentTimeMillis()-start;
	}
	
	public boolean isExpired() {
		if(isRunning()){
			if(getTime() >= milSec){
				setExpired(true);
				stopTimer();
			}
		}
		return expired;
	}
	
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public double getMilSec() {
		return milSec;
	}

	public void setMilSec(double milSec) {
		this.milSec = milSec;
	}

}
