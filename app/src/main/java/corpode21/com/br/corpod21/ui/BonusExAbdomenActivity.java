package corpode21.com.br.corpod21.ui;

import android.os.Bundle;

import java.util.ArrayList;

import corpode21.com.br.corpod21.R;

public class BonusExAbdomenActivity extends BaseSingleActivity {

    private final String TituloPagina = "5 Exercícios para o abdômen";

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
        cards.add(montaCards("Bônus abdômen","5 Exercícios para abdômen", "1" ));
    }

}

