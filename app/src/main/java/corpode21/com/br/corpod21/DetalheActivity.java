package corpode21.com.br.corpod21;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetalheActivity extends ActionBarActivity {

    public static final String EXTRA_TEXTO = "texto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        String string = getIntent().getStringExtra(EXTRA_TEXTO);
        TextView txt = (TextView)findViewById(R.id.txtDetalhe);
        txt.setText(string);
    }

}
