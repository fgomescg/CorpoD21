package corpode21.com.br.corpod21.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import java.io.BufferedInputStream;
import java.io.File;
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
    private String TituloVideo;
    private Chronometer mChronometer;

    private int position = 0;
    private VideoView mVideoView;
    private MediaController mediaControls;
    // declare the dialog as a member field of your activity
    ProgressDialog mProgressDialog;

    //Nome do Vídeo
    private String NomeVideo;
    //Url onde será baixado o vídeo
    private String urlBaseVideo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mVideoView = (VideoView)findViewById(R.id.videoView);
        mChronometer = (Chronometer)findViewById(R.id.chronometer);

        Intent it = getIntent();
        urlBaseVideo = it.getStringExtra("URL_BASE");
        NomeVideo = it.getStringExtra("NOME_VIDEO");
        TituloVideo = it.getStringExtra("SUBTITULO_VIDEO");

        setActionBarBackArrow();
        setBarTitle(TituloVideo);

        if(NomeVideo != "")
            Init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setFullScreen();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            leaveFullScreen();
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

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    //endregion


    //Metodos
    protected void Init()
    {
        File file = new File(this.getFilesDir() +"/"+ NomeVideo);
        if(file.exists()) {
            IniciarVideo();
        }
        else {
            DownloadVideo();
        }
    }



    protected void IniciarVideo()
    {
        try {
            //set the uri of the video to be played
            mVideoView.setVideoURI(Uri.parse(this.getFilesDir() +"/"+ NomeVideo));

            mVideoView.requestFocus();
            //we also set an setOnPreparedListener in order to know when the video file is ready for playback
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        /* * add media controller*/
                            mediaControls = new MediaController(VideoActivity.this);
                            mVideoView.setMediaController(mediaControls);
                        /* * and set its position on screen */
                            mediaControls.setAnchorView(mVideoView);
                        }
                    });
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
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }

    protected void DownloadVideo()
    {

        mProgressDialog = new ProgressDialog(VideoActivity.this);
        mProgressDialog.setMessage("Por favor, aguarde...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        final DownloadVideo downloadVideo = new DownloadVideo(VideoActivity.this);
        downloadVideo.execute(urlBaseVideo + "/" + NomeVideo);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadVideo.cancel(true);
            }
        });
    }

    private void setFullScreen()
    {
        //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void leaveFullScreen()
    {
        //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }


    public void startChronometer(View view) {
        mChronometer.start();
    }

    public void stopChronometer(View view) {
        mChronometer.stop();
    }


    // usually, subclasses of AsyncTask are declared inside the activity class.
// that way, you can easily modify the UI thread from here
    private class DownloadVideo extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadVideo(Context context) {
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
                Log.d("SALVANDO", context.getFilesDir() +"/"+ NomeVideo);
                //FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                //output = new FileOutputStream(context.getCacheDir() + "/video.mp4");
                output = new FileOutputStream(context.getFilesDir() +"/"+ NomeVideo);

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
                Toast.makeText(context,TituloVideo + " baixado com sucesso !", Toast.LENGTH_SHORT).show();
                //Iniciar o vídeo após o download
                IniciarVideo();
        }

    }

}
