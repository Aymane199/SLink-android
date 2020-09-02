package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ensim.mic.slink.Operations.OperationsOnUser;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.OnChangeObject;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "LoginActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private CardView btnSignIn;
    private User user;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        user = new User();

        System.out.println("--------------> userid : " + State.getInstance().getCurrentUser().getContent().getId());

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        final boolean[] uCanCreateUser = {true};
        State.getInstance().getCurrentUser().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onDataReady() {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                String token = FirebaseInstanceId.getInstance().getToken();
                Integer idUser = State.getInstance().getCurrentUser().getContent().getId();
                if(token != null)
                new OperationsOnUser().updateToken(idUser,token);
                finish();
            }

            @Override
            public void onFailed() {
                if(State.getInstance().getCurrentUser().getContent().getGmail() == null && !user.getGmail().isEmpty() && !user.getUserName().isEmpty() && uCanCreateUser[0]) {
                        new OperationsOnUser().createCurrentUser(user.getUserName(),user.getGmail(),"NoTokenAssigned","");
                        uCanCreateUser[0] = false;
                        finish();
                }
            }
        });

    }

    private void initComponents() {
        progress = findViewById(R.id.progress_circular);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            btnSignIn.setVisibility(View.INVISIBLE);
            showProgress();

            user.setGmail(account.getEmail());
            user.setUserName(account.getDisplayName());

            new OperationsOnUser().getCurrentUser(account.getEmail());

            Toast.makeText(getApplicationContext(),"Welcome "+account.getDisplayName(), Toast.LENGTH_LONG).show();

        } else {

            hideProgress();
            btnSignIn.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignIn) {
            signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.

            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        progress.setVisibility(View.INVISIBLE);
    }

}
