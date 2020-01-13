package net.majorkernelpanic.streaming;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.widget.Button;
import android.widget.EditText;

import net.majorkernelpanic.streaming.rtsp.RtspServer;
import net.majorkernelpanic.streaming.video.VideoQuality;

public class MainActivity extends Activity implements Session.Callback,SurfaceHolder.Callback{



    private final static String TAG = "MainActivity";

    private Button mButton1, mButton2;
    private android.view.SurfaceView mSurfaceView;
    private EditText mEditText;
    private Session mSession;
    private android.view.SurfaceView textureView;
    SessionBuilder SB;
    Session session;

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



        mSurfaceView = (SurfaceView) findViewById(R.id.surface);

        textureView = (android.view.SurfaceView) findViewById(R.id.textureView);
        Surface surf = textureView.getHolder().getSurface();

        // Sets the port of the RTSP server to 1234
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(RtspServer.KEY_PORT, String.valueOf(1234));
        editor.commit();
        Log.d("QQQQQQQQQQQQQQQQQQQQQ", String.valueOf(VideoQuality.DEFAULT_VIDEO_QUALITY.resX)+"\t"+String.valueOf(VideoQuality.DEFAULT_VIDEO_QUALITY.bitrate));

        // Configures the SessionBuilder
        final VideoQuality quality = new VideoQuality(
                1280, 720, 30, 10000);


        //mSurfaceView.addMediaCodecSurface(surf);
        mSurfaceView.getHolder().addCallback(this);
        SB = SessionBuilder.getInstance()
                .setSurfaceView( mSurfaceView)
                .setContext(getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_AAC)
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(quality).setCallback(this);


        this.startService(new Intent(this, RtspServer.class));

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.d("SURFACE", "CREATED        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        session = SB.build();
        session.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }



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
        Log.d("QQQQQQQQQ", "THIS SESSION STOPPED");
        session = SB.build();
        session.startPreview();
    }
}
