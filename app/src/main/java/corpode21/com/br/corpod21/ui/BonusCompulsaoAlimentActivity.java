package corpode21.com.br.corpod21.ui;

import android.os.Bundle;

import java.util.ArrayList;

import corpode21.com.br.corpod21.R;

public class BonusCompulsaoAlimentActivity extends BaseSingleActivity {

    private final String TituloPagina = "Supere a compulsão alimentar";

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
        cards.add(montaCards("Vídeo 1","Introdução", getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Vídeo 2","O que é TFT?",  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Vídeo 3","Como funciona o ciclo vicioso",  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Vídeo 4","Passos básicos",  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Vídeo 5","Encerramento",  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
    }

}

