package corpode21.com.br.corpod21.ui;

import android.os.Bundle;

import java.util.ArrayList;

import corpode21.com.br.corpod21.R;

public class Modulo0Activity extends BaseSingleActivity {

    private final String TituloPagina = "Módulo 0";

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
        cards.add(montaCards("Alongamento",getString(R.string.alongamento_c21), getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
    }

}

