package corpode21.com.br.corpod21.ui;

import android.os.Bundle;

import java.util.ArrayList;

import corpode21.com.br.corpod21.R;

public class BonusDietaC21Activity extends BaseSingleActivity {

    private final String TituloPagina = "Dieta CorpoD21";

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
        //cards.add(montaCards("Bônus abdômen","5 Exercícios para abdômen", "1" ));
    }

}

