package corpode21.com.br.corpod21;

/**
 * Created by Fabio on 14/04/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import corpode21.com.br.corpod21.Util.HTML5WebView;

public class Test extends Activity {

    HTML5WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = new HTML5WebView(this);

        String iFrame = "<iframe src=\""+"https://player.vimeo.com/video/95196189"+"\" width=\"100%\" height=\"200\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>";

        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
        } else {
            mWebView.loadUrl("https://player.vimeo.com/video/124596570");
            //mWebView.loadDataWithBaseURL(getString(R.string.url_base_video), iFrame,"text/html", "UTF-8", null);
        }


        setContentView(mWebView.getLayout());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mWebView.stopLoading();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.inCustomView()) {
                mWebView.hideCustomView();
                //  mWebView.goBack();
                //mWebView.goBack();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}