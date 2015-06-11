package corpode21.com.br.corpod21.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import corpode21.com.br.corpod21.R;

/**
 * Created by Fabio on 21/05/2015.
 */
public class RespostaDuvidasFragment extends Fragment {

    private FragmentActivity myContext;
    private String pergunta;
    private TextView pergunta_titulo;
    private TextView respostaPergunta;
    private Button contato;

    public final  String TAG = "RespostaDuvidasFragment";

    public RespostaDuvidasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resposta_duvidas, container, false);
        pergunta = getArguments().getString("PERGUNTAID");

        pergunta_titulo = (TextView) v.findViewById(R.id.pergunta);
        respostaPergunta = (TextView) v.findViewById(R.id.respostaPergunta);

        contato = (Button) v.findViewById(R.id.contato);
        contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new ContatoFragment());
            }
        });

        // Inflate the layout for this fragment
        return  v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Init();
    }

    protected void Init()
    {
        switch (pergunta) {
            case "pergunta1":
                pergunta_titulo.setText(getString(R.string.pergunta1_duvidas_frequentes));
                respostaPergunta.setText(getString(R.string.resposta1_duvidas_frequentes));
                break;
            case "pergunta2":
                pergunta_titulo.setText(getString(R.string.pergunta2_duvidas_frequentes));
                respostaPergunta.setText(getString(R.string.resposta2_duvidas_frequentes));
                break;
            case "pergunta3":
                pergunta_titulo.setText(getString(R.string.pergunta3_duvidas_frequentes));
                respostaPergunta.setText(getString(R.string.resposta3_duvidas_frequentes));
                break;
            case "pergunta4":

                break;
            case "pergunta5":

                break;
            case "pergunta6":

                break;
        }

    }

    private void openFragment(final Fragment fragment) {
        myContext.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left,R.anim.enter_from_left,R.anim.exit_from_right)
                .replace(R.id.content_frame, fragment)
                .addToBackStack(TAG)
                .commit();
    }

}
