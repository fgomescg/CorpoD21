package corpode21.com.br.corpod21.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import corpode21.com.br.corpod21.BaseActivity;
import corpode21.com.br.corpod21.R;


public class EvolucaoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolucao);

        XYSeries series = new XYSeries("Peso");

        // We start filling the series
        int hour = 0;

        series.add(1,80);
        series.add(2, 78);
        series.add(3, 75);
        series.add(4, 70);
        series.add(5, 68);
        series.add(6, 65);


        // Now we create the renderer
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(Color.BLUE);
        // Include low and max value
        renderer.setDisplayBoundingPoints(true);

        // Now we add our series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        // Finaly we create the multiple series renderer to control the graph
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        // We want to avoid black border
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        // Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);

        mRenderer.setShowGrid(true); // we show the grid
        mRenderer.setBarSpacing(1);

        GraphicalView chartView = ChartFactory.getBarChartView(this, dataset, mRenderer, BarChart.Type.DEFAULT);


        // insert into main view
        ViewGroup insertChart = (ViewGroup) findViewById(R.id.viewChart);
        insertChart.addView(chartView,0);


    }


}
