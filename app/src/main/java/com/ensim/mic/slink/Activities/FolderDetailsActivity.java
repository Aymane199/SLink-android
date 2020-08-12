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
import com.ensim.mic.slink.Table.SharePersonne;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    private TextView tvEmptyList ;


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
        tvName = findViewById(R.id.tvUserName);
        tvDescription = findViewById(R.id.tvDescription);
        tvLikes = findViewById(R.id.tvLikes);
        tvLinks = findViewById(R.id.tvLinks);
        ivImage = findViewById(R.id.ivImage);
        recyclerView = findViewById(R.id.myRecycleView);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(FolderDetailsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        hideTvEmptyList();
        hideProgress();

        System.out.println("get extra :" + folder);


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
                List<SharePersonne> content = State.getInstance().getSharePeople().getContent();

                mAdapter = new DataAdapterSharePersonnes(FolderDetailsActivity.this, content);
                recyclerView.setAdapter(mAdapter);

                if(content.isEmpty()) showTvEmptyList();
                else hideTvEmptyList();
            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        });
        new OperationsOnShare().dispalySharePersonnes(Integer.parseInt(folder.getId()));

    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showTvEmptyList(){
        tvEmptyList.setVisibility(View.VISIBLE);
    }

    private void hideTvEmptyList(){tvEmptyList.setVisibility(View.INVISIBLE);}

}
