package control;

import model.Activities;
import model.Activity;

public class ControlAddActivity {
	
	private Activities activities;
	
	public ControlAddActivity(Activities activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity activity) {
		if (!activities.isPresent(activity)) {
			activities.addActivity(activity);
		}
	}

}
