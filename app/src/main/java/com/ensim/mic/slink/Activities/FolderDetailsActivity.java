package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ensim.mic.slink.Adapter.DataAdapterSharePersonnes;
import com.ensim.mic.slink.Repository.ShareRepository;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.OnChangeObject;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.FolderWithoutUser;
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

        // get folder form intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null ) finish();
        FolderWithoutUser folder =
                (FolderWithoutUser) bundle.getSerializable("folder");

        if(folder == null) finish();

        initComponents();

        hideTextViewEmptyList();

        hideProgress();

        fieldComponents(folder);

        Model.getInstance().getFolderMembers().addOnChangeObjectListener(getOnChangeFolderMembersListener());

        new ShareRepository().dispalySharePersonnes(Integer.parseInt(folder.getId()));

    }

    private void fieldComponents(FolderWithoutUser folder) {
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
    }

    private OnChangeObject getOnChangeFolderMembersListener() {
        return new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                List<SharePersonne> content = Model.getInstance().getFolderMembers().getContent();

                mAdapter = new DataAdapterSharePersonnes(FolderDetailsActivity.this, content);
                recyclerView.setAdapter(mAdapter);

                if(content.isEmpty()) showTextViewEmptyList();
                else hideTextViewEmptyList();
            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        };
    }

    private void initComponents() {
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
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showTextViewEmptyList(){
        tvEmptyList.setVisibility(View.VISIBLE);
    }

    private void hideTextViewEmptyList(){tvEmptyList.setVisibility(View.INVISIBLE);}

}
