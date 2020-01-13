package net.majorkernelpanic.streaming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import net.majorkernelpanic.streaming.video.VideoQuality;

public class MainActivity extends Activity{



    private final static String TAG = "MainActivity";

    private Button mButton1, mButton2;
    private net.majorkernelpanic.streaming.gl.SurfaceView mSurfaceView;
    private EditText mEditText;
    private Session mSession;
    private TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //this.stopService(new Intent(this, RtspServer.class));
        VideoQuality.DEFAULT_VIDEO_QUALITY.resX=1280;
        VideoQuality.DEFAULT_VIDEO_QUALITY.resY=720;
        VideoQuality.DEFAULT_VIDEO_QUALITY.framerate = 30;



        mSurfaceView =  (net.majorkernelpanic.streaming.gl.SurfaceView)findViewById(R.id.surface);

        textureView = (TextureView)findViewById(R.id.textureView);


        // Sets the port of the RTSP server to 1234
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(RtspServer.KEY_PORT, String.valueOf(1234));
        editor.commit();
        Log.d("QQQQQQQQQQQQQQQQQQQQQ", String.valueOf(VideoQuality.DEFAULT_VIDEO_QUALITY.resX)+"\t"+String.valueOf(VideoQuality.DEFAULT_VIDEO_QUALITY.bitrate));
        Session mSession;
        // Configures the SessionBuilder
        final VideoQuality quality = new VideoQuality(
                1280, 720, 30, 10000);


       /* Session session = SessionBuilder.getInstance()
                .setSurfaceView( mSurfaceView)
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_AAC)
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(quality).build();*/


        SessionBuilder.getInstance()
                .setSurfaceView( mSurfaceView)
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_AAC)
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(quality);
        //session.startPreview();



        this.startService(new Intent(this, RtspServer.class));
        /*mSurfaceView.getSurfaceTexture().setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(SurfaceTexture surfaceTexture)
            {
                Log.d("1111111111111111", "AVAILABLE!");
            }
        });*/
    }


/*
    @Override
    public void onBitrateUpdate(long l) {
    }

    @Override
    public void onSessionError(int i, int i1, Exception e) {
        Log.d("QQQQQQQQ", e.getMessage());
        Log.d("QQQQQQQQ", i +"  "+i1);
    }

    @Override
    public void onPreviewStarted() {

        Log.d("QQQQQQQQQ", "PREVIEW STARTED");
    }

    @Override
    public void onSessionConfigured() {

        Log.d("QQQQQQQQQ", "SESSION CONFIGURED");
    }

    @Override
    public void onSessionStarted() {
        Log.d("QQQQQQQQQ", "SESSION STARTED");
    }

    @Override
    public void onSessionStopped() {
        Log.d("QQQQQQQQQ", "SESSION STOPPED");

    }*/
}
