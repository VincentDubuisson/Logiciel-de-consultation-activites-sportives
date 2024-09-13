package model;

import java.text.DecimalFormat;

public class Activity {
	
	private String type;
	private String date;
	private String time;
	private String totalDistance;
	private String averageSpeed;
	private String maximumSpeed;
	private String altitudeUp;
	private String altitudeDown;
	private String averageAltitude;
	private String speedGraphFilePath;
	private String altitudeGraphFilePath;
	
	public Activity() {
		this.type = "null";
		this.date = "null";
		this.time = "null";
		this.totalDistance = "null";
		this.averageSpeed = "null";
		this.maximumSpeed = "null";
		this.altitudeUp = "null";
		this.altitudeDown = "null";
		this.averageAltitude = "null";
		this.speedGraphFilePath = "null";
		this.altitudeGraphFilePath = "null";
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
	
	public String getMaximumSpeed() {
		return maximumSpeed;
	}
	
	public String getAltitudeUp() {
		return altitudeUp;
	}
	
	public String getAltitudeDown() {
		return altitudeDown;
	}
	
	public String getAverageAltitude() {
		return averageAltitude;
	}
	
	public String getSpeedGraph() {
		return speedGraphFilePath;
	}
	
	public String getAltitudeGraph() {
		return altitudeGraphFilePath;
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
		this.totalDistance = roundOneDecimalFloat(totalDistance);
	}
	
	public void setAverageSpeed(float averageSpeed) {
		this.averageSpeed = roundTwoDecimalFloat(averageSpeed);
	}
	
	public void setMaximumSpeed(float maximumSpeed) {
		this.maximumSpeed = roundTwoDecimalFloat(maximumSpeed);
	}
	
	public void setAltitudeUp(float altitudeUp) {
		this.altitudeUp = String.valueOf(Math.round(altitudeUp));
	}
	
	public void setAltitudeDown(float altitudeDown) {
		this.altitudeDown = String.valueOf(Math.round(altitudeDown));
	}
	
	public void setAverageAltitude(float averageAltitude) {
		this.averageAltitude = String.valueOf(Math.round(averageAltitude));
	}
	
	public void setSpeedGraph(String speedGraphFilePath) {
		this.speedGraphFilePath = speedGraphFilePath;
	}
	
	public void setAltitudeGraph(String altitudeGraphFilePath) {
		this.altitudeGraphFilePath = altitudeGraphFilePath;
	}
	
	
/* ========================================================================== */
	
	
	private String roundTwoDecimalFloat(float value) {
		String source = String.valueOf(value);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double number = Double.parseDouble(source);
        
        return decimalFormat.format(number);
	}
	
	private String roundOneDecimalFloat(float value) {
		String source = String.valueOf(value);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        double number = Double.parseDouble(source);
        
        return decimalFormat.format(number);
	}
		
}
