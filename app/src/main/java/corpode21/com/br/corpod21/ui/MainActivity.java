package corpode21.com.br.corpod21.ui;


import android.os.Bundle;

import corpode21.com.br.corpod21.BaseActivity;
import corpode21.com.br.corpod21.R;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDefaultNavDrawer();


        selectItemMenu(MENU_MODULOS);

    }

}