package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ensim.mic.slink.Adapter.DataAdapterSharePersonnes;
import com.ensim.mic.slink.Operations.OperationsOnShare;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.OnChangeObject;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.squareup.picasso.Picasso;

public class ShareActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText etSearch;
    private CardView cvAdd;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        assert bundle != null;
        FolderOfUser folder =
                (FolderOfUser) bundle.getSerializable("folder");



        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ShareActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        etSearch = findViewById(R.id.etSearch);
        cvAdd = findViewById(R.id.cvAdd);
        progressBar = findViewById(R.id.progress_circular);


        hideProgress();


        cvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OperationsOnShare().addPersonne(3,4);
            }
        });

        State.getInstance().getSharePersonnesList().setOnChangeObjectListeners(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                mAdapter = new DataAdapterSharePersonnes(ShareActivity.this, State.getInstance().getSharePersonnesList().getObject());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        });
        new OperationsOnShare().dispalySharePersonnes(Integer.parseInt(folder.getId()));

    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
