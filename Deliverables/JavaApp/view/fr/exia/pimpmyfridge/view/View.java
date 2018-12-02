package fr.exia.pimpmyfridge.view;

import org.jfree.chart.ChartPanel;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class View extends ApplicationFrame {

   public View( String applicationTitle , String chartTitle ) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(chartTitle,"Temps","Température (en °C)",createDataset(),PlotOrientation.VERTICAL,true,true,false);
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.PINK );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesPaint( 2 , Color.BLUE );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel );
   }

   private XYSeriesCollection createDataset( ) {
      final double consigne = 3.0;
	   
	  XYSeriesCollection dataset = new XYSeriesCollection( );
      XYSeries series1 = new XYSeries("Temp Dht");
      XYSeries series2 = new XYSeries("Temp Pelt");
      XYSeries series3 = new XYSeries("Consigne");
      
      series1.add(1.0, 2.1);
      series1.add(2.0, 1.0);
      series1.add(3.0, 4.0);
      series1.add(4.0, 3.5);
      series1.add(5.0, 4.2);
      
      series2.add(1.0, 2.0);
      series2.add(2.0, 3.0);
      series2.add(3.0, 4.0);
      series2.add(4.0, 5.0);
      series2.add(5.0, 6.0);
      
      series3.add(1.0, consigne);
      series3.add(2.0, consigne);
      series3.add(3.0, consigne);
      series3.add(4.0, consigne);
      series3.add(5.0, consigne);
      dataset.addSeries(series1);
      dataset.addSeries(series2);
      dataset.addSeries(series3);
      return dataset;
   }
   @SuppressWarnings("unused")
private void customizeChart(JFreeChart chart) {   // here we make some customization
	    XYPlot plot = chart.getXYPlot();
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

	    // sets paint color for each series
	    renderer.setSeriesPaint(0, Color.RED);
	    renderer.setSeriesPaint(1, Color.GREEN);
	    renderer.setSeriesPaint(2, Color.YELLOW);

	    // sets thickness for series (using strokes)
	    renderer.setSeriesStroke(0, new BasicStroke(4.0f));
	    renderer.setSeriesStroke(1, new BasicStroke(3.0f));
	    renderer.setSeriesStroke(2, new BasicStroke(2.0f));
	    
	    // sets paint color for plot outlines
	    plot.setOutlinePaint(Color.BLUE);
	    plot.setOutlineStroke(new BasicStroke(2.0f));
	    
	    // sets renderer for lines
	    plot.setRenderer(renderer);
	    
	    // sets plot background
	    plot.setBackgroundPaint(Color.DARK_GRAY);
	    
	    // sets paint color for the grid lines
	    plot.setRangeGridlinesVisible(true);
	    plot.setRangeGridlinePaint(Color.BLACK);
	    
	    plot.setDomainGridlinesVisible(true);
	    plot.setDomainGridlinePaint(Color.BLACK);
	    
	}
}