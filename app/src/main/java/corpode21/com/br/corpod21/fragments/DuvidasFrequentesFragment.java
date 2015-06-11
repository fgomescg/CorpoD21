package corpode21.com.br.corpod21.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import corpode21.com.br.corpod21.R;


/**
 * Created by Fabio on 14/05/2015.
 */
public class DuvidasFrequentesFragment extends Fragment implements
        View.OnClickListener {

    private TextView pergunta1;
    private TextView pergunta2;
    private TextView pergunta3;

    public final  String TAG = "DuvidasFrequentesFragment";

    private FragmentActivity myContext;

    public DuvidasFrequentesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
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

        final String perguntaId = v.getTag().toString();
        openFragment(new RespostaDuvidasFragment(), perguntaId);

    }

    private void openFragment(final Fragment fragment, String perguntaId) {
        myContext.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left,R.anim.enter_from_left,R.anim.exit_from_right)
                .replace(R.id.content_frame, fragment)
                .addToBackStack(TAG)
                .commit();
        //Passando parametros para o Fragment
        Bundle args = new Bundle();
        args.putString("PERGUNTAID", perguntaId);
        fragment.setArguments(args);
    }
}
