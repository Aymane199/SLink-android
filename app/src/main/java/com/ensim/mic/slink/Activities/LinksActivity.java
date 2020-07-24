package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ensim.mic.slink.Adapter.DataAdapterLink;
import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.Fragment.FoldersFragment;
import com.ensim.mic.slink.Operations.OperationsOnLink;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.LinkOfFolder;

import java.util.List;

public class LinksActivity extends AppCompatActivity {

    //List of likns to display
    List<LinkOfFolder> links;

    //services
    IApiServicesFolder IApiServicesFolder;

    //income information -> folder selected & current user
    String idFolder;
    String nameFolder;
    String idUser;

    //search text
    String searchText;

    //components
    ImageView ivBack, ivMenu;
    TextView FolderLink;
    private EditText etSearch;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);

        //get services
        IApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);


        //get id folder and id user
        Intent intent = getIntent();
        idFolder = intent.getStringExtra("idFolder");
        nameFolder = intent.getStringExtra("nameFolder");
        idUser = FoldersFragment.userId + "";

        //init views
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        etSearch = findViewById(R.id.etSearchLinks);
        progressBar = findViewById(R.id.progress_circular);
        tvTitle = findViewById(R.id.FolderLink);
        tvTitle.setText(nameFolder);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // set on action listener to search compoment
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager) LinksActivity.this.getSystemService(LinksActivity.this
                            .INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    searchText = etSearch.getText().toString();
                    new OperationsOnLink().displayLinks(searchText, idFolder, idUser);
                    return true;
                }
                return false;
            }
        });

        //add behavior when "List Links State" changes
        State.getInstance().setOnChangeLinksListner(new State.OnChangeLinks(){
            @Override
            public void onChange() {
                switch (State.getInstance().getLinks().getState()){
                    case LOADING:
                        showProgress();
                        break;
                    case SUCCESSFUL:
                        hideProgress();
                        mAdapter = new DataAdapterLink(LinksActivity.this, State.getInstance().getLinks().getListLinks());
                        recyclerView.setAdapter(mAdapter);
                        break;
                    case FAILED:
                        hideProgress();
                        break;
                    default:
                        //show fail msg
                        hideProgress();
                }
            }
        });

        new OperationsOnLink().displayLinks(searchText, idFolder, idUser);

    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}