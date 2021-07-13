package me.dnablue2112;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.PieStyler;

import java.util.HashMap;

public class VotePieChart {

    HashMap<String, Integer> data;
    Integer distributions;

    public VotePieChart(HashMap<String, Integer> data, Integer distributions) {
        this.data = data;
        this.distributions = distributions;
        PieChart pieChart = new PieChartBuilder().title("Votes after " + distributions + " distributions").build();
        for (String heading : data.keySet()) {
            pieChart.addSeries(heading, data.get(heading));
        }
        pieChart.getStyler().setLegendVisible(false);
        pieChart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
        pieChart.getStyler().setAnnotationDistance(1.15);
        new SwingWrapper(pieChart).displayChart();
    }

}