package corpode21.com.br.corpod21.Util;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import corpode21.com.br.corpod21.DetalheActivity;
import corpode21.com.br.corpod21.R;

/**
 * Created by Fabio on 30/04/2015.
 */
public class Notifications {

    public static final String ACAO_DELETE =  "DELETE_NOTIFICACAO";
    public static final String ACAO_NOTIFICACAO =  "ACAO_NOTIFICACAO";


    private  static PendingIntent criarPendingIntent(Context ctx, String texto, int id) {

        Intent resultIntent = new Intent(ctx, DetalheActivity.class);
        resultIntent.putExtra(DetalheActivity.EXTRA_TEXTO, texto);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(DetalheActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        return stackBuilder.getPendingIntent(
                id, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static void criarNotificacaoSimples(Context ctx, String texto, int id) {

        PendingIntent resultPendingIntent = criarPendingIntent(ctx, texto, id);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.drawable.ic_notificacao)
                    .setContentTitle("Simples " + id)
                    .setContentText(texto)
                    .setContentIntent(resultPendingIntent);

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        nm.notify(id, mBuilder.build());
    }

    public static void criarNotificacaoCompleta(Context ctx, String texto, int id)
    {
        Uri uriSom = Uri.parse("android.resource://" + ctx.getPackageName()
                                                +"/raw/som_notificacao");

        PendingIntent pitAcao = PendingIntent.getBroadcast(ctx, 0, new Intent(ACAO_NOTIFICACAO), 0);
        PendingIntent pitDelete = PendingIntent.getBroadcast(ctx, 0, new Intent(ACAO_DELETE), 0);
        Bitmap largeIcon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher);
        PendingIntent pitNotificacao = criarPendingIntent(ctx, texto, id);

        NotificationCompat.Builder mBuilder =
                   new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.ic_notificacao)
                .setContentTitle("Completa")
                .setContentText(texto)
                .setTicker("Chegou uma notificação")
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setContentIntent(pitNotificacao)
                .setDeleteIntent(pitDelete)
                .setLights(Color.BLUE, 1000, 5000)
                .setSound(uriSom)
                .setVibrate(new long[]{100,500,200,800})
                .addAction(R.drawable.ic_acao_notificacao, "Ação Customizada", pitAcao)
                .setNumber(id)
                .setSubText("Subtexto");

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        nm.notify(id, mBuilder.build());
    }

    public static void criarNotificationBig(Context ctx, int idNotificacao) {
        int numero = 5;

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Mensagens não lidas:");
        for(int i = 1; i <= numero; i++){
            inboxStyle.addLine("Mensagem "+ i);
        }
        inboxStyle.setSummaryText("Clique para exibir");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.ic_notificacao)
                .setContentTitle("Notificação")
                .setContentTitle("Vários itens pendentes")
                .setContentIntent(criarPendingIntent(ctx, "Mensagens não lidas", 1))
                .setNumber(numero)
                .setStyle(inboxStyle);

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        nm.notify(idNotificacao, mBuilder.build());
    }

}
