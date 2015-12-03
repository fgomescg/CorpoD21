package corpode21.com.br.corpod21;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import corpode21.com.br.corpod21.Util.Notifications;
import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.entidades.SemanaEvolucao;
import corpode21.com.br.corpod21.fragments.EvolucaoFragment;
import corpode21.com.br.corpod21.service.OnAlarmReceiver;
import corpode21.com.br.corpod21.spinnerwheel.AbstractWheel;
import corpode21.com.br.corpod21.spinnerwheel.adapters.ArrayWheelAdapter;


public class MedidasActivity extends ActionBarActivity {

    private Activity myContext;
    private Button btnConcluir;
    private AbstractWheel pesoWheel;
    private AbstractWheel pesoMetaWheel;
    private AbstractWheel bicepsWheel;
    private AbstractWheel cinturaWheel;
    private AbstractWheel quadrilWheel;
    private AbstractWheel pernaWheel;
    ArrayList<String> weight;
    ArrayList<String> bicepsperna;
    ArrayList<String> cinturaQuadril;
    ArrayWheelAdapter<String> listPesos;
    ArrayWheelAdapter<String> lstMedidasBicepsPerna;
    ArrayWheelAdapter<String> lstCinturaQuadril;
    private int ID_SEMANA;
    private TextView txtPesoMeta;
    private Toolbar mToolBar;

    SessionManager session;

    List<SemanaEvolucao> semanas;
    SemanaEvolucao semanaEvolucao;

    String InicioSemana;
    String FimSemana;

    TextView textSemana;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidas);

        myContext = this;

        session = new SessionManager(myContext);
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);

        setActionBarBackArrow();
        setBarTitle("Medidas");

        btnConcluir = (Button) findViewById(R.id.btnConcluir);
        pesoWheel = (AbstractWheel) findViewById(R.id.peso);
        pesoMetaWheel = (AbstractWheel) findViewById(R.id.pesoMeta);
        bicepsWheel = (AbstractWheel) findViewById(R.id.biceps);
        cinturaWheel = (AbstractWheel) findViewById(R.id.cintura);
        quadrilWheel = (AbstractWheel) findViewById(R.id.quadril);
        pernaWheel = (AbstractWheel) findViewById(R.id.perna);
        textSemana = (TextView) findViewById(R.id.textSemana);
        txtPesoMeta = (TextView) findViewById(R.id.txtPesoMeta);

        ID_SEMANA = getIntent().getIntExtra("ID_SEMANA",1);

        Init();
    }

    public void setActionBarBackArrow()
    {
        mToolBar.setNavigationIcon(R.mipmap.ic_up21);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setBarTitle(String s) {
        getSupportActionBar().setTitle(s);
    }

    protected void Init()
    {

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarMedidas();
            }
        });

        weight = new ArrayList<>();
        bicepsperna = new ArrayList<>();
        cinturaQuadril = new ArrayList<>();

        for (float i = 40.0f; i < 199.9f; i = i + 0.1f ){
            weight.add(String.format("%.1f",i).replace(".",","));
        }
        for (float i = 15.0f; i < 80.9f; i = i + 0.1f ){
            bicepsperna.add(String.format("%.1f",i).replace(".", ","));
        }
        for (float i = 30.0f; i < 179.9f; i = i + 0.1f ){
            cinturaQuadril.add(String.format("%.1f",i).replace(".",","));
        }

        listPesos = new ArrayWheelAdapter<>(myContext, weight);

        listPesos.setItemResource(R.layout.wheel_text_centered);
        listPesos.setItemTextResource(R.id.text);

        lstMedidasBicepsPerna =
                new ArrayWheelAdapter<>(myContext, bicepsperna);

        lstMedidasBicepsPerna.setItemResource(R.layout.wheel_text_centered);
        lstMedidasBicepsPerna.setItemTextResource(R.id.text);

        lstCinturaQuadril = new ArrayWheelAdapter<>(myContext, cinturaQuadril);

        lstCinturaQuadril.setItemResource(R.layout.wheel_text_centered);
        lstCinturaQuadril.setItemTextResource(R.id.text);

        pesoWheel.setViewAdapter(listPesos);
        if(ID_SEMANA > 1) {
            pesoMetaWheel.setVisibility(View.GONE);
            txtPesoMeta.setVisibility(View.GONE);
        }
        else {
            pesoMetaWheel.setViewAdapter(listPesos);
        }

        bicepsWheel.setViewAdapter(lstMedidasBicepsPerna);
        pernaWheel.setViewAdapter(lstMedidasBicepsPerna);
        cinturaWheel.setViewAdapter(lstCinturaQuadril);
        quadrilWheel.setViewAdapter(lstCinturaQuadril);

        semanas = session.getSemanas(myContext);

        //Primeira Pesagem
        if((semanas == null)&&(ID_SEMANA == 1))
        {
            Calendar cal = Calendar.getInstance();
            InicioSemana = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d",cal.get(Calendar.MONTH) + 1);
            cal.add(Calendar.DATE, 7);
            FimSemana = String.format("%02d",cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", cal.get(Calendar.MONTH) + 1);

            pesoWheel.setCurrentItem(weight.indexOf("50,0"));
            pesoMetaWheel.setCurrentItem(weight.indexOf("50,0"));
            bicepsWheel.setCurrentItem(bicepsperna.indexOf("25,0"));
            cinturaWheel.setCurrentItem(cinturaQuadril.indexOf("60,0"));
            quadrilWheel.setCurrentItem(cinturaQuadril.indexOf("60,0"));
            pernaWheel.setCurrentItem(bicepsperna.indexOf("50,0"));
        }
        else
        {   //Recupero a ultima semana
            semanaEvolucao = semanas.get(semanas.size() - 1);

            //Atualizando a semana
            if(semanaEvolucao.getId() == ID_SEMANA){
                //Pegando o mesmo Inicio e Fim da semana
                InicioSemana = semanaEvolucao.getIni_semana();
                FimSemana = semanaEvolucao.getFim_semana();

                pesoWheel.setCurrentItem(weight.indexOf(semanaEvolucao.getPeso()));
                pesoMetaWheel.setCurrentItem(weight.indexOf(semanaEvolucao.getPesoMeta()));
                cinturaWheel.setCurrentItem(cinturaQuadril.indexOf(semanaEvolucao.getCintura()));
                quadrilWheel.setCurrentItem(cinturaQuadril.indexOf(semanaEvolucao.getQuadril()));
                bicepsWheel.setCurrentItem(bicepsperna.indexOf(semanaEvolucao.getBiceps()));
                pernaWheel.setCurrentItem(bicepsperna.indexOf(semanaEvolucao.getPerna()));
            }
            else { //Nova semana
                InicioSemana = semanaEvolucao.getFim_semana();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(InicioSemana.substring(0, 2)));
                cal.set(Calendar.MONTH, Integer.parseInt(InicioSemana.substring(3, 5)) -1 );
                cal.add(Calendar.DATE, 7);

                FimSemana = String.format("%02d",cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", cal.get(Calendar.MONTH) + 1);

                pesoWheel.setCurrentItem(weight.indexOf(semanaEvolucao.getPeso()));
                //pesoMetaWheel.setCurrentItem(weight.indexOf(semanaEvolucao.getPesoMeta()));
                cinturaWheel.setCurrentItem(cinturaQuadril.indexOf(semanaEvolucao.getCintura()));
                quadrilWheel.setCurrentItem(cinturaQuadril.indexOf(semanaEvolucao.getQuadril()));
                bicepsWheel.setCurrentItem(bicepsperna.indexOf(semanaEvolucao.getBiceps()));
                pernaWheel.setCurrentItem(bicepsperna.indexOf(semanaEvolucao.getPerna()));
            }
        }

        textSemana.setText("Semana "+String.valueOf(ID_SEMANA)+" (" + InicioSemana + " - " + FimSemana + ")");
    }

    private void salvarMedidas()
    {
        try {
            //Remove a semana e insere novamente
            if(semanaEvolucao != null) {
                if (semanaEvolucao.getId() == ID_SEMANA) {
                    session.removeSemana(semanaEvolucao);
                }
            }
            String peso = listPesos.getItemText(pesoWheel.getCurrentItem());
            String biceps = lstMedidasBicepsPerna.getItemText(bicepsWheel.getCurrentItem());
            String perna = lstMedidasBicepsPerna.getItemText(pernaWheel.getCurrentItem());
            String quadril = lstCinturaQuadril.getItemText(quadrilWheel.getCurrentItem());
            String cintura = lstCinturaQuadril.getItemText(cinturaWheel.getCurrentItem());

            String pesoMeta;

            if(ID_SEMANA <= 1)
                pesoMeta = listPesos.getItemText(pesoMetaWheel.getCurrentItem());
            else
                pesoMeta = "0";

            semanaEvolucao = new SemanaEvolucao(ID_SEMANA, InicioSemana, FimSemana, peso, pesoMeta, biceps, cintura, quadril, perna);
            session.addSemana(myContext, semanaEvolucao);
        }
        catch (Exception ex)
        {
            Toast.makeText(myContext, "Erro ao atualizar " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        finally {
            //Toast.makeText(myContext, "Peso e medidas atualizados com sucesso.", Toast.LENGTH_LONG).show();


            semanas = session.getSemanas(myContext);

            if(semanas != null) {
                if (semanas.size() > 0) {
                    SemanaEvolucao iSemana = semanas.get(0);
                    SemanaEvolucao aSemana = semanas.get(semanas.size() - 1);

                    float PesoIni = Float.parseFloat(iSemana.getPeso().replace(",","."));
                    float PesoMeta = Float.parseFloat(iSemana.getPesoMeta().replace(",", "."));
                    float PesoAtual = Float.parseFloat(aSemana.getPeso().replace(",", "."));

                    float PesoAperder = PesoIni - PesoMeta;
                    float PesoPerdido = PesoIni - PesoAtual;

                    float progress = (PesoPerdido / PesoAperder) * 100;

                    if (Math.round(progress) >= 20 && Math.round(progress) <= 40)
                        Notifications.criarNotificacaoParabens(myContext, getString(R.string.evolucao_p1), 0);
                    if (Math.round(progress) >= 40 && Math.round(progress) <= 60)
                        Notifications.criarNotificacaoParabens(myContext, getString(R.string.evolucao_p2), 0);
                    if (Math.round(progress) >= 60 && Math.round(progress) <= 80)
                        Notifications.criarNotificacaoParabens(myContext, getString(R.string.evolucao_p3), 0);
                    if (Math.round(progress) >= 100)
                        Notifications.criarNotificacaoParabens(myContext, getString(R.string.evolucao_p4), 0);

                }
            }

            goActivity(MainActivity.class);
        }
    }

    private void goActivity(Class c) {
        Intent it = new Intent(this.getApplicationContext(), c);
        it.putExtra("FRAGMENT_ID", 4);//Evolucao
        startActivity(it);
    }

    public void setAlarm(Context context, int ID_SEMANA, int DIA, int MES, int ANO){

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        cal.set(ANO, MES, DIA, 18, 16);

        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), getPendingIntent(context, ID_SEMANA));

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), getPendingIntent(context, ID_SEMANA));
        }else{
            am.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), getPendingIntent(context, ID_SEMANA));
        }
    }

    //get a PendingIntent
    PendingIntent getPendingIntent(Context context, int id) {
        Intent intent =  new Intent(context, OnAlarmReceiver.class).putExtra("ID_SEMANA", id);
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_ONE_SHOT);
    }


}
