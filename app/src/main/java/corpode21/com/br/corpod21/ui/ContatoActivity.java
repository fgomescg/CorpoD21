package corpode21.com.br.corpod21.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.HashMap;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.GMailSender;
import corpode21.com.br.corpod21.Util.SessionManager;


public class ContatoActivity extends BaseSingleActivity {

    private Spinner spinner1;
    private EditText edtMensagem;
    private Button btnSubmit;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();
        if (!session.isLoggedIn()) {
            finish();
        }
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //NAME = user.get(SessionManager.KEY_NAME);
        //EMAIL = user.get(SessionManager.KEY_EMAIL);

        setActionBarBackArrow();
        setBarTitle(getString(R.string.titulo_contato));

        spinner1 = (Spinner) findViewById(R.id.spinner);
        edtMensagem = (EditText) findViewById(R.id.edtMensagem);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

    }

    public void addListenerOnSpinnerItemSelection() {

        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    GMailSender sender = new GMailSender("fgomescg@gmail.com", "Pprsliiip1!");
                    sender.sendMail("Contato Corpo D21 - " + String.valueOf(spinner1.getSelectedItem()),
                            edtMensagem.getText().toString(),
                            "fabio@orbitaldev.com.br",
                            "fabinhocg@hotmail.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                finally {
                    Log.d("Email", "enviado com sucesso.");
                }
            }

        });
    }

}
