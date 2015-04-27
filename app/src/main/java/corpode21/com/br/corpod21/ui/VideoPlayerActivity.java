package corpode21.com.br.corpod21.ui;
import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import corpode21.com.br.corpod21.R;

public class VideoPlayerActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl {

    SurfaceView videoSurface;
    MediaPlayer player;
    VideoControllerView controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
        SurfaceHolder videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);

        player = new MediaPlayer();
        controller = new VideoControllerView(this);

        try {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(this, Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
            player.setOnPreparedListener(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.show();
        return false;
    }

    // Implement SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setDisplay(holder);
        player.prepareAsync();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    // End SurfaceHolder.Callback

    // Implement MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
        controller.setMediaPlayer(this);
        controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
        player.start();
    }
    // End MediaPlayer.OnPreparedListener

    // Implement VideoMediaController.MediaPlayerControl
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void seekTo(int i) {
        player.seekTo(i);
    }

    @Override
    public void start() {
        player.start();
    }

    //@Override
    //public boolean isFullScreen() {
     //   return false;
    //}


    private boolean mFullScreen = true;

    @Override
    public boolean isFullScreen() {
        if(mFullScreen){
            Log.v("FullScreen", "--set icon full screen--");
            return false;
        }else{
            Log.v("FullScreen", "--set icon small full screen--");
            return true;
        }
    }
    @Override
    public void toggleFullScreen() {
        Log.v("FullScreen", "-----------------click toggleFullScreen-----------");
        setFullScreen(isFullScreen());

    }
// End VideoMediaController.MediaPlayerControl

    public void setFullScreen(boolean fullScreen){
        fullScreen = false;

        if (mFullScreen)

        {
            Log.v("FullScreen", "-----------Set full screen SCREEN_ORIENTATION_LANDSCAPE------------");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoSurface.getLayoutParams();
            params.width = width;
            params.height=height;
            params.setMargins(0, 0, 0, 0);
            //set icon is full screen
            mFullScreen = fullScreen;
        }
        else{
            Log.v("FullScreen", "-----------Set small screen SCREEN_ORIENTATION_PORTRAIT------------");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            final FrameLayout mFrame = (FrameLayout) findViewById(R.id.videoSurfaceContainer);
            // int height = displaymetrics.heightPixels;
            int height = mFrame.getHeight();//get height Frame Container video
            int width = displaymetrics.widthPixels;
            android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) videoSurface.getLayoutParams();
            params.width = width;
            params.height= height;
            params.setMargins(0, 0, 0, 0);
            //set icon is small screen
            mFullScreen = !fullScreen;
        }
    }
}
