package corpode21.com.br.corpod21.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import corpode21.com.br.corpod21.R;

public class RespostaDuvidasActivity extends BaseSingleActivity {

    private String pergunta;
    private TextView pergunta_titulo;
    private TextView respostaPergunta;
    private Button contato;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta_duvidas);

        setActionBarBackArrow();
        setBarTitle(getString(R.string.duvidas_frequentes));

        pergunta_titulo = (TextView) findViewById(R.id.pergunta);
        respostaPergunta = (TextView) findViewById(R.id.respostaPergunta);

        contato = (Button) findViewById(R.id.contato);
        contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ContatoActivity.class);
                //it.putExtra("Usuario", usuario);
                startActivity(it);
            }
        });

        Intent it = getIntent();
        pergunta = it.getStringExtra("pergunta");

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


}

