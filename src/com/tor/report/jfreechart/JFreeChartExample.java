package com.tor.report.jfreechart;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 29.03.2010
 * Time: 14:00:30
 * не работает!!!
 */
public class JFreeChartExample {
    private static final Logger log = Logger.getLogger(JFreeChartExample.class);

    //   double[][] data = new double[][]{{1, 2, 3, 4, 5, 6}, {1, 1, 2, 3, 5, 8}};
    public static void main(String[] args) {

        final XYSeries series = new XYSeries("Random Data");
        for (int i = 0; i < 1440; i++) {
            final double x = Math.random();
            final double y = Math.random();
            series.add(x, y);
        }
        XYDataset dataset = new XYSeriesCollection(series);
        // dataset.addSeries("first", data);
        final XYPlot plot = new XYPlot(dataset, new NumberAxis(), new NumberAxis(), new DefaultXYItemRenderer());

        abstract class SerializableRunnable implements Serializable, Runnable {
        }
        Runnable r = new SerializableRunnable() {
            public void run() {
                ChartFrame frame = new ChartFrame("Chart", new JFreeChart(plot));
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        };
        SwingUtilities.invokeLater(r);

        JFreeChartExample example = new JFreeChartExample();
        example.charXY();
        example.createPieChart();
        example.createBarChart();
        example.create3DBarChart();
        example.createTimeSeries();
    }

    public void charXY() {
        //         Create a simple XY chart
        XYSeries series = new XYSeries("XYGraph");
        series.add(1, 1);
        series.add(1, 2);
        series.add(2, 1);
        series.add(3, 9);
        series.add(4, 10);
        //         Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        //         Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart("XY Chart", // Title
                "x-axis", // x-axis Label
                "y-axis", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
        //ChartUtilities.saveChartAsJPEG(new File("C:chart.jpg"), chart, 500, 300);
        new ChartFrame("XYLineChart", chart).setVisible(true);
    }

    public void createPieChart() {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("A", new Integer(75));
        pieDataset.setValue("B", new Integer(10));
        pieDataset.setValue("C", new Integer(10));
        pieDataset.setValue("D", new Integer(5));
        JFreeChart chart = ChartFactory.createPieChart(
                "CSC408 Mark Distribution",
                pieDataset,
                true,
                true,
                false);
        new ChartFrame("PieChart", chart).setVisible(true);
    }

    public void createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(6, "Profit", "Jane");
        dataset.setValue(7, "Profit", "Tom");
        dataset.setValue(8, "Profit", "Jill");
        dataset.setValue(5, "Profit", "John");
        dataset.setValue(12, "Profit", "Fred");
        JFreeChart chart = ChartFactory.createBarChart("Comparison between Salesman",
                "Salesman", "Profit", dataset, PlotOrientation.VERTICAL, false,
                true, false);
        new ChartFrame("BarChart", chart).setVisible(true);
    }

    public void create3DBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(6, "Profit1", "Jane");
        dataset.setValue(3, "Profit2", "Jane");
        dataset.setValue(7, "Profit1", "Tom");
        dataset.setValue(10, "Profit2", "Tom");
        dataset.setValue(8, "Profit1", "Jill");
        dataset.setValue(8, "Profit2", "Jill");
        dataset.setValue(5, "Profit1", "John");
        dataset.setValue(6, "Profit2", "John");
        dataset.setValue(12, "Profit1", "Fred");
        dataset.setValue(5, "Profit2", "Fred");
// Profit1, Profit2 represent the row keys
// Jane, Tom, Jill, etc. represent the column keys
        JFreeChart chart = ChartFactory.createBarChart3D("Comparison between Salesman",
                "Salesman", "Value ($)", dataset, PlotOrientation.VERTICAL, true, true, false);
        new ChartFrame("BarChart", chart).setVisible(true);
    }

    public void createTimeSeries() {
        // Create a time series chart
        TimeSeries pop = new TimeSeries("Population", Day.class);
        pop.add(new Day(10, 1, 2004), 100);
        pop.add(new Day(10, 2, 2004), 150);
        pop.add(new Day(10, 3, 2004), 250);
        pop.add(new Day(10, 4, 2004), 275);
        pop.add(new Day(10, 5, 2004), 325);
        pop.add(new Day(10, 6, 2004), 425);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(pop);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Population of CSC408 Town",
                "Date",
                "Population",
                dataset,
                true,
                true,
                false);
        new ChartFrame("TimeSeriesChart", chart).setVisible(true);
    }
}
