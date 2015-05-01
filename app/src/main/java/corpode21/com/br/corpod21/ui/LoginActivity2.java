package corpode21.com.br.corpod21.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.entidades.Usuario;


public class LoginActivity2 extends Activity implements View.OnClickListener{

    Usuario usuario;
    SessionManager session;

    EditText edtPesoIni;
    EditText edtAltura;
    EditText edtPesoMeta;
    EditText edtBiceps;
    EditText edtCintura;
    EditText edtQuadril;
    EditText edtPerna;
    Button btnConcluir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2);

        usuario = getIntent().getParcelableExtra("Usuario");

        edtPesoIni = (EditText) findViewById(R.id.edtPesoIni);
        edtAltura = (EditText) findViewById(R.id.edtAltura);
        edtPesoMeta = (EditText) findViewById(R.id.edtPesoMeta);
        edtBiceps = (EditText) findViewById(R.id.edtBiceps);
        edtCintura = (EditText) findViewById(R.id.edtCintura);
        edtQuadril = (EditText) findViewById(R.id.edtQuadril);
        edtPerna = (EditText) findViewById(R.id.edtPerna);

        btnConcluir = (Button) findViewById(R.id.btnConcluir);
        btnConcluir.setOnClickListener(this);


    }

    public void onClick(View view){

       // Log.d("Valor", String.valueOf(PesoIni));
        boolean ok = true;

        if(edtPesoIni.getText().toString().equalsIgnoreCase("")){
            edtPesoIni.setError(getString(R.string.msg_campo_requirido));
            ok = false;
        }
        if(edtAltura.getText().toString().equalsIgnoreCase("")){
            edtAltura.setError(getString(R.string.msg_campo_requirido));
            ok = false;
        }
        if(edtPesoMeta.getText().toString().equalsIgnoreCase("")){
            edtPesoMeta.setError(getString(R.string.msg_campo_requirido));
            ok = false;
        }

        if(ok){

            usuario.setPesoIni(Float.parseFloat(edtPesoIni.getText().toString()));
            usuario.setAltura(Float.parseFloat(edtAltura.getText().toString()));
            usuario.setPesoMeta(Float.parseFloat(edtPesoMeta.getText().toString()));

            if(!edtBiceps.getText().toString().equalsIgnoreCase(""))
               usuario.setBiceps(Float.parseFloat(edtBiceps.getText().toString()));

            if(!edtCintura.getText().toString().equalsIgnoreCase(""))
                usuario.setCintura(Float.parseFloat(edtCintura.getText().toString()));

            if(!edtQuadril.getText().toString().equalsIgnoreCase(""))
                usuario.setQuadril(Float.parseFloat(edtQuadril.getText().toString()));

            if(!edtPerna.getText().toString().equalsIgnoreCase(""))
                usuario.setPerna(Float.parseFloat(edtPerna.getText().toString()));

            // Session Manager
            session = new SessionManager(getApplicationContext());

            session.createLoginSession(usuario);

           Intent it = new Intent(getApplicationContext(), MainActivity.class);
           it.putExtra("Usuario", usuario);
           startActivity(it);
           finish();
        }

    }


    private String acessaSharedPreferences() {
        // Acesso às Shared Preferences usando o nome definido.
        SharedPreferences prefs = getSharedPreferences("preferencias_1", Context.MODE_PRIVATE);

        // Acesso às informações de acordo com o tipo.
        String texto = prefs.getString("TEXTO", "não encontrado");
        long numero = prefs.getLong("NUMERO", 0);

        // Formata um string com tdo o conteúdo separado por linha.
        return (texto + "n" + numero);
    }

}

