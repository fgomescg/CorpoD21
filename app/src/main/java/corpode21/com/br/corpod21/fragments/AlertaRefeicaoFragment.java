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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.service.ScheduleClient;

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

    SessionManager session;

    private int mHour, mMinute;
    ScheduleClient scheduleClient;


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

        switchCafe.setOnCheckedChangeListener(switchClick);
        switchLancheManha.setOnCheckedChangeListener(switchClick);
        switchAlmoco.setOnCheckedChangeListener(switchClick);
        switchLancheTarde.setOnCheckedChangeListener(switchClick);
        switchJantar.setOnCheckedChangeListener(switchClick);
        switchCeia.setOnCheckedChangeListener(switchClick);

        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(getActivity());
        scheduleClient.doBindService();

        initFragment();

    }

    private void initFragment()
    {
        boolean checked = false;

        checked = (session.getVALUE(SessionManager.KEY_NCAFE).equals("1"));
        switchCafe.setChecked(checked);

        checked = (session.getVALUE(SessionManager.KEY_NLANCHEMANHA).equals("1")) ? true : false;
        switchLancheManha.setChecked(checked);

        checked = (session.getVALUE(SessionManager.KEY_NALMOCO).equals("1")) ? true : false;
        switchAlmoco.setChecked(checked);

        checked = (session.getVALUE(SessionManager.KEY_NLANCHETARDE).equals("1")) ? true : false;
        switchLancheTarde.setChecked(checked);

        checked = (session.getVALUE(SessionManager.KEY_NJANTAR).equals("1")) ? true : false;
        switchJantar.setChecked(checked);

        checked = (session.getVALUE(SessionManager.KEY_NCEIA).equals("1")) ? true : false;
        switchCeia.setChecked(checked);

        horaCafeManha.setText(session.getVALUE(SessionManager.KEY_HCAFE));
        horaLancheManha.setText(session.getVALUE(SessionManager.KEY_HLANCHEMANHA));
        horaAlmoco.setText(session.getVALUE(SessionManager.KEY_HALMOCO));
        horaLancheTarde.setText(session.getVALUE(SessionManager.KEY_HLANCHETARDE));
        horaJantar.setText(session.getVALUE(SessionManager.KEY_HJANTAR));
        horaCeia.setText(session.getVALUE(SessionManager.KEY_HCEIA));

    }

    private CompoundButton.OnCheckedChangeListener switchClick = new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            final String tag = buttonView.getTag().toString();
            final String onOFF = (isChecked) ? "1" : "0";

                       // Get a reference to our date picker


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
           // final Calendar c = Calendar.getInstance();
           // mHour = c.get(Calendar.HOUR_OF_DAY);
           // mMinute = c.get(Calendar.MINUTE);

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

        int day = 17;
        int month = 4;
        int year = 2015;
        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 26);
        c.set(Calendar.SECOND, 0);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c);
        // Notify the user what they just did
        Toast.makeText(getActivity(), "Notification set for: " + day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();


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
                                    break;
                                case "LancheManha":
                                    horaLancheManha.setText(time);
                                    session.setUserDetails(session.KEY_HLANCHEMANHA, time);
                                    break;
                                case "Almoco":
                                    horaAlmoco.setText(time);
                                    session.setUserDetails(session.KEY_HALMOCO, time);
                                    break;
                                case "LancheTarde":
                                    horaLancheTarde.setText(time);
                                    session.setUserDetails(session.KEY_HLANCHETARDE, time);
                                    break;
                                case "Jantar":
                                    horaJantar.setText(time);
                                    session.setUserDetails(session.KEY_HJANTAR, time);
                                    break;
                                case "Ceia":
                                    horaCeia.setText(time);
                                    session.setUserDetails(session.KEY_HCEIA, time);
                                    break;
                            }
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }


    @Override
    public void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }


}