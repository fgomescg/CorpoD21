package corpode21.com.br.corpod21.fragments;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.ui.RespostaDuvidasActivity;

/**
 * Created by Fabio on 14/05/2015.
 */
public class DuvidasFrequentesFragment extends Fragment implements
        View.OnClickListener {

    private TextView pergunta1;
    private TextView pergunta2;
    private TextView pergunta3;

    public DuvidasFrequentesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_duvidas, container, false);

        pergunta1 = (TextView) v.findViewById(R.id.pergunta1);
        pergunta1.setOnClickListener(this);

        pergunta2 = (TextView) v.findViewById(R.id.pergunta2);
        pergunta2.setOnClickListener(this);

        pergunta3 = (TextView) v.findViewById(R.id.pergunta3);
        pergunta3.setOnClickListener(this);

        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onClick(View v) {

        final String tag = v.getTag().toString();

        Intent it = new Intent(getActivity(), RespostaDuvidasActivity.class);
        it.putExtra("pergunta", tag);
        startActivity(it);
    }
}
