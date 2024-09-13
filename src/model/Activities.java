package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activities {
	
	private Activity[] activities;
	private int activitiesNumbers;
	
	public Activities() {
		this.activities = new Activity[20];
		this.activitiesNumbers = 0;
	}
	
	public void addActivity(Activity activity) {
		for (int i = 0; i < activities.length; i++) {
			if (activities[i] == null) {
				activities[i] = activity;
				activitiesNumbers++;
				break;
			}
		}
	}
	
	public boolean isPresent(Activity activity) {
		for (int i = 0; i < activities.length; i++) {
			if (activities[i] == activity) {
				return true;
			}
		}
		return false;
	}
	
	public int getActivitiesNumbers() {
		return this.activitiesNumbers;
	}
	
	public Activity getActivityById(int id) {
		return activities[id];
	}
	
	public String[] getActivitiesList() {
		String[] activitiesList = new String[activitiesNumbers];
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM"); // Format de sortie
		
		for (int i = 0; i < activitiesNumbers; i++) {
			Date date = null;
			try {
				date = inputFormat.parse(activities[i].getDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String formattedDate = outputFormat.format(date);
			activitiesList[i] = "Activité du " + formattedDate +
					" - " + activities[i].getTime() + " - " + activities[i].getTotalDistance() + "km";
		}
		
		return activitiesList;
	}
}
