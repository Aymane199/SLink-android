package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Adapter.DataAdapterChooseFolder;
import com.ensim.mic.slink.Component.FolderComponents;
import com.ensim.mic.slink.Operations.OperationsOnFolder;
import com.ensim.mic.slink.Operations.OperationsOnUser;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.OnChangeObject;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.ponnamkarthik.richlinkpreview.MetaData;
import io.github.ponnamkarthik.richlinkpreview.ResponseListener;
import io.github.ponnamkarthik.richlinkpreview.RichPreview;

public class ChooseFolderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 1;
    //user state information
    public int userId;
    public String userName;
    List<FolderOfUser> folderOutputList;
    //link to add in the choosen foolder
    LinkOfFolder linkToPut;
    //search test
    String searchText = "";
    //components
    String url;
    EditText etSearch;
    RichPreview richPreview;
    private GoogleSignInClient mGoogleSignInClient;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardViewAdd;
    private ProgressBar progressBar;
    private TextView tvEmptyList;
    private ImageView ivRefresh;
    private ImageView ivBack;

    public ChooseFolderActivity() {
        //userId = State.getInstance().getCurrentUser().getContent().getId();
        //userName = State.getInstance().getCurrentUser().getContent().getUserName();
        //Toast.makeText(this,userName,Toast.LENGTH_LONG).show();
    }

    /*
     * show progress bar
     * init components
     * get the link URL
     * get the link preview (picture, title ...) - richpreview api
     * listner for search Edittext
     * add lisnter to State on change FolderList
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_folder);

        initComponents();

        hideTvEmptyList();
        showProgress();

        //get URL link
        Intent receiverdIntent = getIntent();
        String receivedAction = receiverdIntent.getAction();
        String receivedType = receiverdIntent.getType();

        /*
            if the action is a test -> url
            we will try to get the link preview
        */

        assert receivedAction != null;
        assert receivedType != null;
        if (!receivedAction.equals(Intent.ACTION_SEND) || !receivedType.startsWith("text/")) {
            finish();
        }

        // check mime type
        linkToPut.setUrl(receiverdIntent.getStringExtra(Intent.EXTRA_TEXT));

        //System.out.println(receiverdIntent.toString());

        if (!URLUtil.isValidUrl(linkToPut.getUrl())) {
            Toast.makeText(this, "This is not a Url", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }




        richPreview = new RichPreview(new ResponseListener() {
            @Override
            public void onData(MetaData metaData) {
                linkToPut.setPicture(metaData.getImageurl());
                linkToPut.setName(metaData.getTitle());
                linkToPut.setDescription(metaData.getDescription());
                new OperationsOnFolder().displayEditableFolders(searchText);
                hideProgress();
            }

            @Override
            public void onError(Exception e) {
                System.out.println(e.toString());
                hideProgress();
            }
        });

        State.getInstance().getCurrentUser().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                if (richPreview != null && linkToPut.getUrl() != null)
                    richPreview.getPreview(linkToPut.getUrl());

               hideProgress();


            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        });

        State.getInstance().getFolders().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                List<FolderOfUser> content = State.getInstance().getFolders().getContent();

                mAdapter = new DataAdapterChooseFolder(ChooseFolderActivity.this, content, linkToPut);
                recyclerView.setAdapter(mAdapter);

                if (content.isEmpty()) showTvEmptyList();
                else hideTvEmptyList();
            }

            @Override
            public void onFailed() {
                hideProgress();

            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager) ChooseFolderActivity.this.getSystemService(ChooseFolderActivity.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    searchText = etSearch.getText().toString();
                    if (linkToPut.getUrl() != null)
                        new OperationsOnFolder().displayEditableFolders(searchText);
                    return true;
                }
                return false;
            }
        });

        checkConnection();


    }

    private void initComponents() {
        tvEmptyList = findViewById(R.id.tvEmptyList);
        progressBar = findViewById(R.id.progress_circular);
        cardViewAdd = findViewById(R.id.card_view_add);
        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.my_recycler_view);
        ivBack = findViewById(R.id.ivBack);
        ivRefresh = findViewById(R.id.ivRefresh);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ChooseFolderActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        cardViewAdd.setOnClickListener(this);
        ivRefresh.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        linkToPut = new LinkOfFolder();
    }

    /*
     *   open "add Folder"dialog
     */
    public void onClick(View v) {
        if (v.getId() == R.id.card_view_add) {
            new FolderComponents().showAddFolderDialog(ChooseFolderActivity.this, userId);
        }
        if (v.getId() == R.id.ivRefresh) {
            new OperationsOnFolder().displayEditableFolders(searchText);
        }
        if (v.getId() == R.id.ivBack) {
            finish();
        }

    }

    private void checkConnection() {
        if(State.getInstance().getCurrentUser().getContent().getGmail() != null){
            richPreview.getPreview(linkToPut.getUrl());
            return;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        updateUser(account);

    }

    private void updateUser(GoogleSignInAccount account){
        if (account != null) {
            showProgress();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();


            System.out.println("profile [ " +
                    ", Name :" + account.getEmail() + "" +
                    ", personGivenName :" + personGivenName + "" +
                    ", personFamilyName :" + personFamilyName + "" +
                    ", personEmail :" + account.getEmail() + "" +
                    ", personId :" + personId + "" +
                    ", personPhoto :" + personPhoto + "" +
                    "]");

            new OperationsOnUser().getCurrentUser(account.getEmail());

        } else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUser(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
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

    /**
     * show Progress bar on UI
     */
    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showTvEmptyList() {
        tvEmptyList.setVisibility(View.VISIBLE);
    }

    private void hideTvEmptyList() {
        tvEmptyList.setVisibility(View.INVISIBLE);
    }


}
