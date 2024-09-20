package model;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ImportFile {
	
	private String filePath;
	
	public ImportFile(String filePath) {
		this.filePath = filePath;
	}
	
	
	/* isExcelFile()
	 * input: none
	 * output: boolean
	 * do: renvoie true si le fichier importe est un fichier avec l'extension .xlsx */
	public boolean isExcelFile() {
		return filePath.endsWith(".xlsx");
	}
	
	/* copyFile()
	 * input: none
	 * output: int
	 * do: copie le fichier importe dans le dossier data_in et renvoie 0 si une exception est levee*/
	public int copyFile() {
		Path sourcePath = Paths.get(filePath);
        Path destinationFolder = Paths.get("data_in/");
        
        try {
            // Copie du fichier vers le dossier data_in
            Files.copy(sourcePath, destinationFolder.resolve(sourcePath.getFileName()));
            return 1;
        } catch (IOException e) {
            return 0;
        }
	}
	
	/* copyFile()
	 * input: none
	 * output: none
	 * do: creation du dossier qui va comporter les graphiques de l'activite importe */
	public void createDataFolder() {
		File file = new File(filePath);
		String fileName = file.getName(); // Récupération du nom du fichier

		// Suppression de l'extension du fichier
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex != -1) {
		    fileName = fileName.substring(0, lastDotIndex);
		}
		
        String nomDossier = "data_out/" + fileName; // Chemin du dossier à créer 
        File dossier = new File(nomDossier); // Création du dossier
        
        // Création du dossier si nécessaire
        if (!dossier.exists()) {
            dossier.mkdirs();
        }
	}
	
	/* copyFile()
	 * input: none
	 * output: Activity
	 * do: renvoie l'activite liee au fichier importe */
	public Activity getActivity() {
		Activity activity = new Activity();
		ExtractData data = new ExtractData(filePath);
		data.openFile();
		File file = new File(filePath);
		String fileName = file.getName(); // Récupération du nom du fichier
		
		// Suppression de l'extension du fichier
		int lastDotIndex = fileName.lastIndexOf(".");
		if (lastDotIndex != -1) {
		    fileName = fileName.substring(0, lastDotIndex);
		}
		
		activity.setType(data.getActivityType());
		activity.setDate(data.getDate());
		activity.setTime(data.getActivityDuration());
		activity.setTotalDistance(data.getTotalDistance());
		activity.setAverageSpeed(data.getAverageSpeed());
		activity.setMaximumSpeed(data.getMaximumSpeed());
		activity.setAltitudeUp(data.getAltitudeUp());
		activity.setAltitudeDown(data.getAltitudeDown());
		activity.setAverageAltitude(data.getAverageAltitude());
		activity.setSpeedGraph(data.createSpeedGraph(fileName));
		activity.setAltitudeGraph(data.createAltitudeGraph(fileName));
		
		data.closeFile();
		
		return activity;
	}
	
	
/* ========================================================================== */
/*                         CLASSE INTERNE EXTRACTDATA                         */
/* ========================================================================== */
	
	private static class ExtractData {
		
		private String filePath;
		private FileInputStream file = null;
		private Workbook workbook = null;
		
		private float[] altitudeArray = null;
		private float[] distanceArray = null;
		private float[] speedArray = null;

	    private ExtractData(String filePath) {
	        this.filePath = filePath;
	    }

	    /* openFile()
		 * input: none
		 * output: none
		 * do: ouvre le fichier importe */
	    private void openFile() {
	        try {
	            file = new FileInputStream(new File(filePath));
	            workbook = WorkbookFactory.create(file);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /* closeFile()
		 * input: none
		 * output: none
		 * do: ferme le fichier importe */
	    private void closeFile() {
	        if (workbook != null) {
	            try {
	                workbook.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (file != null) {
	            try {
	                file.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    /* getActivityType()
		 * input: none
		 * output: String
		 * do: renvoie le type de l'activite */
	    private String getActivityType() {
	        return getColumnValue(0);
	    }

	    /* getDate()
		 * input: none
		 * output: String
		 * do: renvoie la date et l'heure de debut de l'activite */
	    private String getDate() {
	        String startHour = getColumnValue(1);
	        // Conversion au bon format
	        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm");
	        LocalDateTime dateTime = LocalDateTime.parse(startHour, originalFormatter);
	        DateTimeFormatter destinationFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	        
	        return dateTime.format(destinationFormatter);
	    }

	    /* getActivityDuration()
		 * input: none
		 * output: String
		 * do: renvoie la duree de l'activite */
	    private String getActivityDuration() {
	        return getColumnValue(2);
	    }

	    /* getTotalDistance()
		 * input: none
		 * output: float
		 * do: renvoie la distance parcouru lors de l'activite */
	    private float getTotalDistance() {
	        return Float.parseFloat(getColumnValue(3));
	    }

	    /* getAverageSpeed()
		 * input: none
		 * output: float
		 * do: renvoie la vitesse moyenne de l'activite */
	    private float getAverageSpeed() {
	        return Float.parseFloat(getColumnValue(4));
	    }

	    /* getSpeedArray()
		 * input: none
		 * output: float[]
		 * do: renvoie la liste des vitesses de l'activite, une vitesse correspond a une seconde */
	    private float[] getSpeedArray() {
	    	// Si le tableau d'altitudes n'est pas encore calculé, on le calcule et le stocke
	        if (speedArray == null) {
	        	speedArray = stringArrayToFloatArray(getColumnValues(5, 0));
	        }
	        return speedArray;
	    }

	    /* getAltitudeArray()
		 * input: none
		 * output: float[]
		 * do: renvoie la liste des altitudes*/
	    private float[] getAltitudeArray() {
	        if (altitudeArray == null) {
	            altitudeArray = stringArrayToFloatArray(getColumnValues(6, 0));
	        }
	        return altitudeArray;
	    }

	    /* getDistanceArray()
		 * input: none
		 * output: float[]
		 * do: renvoie la liste des distances*/
	    private float[] getDistanceArray() {
	        if (distanceArray == null) {
	        	distanceArray = stringArrayToFloatArray(getColumnValues(7, 0));
	        }
	        return distanceArray;
	    }

	    /* getMaximumSpeed()
		 * input: none
		 * output: float
		 * do: renvoie la vitesse maximum*/
	    private float getMaximumSpeed() {
	    	speedArray = getSpeedArray();
	        float maximumSpeed = speedArray[0];
	        for (int i = 1; i < speedArray.length - 1; i++) {
	            if (speedArray[i] > maximumSpeed) {
	                maximumSpeed = speedArray[i];
	            }
	        }
	        return maximumSpeed;
	    }
	    
	    /* getAltitudeUp()
		 * input: none
		 * output: float
		 * do: renvoie le denivele positif cumule */
	    private float getAltitudeUp() {
	    	altitudeArray = getAltitudeArray();
	        float altitude = 0;
	        
	        for (int i = 0; i < altitudeArray.length - 10; i += 10) {
	            float altitudeCurrent = Math.round(altitudeArray[i]);
	            float altitudeNext = Math.round(altitudeArray[i + 10]);

	            if (altitudeNext > altitudeCurrent) {
	                altitude += altitudeNext - altitudeCurrent;
	            }
	        }
	        return altitude;
	    }

	    /* getAltitudeDown()
		 * input: none
		 * output: float
		 * do: renvoie le denivele negatif cumule */
	    private float getAltitudeDown() {
	        float altitude = 0;
	        
	        for (int i = 0; i < altitudeArray.length - 10; i += 10) {
	            float altitudeCurrent = Math.round(altitudeArray[i]);
	            float altitudeNext = Math.round(altitudeArray[i + 10]);

	            if (altitudeNext < altitudeCurrent) {
	                altitude += altitudeCurrent - altitudeNext;
	            }
	        }
	        return altitude;
	    }

	    /* getAverageAltitude()
		 * input: none
		 * output: float
		 * do: renvoie l'altitude moyenne */
	    private float getAverageAltitude() {
	        float altitude = 0;
	        for (int i = 0; i < altitudeArray.length; i++) {
	            altitude += altitudeArray[i];
	        }
	        return altitude / altitudeArray.length;
	    }

	    /* getColumnValue()
		 * input: int
		 * output: String
		 * do: renvoie le String de la deuxieme ligne de la colonne columnNum du fichier excel */
	    private String getColumnValue(int columnNum) {
	        return getColumnValues(columnNum, 1)[0];
	    }
	    
	    /* stringArrayToFloatArray()
		 * input: String[]
		 * output: float[]
		 * do: converti la liste de String[] strings en liste de float[] */
	    private float[] stringArrayToFloatArray(String[] strings) {
	        float[] floats = new float[strings.length];
	        
	        for (int i = 0; i < strings.length; i++) {
	            try {
	            	floats[i] = Float.parseFloat(strings[i]);
	            } catch (NumberFormatException e) {
	                // Si la conversion échoue, valeur par défaut
	            	floats[i] = 0.0f;
	            }
	        }
	        return floats;
	    }
		
	    /* getColumnValues()
		 * input: int, int
		 * output: String[]
		 * do: renvoie la liste des valeurs de la colonne columnNum */
	    private String[] getColumnValues(int columnNum, int numRow) {
	        Sheet sheet = workbook.getSheetAt(0);
	        
	        if (numRow == 1) {
	        	return getOneRow(sheet, columnNum);
	        	
	        } else {
	        	return getMultipleRow(sheet, columnNum);
	    	}
		}
	    
	    /* getOneRow()
		 * input: Sheet, int
		 * output: String[]
		 * do: renvoie la liste des valeurs de la colonne columnNum */
	    private String[] getOneRow(Sheet sheet, int columnNum) {
	    	String[] values = new String[1];
	    	Row row = sheet.getRow(1);
	    	Cell cell = row.getCell(columnNum);
	    	
	    	if (cell.getCellType() == CellType.STRING) {
	    		values[0] = cell.getStringCellValue();
	    	} else {
	    		values[0] = String.valueOf(cell.getNumericCellValue());
	    	}
	    	return values;
	    }
	    
	    /* getMultipleRow()
		 * input: Sheet, int
		 * output: String[]
		 * do: renvoie la liste des valeurs de la colonne columnNum */
	    private String[] getMultipleRow(Sheet sheet, int columnNum) {
	    	ArrayList<String> valuesArray = new ArrayList<>();
	    	
	    	Row row;
	    	Cell cell;
	    	
	    	int i = 0;
	    	do {
	            row = sheet.getRow(i + 1);
	            if (row != null) {
	                cell = row.getCell(columnNum);
	                if (cell != null) {
	                    if (cell.getCellType() == CellType.STRING) {
	                        valuesArray.add(cell.getStringCellValue());
	                    } else if (cell.getCellType() == CellType.NUMERIC) {
	                        valuesArray.add(String.valueOf(cell.getNumericCellValue()));
	                    }
	                } else {
	                    break;
	                }
	            } else {
	                break;
	            }
	            i++;
	        } while (true);

	    	String[] values = new String[valuesArray.size()];
	        valuesArray.toArray(values);
	        
	        return values;
	    }
	    
	    /* createSpeedGraph()
		 * input: String
		 * output: String
		 * do: creer le graphique de vitesse et renvoie le chemin d'acces au fichier png du graphique */
	    private String createSpeedGraph(String fileName) {
	        distanceArray = getDistanceArray();
	        
	        // Création d'une série pour stocker les données de vitesse et de distance
	        XYSeries seriesSpeed = new XYSeries("Vitesse");

	        // Ajout des données à la série
	        for (int i = 0; i < speedArray.length; i++) {
	            seriesSpeed.add(distanceArray[i], speedArray[i]);
	        }

	        // Création d'un ensemble de données à partir de la série
	        XYSeriesCollection dataSpeed = new XYSeriesCollection();
	        dataSpeed.addSeries(seriesSpeed);

	        // Création du graphique
	        JFreeChart chartSpeed = ChartFactory.createXYLineChart(
	                "", // Titre du graphique
	                "", // Etiquette de l'axe des abscisses
	                "", // Etiquette de l'axe des ordonnées
	                dataSpeed // Ensemble de données
	        );
	        
	        // Personnalisation de la "forme" du graphique
	        XYPlot plotSpeed = (XYPlot) chartSpeed.getPlot();
	        plotSpeed.setBackgroundPaint(Color.WHITE); // Couleur arrière-plan
	        plotSpeed.setDomainGridlinePaint(Color.LIGHT_GRAY); // Couleur grille verticale
	        plotSpeed.setRangeGridlinePaint(Color.LIGHT_GRAY); // Couleur grille horizontale
	        
	        // Définition de la plage de l'axe des abscisses (distance)
	        NumberAxis domainAxis = (NumberAxis) plotSpeed.getDomainAxis();
	        domainAxis.setRange(0, getTotalDistance());
	        
	        // Utilisation du XYAreaRenderer pour créer un graphe en aire
	        XYAreaRenderer areaRenderer = new XYAreaRenderer();
	        areaRenderer.setSeriesPaint(0, new Color(19, 255, 0, 180)); // Couleur de l'aire
	        
	        // Remplacement du renderer
	        plotSpeed.setRenderer(areaRenderer);

	        // Sauvegarder le graphique dans une image png
	        int widthSpeed = 950; // Largeur de l'image
	        int heightSpeed = 150; // Hauteur de l'image
	        String filePath = "data_out/" + fileName + "/speed_chart.png";
	        File chartFileSpeed = new File(filePath); // Chemin/Nom du fichier
	        try {
	            ChartUtils.saveChartAsPNG(chartFileSpeed, chartSpeed, widthSpeed, heightSpeed);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return filePath;
	    }

	    /* createAltitudeGraph()
		 * input: String
		 * output: String
		 * do: creer le graphique de vitesse et renvoie le chemin d'acces au fichier png du graphique */
	    private String createAltitudeGraph(String fileName) {
	    	
	    	float minAltitude = altitudeArray[0];
	    	float maxAltitude = minAltitude;
	    	for (float altitude : altitudeArray) {
	    	    if (altitude < minAltitude) {
	    	        minAltitude = altitude;
	    	    }
	    	    if (altitude > maxAltitude) {
	    	        maxAltitude = altitude;
	    	    }
	    	}
	    	
	    	// Création d'une série pour stocker les données de vitesse et de distance
	        XYSeries seriesAltitude = new XYSeries("Altitude");

	        // Ajout des données à la série
	        for (int i = 0; i < altitudeArray.length; i++) {
	            seriesAltitude.add(distanceArray[i], altitudeArray[i]);
	        }

	        // Création d'un ensemble de données à partir de la série
	        XYSeriesCollection datasetAltitude = new XYSeriesCollection();
	        datasetAltitude.addSeries(seriesAltitude);

	        // Création du graphique
	        JFreeChart chartAltitude = ChartFactory.createXYLineChart(
	                "", // Titre du graphique
	                "", // Etiquette de l'axe des abscisses
	                "", // Etiquette de l'axe des ordonnées
	                datasetAltitude // Ensemble de données
	        );

	        // Personnalisation de la "forme" du graphique
	        XYPlot plotAltitude = (XYPlot) chartAltitude.getPlot();
	        plotAltitude.setBackgroundPaint(Color.WHITE); // Couleur arrière-plan
	        plotAltitude.setDomainGridlinePaint(Color.LIGHT_GRAY); // Couleur grille verticale
	        plotAltitude.setRangeGridlinePaint(Color.LIGHT_GRAY); // Couleur grille horizontale
	        
	        // Définition de la plage de l'axe des ordonnées
	        NumberAxis rangeAxis = (NumberAxis) plotAltitude.getRangeAxis();
	        int decalage = (int) ((maxAltitude-minAltitude)*0.1);
	        rangeAxis.setRange(minAltitude-decalage, maxAltitude+decalage);
	        
	        // Définition de la plage de l'axe des abscisses (distance)
	        NumberAxis domainAxis = (NumberAxis) plotAltitude.getDomainAxis();
	        domainAxis.setRange(0, getTotalDistance());
	        
	        XYAreaRenderer areaRenderer = new XYAreaRenderer();
	        areaRenderer.setSeriesPaint(0, new Color(34, 131, 230, 180)); // Couleur de la courbe
	        
	        // Remplacement du renderer
	        plotAltitude.setRenderer(areaRenderer);

	        // Sauvegarder le graphique dans une image png
	        int widthAltitude = 950; // Largeur de l'image
	        int heightAltitude = 150; // Hauteur de l'image
	        String filePath = "data_out/" + fileName + "/altitude_chart.png";
	        File chartFileAltitude = new File(filePath); // Chemin/Nom du fichier
	        try {
				ChartUtils.saveChartAsPNG(chartFileAltitude, chartAltitude, widthAltitude, heightAltitude);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        return filePath;
	    }
	}
	
}
