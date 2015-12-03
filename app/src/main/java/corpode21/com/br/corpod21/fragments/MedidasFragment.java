package corpode21.com.br.corpod21.fragments;


import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.entidades.SemanaEvolucao;
import corpode21.com.br.corpod21.spinnerwheel.AbstractWheel;
import corpode21.com.br.corpod21.spinnerwheel.adapters.ArrayWheelAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedidasFragment extends Fragment {

    private FragmentActivity myContext;
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

   SessionManager session;

    List<SemanaEvolucao> semanas;
    SemanaEvolucao semanaEvolucao;

    String InicioSemana;
    String FimSemana;

    TextView textSemana;

    private final String TAG = "MedidasFragment";

    public MedidasFragment() {
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_medidas, container, false);

        session = new SessionManager(myContext);


        //getActivity().setTitle("Medidas");

        btnConcluir = (Button)v.findViewById(R.id.btnConcluir);
        pesoWheel = (AbstractWheel) v.findViewById(R.id.peso);
        pesoMetaWheel = (AbstractWheel) v.findViewById(R.id.pesoMeta);
        bicepsWheel = (AbstractWheel) v.findViewById(R.id.biceps);
        cinturaWheel = (AbstractWheel) v.findViewById(R.id.cintura);
        quadrilWheel = (AbstractWheel) v.findViewById(R.id.quadril);
        pernaWheel = (AbstractWheel) v.findViewById(R.id.perna);
        textSemana = (TextView) v.findViewById(R.id.textSemana);
        txtPesoMeta = (TextView) v.findViewById(R.id.txtPesoMeta);

        ID_SEMANA = getArguments().getInt("ID_SEMANA");

        if(ID_SEMANA == 0)
            ID_SEMANA = 1;

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Init();
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
            weight.add(String.format("%.1f",i));
        }
        for (float i = 15.0f; i < 80.9f; i = i + 0.1f ){
            bicepsperna.add(String.format("%.1f",i));
        }
        for (float i = 30.0f; i < 179.9f; i = i + 0.1f ){
            cinturaQuadril.add(String.format("%.1f",i));
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
            Toast.makeText(myContext, "Peso e medidas atualizados com sucesso.",
                    Toast.LENGTH_LONG).show();

            openFragment(new EvolucaoFragment());

        }

    }

    private void openFragment(final Fragment fragment) {
        myContext.getSupportFragmentManager()
                .beginTransaction()//.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left,R.anim.enter_from_left,R.anim.exit_from_right)
                .replace(R.id.content_frame, fragment)
                .commit();
    }

}


