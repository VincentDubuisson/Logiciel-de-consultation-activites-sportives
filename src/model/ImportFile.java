package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ImportFile {
	
	private String filePath;

	
	public ImportFile(String filePath) {
		this.filePath = filePath;
	}
	
	public boolean isExcelFile() {
		return filePath.endsWith(".xlsx");
	}
	
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
	
	public Activity getActivity() {
		Activity activity = new Activity();
		ExtractData data = new ExtractData(filePath);
		data.openFile();
		
		activity.setType(data.getActivityType());
		activity.setDate(data.getDate());
		activity.setTime(data.getActivityDuration());
		activity.setTotalDistance(data.getTotalDistance());
		activity.setAverageSpeed(data.getAverageSpeed());
		
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
		
		private ExtractData(String filePath) {
			this.filePath = filePath;
		}
		
		private void openFile() {
			try {
				file = new FileInputStream(new File(filePath));
				workbook = WorkbookFactory.create(file);
			} catch (IOException e) {
	            e.printStackTrace();
	        } 
		}
		
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
		
		private String getActivityType() {
			return getColumnValue(0);
		}
		
		private String getDate() {
			String startHour = getColumnValue(1);
			// Conversion au bon format
			DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm");
	        LocalDateTime dateTime = LocalDateTime.parse(startHour, originalFormatter);
	        DateTimeFormatter destinationFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			
	        return dateTime.format(destinationFormatter);
		}
		
		private String getActivityDuration() {
			return getColumnValue(2);
		}
		
		private float getTotalDistance() {
	        return Float.parseFloat(getColumnValue(3));
	    }

		private float getAverageSpeed() {
	        return Float.parseFloat(getColumnValue(4));
	    }

		private float[] getSpeedArray() {
	        return stringArrayToFloatArray(getColumnValues(5, 0));
	    }

		private float[] getAltitudeArray() {
	        return stringArrayToFloatArray(getColumnValues(6, 0));
	    }

		private float[] getDistanceArray() {
	        return stringArrayToFloatArray(getColumnValues(7, 0));
	    }

	    
	    private String getColumnValue(int columnNum) {
	        return getColumnValues(columnNum, 1)[0];
	    }
	    
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
		
	    private String[] getColumnValues(int columnNum, int numRow) {
	        Sheet sheet = workbook.getSheetAt(0);
	        
	        if (numRow == 1) {
	        	return getOneRow(sheet, columnNum);
	        	
	        } else {
	        	return getMultipleRow(sheet, columnNum);
	    	}
		}
	    
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
	}
	
	
}
