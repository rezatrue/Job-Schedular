package application;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TimeCalculator {
	private Calendar cal;
	private Date startTime;
	private Date endTime;
	private Date randomTime;
	private int timeBound = 1;
	
	public TimeCalculator() {
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public Date getRandomTime() {
		return randomTime;
	}
	
	public Date getCurrentTime() {
		cal = Calendar.getInstance();
		Date date = cal.getTime();
	    return date;
	}
	
	public void initTimeLogger() {
		cal = Calendar.getInstance();
	    startTime = setTimes(0);
	    System.out.println(startTime);
	    endTime = setTimes(timeBound);
	    System.out.println(endTime);
	    randomTime = setTimes(getRandomNumber() - timeBound );
	    System.out.println(randomTime);
	}
	
	private Date setTimes(int num) {
		cal.add(Calendar.MINUTE, num);
	    Date date = cal.getTime();
	    return date;
	}
	
	private int getRandomNumber() {
		Random random = new Random();
		return (random.nextInt(timeBound + 1)); // generate 0 - 10 random number
	}

	
}
