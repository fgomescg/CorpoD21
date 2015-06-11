package corpode21.com.br.corpod21.service;

/**
 * Created by Fabio on 18/05/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * BroadCastReceiver for android.intent.action.BOOT_COMPLETED
 * passes all responsibility to TaskButlerService.
 * @author Dhimitraq Jorgji
 *
 */
public class OnBootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        WakefulIntentService.acquireStaticLock(context); //acquire a partial WakeLock
        context.startService(new Intent(context, TaskButlerService.class)); //start TaskButlerService
    }
}