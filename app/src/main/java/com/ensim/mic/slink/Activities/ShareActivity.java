package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ensim.mic.slink.Adapter.DataAdapterSharePersonnes;
import com.ensim.mic.slink.Operations.OperationsOnShare;
import com.ensim.mic.slink.Operations.OperationsOnUser;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.OnChangeObject;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShareActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText etSearch;
    private CardView cvAdd;
    private ProgressBar progressBar;
    private ImageView ivRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        assert bundle != null;
        final FolderOfUser folder =
                (FolderOfUser) bundle.getSerializable("folder");


        ivRefresh = findViewById(R.id.ivRefresh);
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
                String userName = etSearch.getText().toString();
                if(userName.isEmpty()) return;
                new OperationsOnUser().getSearchUser(userName);

            }
        });

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OperationsOnShare().dispalySharePersonnes(Integer.parseInt(folder.getId()));
            }
        });

        State.getInstance().getSearchUser().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                Toast.makeText(ShareActivity.this,"onLoading",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDataReady() {
                User user = State.getInstance().getSearchUser().getContent();
                assert user.getId()!=null;
                new OperationsOnShare().addPersonne(Integer.parseInt(folder.getId()),user.getId());
                System.out.println("add personne "+State.getInstance().getSearchUser().getContent());

            }

            @Override
            public void onFailed() {
                Toast.makeText(ShareActivity.this,"Do u want to send him an invitation ?",Toast.LENGTH_SHORT).show();
            }
        });

        State.getInstance().getSharePeople().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                mAdapter = new DataAdapterSharePersonnes(ShareActivity.this, State.getInstance().getSharePeople().getContent());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        });
        assert folder != null;
        new OperationsOnShare().dispalySharePersonnes(Integer.parseInt(folder.getId()));

    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
