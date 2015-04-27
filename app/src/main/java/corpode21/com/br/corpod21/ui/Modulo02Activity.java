package corpode21.com.br.corpod21.ui;


import android.os.Bundle;
import java.util.ArrayList;

import corpode21.com.br.corpod21.R;


public class Modulo02Activity extends BaseSingleActivity {

    private final String TituloPagina = "Calendário da Semana 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_listview_base);
        setActionBarBackArrow();
        setBarTitle(TituloPagina);
        preencheCards();
        initCards();
    }



    protected void preencheCards()
    {
        cards = new ArrayList<>();
        cards.add(montaCards("1° dia",getString(R.string.super_queima), getString(R.string.url_super_queima) ));
        cards.add(montaCards("2° dia",getString(R.string.descanso), "" ));
        cards.add(montaCards("3° dia",getString(R.string.top_treino), getString(R.string.url_top_treino) ));
        cards.add(montaCards("4° dia",getString(R.string.descanso), ""));
        cards.add(montaCards("5° dia",getString(R.string.rapido_e_furioso),getString(R.string.url_rapido_furioso) ));
        cards.add(montaCards("6° e 7°dia",getString(R.string.alongamento), getString(R.string.url_alongamento_c21) ));

    }
}