package corpode21.com.br.corpod21.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Calendar;
import java.util.List;

import corpode21.com.br.corpod21.MainActivity;
import corpode21.com.br.corpod21.MedidasActivity;
import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.GMailSender;
import corpode21.com.br.corpod21.Util.Notifications;
import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.entidades.SemanaEvolucao;
import corpode21.com.br.corpod21.service.OnAlarmReceiver;

/**
 * Created by Fabio on 22/05/2015.
 */
public class EvolucaoFragment extends Fragment {

    private FragmentActivity myContext;
    private Button btnAtualizar;
    private TextView iBiceps;
    private TextView aBiceps;
    private TextView iCintura;
    private TextView aCintura;
    private TextView iQuadril;
    private TextView aQuadril;
    private TextView iPerna;
    private TextView aPerna;

    private TextView PesoIni;
    private TextView PesoMeta;

    private TextView iGluteos;
    private TextView aGluteos;
    private ProgressBar progressBar;

    SessionManager session;
    List<SemanaEvolucao> semanas;
    SemanaEvolucao iSemana;
    SemanaEvolucao aSemana;

    private int ID_SEMANA;

    public final  String TAG = "BonusFragment";

    public EvolucaoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_evolucao, container, false);
        btnAtualizar = (Button) v.findViewById(R.id.btnAtualizarDados);

        iBiceps = (TextView) v.findViewById(R.id.iBiceps);
        aBiceps = (TextView) v.findViewById(R.id.aBiceps);
        iCintura = (TextView) v.findViewById(R.id.iCintura);
        aCintura = (TextView) v.findViewById(R.id.aCintura);
        iQuadril = (TextView) v.findViewById(R.id.iQuadril);
        aQuadril = (TextView) v.findViewById(R.id.aQuadril);
        iPerna = (TextView) v.findViewById(R.id.iPerna);
        aPerna = (TextView) v.findViewById(R.id.aPerna);

        PesoIni = (TextView) v.findViewById(R.id.pesoIni);
        PesoMeta = (TextView) v.findViewById(R.id.pesoMeta);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        btnAtualizar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verificaSemana();
                goActivity(MedidasActivity.class);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        session = new SessionManager(myContext);
        semanas = session.getSemanas(myContext);

        if(semanas != null) {
            if (semanas.size() > 0) {
                iSemana = semanas.get(0);
                aSemana = semanas.get(semanas.size() - 1);
                InitChart();
                preencheDados();
            }
        }
    }

    protected void InitChart(){

        // Bar 1
        Double maxValue = 100.0;
        CategorySeries series = new CategorySeries("");

        for (SemanaEvolucao semana: semanas) {
            series.add("Bar " + (semana.getId()), Double.parseDouble(semana.getPeso().replace(",",".")));
            if(semana.getId() == 1)
            {
                maxValue  = Double.parseDouble(semana.getPeso().replace(",",".")) + 20;
            }
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series.toXYSeries());

        // This is how the "Graph" itself will look like
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        //mRenderer.setChartTitle("Demo Graph Title");
        mRenderer.setXTitle("SEMANAS");
        mRenderer.setYTitle("PESO");

        mRenderer.setPanEnabled(false, false);
        mRenderer.setZoomEnabled(false, false);
        mRenderer.setAxesColor(Color.GREEN);
        mRenderer.setLabelsColor(Color.RED);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins

        DisplayMetrics metrics = myContext.getResources().getDisplayMetrics();
        float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, metrics);

        mRenderer.setAxisTitleTextSize(35);

        mRenderer.setLabelsTextSize(val - 3);
        mRenderer.setBarSpacing(0.5);
        mRenderer.setBarWidth(70);
        mRenderer.setXAxisMin(0);
        mRenderer.setXAxisMax(6);
        mRenderer.setYAxisMin(0);
        mRenderer.setYAxisMax(maxValue);

        // Customize bar 1
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesSpacing((float) 0.5);
        renderer.setLineWidth((float) 1);
        renderer.setShowLegendItem(false);
        renderer.setChartValuesTextSize(35);
        renderer.setColor(Color.RED);

        mRenderer.addSeriesRenderer(renderer);

        GraphicalView chartView = ChartFactory.getBarChartView(myContext, dataset, mRenderer, BarChart.Type.DEFAULT);

        // insert into main view
        ViewGroup insertChart = (ViewGroup) myContext.findViewById(R.id.viewChart);
        insertChart.addView(chartView, 0);

    }

    private void preencheDados()
    {
        PesoIni.setText(iSemana.getPeso().concat("kg"));
        PesoMeta.setText(iSemana.getPesoMeta().concat("kg"));

        float PesoIni = Float.parseFloat(iSemana.getPeso().replace(",","."));
        float PesoMeta = Float.parseFloat(iSemana.getPesoMeta().replace(",", "."));
        float PesoAtual = Float.parseFloat(aSemana.getPeso().replace(",", "."));

        float PesoAperder = PesoIni - PesoMeta;
        float PesoPerdido = PesoIni - PesoAtual;

        float progress = (PesoPerdido / PesoAperder) * 100;

        //Perda de peso percentual = [(IW - CW) / IW] * 100 ou (L / IW) * 100

        progressBar.setMax(100);
        progressBar.setProgress(Math.round(progress));

        iBiceps.setText(iSemana.getBiceps());
        aBiceps.setText(aSemana.getBiceps());
        iCintura.setText(iSemana.getCintura());
        aCintura.setText(aSemana.getCintura());
        iQuadril.setText(iSemana.getQuadril());
        aQuadril.setText(aSemana.getQuadril());
        iPerna.setText(iSemana.getPerna());
        aPerna.setText(aSemana.getPerna());
        //iGluteos.setText(iSemana.getG());
    }

    private void verificaSemana()
    {
        if(aSemana == null) {
            ID_SEMANA = 1;
        }
        else {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.DAY_OF_MONTH, Integer.parseInt(aSemana.getFim_semana().substring(0, 2)));
            calSet.set(Calendar.MONTH, Integer.parseInt(aSemana.getFim_semana().substring(3, 5)) - 1);
            //Se o final da semana atual for menor que a data atual, entao sera a proxima semana.
            if (calSet.compareTo(calNow) <= 0) {
                //Proxima Semana
                ID_SEMANA = aSemana.getId() + 1;
                if (ID_SEMANA > 6)
                    ID_SEMANA = 6;
            } else {
                //Semana atual
                ID_SEMANA = aSemana.getId();
            }
        }
    }

    private void goActivity(Class c) {

        Intent it = new Intent(myContext.getApplicationContext(), c);
        it.putExtra("ID_SEMANA", ID_SEMANA);
        startActivity(it);
    }
}
