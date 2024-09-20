package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activities {
	
	private Activity[] activities;
	private int activitiesNumbers;
	
	public Activities() {
		this.activities = new Activity[30];
		this.activitiesNumbers = 0;
	}
	
	/* addActivity()
	 * input: Activity
	 * output: none
	 * do: ajoute l'activite activity dans la liste des activites */
	public void addActivity(Activity activity) {
		for (int i = 0; i < activities.length; i++) {
			if (activities[i] == null) {
				activities[i] = activity;
				activitiesNumbers++;
				break;
			}
		}
	}
	
	/* isPresent()
	 * input: Activity
	 * output: boolean
	 * do: renvoie true si l'activite activity est presente dans la liste des activites, false sinon */
	public boolean isPresent(Activity activity) {
		for (int i = 0; i < activities.length; i++) {
			if (activities[i] == activity) {
				return true;
			}
		}
		return false;
	}
	
	/* getActivitiesNumbers()
	 * input: none
	 * output: int
	 * do: renvoie le nombre d'activite presente dans la liste d'activites */
	public int getActivitiesNumbers() {
		return this.activitiesNumbers;
	}
	
	/* getActivityById()
	 * input: int
	 * output: Activity
	 * do: renvoie l'activite a l'indice id de la liste des activites */
	public Activity getActivityById(int id) {
		return activities[id];
	}
	
	/* getActivitiesList()
	 * input: none
	 * output: String[]
	 * do: renvoie une liste de String contenant les noms des activites */
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
