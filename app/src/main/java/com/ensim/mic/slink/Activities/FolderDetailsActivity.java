package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ensim.mic.slink.Adapter.DataAdapterSharePersonnes;
import com.ensim.mic.slink.Operations.OperationsOnShare;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.OnChangeObject;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FolderDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tvName, tvDescription, tvLikes, tvLinks;
    private ImageView ivImage;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_details);


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        assert bundle != null;
        FolderOfUser folder =
                (FolderOfUser) bundle.getSerializable("folder");

        progressBar = findViewById(R.id.progress_circular);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        tvLikes = findViewById(R.id.tvLikes);
        tvLinks = findViewById(R.id.tvLinks);
        ivImage = findViewById(R.id.ivImage);
        recyclerView = findViewById(R.id.myRecycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(FolderDetailsActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        System.out.println("get extra :" + folder);

        hideProgress();

        assert folder != null;
        tvName.setText(folder.getName());
        tvDescription.setText(folder.getDescription());
        if (folder.getLikes() == null)
            tvLikes.setText("0");
        else
            tvLikes.setText(folder.getLikes());
        if (folder.getLinks() == null)
            tvLinks.setText("0");
        else
            tvLinks.setText(folder.getLinks());
        try {
            String imgUrl = folder.getPicture();
            Picasso.get().load(Uri.parse(imgUrl)).placeholder(R.drawable.ic_folder).into(ivImage);
        } catch (Exception e) {
            System.out.println("error loading image");
        }

        State.getInstance().getSharePeople().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                mAdapter = new DataAdapterSharePersonnes(FolderDetailsActivity.this, State.getInstance().getSharePeople().getContent());
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
