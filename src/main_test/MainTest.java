package main_test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//import model.ExtractData;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class MainTest {
	
    public static void main(String[] args) throws IOException {
    	String fileName = "2024_04_30-09_28";
//    	ExtractData dataFile = new ExtractData("data/" + fileName + ".xlsx");
//    	dataFile.openFile();
//    	float[] speed = dataFile.getSpeedArray();
//        float[] distance = dataFile.getDistanceArray();
//        float[] altitude = dataFile.getAltitudeArray();
//        dataFile.closeFile();

        
/* ========================================================================== */
/*            CREATION GRAPHIQUE VITESSE PAR RAPPORT A LA DISTANCE            */
/* ========================================================================== */
        
        // Création d'une série pour stocker les données de vitesse et de distance
        XYSeries seriesSpeed = new XYSeries("Speed");

        // Ajout des données à la série
        /*for (int i = 0; i < speed.length; i++) {
        	seriesSpeed.add(distance[i], speed[i]);
        }*/

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
        
        XYLineAndShapeRenderer rendererSpeed = (XYLineAndShapeRenderer) plotSpeed.getRenderer();
        rendererSpeed.setSeriesPaint(0, Color.BLUE); // Couleur de la courbe

        // Sauvegarder le graphique dans une image png
        int widthSpeed = 950; // Largeur de l'image
        int heightSpeed = 200; // Hauteur de l'image
        File chartFileSpeed = new File("data_out/" + fileName + "/speed_chart.png"); // Chemin/Nom du fichier
        ChartUtils.saveChartAsPNG(chartFileSpeed, chartSpeed, widthSpeed, heightSpeed);
        
        
        
/* ========================================================================== */
/*            CREATION GRAPHIQUE ALTITUDE PAR RAPPORT A LA DISTANCE           */
/* ========================================================================== */
        
        // Création d'une série pour stocker les données de vitesse et de distance
        XYSeries seriesAltitude = new XYSeries("Altitude");

        // Ajout des données à la série
        /*for (int i = 0; i < altitude.length; i++) {
            seriesAltitude.add(distance[i], altitude[i]);
        }*/

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
        
        XYLineAndShapeRenderer rendererAltitude = (XYLineAndShapeRenderer) plotAltitude.getRenderer();
        rendererAltitude.setSeriesPaint(0, Color.GREEN); // Couleur de la courbe

        // Sauvegarder le graphique dans une image png
        int widthAltitude = 950; // Largeur de l'image
        int heightAltitude = 200; // Hauteur de l'image
        File chartFileAltitude = new File("data_out/" + fileName + "/altitude_chart.png"); // Chemin/Nom du fichier
        ChartUtils.saveChartAsPNG(chartFileAltitude, chartAltitude, widthAltitude, heightAltitude);
        
        System.out.println("Les images png ont étés générés dans le dossier data_out");
    }
}
