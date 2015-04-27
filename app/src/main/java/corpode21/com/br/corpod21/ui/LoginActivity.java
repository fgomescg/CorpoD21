package corpode21.com.br.corpod21.ui;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.AlertManager;

import corpode21.com.br.corpod21.entidades.Usuario;

public class LoginActivity extends Activity  implements View.OnClickListener {

    EditText edtEmail;
    EditText edtSenha;
    Button btLogin;
    Usuario usuario;

    // Alert  Manager
    AlertManager alert = new AlertManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView t1 = (TextView) findViewById(R.id.txtLinkPrograma);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        btLogin = (Button) findViewById(R.id.btnLogin);
        btLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){

            String nome = "Fábio";//TODO - Pegar nome do WS
            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();

            boolean ok = true;

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edtEmail.setError(getString(R.string.msg_erro_email));
                ok = false;
            }
            if(senha.toString().equalsIgnoreCase("")){
                edtSenha.setError("Entre com a senha.");
                ok = false;
            }
            else
            if(!senha.equals("123")) {
                alert.showAlertDialog(LoginActivity.this, "Erro", "Email ou Senha inválidos!", false);
                ok = false;
            }

            if(ok){

                usuario = new Usuario(nome,email,senha,0,0,0,0,0,0,0,null);

                Intent it = new Intent(getApplicationContext(), LoginActivity2.class);
                it.putExtra("Usuario", usuario);
                startActivity(it);
                finish();
            }
      }



    protected void onResume() {
       super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

    }

 }


