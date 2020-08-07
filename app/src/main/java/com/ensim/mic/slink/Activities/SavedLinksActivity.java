package com.ensim.mic.slink.Activities;

import android.os.Bundle;
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
import com.ensim.mic.slink.Operations.OperationsOnLink;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.OnChangeObject;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.LinkOfFolder;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedLinksActivity extends AppCompatActivity {

    //List of likns to display
    List<LinkOfFolder> links;

    //services
    IApiServicesFolder IApiServicesFolder;

    //income information -> current user
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
        setContentView(R.layout.activity_save);

        //get services
        IApiServicesFolder = RetrofitFactory.getINSTANCE().getRetrofit().create(IApiServicesFolder.class);


        //get id user
        idUser = State.getInstance().getCurrentUser().getContent().getId()+ "";

        //init views
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        etSearch = findViewById(R.id.etSearchLinks);
        progressBar = findViewById(R.id.progress_circular);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // set on action listener to search compoment
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager) SavedLinksActivity.
                            this.getSystemService(INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    searchText = etSearch.getText().toString();
                    new OperationsOnLink().displaySavedLinks(searchText, idUser);
                    return true;
                }
                return false;
            }
        });

        //add behavior when "List Links State" changes
        State.getInstance().getSavedLinks().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                mAdapter = new DataAdapterLink(SavedLinksActivity.this, State.getInstance().getSavedLinks().getContent());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        });

        new OperationsOnLink().displaySavedLinks(searchText, idUser);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
