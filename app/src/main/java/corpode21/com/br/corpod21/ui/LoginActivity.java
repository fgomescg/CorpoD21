package corpode21.com.br.corpod21.ui;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.List;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.AlertManager;

import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.entidades.Usuario;

public class LoginActivity extends Activity  implements Runnable  {

    EditText edtEmail;
    EditText edtSenha;
    Button btLogin;
    Usuario usuario;

    SessionManager session;

    // Alert  Manager
    AlertManager alert = new AlertManager();

    private static final String NAMESPACE = "http://www.w3schools.com/webservices/";
    private static final String MAIN_REQUEST_URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
    private static final String SOAP_ACTION = "http://www.w3schools.com/webservices/FahrenheitToCelsius";

    public void run() {
        chamarWS();
    }


    public void chamarWS() {

        //Objeto composto pelo NameSpace e pelo método que queremos chamar
        SoapObject soap = new SoapObject("http://api.leadlovers.com/api/",
                "GetToken");

        soap.addProperty("userLogin","raphamzn@gmail.com");
        soap.addProperty("userPass", "AB3");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);

        String url = "http://api.leadlovers.com/api/LeadLoversAPI.asmx";

        HttpTransportSE httpTransport = new HttpTransportSE(url);

        try{
            httpTransport.call("http://tempuri.org/GetToken", envelope);
            Object msg = envelope.getResponse();
            Log.d("WSERVICE", "Resposta: " + msg);
            dialog.dismiss();
        }
            catch (Exception e) {
                Log.e("GB", "CATH Login!");
                finish();
            }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView t1 = (TextView) findViewById(R.id.txtLinkPrograma);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btLogin = (Button) findViewById(R.id.btnLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoginClick(v);
            }
        });

    }


    ProgressDialog dialog;

    public void btnLoginClick(View view){

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
               // dialog = ProgressDialog.show(LoginActivity.this, "", "Por Favor, aguarde...", true);
                //new Thread(this).start();

                //TODO Login 70if(retorno == "ok")
                usuario = new Usuario(nome,email,senha,0,0,0,0,0,0,0,null);

                session = new SessionManager(getApplicationContext());

                session.createLoginSession(usuario);

                Intent it = new Intent(getApplicationContext(), MainActivity.class);
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


