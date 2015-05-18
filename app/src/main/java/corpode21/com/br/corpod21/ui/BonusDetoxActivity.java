package corpode21.com.br.corpod21.ui;

import android.os.Bundle;

import java.util.ArrayList;

import corpode21.com.br.corpod21.R;

public class BonusDetoxActivity extends BaseSingleActivity {

    private final String TituloPagina = "Detox 2 dias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_detox);
        setActionBarBackArrow();
        setBarTitle(TituloPagina);
        preencheCards();

    }


    protected void preencheCards()
    {

    }

}

