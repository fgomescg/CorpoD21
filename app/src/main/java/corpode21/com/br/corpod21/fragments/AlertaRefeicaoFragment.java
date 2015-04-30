package corpode21.com.br.corpod21.fragments;

/**
 * Created by Fabio on 15/04/2015.
 */


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.SessionManager;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AlertaRefeicaoFragment extends Fragment implements
        View.OnClickListener {

    private TextView horaCafeManha;
    private TextView horaLancheManha;
    private TextView horaAlmoco;
    private TextView horaLancheTarde;
    private TextView horaJantar;
    private TextView horaCeia;

    private Switch sw_refeicao;
    private ImageView img_hora_edit;
    List<ImageView> buttons = new ArrayList<ImageView>();
    ViewGroup Container;

    SessionManager session;

    // Variable for storing current date and time
    private int mHour, mMinute;


    public AlertaRefeicaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.alerta_refeicao_fragment, container, false);
        Container = container;

        ImageView clockCafe = (ImageView) v.findViewById(R.id.clockCafe);
        clockCafe.setOnClickListener(this);

        ImageView clockLancheManha = (ImageView) v.findViewById(R.id.clockLancheManha);
        clockLancheManha.setOnClickListener(this);

        ImageView clockAlmoco = (ImageView) v.findViewById(R.id.clockAlmoco);
        clockAlmoco.setOnClickListener(this);

        ImageView clockLancheTarde = (ImageView) v.findViewById(R.id.clockLancheTarde);
        clockLancheTarde.setOnClickListener(this);

        ImageView clockJantar = (ImageView) v.findViewById(R.id.clockJantar);
        clockJantar.setOnClickListener(this);

        ImageView clockCeia = (ImageView) v.findViewById(R.id.clockCeia);
        clockCeia.setOnClickListener(this);

        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        session = new SessionManager(getActivity().getApplicationContext());

        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //NAME = user.get(SessionManager.KEY_NAME);
        //EMAIL = user.get(SessionManager.KEY_EMAIL);


        horaCafeManha = (TextView) getActivity().findViewById(R.id.horaCafeManha);
        horaLancheManha = (TextView) getActivity().findViewById(R.id.horaLancheManha);
        horaAlmoco = (TextView) getActivity().findViewById(R.id.horaAlmoco);
        horaLancheTarde = (TextView) getActivity().findViewById(R.id.horaLancheTarde);
        horaJantar = (TextView) getActivity().findViewById(R.id.horaJantar);
        horaCeia = (TextView) getActivity().findViewById(R.id.horaCeia);
        sw_refeicao = (Switch) getActivity().findViewById(R.id.switchRefeicao);

        //set the switch to ON
        //sw_refeicao.setChecked(true);



        //attach a listener to check for changes in state
        sw_refeicao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                final String tag = buttonView.getTag().toString();
                final String onOFF = (isChecked) ? "1" : "0";

                switch (tag) {
                    case "Cafe":
                      session.setUserDetails(session.KEY_NCAFE, onOFF);
                        break;
                    case "LancheManha":
                        session.setUserDetails(session.KEY_NLANCHEMANHA, onOFF);
                        break;
                    case "Almoco":
                        session.setUserDetails(session.KEY_NALMOCO, onOFF);
                        break;
                    case "LancheTarde":
                        session.setUserDetails(session.KEY_NLANCHETARDE, onOFF);
                        break;
                    case "Jantar":
                        session.setUserDetails(session.KEY_NJANTAR, onOFF);
                        break;
                    case "Ceia":
                        session.setUserDetails(session.KEY_NCEIA, onOFF);
                        break;
                }
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener switchClick = new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            final String tag = buttonView.getTag().toString();
            final String onOFF = (isChecked) ? "1" : "0";

            switch (tag) {
                case "Cafe":
                    session.setUserDetails(session.KEY_NCAFE, onOFF);
                    break;
                case "LancheManha":
                    session.setUserDetails(session.KEY_NLANCHEMANHA, onOFF);
                    break;
                case "Almoco":
                    session.setUserDetails(session.KEY_NALMOCO, onOFF);
                    break;
                case "LancheTarde":
                    session.setUserDetails(session.KEY_NLANCHETARDE, onOFF);
                    break;
                case "Jantar":
                    session.setUserDetails(session.KEY_NJANTAR, onOFF);
                    break;
                case "Ceia":
                    session.setUserDetails(session.KEY_NCEIA, onOFF);
                    break;
            }
        }
    };


    /** Add padding to numbers less than ten */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public void onClick(View v) {

        final String tag = v.getTag().toString();

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            switch (tag) {
                                case "Cafe":
                                    horaCafeManha.setText(hourOfDay + ":" + minute);
                                    break;
                                case "LancheManha":
                                    horaLancheManha.setText(hourOfDay + ":" + minute);
                                    break;
                                case "Almoco":
                                    horaAlmoco.setText(hourOfDay + ":" + minute);
                                    break;
                                case "LancheTarde":
                                    horaLancheTarde.setText(hourOfDay + ":" + minute);
                                    break;
                                case "Jantar":
                                    horaJantar.setText(hourOfDay + ":" + minute);
                                    break;
                                case "Ceia":
                                    horaCeia.setText(hourOfDay + ":" + minute);
                                    break;
                            }
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }


}