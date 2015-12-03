package corpode21.com.br.corpod21.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import corpode21.com.br.corpod21.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardGridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BonusFragment extends Fragment {

    ArrayList<Card> cards;
    private FragmentActivity myContext;

    public final  String TAG = "BonusFragment";

    public BonusFragment() {
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
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.base_fragment_cards, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }


    private void initCards() {

        //Init an array of Cards
        cards = new ArrayList<Card>();
        getCards();

        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getActivity(),cards);

        CardGridView gridView = (CardGridView) getActivity().findViewById(R.id.carddemo_grid_cursor);
        if (gridView!=null){
            gridView.setAdapter(mCardArrayAdapter);
        }
    }


    private Card cardGridModulos(String titulo1,String titulo2,int img, String id) {

        //Create a Card
        Card card = new Card(getActivity());
        //Create a CardHeader
        CustomHeaderInnerCard header = new CustomHeaderInnerCard(getActivity());
        //Set the header title
        header.setTitulo1(titulo1);
        header.setTitulo2(titulo2);
        //Set visible the expand/collapse button
        header.setButtonExpandVisible(false);
        //Add Header to card
        card.addCardHeader(header);

        card.setId(id);

        //Click listener
        CardThumbnail thumb = new CardThumbnail(getActivity());
        thumb.setDrawableResource(img);//
        card.addCardThumbnail(thumb);

        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                openFragment(new ModuloFragment(), card.getId());
            }
        });

        return card;
    }


    public class CustomHeaderInnerCard extends CardHeader {

        private String titulo1;
        private String titulo2;
        TextView t1;
        TextView t2;

        public CustomHeaderInnerCard(Context context) {
            super(context, R.layout.modulo_custom_cardheader);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            if (view!=null){
                t1 = (TextView) view.findViewById(R.id.text_titulo1);
                t2 = (TextView) view.findViewById(R.id.text_titulo2);
            }

            if (t1!=null)
                t1.setText(titulo1);

            if (t2!=null)
                t2.setText(titulo2);
        }

        public void setTitulo1(String titulo1) {
            this.titulo1=titulo1;
        }

        public void setTitulo2(String titulo2) {
            this.titulo2=titulo2;
        }
    }

    private void openFragment(final Fragment fragment, String moduloId) {
        myContext.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left,R.anim.enter_from_left,R.anim.exit_from_right)
                .replace(R.id.content_frame, fragment)
                .addToBackStack(TAG)
                .commit();
        //Passando parametros para o Fragment
        Bundle args = new Bundle();
        args.putString("MODULOID", moduloId);
        fragment.setArguments(args);
    }

    private void getCards()
    {
        cards.add(cardGridModulos(getString(R.string.por_olivia),getString(R.string.ex_abdomen),R.drawable.img_bonus_1, "08"));
        cards.add(cardGridModulos(getString(R.string.por_olivia),getString(R.string.pilates_casa),R.drawable.img_bonus_4, "09"));
        cards.add(cardGridModulos(getString(R.string.por_olivia),getString(R.string.detox_2_dias),R.drawable.img_bonus_2, "10"));
        cards.add(cardGridModulos(getString(R.string.por_olivia),getString(R.string.dieta_c21),R.drawable.img_bonus_3, "11"));
    }
}
