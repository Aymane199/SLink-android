package com.ensim.mic.slink.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Adapter.DataAdapter_chooseFolder;
import com.ensim.mic.slink.Api.FolderApiServices;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.Api.UserApiServices;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderLink;
import com.ensim.mic.slink.Table.UserFolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.ponnamkarthik.richlinkpreview.MetaData;
import io.github.ponnamkarthik.richlinkpreview.ResponseListener;
import io.github.ponnamkarthik.richlinkpreview.RichPreview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseFolderActivity extends AppCompatActivity implements View.OnClickListener {

    static public int userId = 3;

    UserApiServices userApiServices;
    FolderApiServices folderApiService;

    List<UserFolder> folderOutputList;

    FolderLink linkToPut;

    String searchText = "";

    EditText etSearch;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardViewAdd;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_folder);

        //get URL link
        Intent receiverdIntent = getIntent();
        String receivedAction = receiverdIntent.getAction();
        String receivedType = receiverdIntent.getType();

        if (receivedAction.equals(Intent.ACTION_SEND)) {

            // check mime type
            if (receivedType.startsWith("text/")) {

                linkToPut = new FolderLink();
                String url =  receiverdIntent
                        .getStringExtra(Intent.EXTRA_TEXT);
                linkToPut.setUrl(url);

                RichPreview richPreview = new RichPreview(new ResponseListener() {
                    @Override
                    public void onData(MetaData metaData) {
                        linkToPut.setPicture(metaData.getImageurl());
                        linkToPut.setName(metaData.getTitle());
                        linkToPut.setDescription(metaData.getDescription());
                        displayFolders(searchText);
                    }

                    @Override
                    public void onError(Exception e) {

                    }

                });

                richPreview.getPreview(url);
            }
        }
        // get services
        userApiServices = RetrofitFactory.getINSTANCE().getRetrofit().create(UserApiServices.class);
        folderApiService = RetrofitFactory.getINSTANCE().getRetrofit().create(FolderApiServices.class);


        folderOutputList = new ArrayList<>();

        //init views+
        progressBar = findViewById(R.id.progress_circular_album);
        hideProgress();


        cardViewAdd = findViewById(R.id.card_view_add);
        etSearch = findViewById(R.id.etSearch);

        cardViewAdd.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager) ChooseFolderActivity.this.getSystemService(ChooseFolderActivity.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    searchText = etSearch.getText().toString();
                    if(linkToPut.getUrl()!=null)
                    displayFolders(searchText);
                    return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ChooseFolderActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        //displayFolders(searchText);


    }

    private void displayFolders(String searchText) {
        showProgress();

        folderOutputList = new ArrayList<>();
        // etablish the request
        Call<List<UserFolder>> callUserFolder;
        Call<List<UserFolder>> callUserShare;

        callUserFolder = userApiServices.getUserFolders(userId, searchText);

        //fill the folder list
        callUserFolder.enqueue(new Callback<List<UserFolder>>() {

            @Override
            public void onResponse(Call<List<UserFolder>> call, Response<List<UserFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    hideProgress();
                    return;
                }
                folderOutputList.addAll(response.body());
                for (UserFolder folder : folderOutputList) {
                    System.out.println(folder.toString());
                }
                mAdapter = new DataAdapter_chooseFolder(ChooseFolderActivity.this, folderOutputList,linkToPut);
                recyclerView.setAdapter(mAdapter);
                hideProgress();
            }

            @Override
            public void onFailure(Call<List<UserFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                hideProgress();
            }
        });
        callUserShare = userApiServices.getUserShare(userId, searchText);

        //fill the folder list
        callUserShare.enqueue(new Callback<List<UserFolder>>() {

            @Override
            public void onResponse(Call<List<UserFolder>> call, Response<List<UserFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    hideProgress();
                    return;
                }
                folderOutputList.addAll(response.body());
                mAdapter = new DataAdapter_chooseFolder(ChooseFolderActivity.this, folderOutputList,linkToPut);
                recyclerView.setAdapter(mAdapter);
                for (UserFolder folder : folderOutputList) {
                    System.out.println(folder.toString());
                }

                hideProgress();
            }

            @Override
            public void onFailure(Call<List<UserFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                hideProgress();
            }
        });


    }

    public void onClick(View v) {
        if (v.getId() == R.id.card_view_add) {
            showAddFolderDialog();
        }

    }

    private void showAddFolderDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChooseFolderActivity.this, R.style.CustomAlertDialog);

        final EditText input = new EditText(ChooseFolderActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 0, 50, 0);
        input.setLayoutParams(layoutParams);
        input.setInputType(EditText.AUTOFILL_TYPE_TEXT);
        input.setSingleLine();

        LinearLayout layout = new LinearLayout(ChooseFolderActivity.this);
        layout.addView(input);

        TextView tvTitle = new TextView(ChooseFolderActivity.this);
        tvTitle.setText("Create folder");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Please entre the folder name ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true)
                .setView(layout)
                .setCancelable(true);

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(ChooseFolderActivity.this, input.getText() + "created",
                        Toast.LENGTH_LONG).show();
                createFolder(input.getText().toString(), userId);
                displayFolders(searchText);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(ChooseFolderActivity.this, "create folder cancled ",
                        Toast.LENGTH_LONG).show();
            }

        });
        alertDialogBuilder.show();
    }

    public void createFolder(String name, int owner) {
        System.out.println("create folder ------------------------------------- ");

        HashMap<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("owner", owner);
        Call<Folder> call = folderApiService.createFolder(body);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return;
                }
                Folder folder1 = response.body();
                UserFolder userFolder =new UserFolder();
                userFolder.setId(folder1.getId()+"");
                userFolder.setName(folder1.getName());
                userFolder.setOwner(folder1.getUser().getUserName());

                folderOutputList.add(0,userFolder);
                mAdapter = new DataAdapter_chooseFolder(ChooseFolderActivity.this, folderOutputList,linkToPut);
                recyclerView.setAdapter(mAdapter);

                // displayFolders(searchText);
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                System.out.println(t.getMessage());
                return;
            }
        });
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }


}
