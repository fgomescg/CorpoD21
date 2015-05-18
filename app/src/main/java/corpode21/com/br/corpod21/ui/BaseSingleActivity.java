package corpode21.com.br.corpod21.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import corpode21.com.br.corpod21.BaseActivity;
import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.ui.VideoActivity;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by Fabio on 09/04/2015.
 */
public abstract class BaseSingleActivity extends BaseActivity {

    ArrayList<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initCards() {

        //Init an array of Cards
        //preencheCards();

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this,cards);

        CardListView listView = (CardListView) this.findViewById(R.id.carddemo_list_view);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    public Card montaCards(String titulo, final String subTitulo, String urlBase, String Video) {

        //Create a CardHeader
        CardHeader header = new CardHeader(this);
        //Set the header title
        header.setTitle(titulo);
        //Create a Card
        Card card = new Card(this,R.layout.carddemo_example_inner_content);
        //Set the card inner text
        card.setTitle(subTitulo);
        //add header on card
        card.addCardHeader(header);
        //Card url Vídeo
        card.setUrlBaseVideo(urlBase);
        //Card Name Video
        card.setVideo(Video);

        //Set onClick listener
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent it = new Intent(getApplicationContext(), VideoActivity.class);
                it.putExtra("URL_BASE", card.getUrlBaseVideo());
                it.putExtra("NOME_VIDEO", card.getVideo());
                it.putExtra("SUBTITULO_VIDEO", subTitulo);
                startActivity(it);
                finish();
            }
        });

        card.setSwipeable(false);

        return card;
    }
}

