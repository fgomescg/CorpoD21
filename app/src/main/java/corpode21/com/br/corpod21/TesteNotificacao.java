package corpode21.com.br.corpod21;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import corpode21.com.br.corpod21.Util.Notifications;


public class TesteNotificacao extends ActionBarActivity {

    private static final int NOTIFICACAO_SIMPLES = 1;
    private static final int NOTIFICACAO_COMPLETA = 2;
    private static final int NOTIFICACAO_BIG = 3;

    EditText mEdtTexto;
    MeuReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_notificacao);
        mEdtTexto = (EditText) findViewById(R.id.editText);
        mReceiver = new MeuReceiver();
        registerReceiver(mReceiver, new IntentFilter(Notifications.ACAO_DELETE));
        registerReceiver(mReceiver, new IntentFilter(Notifications.ACAO_NOTIFICACAO));

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    public void criarNofiticacaoSimples(View v){
      Notifications.criarNotificacaoSimples(this, mEdtTexto.getText().toString(), NOTIFICACAO_SIMPLES);
    }


    public void criarNotificacaoCompleta(View v){
        Notifications.criarNotificacaoCompleta(this, mEdtTexto.getText().toString(), NOTIFICACAO_COMPLETA);
    }

    public void criarNotificacaoBig(View v){
        Notifications.criarNotificationBig(this, NOTIFICACAO_BIG);
    }

    class MeuReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(TesteNotificacao.this, intent.getAction(), Toast.LENGTH_SHORT).show();
        }


    }

}
