package control;

import javax.swing.JOptionPane;

import model.Activity;
import model.ImportFile;

public class ControlImportFile {
	
	private ImportFile importFile;

	public ControlImportFile(ImportFile importFile) {
		this.importFile = importFile;
	}
	
	public int importFile() {
		if (importFile.isExcelFile()) {
			if (importFile.copyFile() == 0) {
				JOptionPane.showMessageDialog(null, "Erreur lors de l'importation du fichier: fichier déjà existant", "Erreur", JOptionPane.ERROR_MESSAGE, null);
				return 0;
			} else {
				importFile.createDataFolder();
				return 1;
			}
			
		}
		JOptionPane.showMessageDialog(null, "Erreur lors de l'importation du fichier: fichier au mauvais format", "Erreur", JOptionPane.ERROR_MESSAGE, null);
		return 0;
	}
	
	public Activity getActivity() {
		return importFile.getActivity();
	}
}
