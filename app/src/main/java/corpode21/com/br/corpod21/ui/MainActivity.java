package corpode21.com.br.corpod21.ui;


import android.app.PendingIntent;
import android.os.Bundle;

import java.util.Calendar;

import corpode21.com.br.corpod21.BaseActivity;
import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.service.ScheduleClient;


public class MainActivity extends BaseActivity {

    private PendingIntent pendingIntent;

    // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDefaultNavDrawer();

        selectItemMenu(MENU_MODULOS);

        // Create a new service client and bind our activity to this service
        //scheduleClient = new ScheduleClient(this);
        //scheduleClient.doBindService();

        //setNotification(11, 5, 2015, 21, 44, 0);

    }

    public void setNotification(int day, int month, int year, int hour, int minute, int second){

        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c);
        // Notify the user what they just did
        //Toast.makeText(this, "Notification set for: "+ day +"/"+ (month+1) +"/"+ year, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

}