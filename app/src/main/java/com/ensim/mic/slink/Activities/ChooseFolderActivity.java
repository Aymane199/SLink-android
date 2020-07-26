package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Adapter.DataAdapterChooseFolder;
import com.ensim.mic.slink.Operations.OperationsOnFolder;
import com.ensim.mic.slink.Component.FolderComponents;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.Table.FolderOfUser;

import java.util.List;

import io.github.ponnamkarthik.richlinkpreview.MetaData;
import io.github.ponnamkarthik.richlinkpreview.ResponseListener;
import io.github.ponnamkarthik.richlinkpreview.RichPreview;

public class ChooseFolderActivity extends AppCompatActivity implements View.OnClickListener {

    //user state information
    static public int userId = 3;
    static public String  userName = "Aymanerzk";

    List<FolderOfUser> folderOutputList;

    //link to add in the choosen foolder
    LinkOfFolder linkToPut;

    //search test
    String searchText = "";

    //components
    EditText etSearch;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardViewAdd;
    private ProgressBar progressBar;


    /*
    * show progress bar
    * init components
    * get the link URL
    * get the link preview (picture, title ...)
    * listner for search Edittext
    * add lisnter to State on change FolderList
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_folder);


        progressBar = findViewById(R.id.progress_circular_album);
        hideProgress();


        //get URL link
        Intent receiverdIntent = getIntent();
        String receivedAction = receiverdIntent.getAction();
        String receivedType = receiverdIntent.getType();
        /*  if the action is a test -> url
            we will try to get the link preview
        */
        if (receivedAction.equals(Intent.ACTION_SEND)) {
            showProgress();
            // check mime type
            if (receivedType.startsWith("text/")) {

                linkToPut = new LinkOfFolder();
                String url =  receiverdIntent
                        .getStringExtra(Intent.EXTRA_TEXT);
                String title =  receiverdIntent
                        .getStringExtra(Intent.EXTRA_TITLE);
                if( !URLUtil.isValidUrl(url)){
                    Toast.makeText(this,"This is not a Url",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                linkToPut.setUrl(url);

                RichPreview richPreview = new RichPreview(new ResponseListener() {
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
                        hideProgress();
                    }

                });

                try{
                    richPreview.getPreview(url);
                }catch (Exception e){
                    new OperationsOnFolder().displayEditableFolders(searchText);
                }
            }
        }


        //init views
        cardViewAdd = findViewById(R.id.card_view_add);
        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ChooseFolderActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        //set listener
        cardViewAdd.setOnClickListener(this);

        State.getInstance().setOnChangeFoldersListner(new State.OnChangeObject() {
            @Override
            public void onChange() {
                System.out.println("ChoosenFolderActivity Listener");
                mAdapter = new DataAdapterChooseFolder(ChooseFolderActivity.this, State.getInstance().getFolders().getListFolder(),linkToPut);
                recyclerView.setAdapter(mAdapter);
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
                    if(linkToPut.getUrl()!=null)
                        new OperationsOnFolder().displayEditableFolders(searchText);
                    return true;
                }
                return false;
            }
        });

    }

    /*
    *   open "add Folder"dialog
     */
    public void onClick(View v) {
        if (v.getId() == R.id.card_view_add) {
            new FolderComponents().showAddFolderDialog(ChooseFolderActivity.this,userId);
        }

    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }


}
