package corpode21.com.br.corpod21.service;

/**
 * Created by Fabio on 18/05/2015.
 */
import corpode21.com.br.corpod21.Util.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * BroadCastReceiver for Alarms, displays notifications as it receives alarm
 * and then starts TaskButlerService to update alarm schedule with AlarmManager
 * @author Dhimitraq Jorgji
 *
 */
public class OnAlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICACAO_CAFE = 1;
    private static final int NOTIFICACAO_LANCHE_MANHA = 2;
    private static final int NOTIFICACAO_ALMOCO = 3;
    private static final int NOTIFICACAO_LANCHE_TARDE = 4;
    private static final int NOTIFICACAO_JANTAR = 5;
    private static final int NOTIFICACAO_CEIA = 6;

    @Override
    public void onReceive(Context context, Intent intent) {
        WakefulIntentService.acquireStaticLock(context); //acquire a partial WakeLock

        //send notification, bundle intent with taskID
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("REFEICAO_ID");

        switch (id) {

            case NOTIFICACAO_CAFE:
                Notifications.criarNotificacaoRefeicao(context, "Cafe da Manha", 0);
                break;
            case NOTIFICACAO_LANCHE_MANHA:
                Notifications.criarNotificacaoRefeicao(context, "Lanche da Manha", 0);
                break;
            case NOTIFICACAO_ALMOCO:
                Notifications.criarNotificacaoRefeicao(context, "Almoco", 0);
                break;
            case NOTIFICACAO_LANCHE_TARDE:
                Notifications.criarNotificacaoRefeicao(context, "Lanche da Tarde", 0);
                break;
            case NOTIFICACAO_JANTAR:
                Notifications.criarNotificacaoRefeicao(context, "Jantar", 0);
                break;
            case NOTIFICACAO_CEIA:
                Notifications.criarNotificacaoRefeicao(context, "Ceia", 0);
                break;
        }

        context.startService(new Intent(context, TaskButlerService.class)); //start TaskButlerService
    }

}