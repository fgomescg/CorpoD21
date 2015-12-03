package corpode21.com.br.corpod21.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.ui.VideoActivity;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by Fabio on 21/05/2015.
 */
public class ModuloFragment extends Fragment  {

    ArrayList<Card> cards;
    ProgressDialog mProgressDialog;
    private String NOMEARQUIVO;
    private String NOMEARQUIVOPDF;
    private FragmentActivity myContext;
    private String MODULOID;


    public ModuloFragment() {
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
         View v = inflater.inflate(R.layout.fragment_card_listview_base, container, false);
         MODULOID = getArguments().getString("MODULOID");
        // Inflate the layout for this fragment
        return  v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preencheCards();
        initCards();
    }

    protected void preencheCards()
    {
        cards = new ArrayList<>();
        switch (MODULOID)
        {
            case "00":
                cards.add(montaCards("Alongamento", getString(R.string.alongamento_c21), getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
                break;
            case "01":
                cards.add(montaCards(getString(R.string.dia1), getString(R.string.contracao), getString(R.string.url_base_video), getString(R.string.video_contracao)));
                cards.add(montaCards(getString(R.string.dia1), getString(R.string.respiracao),  getString(R.string.url_base_video), getString(R.string.video_respiracao)));
                cards.add(montaCards(getString(R.string.dia2), getString(R.string.ex_abdomen),  getString(R.string.url_base_video), getString(R.string.video_modulo_abdomen)));
                cards.add(montaCards(getString(R.string.dia3), getString(R.string.ex_pernas_gluteos),  getString(R.string.url_base_video), getString(R.string.video_pernas_gluteos)));
                cards.add(montaCards(getString(R.string.dia4), getString(R.string.ex_bracos),  getString(R.string.url_base_video), getString(R.string.video_bracos)));
                cards.add(montaCards(getString(R.string.dia5), getString(R.string.repita), getString(R.string.url_base_video), getString(R.string.video_dia5)));
                break;
            case "02":
                cards.add(montaCards(getString(R.string.primeiro_dia), getString(R.string.super_queima), getString(R.string.url_base_video), getString(R.string.video_super_queima)));
                cards.add(montaCards(getString(R.string.segundo_dia),getString(R.string.descanso), getString(R.string.url_base_video), ""));
                cards.add(montaCards(getString(R.string.terceiro_dia),getString(R.string.top_treino), getString(R.string.url_base_video), getString(R.string.video_top_treino)));
                cards.add(montaCards(getString(R.string.quarto_dia),getString(R.string.descanso), getString(R.string.url_base_video), ""));
                cards.add(montaCards(getString(R.string.quinto_dia),getString(R.string.rapido_e_furioso),getString(R.string.url_base_video), getString(R.string.video_rapido_furioso)));
                cards.add(montaCards(getString(R.string.sexto_set_dia), getString(R.string.alongamento), getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
                break;
            case "03":
                cards.add(montaCards(getString(R.string.primeiro_dia),getString(R.string.super_queima), getString(R.string.url_base_video), getString(R.string.video_super_queima)));
                cards.add(montaCards(getString(R.string.segundo_dia),getString(R.string.ex_abdomen), getString(R.string.url_base_video), getString(R.string.video_modulo_abdomen)));
                cards.add(montaCards(getString(R.string.terceiro_dia),getString(R.string.top_treino), getString(R.string.url_base_video), getString(R.string.video_top_treino)));
                cards.add(montaCards(getString(R.string.quarto_dia),getString(R.string.ex_pernas_gluteos),getString(R.string.url_base_video), getString(R.string.video_pernas_gluteos)));
                cards.add(montaCards(getString(R.string.quinto_dia),getString(R.string.rapido_e_furioso), getString(R.string.url_base_video), getString(R.string.video_rapido_furioso)));
                cards.add(montaCards(getString(R.string.sexto_set_dia), getString(R.string.alongamento), getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
                break;
            case "04":
                cards.add(montaCards(getString(R.string.primeiro_dia),getString(R.string.super_queima),getString(R.string.url_base_video), getString(R.string.video_super_queima)));
                cards.add(montaCards(getString(R.string.segundo_dia),getString(R.string.top_treino), getString(R.string.url_base_video), getString(R.string.video_top_treino)));
                cards.add(montaCards(getString(R.string.terceiro_dia),getString(R.string.rapido_e_furioso), getString(R.string.url_base_video), getString(R.string.video_rapido_furioso)));
                cards.add(montaCards(getString(R.string.quarto_dia),getString(R.string.super_queima), getString(R.string.url_base_video), getString(R.string.video_super_queima)));
                cards.add(montaCards(getString(R.string.quinto_dia),getString(R.string.top_treino), getString(R.string.url_base_video), getString(R.string.video_top_treino)));
                cards.add(montaCards(getString(R.string.sexto_set_dia), getString(R.string.alongamento), getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
                break;
            case "05":
                cards.add(montaCards(getString(R.string.primeiro_dia),getString(R.string.rapido_e_furioso),getString(R.string.url_base_video), getString(R.string.video_rapido_furioso)));
                cards.add(montaCards(getString(R.string.segundo_dia),getString(R.string.super_queima), getString(R.string.url_base_video), getString(R.string.video_super_queima)));
                cards.add(montaCards(getString(R.string.terceiro_dia),getString(R.string.top_treino), getString(R.string.url_base_video), getString(R.string.video_top_treino)));
                cards.add(montaCards(getString(R.string.quarto_dia),getString(R.string.rapido_e_furioso), getString(R.string.url_base_video), getString(R.string.video_rapido_furioso)));
                cards.add(montaCards(getString(R.string.quinto_dia),getString(R.string.super_queima), getString(R.string.url_base_video), getString(R.string.video_super_queima)));
                cards.add(montaCards(getString(R.string.sexto_set_dia), getString(R.string.alongamento), getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
                break;
            case "06":
                cards.add(montaCards(getString(R.string.primeiro_dia),getString(R.string.top_treino), getString(R.string.url_base_video), getString(R.string.video_top_treino)));
                cards.add(montaCards(getString(R.string.segundo_dia),getString(R.string.rapido_e_furioso), getString(R.string.url_base_video), getString(R.string.video_rapido_furioso)));
                cards.add(montaCards(getString(R.string.terceiro_dia),getString(R.string.super_queima), getString(R.string.url_base_video), getString(R.string.video_super_queima)));
                cards.add(montaCards(getString(R.string.quarto_dia),getString(R.string.top_treino), getString(R.string.url_base_video), getString(R.string.video_top_treino)));
                cards.add(montaCards(getString(R.string.quinto_dia),getString(R.string.rapido_e_furioso), getString(R.string.url_base_video), getString(R.string.video_rapido_furioso)));
                cards.add(montaCards(getString(R.string.sexto_set_dia), getString(R.string.alongamento), getString(R.string.url_base_video), getString(R.string.video_alongamento_c21)));
                break;
            case "08":
                cards.add(montaCards(getString(R.string.bonus_abdomen),getString(R.string.ex_abdomen_5) ,getString(R.string.url_base_video), getString(R.string.video_bonus_abdomen)));
                break;
            case "09":
                cards.add(montaCards(getString(R.string.pilates_casa),getString(R.string.faca_voce),getString(R.string.url_base_video), getString(R.string.video_bonus_pilates)));
                break;
            case "10":
                cards.add(montaCards(getString(R.string.bonus),getString(R.string.detox_2_dias), getString(R.string.url_base_video), getString(R.string.pdf_detox_2_dias)));
                break;
            case "11":
                cards.add(montaCards(getString(R.string.bonus),getString(R.string.dieta_c21), getString(R.string.url_base_video), getString(R.string.pdf_dieta_c21)));
                break;


           /* case "07":
                cards.add(montaCards(getString(R.string.palestra_marcia),getString(R.string.como_alcancar),getString(R.string.url_base_video), ""));
                break;
              case "12":
                cards.add(montaCards(getString(R.string.video1),getString(R.string.introducao), getString(R.string.url_base_video), getString(R.string.stringEmpty)));
                cards.add(montaCards(getString(R.string.video2),getString(R.string.tft),getString(R.string.url_base_video), getString(R.string.stringEmpty)));
                cards.add(montaCards(getString(R.string.video3),getString(R.string.como_funciona), getString(R.string.url_base_video), getString(R.string.stringEmpty)));
                cards.add(montaCards(getString(R.string.video4),getString(R.string.passos_basicos), getString(R.string.url_base_video), getString(R.string.stringEmpty)));
                cards.add(montaCards(getString(R.string.video5),getString(R.string.encerramento), getString(R.string.url_base_video), getString(R.string.stringEmpty)));
                break;
            case "13":
                cards.add(montaCards("Palestra sobre relacionamento","Relacionamento", getString(R.string.url_base_video), getString(R.string.stringEmpty)));
                break;*/

        }

    }

    private void initCards() {

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        CardListView listView = (CardListView)  myContext.findViewById(R.id.carddemo_list_view);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    public Card montaCards(String titulo, final String subTitulo, String urlBase, String Video) {

        //Create a CardHeader
        CardHeader header = new CardHeader(getActivity());
        //Set the header title
        header.setTitle(titulo);
        //Create a Card
        Card card = new Card(getActivity(),R.layout.carddemo_example_inner_content);
        //Set the card inner text
        card.setTitle(subTitulo);
        //add header on card
        card.addCardHeader(header);
        //Card url Video
        card.setUrlBaseVideo(urlBase);
        //Card Name Video
        card.setVideo(Video);

        //Set onClick listener
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                NOMEARQUIVO = card.getVideo();
                if(NOMEARQUIVO != "") {
                    if (NOMEARQUIVO.substring(NOMEARQUIVO.lastIndexOf('.') + 1, NOMEARQUIVO.length()).equals("pdf")) {
                        InitPDF();
                    } else {
                        Intent it = new Intent(myContext, VideoActivity.class);
                        it.putExtra("URL_BASE", card.getUrlBaseVideo());
                        it.putExtra("NOME_VIDEO", NOMEARQUIVO);
                        it.putExtra("SUBTITULO_VIDEO", subTitulo);
                        startActivity(it);
                    }
                }
            }
        });

        card.setSwipeable(false);

        return card;
    }

    protected void InitPDF()
    {
        NOMEARQUIVOPDF = NOMEARQUIVO.substring( NOMEARQUIVO.lastIndexOf('/')+1, NOMEARQUIVO.length());
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +"/"+ NOMEARQUIVOPDF);
        if(file.exists()) {
            IniciarPDF();
        }
        else {
            DownloadPDF();
        }
    }

    protected void IniciarPDF()
    {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + NOMEARQUIVOPDF);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(myContext, R.string.mensagem_pdf, Toast.LENGTH_LONG).show();

        }
    }

    protected void DownloadPDF()
    {
        mProgressDialog = new ProgressDialog(myContext);
        mProgressDialog.setMessage("Por favor, aguarde...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        final DownloadPDF downloadPDF = new DownloadPDF(myContext);
        downloadPDF.execute(getString(R.string.url_base_video) + NOMEARQUIVO);

    }

    private class DownloadPDF extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadPDF(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try {

                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                //input = connection.getInputStream();
                input = new BufferedInputStream(connection.getInputStream());

                //Define InputStreams to read from the URLConnection.
                //output = new FileOutputStream("/sdcard/file_name.extension");

                Log.d("SALVANDO", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + NOMEARQUIVOPDF);
                //FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                //output = new FileOutputStream(context.getCacheDir() + "/video.mp4");
                output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +"/"+ NOMEARQUIVOPDF);

                byte data[] = new byte[4096];

                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                //Toast.makeText(context,TituloVideo + " baixado com sucesso !", Toast.LENGTH_SHORT).show();
                //Iniciar o video apos o download
                IniciarPDF();
        }

    }
}
