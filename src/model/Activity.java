package model;

import java.text.DecimalFormat;

public class Activity {
	
	private String type;
	private String date;
	private String time;
	private String totalDistance;
	private String averageSpeed;
	
	public Activity() {
		this.type = "null";
		this.date = "null";
		this.time = "null";
		this.totalDistance = "null";
		this.averageSpeed = "null";
	}
	
	
/* ========================================================================== */
/*                                  GETTEURS                                  */
/* ========================================================================== */	
	
	public String getType() {
		return type;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getTotalDistance() {
		return totalDistance;
	}
	
	public String getAverageSpeed() {
		return averageSpeed;
	}

	
/* ========================================================================== */
/*                                  SETTEURS                                  */
/* ========================================================================== */	
	
	public void setType(String type) {
		this.type = type;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTotalDistance(float totalDistance) {
		this.totalDistance = roundTwoDecimalFloat(totalDistance);
	}
	
	public void setAverageSpeed(float averageSpeed) {
		this.averageSpeed = String.valueOf(averageSpeed);
	}
	
	
/* ========================================================================== */
	
	
	private String roundTwoDecimalFloat(float value) {
		String source = String.valueOf(value);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double number = Double.parseDouble(source);
        
        return decimalFormat.format(number);
	}
		
}
