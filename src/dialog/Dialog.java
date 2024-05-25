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

import frame.Frame;
import main_test.InterfaceNoyauFonctionnelTest;

/**
 *
 * @author vince
 */
public class Dialog {
    
    
/* ========================================================================== */
/*                               VARIABLES                                    */
/* ========================================================================== */
    
	private InterfaceNoyauFonctionnelTest inf;
    private Frame frame;
    
    private JFileChooser fileChooser;
    private JList activitiesList;
    
    private JPanel noActivityPanel;
    private JPanel cyclingActivityPanel;
    
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

    public Dialog(InterfaceNoyauFonctionnelTest inf) {
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
    
    public void handleActivitiesListEvent(int activity) {
    	noActivityPanel.setVisible(false);
    }
    
    private void getActivitiesList() {
        activities = inf.getActivitiesList();
    }
    
    private void setActivitiesList(){
        activitiesList.setListData(activities);
    }
    
    
/* ========================================================================== */
/*                                    MAIN                                    */
/* ========================================================================== */
    
    public static void main(String[] args) {
        Dialog dialog = new Dialog(new InterfaceNoyauFonctionnelTest());
        EventQueue.invokeLater(() -> {
            dialog.initDialog();
            dialog.initComponents();
        });
    }
    
}
