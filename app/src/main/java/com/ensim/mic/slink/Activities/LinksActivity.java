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

import com.ensim.mic.slink.Adapter.DataAdapter_link;
import com.ensim.mic.slink.Api.IApiServicesFolder;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.Fragment.FoldersFragment;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.LinkOfFolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    displayLinks(searchText);
                    return true;
                }
                return false;
            }
        });

        displayLinks(searchText);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void displayLinks(String searchText) {
        showProgress();


        // etablish the request
        Call<List<LinkOfFolder>> call = IApiServicesFolder.getFolderLinks(idFolder, idUser, searchText);

        //fill the folder list
        call.enqueue(new Callback<List<LinkOfFolder>>() {

            @Override
            public void onResponse(Call<List<LinkOfFolder>> call, Response<List<LinkOfFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    hideProgress();
                    return;
                }
                links = response.body();
                //set sort
                if (!links.isEmpty())
                    for (LinkOfFolder link : links) {
                        System.out.println(link.toString());
                    }
                mAdapter = new DataAdapter_link(LinksActivity.this, links);
                recyclerView.setAdapter(mAdapter);
                hideProgress();
            }

            @Override
            public void onFailure(Call<List<LinkOfFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                hideProgress();
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