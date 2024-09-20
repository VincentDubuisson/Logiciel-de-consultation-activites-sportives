package interface_noyau_fonctionnel;

import control.ControlAddActivity;
import control.ControlImportFile;
import model.Activities;
import model.Activity;
import model.ImportFile;

public class InterfaceNoyauFonctionnel {
	
	private Activities activities = new Activities();
	ControlAddActivity controlAddActivity = new ControlAddActivity(activities);

    public void importFile(String filePath) {
    	ControlImportFile controlImportFile = new ControlImportFile(new ImportFile(filePath));
    	
    	if (controlImportFile.importFile() == 1) {
        	
        	Activity activity = controlImportFile.getActivity();
        	controlAddActivity.addActivity(activity);
    	}
    }
    
    public String[] getActivitiesList() {
        return activities.getActivitiesList();
    }
    
    public Activity getActivityById(int id) {
    	return activities.getActivityById(id);
    }

}


