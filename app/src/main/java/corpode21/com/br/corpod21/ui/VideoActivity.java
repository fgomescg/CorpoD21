package corpode21.com.br.corpod21.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.Chronometer;
import corpode21.com.br.corpod21.Util.SessionManager;


public class VideoActivity extends BaseSingleActivity {

    SessionManager session;
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private final String TituloPagina = "Vídeo 01";
    private WebView myVideoView;
    private Chronometer mChronometer;
    private String urlVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setActionBarBackArrow();
        setBarTitle(TituloPagina);

        mChronometer = (Chronometer)findViewById(R.id.chronometer);

        Intent it = getIntent();

        urlVideo = it.getStringExtra("urlVideo");

        if(urlVideo != "")
            IniciaVideo();

    }

    protected void IniciaVideo()
    {

        myVideoView = (WebView) findViewById(R.id.video_view);
        myVideoView.getSettings().setJavaScriptEnabled(true);
        myVideoView.getSettings().setAppCacheEnabled(true);
        myVideoView.getSettings().setDomStorageEnabled(true);
        myVideoView.setWebChromeClient(new WebChromeClient());


        String iFrame = "<iframe src=\""+urlVideo+"\" width=\"100%\" height=\"200\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>";



        Log.d("url", iFrame);

        myVideoView.loadDataWithBaseURL(getString(R.string.url_base_video), iFrame,"text/html", "UTF-8", null);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    long timeWhenStopped = 0;

    public void startChronometer(View view) {
        mChronometer.start();
    }

    public void stopChronometer(View view) {
        mChronometer.stop();
    }

    //Reset
    //mChronometer.setBase(SystemClock.elapsedRealtime());
    //timeWhenStopped = 0;

}
