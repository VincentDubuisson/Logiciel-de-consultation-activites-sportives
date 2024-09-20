package dialog;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.ControlAddActivity;
import control.ControlImportFile;
import frame.Frame;
import interface_noyau_fonctionnel.InterfaceNoyauFonctionnel;
import model.Activities;
import model.Activity;
import model.ImportFile;

/**
 *
 * @author vince
 */
public class Dialog {
    
    
/* ========================================================================== */
/*                               VARIABLES                                    */
/* ========================================================================== */
    
	private InterfaceNoyauFonctionnel inf;
    private Frame frame;
    
    private JFileChooser fileChooser;
    private JList activitiesList;
    
    private JPanel noActivityPanel;
    private JPanel cyclingActivityPanel;
    
    private JLabel loadingLabel;
    private JLabel cyclingAltitudeDownLabel;
    private JLabel cyclingAltitudeGraphLabel;
    private JLabel cyclingAltitudeUpLabel;
    private JLabel cyclingAverageAltitudeLabel;
    private JLabel cyclingAverageSpeedLabel;
    private JLabel cyclingDateLabel;
    private JLabel cyclingDistanceLabel;
    private JLabel cyclingMaxSpeedLabel;
    private JLabel cyclingSpeedGraphLabel;
    private JLabel cyclingTimeLabel;
    private JLabel cyclingTypeLabel;
    
    private String[] activities;
    
    
/* ========================================================================== */
/*                              INITIALISATION                                */
/* ========================================================================== */

    public Dialog(InterfaceNoyauFonctionnel inf) {
        this.inf = inf;
    }

    public void initDialog() {
        frame = new Frame();
        frame.initFrame();
        frame.setDialog(this);
        frame.setVisible(true);
        
    }
    
    private void initComponents() {
    	frame.getImportButton();
        fileChooser = frame.getFileChooser();
        activitiesList = frame.getActivitiesList();
        
        noActivityPanel = frame.getNoActivityPanel();
        cyclingActivityPanel = frame.getCyclingActivityPanel();
        
        loadingLabel = frame.getLoadingLabel();
        cyclingAltitudeDownLabel = frame.getCyclingAltitudeDownLabel();
        cyclingAltitudeGraphLabel = frame.getCyclingAltitudeGraphLabel();
        cyclingAltitudeUpLabel = frame.getCyclingAltitudeUpLabel();
        cyclingAverageAltitudeLabel = frame.getCyclingAverageAltitudeLabel();
        cyclingAverageSpeedLabel = frame.getCyclingAverageSpeedLabel();
        cyclingDateLabel = frame.getCyclingDateLabel();
        cyclingDistanceLabel = frame.getCyclingDistanceLabel();
        cyclingMaxSpeedLabel = frame.getCyclingMaxSpeedLabel();
        cyclingSpeedGraphLabel = frame.getCyclingSpeedGraphLabel();
        cyclingTimeLabel = frame.getCyclingTimeLabel();
        cyclingTypeLabel = frame.getCyclingTypeLabel();
        
    }
    
    private void loadExistingFiles() {
        File folder = new File("data_in"); // Spécifiez le chemin du dossier
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".xlsx")); // Filtrer pour les fichiers Excel

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    inf.importFile(file.getPath()); // Importer le fichier via votre interface
                }
            }
            getActivitiesList(); // Récupérer la liste mise à jour des activités
            setActivitiesList(); // Mettre à jour l'affichage de la liste des activités
            loadingLabel.setVisible(false);
        }
    }

    
    
/* ========================================================================== */
/*                                  HANDLES                                   */
/* ========================================================================== */    
    
    public void handleImportEvent() {
        int returnValue = fileChooser.showDialog(frame, "Importer");
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
        	
            // Récupération du chemin absolu du fichier sélectionné
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getPath();
            
            inf.importFile(filePath);
            
            getActivitiesList();
            setActivitiesList();
            
        }
        
    }
    
    public void handleActivitiesListEvent(int id) {
    	noActivityPanel.setVisible(false);
    	if (id != -1) {
    		setAffichage(inf.getActivityById(id));
    	}
    	
    }
    
    private void getActivitiesList() {
        activities = inf.getActivitiesList();
    }
    
    private void setActivitiesList() {
        activitiesList.setListData(activities);
    }
    
    // TEMPORAIRE
    private void setAffichage(Activity activity) {
    	cyclingTypeLabel.setText(activity.getType());
    	cyclingTimeLabel.setText(activity.getTime());
    	cyclingDateLabel.setText(activity.getDate());
    	cyclingAverageSpeedLabel.setText(activity.getAverageSpeed() + " km/h");
    	cyclingDistanceLabel.setText(activity.getTotalDistance() + " km");
    	cyclingMaxSpeedLabel.setText(activity.getMaximumSpeed() + " km/h");
    	cyclingAltitudeDownLabel.setText("-" + activity.getAltitudeDown() + " m");
        cyclingAltitudeUpLabel.setText("+" + activity.getAltitudeUp() + " m");
        cyclingAverageAltitudeLabel.setText(activity.getAverageAltitude() + " m");
        cyclingSpeedGraphLabel.setIcon(new javax.swing.ImageIcon(activity.getSpeedGraph()));
        cyclingAltitudeGraphLabel.setIcon(new javax.swing.ImageIcon(activity.getAltitudeGraph()));
    }
    
    
    
    
/* ========================================================================== */
/*                                    MAIN                                    */
/* ========================================================================== */
    
    public static void main(String[] args) {
        Dialog dialog = new Dialog(new InterfaceNoyauFonctionnel());
        EventQueue.invokeLater(() -> {
        	dialog.initDialog();
            dialog.initComponents();
            
            // Thread pour charger les activites tout en affichant l'interface
            Thread thread = new Thread(() -> dialog.loadExistingFiles());
            thread.start(); // Lancement du thread
            
            
        });
    }
    
}
