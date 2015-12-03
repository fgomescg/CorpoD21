package corpode21.com.br.corpod21.fragments;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.GMailSender;
import corpode21.com.br.corpod21.Util.SessionManager;

/**
 * Created by Fabio on 21/05/2015.
 */
public class ContatoFragment extends Fragment {

    private FragmentActivity myContext;
    private Spinner spinner1;
    private EditText edtMensagem;
    private Button btnSubmit;

    SessionManager session;
    HashMap<String, String> user;

    public ContatoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contato, container, false);

        session = new SessionManager(myContext);
         user = session.getUserDetails();

        spinner1 = (Spinner) v.findViewById(R.id.spinner);
        edtMensagem = (EditText) v.findViewById(R.id.edtMensagem);
        // Inflate the layout for this fragment
        return  v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }


    public void addListenerOnSpinnerItemSelection() {

        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        btnSubmit = (Button) myContext.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendEmail();
            }

        });
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"olivia@eumagra.com.br"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato APP CorpoD21");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Aluna: " + user.get(SessionManager.KEY_NAME) + "\n" +
                                                "Login: " + user.get(SessionManager.KEY_EMAIL)+ "\n" +
                                                "Assunto: " + String.valueOf(spinner1.getSelectedItem()) + "\n\n" +
                                                "Menssagem: "+ edtMensagem.getText());


        try {
            startActivity(Intent.createChooser(emailIntent, "Enviando email..."));
            Log.i("Finished send email...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(myContext, "Nao foi encontrado nenhum prongrama de email instalado.", Toast.LENGTH_SHORT).show();
        }
    }


}
