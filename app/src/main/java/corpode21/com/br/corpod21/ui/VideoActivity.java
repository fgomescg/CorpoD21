package corpode21.com.br.corpod21.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.Util.Chronometer;
import corpode21.com.br.corpod21.Util.SessionManager;


public class VideoActivity extends BaseSingleActivity {

    SessionManager session;
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private final String TituloPagina = "Vídeo 01";
    private Chronometer mChronometer;
    private String urlVideo;
    private int position = 0;
    private VideoView mVideoView;
    private MediaController mediaControls;


    // declare the dialog as a member field of your activity
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setActionBarBackArrow();
        setBarTitle(TituloPagina);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        mVideoView = (VideoView)findViewById(R.id.videoView);
        //set the media controller buttons

        if (mediaControls == null) {
            mediaControls = new MediaController(VideoActivity.this);
        }

        try {
            //set the media controller in the VideoView
            mVideoView.setMediaController(mediaControls);
            //set the uri of the video to be played
            mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mobile));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }


        mVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                //progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                mVideoView.seekTo(position);
                if (position == 0) {
                    mVideoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    mVideoView.pause();
                }
            }
        });


       // mChronometer = (Chronometer)findViewById(R.id.chronometer);
        Intent it = getIntent();

        urlVideo = it.getStringExtra("urlVideo");

        if(urlVideo != "")
            IniciaVideo();

    }

    private LinearLayout.LayoutParams paramsNotFullscreen;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) mVideoView.getLayoutParams();

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();

            toolbar.hideOverflowMenu(); // animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            params.width =  metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            mVideoView.setLayoutParams(params);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();

            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();

            params.width = (int) (300*metrics.density);
            params.height =  (int) (250*metrics.density);
            params.leftMargin = 30;
            mVideoView.setLayoutParams(params);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", mVideoView.getCurrentPosition());
        mVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        mVideoView.seekTo(position);
    }


    protected void IniciaVideo()
    {
        mProgressDialog = new ProgressDialog(VideoActivity.this);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        // execute this when the downloader must be fired
        /*final DownloadTask downloadTask = new DownloadTask(VideoActivity.this);
        downloadTask.execute("http://www.orbitaldev.com.br/Mobile.mp4");

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        }); */

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    public void startChronometer(View view) {
        mChronometer.start();
    }

    public void stopChronometer(View view) {
        mChronometer.stop();
    }


    // usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
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
                Log.d("DIR",context.getCacheDir().toString());
                //FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                //output = new FileOutputStream(context.getCacheDir() + "/video.mp4");
                output = new FileOutputStream("android:resource://corpode21.com.br.corpode21/raw/video.mp4");


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
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
        }

    }

}
