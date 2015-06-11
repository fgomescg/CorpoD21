package corpode21.com.br.corpod21.fragments;

/**
 * Created by Fabio on 15/04/2015.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.HashMap;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.service.OnAlarmReceiver;

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

    private Switch switchCafe;
    private Switch switchLancheManha;
    private Switch switchAlmoco;
    private Switch switchLancheTarde;
    private Switch switchJantar;
    private Switch switchCeia;

    private static final int NOTIFICACAO_CAFE = 1;
    private static final int NOTIFICACAO_LANCHE_MANHA = 2;
    private static final int NOTIFICACAO_ALMOCO = 3;
    private static final int NOTIFICACAO_LANCHE_TARDE = 4;
    private static final int NOTIFICACAO_JANTAR = 5;
    private static final int NOTIFICACAO_CEIA = 6;

    SessionManager session;

    private int mHour, mMinute;

    AlarmManager am;


    public AlertaRefeicaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_alerta_refeicao, container, false);

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

        HashMap<String, String> user = session.getUserDetails();

        horaCafeManha = (TextView) getActivity().findViewById(R.id.horaCafe);
        horaLancheManha = (TextView) getActivity().findViewById(R.id.horaLancheManha);
        horaAlmoco = (TextView) getActivity().findViewById(R.id.horaAlmoco);
        horaLancheTarde = (TextView) getActivity().findViewById(R.id.horaLancheTarde);
        horaJantar = (TextView) getActivity().findViewById(R.id.horaJantar);
        horaCeia = (TextView) getActivity().findViewById(R.id.horaCeia);

        switchCafe = (Switch) getActivity().findViewById(R.id.switchCafe);
        switchLancheManha = (Switch) getActivity().findViewById(R.id.switchLancheManha);
        switchAlmoco = (Switch) getActivity().findViewById(R.id.switchAlmoco);
        switchLancheTarde = (Switch) getActivity().findViewById(R.id.switchLancheTarde);
        switchJantar = (Switch) getActivity().findViewById(R.id.switchJantar);
        switchCeia = (Switch) getActivity().findViewById(R.id.switchCeia);

        init();

    }


    private void init()
    {
        horaCafeManha.setText(session.getVALUE(SessionManager.KEY_HCAFE));
        horaLancheManha.setText(session.getVALUE(SessionManager.KEY_HLANCHEMANHA));
        horaAlmoco.setText(session.getVALUE(SessionManager.KEY_HALMOCO));
        horaLancheTarde.setText(session.getVALUE(SessionManager.KEY_HLANCHETARDE));
        horaJantar.setText(session.getVALUE(SessionManager.KEY_HJANTAR));
        horaCeia.setText(session.getVALUE(SessionManager.KEY_HCEIA));

        switchCafe.setClickable(true);

        switchCafe.setChecked((Boolean.valueOf(session.getVALUE(SessionManager.KEY_NCAFE))));
        switchLancheManha.setChecked((Boolean.valueOf(session.getVALUE(SessionManager.KEY_NLANCHEMANHA))));
        switchAlmoco.setChecked((Boolean.valueOf(session.getVALUE(SessionManager.KEY_NALMOCO))));
        switchLancheTarde.setChecked((Boolean.valueOf(session.getVALUE(SessionManager.KEY_NLANCHETARDE))));
        switchJantar.setChecked((Boolean.valueOf(session.getVALUE(SessionManager.KEY_NJANTAR))));
        switchCeia.setChecked((Boolean.valueOf(session.getVALUE(SessionManager.KEY_NCEIA))));

        switchCafe.setOnCheckedChangeListener(switchClick);
        switchLancheManha.setOnCheckedChangeListener(switchClick);
        switchAlmoco.setOnCheckedChangeListener(switchClick);
        switchLancheTarde.setOnCheckedChangeListener(switchClick);
        switchJantar.setOnCheckedChangeListener(switchClick);
        switchCeia.setOnCheckedChangeListener(switchClick);

    }



    private CompoundButton.OnCheckedChangeListener switchClick = new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton switchView, boolean isChecked) {

            final String tag = switchView.getTag().toString();

            int Hour;
            int Minute;

            switch (tag) {
                case "Cafe":
                    session.setUserDetails(session.KEY_NCAFE, String.valueOf(isChecked));
                    if(isChecked) {
                        Hour = Integer.parseInt(horaCafeManha.getText().toString().substring(0, 2));
                        Minute = Integer.parseInt(horaCafeManha.getText().toString().substring(3,5));

                        setAlarm(getActivity(), NOTIFICACAO_CAFE, Hour, Minute);
                    }
                    else
                        cancelAlarm(getActivity(), NOTIFICACAO_CAFE);
                    break;
                case "LancheManha":
                    session.setUserDetails(session.KEY_NLANCHEMANHA, String.valueOf(isChecked));

                    if(isChecked) {
                        Hour = Integer.parseInt(horaLancheManha.getText().toString().substring(0, 2));
                        Minute = Integer.parseInt(horaLancheManha.getText().toString().substring(3,5));

                        setAlarm(getActivity(), NOTIFICACAO_LANCHE_MANHA, Hour, Minute);
                    }
                    else
                        cancelAlarm(getActivity(), NOTIFICACAO_LANCHE_MANHA);
                    break;
                case "Almoco":
                    session.setUserDetails(session.KEY_NALMOCO, String.valueOf(isChecked));
                        if(isChecked) {
                             Hour = Integer.parseInt(horaAlmoco.getText().toString().substring(0, 2));
                             Minute = Integer.parseInt(horaAlmoco.getText().toString().substring(3,5));

                            setAlarm(getActivity(), NOTIFICACAO_ALMOCO, Hour, Minute);
                        }
                    else
                        cancelAlarm(getActivity(), NOTIFICACAO_ALMOCO);

                    break;
                case "LancheTarde":
                    session.setUserDetails(session.KEY_NLANCHETARDE, String.valueOf(isChecked));

                    if(isChecked) {
                         Hour = Integer.parseInt(horaLancheTarde.getText().toString().substring(0, 2));
                         Minute = Integer.parseInt(horaLancheTarde.getText().toString().substring(3,5));

                        setAlarm(getActivity(), NOTIFICACAO_LANCHE_TARDE, Hour, Minute);
                    }
                    else
                        cancelAlarm(getActivity(), NOTIFICACAO_LANCHE_TARDE);
                    break;
                case "Jantar":
                    session.setUserDetails(session.KEY_NJANTAR, String.valueOf(isChecked));

                    if(isChecked) {
                         Hour = Integer.parseInt(horaJantar.getText().toString().substring(0, 2));
                         Minute = Integer.parseInt(horaJantar.getText().toString().substring(3,5));

                        setAlarm(getActivity(), NOTIFICACAO_JANTAR, Hour, Minute);
                    }
                    else
                        cancelAlarm(getActivity(), NOTIFICACAO_JANTAR);
                    break;
                case "Ceia":
                    session.setUserDetails(session.KEY_NCEIA, String.valueOf(isChecked));

                    if(isChecked) {
                         Hour = Integer.parseInt(horaCeia.getText().toString().substring(0, 2));
                         Minute = Integer.parseInt(horaCeia.getText().toString().substring(3,5));

                        setAlarm(getActivity(), NOTIFICACAO_CEIA, Hour, Minute);
                    } else
                        cancelAlarm(getActivity(), NOTIFICACAO_CEIA);

                    break;
            }

        }
    };


    public void setAlarm(Context context, int id, int Hour, int Minute){

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, Hour);
        calSet.set(Calendar.MINUTE, Minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.compareTo(calNow) <= 0) {
            //Hora setada de hoje passou, pula pra amanhã
            calSet.add(Calendar.DATE, 1);
        }

        am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), am.INTERVAL_DAY , getPendingIntent(context, id));
    }

    public void cancelAlarm(Context context, int id){

        am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(getPendingIntent(context, id));
    }

    //get a PendingIntent
    PendingIntent getPendingIntent(Context context, int id) {
        Intent intent =  new Intent(context, OnAlarmReceiver.class).putExtra("REFEICAO_ID", id);
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


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

                switch (tag) {
                    case "Cafe":
                        mHour = Integer.parseInt(horaCafeManha.getText().toString().substring(0, 2));
                        mMinute = Integer.parseInt(horaCafeManha.getText().toString().substring(3,5));
                        break;
                    case "LancheManha":
                        mHour = Integer.parseInt(horaLancheManha.getText().toString().substring(0, 2));
                        mMinute = Integer.parseInt(horaLancheManha.getText().toString().substring(3,5));
                        break;
                    case "Almoco":
                        mHour = Integer.parseInt(horaAlmoco.getText().toString().substring(0, 2));
                        mMinute = Integer.parseInt(horaAlmoco.getText().toString().substring(3,5));
                        break;
                    case "LancheTarde":
                        mHour = Integer.parseInt(horaLancheTarde.getText().toString().substring(0, 2));
                        mMinute = Integer.parseInt(horaLancheTarde.getText().toString().substring(3,5));
                        break;
                    case "Jantar":
                        mHour = Integer.parseInt(horaJantar.getText().toString().substring(0, 2));
                        mMinute = Integer.parseInt(horaJantar.getText().toString().substring(3,5));
                        break;
                    case "Ceia":
                        mHour = Integer.parseInt(horaCeia.getText().toString().substring(0, 2));
                        mMinute = Integer.parseInt(horaCeia.getText().toString().substring(3,5));
                        break;
                }


            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            String time = String.format("%02d:%02d", hourOfDay, minute);
                            switch (tag) {
                                case "Cafe":
                                    horaCafeManha.setText(time);
                                    session.setUserDetails(session.KEY_HCAFE, time);
                                    if(switchCafe.isChecked()) {
                                        setAlarm(getActivity(), NOTIFICACAO_CAFE, hourOfDay, minute);
                                    }
                                    break;
                                case "LancheManha":
                                    horaLancheManha.setText(time);
                                    session.setUserDetails(session.KEY_HLANCHEMANHA, time);
                                    if(switchLancheManha.isChecked()) {
                                        setAlarm(getActivity(), NOTIFICACAO_LANCHE_MANHA, hourOfDay, minute);
                                    }
                                    break;
                                case "Almoco":
                                    horaAlmoco.setText(time);
                                    session.setUserDetails(session.KEY_HALMOCO, time);
                                    if(switchAlmoco.isChecked()) {
                                        setAlarm(getActivity(), NOTIFICACAO_ALMOCO, hourOfDay, minute);
                                    }
                                    break;
                                case "LancheTarde":
                                    horaLancheTarde.setText(time);
                                    session.setUserDetails(session.KEY_HLANCHETARDE, time);
                                    if(switchLancheTarde.isChecked()) {
                                        setAlarm(getActivity(), NOTIFICACAO_LANCHE_TARDE, hourOfDay, minute);
                                    }
                                    break;
                                case "Jantar":
                                    horaJantar.setText(time);
                                    session.setUserDetails(session.KEY_HJANTAR, time);
                                    if(switchJantar.isChecked()) {
                                        setAlarm(getActivity(), NOTIFICACAO_JANTAR, hourOfDay, minute);
                                    }
                                    break;
                                case "Ceia":
                                    horaCeia.setText(time);
                                    session.setUserDetails(session.KEY_HCEIA, time);
                                    if(switchCeia.isChecked()) {
                                        setAlarm(getActivity(), NOTIFICACAO_CEIA, hourOfDay, minute);
                                    }
                                    break;
                            }

                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }

}