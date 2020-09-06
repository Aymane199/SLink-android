package com.ensim.mic.slink.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.ensim.mic.slink.Repository.UserRepository;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.OnChangeObject;
import com.ensim.mic.slink.Model.Model;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private VideoView videoView;
    private ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.progress_circular);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();
            }
        });

        videoView.setZOrderOnTop(true);

        showProgress();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            new UserRepository().getCurrentUser(account.getEmail());
            videoView.setVisibility(View.INVISIBLE);
        } else {
            playVideo();
        }

        Model.getInstance().getCurrentUser().addOnChangeObjectListener(getOnChangeCurrentUserListener());

    }

    private OnChangeObject getOnChangeCurrentUserListener() {
        return new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                String token = FirebaseInstanceId.getInstance().getToken();
                Integer idUser = Model.getInstance().getCurrentUser().getContent().getId();
                assert token != null;
                new UserRepository().updateToken(idUser,token);
                finish();
            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        startNextActivity();
    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void playVideo() {

        String path = "android.resource://" + getPackageName() + "/" + R.raw.slink_animation;

        Uri u = Uri.parse(path);

        videoView.setVideoURI(u);

        videoView.start();

    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }



}
