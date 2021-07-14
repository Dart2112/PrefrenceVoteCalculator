package me.dnablue2112;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.PieStyler;

import javax.swing.*;
import java.util.HashMap;

public class VotePieChart {

    HashMap<String, Integer> data;
    Integer distributions;
    JFrame frame;

    public VotePieChart(HashMap<String, Integer> data, Integer distributions) {
        this.data = data;
        this.distributions = distributions;
        PieChart pieChart = new PieChartBuilder().title("Votes after " + distributions + " distributions").build();
        for (String heading : data.keySet()) {
            pieChart.addSeries(heading, data.get(heading));
        }
        pieChart.getStyler().setLegendVisible(true);
        pieChart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
        pieChart.getStyler().setAnnotationDistance(.5);
        SwingWrapper<PieChart> wrapper;
        wrapper = new SwingWrapper<>(pieChart);
        wrapper.setTitle(distributions + " distributions");
        frame = wrapper.displayChart();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

}
