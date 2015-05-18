package corpode21.com.br.corpod21.ui;

import android.os.Bundle;

import java.util.ArrayList;

import corpode21.com.br.corpod21.R;

public class BonusPalestraMarciaActivity extends BaseSingleActivity {

    private final String TituloPagina = "Palestra Márcia Luz";

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
        cards.add(montaCards("Palestra Márcia Luz","Como Alcançar seus Objetivos e Melhorar sua Autoestima",getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
    }

}

