package corpode21.com.br.corpod21.ui;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import corpode21.com.br.corpod21.MainActivity;
import corpode21.com.br.corpod21.Util.SOAPWservice;


import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.AlertManager;

import corpode21.com.br.corpod21.Util.SessionManager;
import corpode21.com.br.corpod21.entidades.Usuario;

public class LoginActivity extends Activity   {

    EditText edtEmail;
    EditText edtSenha;
    Button btLogin;
    Usuario usuario;

    SessionManager session;
    ProgressDialog dialog;

    // Alert  Manager
    AlertManager alert = new AlertManager();

    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String MAIN_REQUEST_URL = "http://api.leadlovers.com/api/LeadLoversAPI.asmx";
    private static final String METHOD_GETTOKEN = "GetToken";
    private static final String METHOD_GETPRODUTOS = "GetProdutos";
    private static final String METHOD_VALIDAALUNO = "ValidaAluno";

    private String TOKEN;
    private String HASHPRODUTO;

    private String NOME;
    private String EMAIL;
    private String SENHA;
    private String FOTO_ALUNO;
    private String URL_FOTO;
    private String MENSAGEM = null;


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


    public void btnLoginClick(View view){

            EMAIL = edtEmail.getText().toString();
            SENHA = edtSenha.getText().toString();

            boolean ok = true;

            if(EMAIL.toString().equalsIgnoreCase("")){
                edtEmail.setError(getString(R.string.msg_campo_requirido));
                ok = false;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                edtEmail.setError(getString(R.string.msg_erro_email));
                ok = false;
            }
            if(SENHA.toString().equalsIgnoreCase("")){
                edtSenha.setError(getString(R.string.msg_campo_requirido));
                ok = false;
            }

            if(ok){
                dialog = ProgressDialog.show(LoginActivity.this, "", "Por Favor, aguarde...", true);
                final loginAluno LoginAluno = new loginAluno(LoginActivity.this);
                LoginAluno.execute("");
            }
      }


    private class loginAluno extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public loginAluno(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... param) {

            HashMap<String, String> parametersToken = new HashMap<>();

            parametersToken.put("userLogin", getString(R.string.c21_userLogin_Token));
            parametersToken.put("userPass", getString(R.string.c21_userPass_Token));

            SoapObject token = SOAPWservice.getWsResponse(NAMESPACE, METHOD_GETTOKEN, MAIN_REQUEST_URL, parametersToken);
            if(token != null){
                SoapObject t=(SoapObject)token.getProperty(0);
                TOKEN = t.getProperty("Retorno").toString();
                //Log.d("TOKEN", TOKEN);
            }

            if(TOKEN != null){
                HashMap<String, String> parametersProduto = new HashMap<>();
                parametersProduto.put("tokenAcesso", TOKEN);

                SoapObject hash = SOAPWservice.getWsResponse(NAMESPACE, METHOD_GETPRODUTOS, MAIN_REQUEST_URL, parametersProduto);
                if(hash != null){
                    SoapObject t=(SoapObject)hash.getProperty(0);
                    SoapObject produto=(SoapObject)t.getProperty("Produto");
                    HASHPRODUTO = produto.getProperty("HashProduto").toString();
                    //Log.d("HASHPROD", HASHPRODUTO);
                }
            }

            HashMap<String, String> parametersAluno = new HashMap<>();

            parametersAluno.put("tokenAcesso", TOKEN);
            parametersAluno.put("hashProd", HASHPRODUTO);
            parametersAluno.put("email", EMAIL);
            parametersAluno.put("senha", SENHA);

            SoapObject aluno = SOAPWservice.getWsResponse(NAMESPACE, METHOD_VALIDAALUNO, MAIN_REQUEST_URL, parametersAluno);

            if (aluno != null) {

                SoapObject alunoObj = (SoapObject) aluno.getProperty(0);
                MENSAGEM = alunoObj.getProperty("Mensagem").toString();

                if (!MENSAGEM.equals("Aluno não encontrado")) {
                    NOME = alunoObj.getProperty("Nome").toString();
                    URL_FOTO = alunoObj.getProperty("Foto").toString();
                    FOTO_ALUNO = URL_FOTO.substring(URL_FOTO.lastIndexOf('/') + 1, URL_FOTO.length());
                }
                else{
                    return MENSAGEM;
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            dialog.dismiss();
            if (result != null) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            } else {
                verificaImagemAluno();
            }
        }
    }

    protected void verificaImagemAluno()
    {
        File file = new File(this.getFilesDir() +"/"+ FOTO_ALUNO);
        if(!file.exists()) {
            final DownloadFoto downloadFoto = new DownloadFoto(LoginActivity.this);
            downloadFoto.execute(URL_FOTO);
        }
        else
        {
            Login();
        }

    }

    protected void Login()
    {
        usuario = new Usuario(NOME, EMAIL, FOTO_ALUNO);

        SharedPreferences prefs = getSharedPreferences("c21_user_email", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", EMAIL);
        editor.commit();

        session = new SessionManager(getApplicationContext());
        session.createLoginSession(usuario);

        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        it.putExtra("Usuario", usuario);
        startActivity(it);
        finish();
    }

    // usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
    private class DownloadFoto extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadFoto(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try {

                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                //input = connection.getInputStream();
                input = new BufferedInputStream(connection.getInputStream());

                //Define InputStreams to read from the URLConnection.
                //output = new FileOutputStream("/sdcard/file_name.extension");
                Log.d("SALVANDO", context.getFilesDir() +"/"+ FOTO_ALUNO);
                //FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                //output = new FileOutputStream(context.getCacheDir() + "/video.mp4");
                output = new FileOutputStream(context.getFilesDir() +"/"+ FOTO_ALUNO);

                byte data[] = new byte[4096];

                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            dialog.dismiss();
            //if (result != null)
                //Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();

                Login();
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