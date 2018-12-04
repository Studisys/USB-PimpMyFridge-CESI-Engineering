package fr.cesi.pimpmyfridge.view;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;

@SuppressWarnings("serial")
// Class for the chart
public class LineChart extends ApplicationFrame {

 private long counter;
 public ValueMarker mark;

 // Objects for chart
 private JFreeChart lineChart; // Chart
 private XYSeries tempPeltier; // Peltier Temp
 private XYSeries tempDHT; // DHT Temp Inside
 private XYSeries tempOutside; // DHT Temp Outside
 private XYSeries tempDew; // DHT Temp Outside

 // Set properties for the chart
 public LineChart(String applicationTitle, String chartTitle) {
  super(applicationTitle);
  lineChart = ChartFactory.createXYLineChart(
   chartTitle, "Time (s)", "Temperature (°C)",
   createDataset(),
   PlotOrientation.VERTICAL,
   true, true, false);

  setTitle(null);
  getJChart().setTitle((String) null);
  getJChart().getLegend().setPosition(RectangleEdge.RIGHT);

  // 
  ChartPanel chartPanel = new ChartPanel(lineChart);
  chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
  setContentPane(chartPanel);
  counter = 0;


  // For the Target Temperature Marker	      
  XYPlot plot = lineChart.getXYPlot();
  plot.addRangeMarker(mark, Layer.BACKGROUND);
  mark = new ValueMarker(0, Color.ORANGE, new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 12.0f, new float[] {5.0f}, 0.0f));

  XYItemRenderer renderer = plot.getRenderer();

  // Graphical & Aesthetical settings for chart
  plot.setBackgroundPaint(new Color(107, 106, 104));
  plot.setRangeGridlinePaint(new Color(10, 10, 10));
  plot.setDomainGridlinePaint(new Color(10, 10, 10));
  plot.getDomainAxis().setLabelPaint(Color.WHITE);
  plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
  plot.getRangeAxis().setRange(new Range(0, 30));
  plot.getRangeAxis().setTickLabelPaint(Color.WHITE);
  plot.getRangeAxis().setLabelPaint(Color.WHITE);

  // Propeties for Series 1
  renderer.setSeriesItemLabelPaint(0, new Color(241, 61, 7));
  renderer.setSeriesPaint(0, new Color(241, 61, 7));
  renderer.setSeriesStroke(0, new BasicStroke(2));

  // Propeties for Series 2		  
  renderer.setSeriesItemLabelPaint(1, new Color(0, 174, 189));
  renderer.setSeriesPaint(1, new Color(0, 174, 189));
  renderer.setSeriesStroke(1, new BasicStroke(2));

  // Propeties for Series 3
  renderer.setSeriesItemLabelPaint(2, new Color(34, 139, 34));
  renderer.setSeriesPaint(2, new Color(34, 139, 34));
  renderer.setSeriesStroke(2, new BasicStroke(2));

  // Propeties for Series 4
  renderer.setSeriesItemLabelPaint(3, new Color(153, 51, 255));
  renderer.setSeriesPaint(3, new Color(153, 51, 255));
  renderer.setSeriesStroke(3, new BasicStroke(2));

  // Chart Legend Settings
  lineChart.getLegend().setBackgroundPaint(new Color(107, 106, 104));
  lineChart.getLegend().setItemPaint(Color.WHITE);
  lineChart.getLegend().setBorder(0, 0, 0, 0);
 }


 private XYSeriesCollection createDataset() {
  XYSeriesCollection dataset = new XYSeriesCollection();
  tempPeltier = new XYSeries("°C Peltier");
  tempDHT = new XYSeries("°C Inside");
  tempOutside = new XYSeries("°C Outside");
  tempDew = new XYSeries("°C Dew");

  // Max 50 values for each series
  this.tempPeltier.setMaximumItemCount(50);
  this.tempDHT.setMaximumItemCount(50);
  this.tempOutside.setMaximumItemCount(50);
  this.tempDew.setMaximumItemCount(50);

  // Add Series on chart
  dataset.addSeries(tempPeltier);
  dataset.addSeries(tempDHT);
  dataset.addSeries(tempOutside);
  dataset.addSeries(tempDew);

  // Return the dataset
  return dataset;
 }


 // add data to the chart
 public void addData(float tempDHT, float tempOutside, float tempPeltier, float tempDew) {
  counter++; // We add one more row to the counter of the array
  this.tempPeltier.add(counter, tempPeltier); // Add value for PeltierTemp
  this.tempDHT.add(counter, tempDHT); // Add value for DHT Temp
  this.tempOutside.add(counter, tempOutside); // Add value for outside temp
  this.tempDew.add(counter, tempDew); // Add value for Dew temp

 }

 // Return the chart
 public JFreeChart getJChart() {
  return lineChart;
 }

}