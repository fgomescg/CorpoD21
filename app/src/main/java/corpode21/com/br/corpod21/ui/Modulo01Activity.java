package corpode21.com.br.corpod21.ui;

import android.os.Bundle;
import java.util.ArrayList;

import corpode21.com.br.corpod21.R;


public class Modulo01Activity extends BaseSingleActivity {

    private final String TituloPagina = "Módulo 01";

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
        cards.add(montaCards("Dia 1", getString(R.string.contracao), getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Dia 1", getString(R.string.respiracao),  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Dia 2", getString(R.string.ex_abdomen),  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Dia 3", getString(R.string.ex_pernas_gluteos),  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Dia 4", getString(R.string.ex_bracos),  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
        cards.add(montaCards("Dia 5", getString(R.string.repita),  getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));

    }
}
