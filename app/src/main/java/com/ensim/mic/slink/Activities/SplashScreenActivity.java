package com.ensim.mic.slink.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.ensim.mic.slink.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    VideoView videoView;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        videoView = findViewById(R.id.videoView);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();
            }
        });

        videoView.setZOrderOnTop(true);


        playVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        startNextActivity();
    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void playVideo() {

        String path = "android.resource://" + getPackageName() + "/" + R.raw.slink_animation;

        Uri u = Uri.parse(path);

        videoView.setVideoURI(u);

        videoView.start();

    }
}
