package com.ensim.mic.slink.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Adapter.DataAdapterSharePersonnes;
import com.ensim.mic.slink.Repository.ShareRepository;
import com.ensim.mic.slink.Repository.UserRepository;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.OnChangeObject;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.Table.User;

import java.util.List;

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
    private String searchText;
    private TextView tvEmptyList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if( bundle == null ) finish();

        final FolderWithoutUser folder =
                (FolderWithoutUser) bundle.getSerializable("folder");


        initComponents();

        hideTvEmptyList();
        hideProgress();

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager) ShareActivity.this.getSystemService(ChooseFolderActivity.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    searchText = etSearch.getText().toString();
                    String userName = etSearch.getText().toString();
                    if(!userName.isEmpty())  {
                        new UserRepository().getSearchUser(userName);
                        etSearch.setText("");
                        etSearch.clearFocus();
                    }
                    return true;
                }
                return false;
            }
        });

        cvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etSearch.getText().toString();
                if(userName.isEmpty()) return;
                new UserRepository().getSearchUser(userName);
                etSearch.clearFocus();
                etSearch.setText("");

            }
        });

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareRepository().dispalySharePersonnes(Integer.parseInt(folder.getId()));
            }
        });

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Model.getInstance().getSearchUser().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                //Toast.makeText(ShareActivity.this,"onLoading",Toast.LENGTH_SHORT).show();
                showProgress();
            }

            @Override
            public void onDataReady() {
                User user = Model.getInstance().getSearchUser().getContent();
                assert user.getId()!=null;
                new ShareRepository().addPersonne(Integer.parseInt(folder.getId()),user.getId());
                System.out.println("add personne "+ Model.getInstance().getSearchUser().getContent());
                hideProgress();


            }

            @Override
            public void onFailed() {
                Toast.makeText(ShareActivity.this,"User do not exist, do u want to send him an invitation ?",Toast.LENGTH_LONG).show();
                hideProgress();

            }
        });

        Model.getInstance().getFolderMembers().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                List<SharePersonne> content = Model.getInstance().getFolderMembers().getContent();

                mAdapter = new DataAdapterSharePersonnes(ShareActivity.this, content);
                recyclerView.setAdapter(mAdapter);

                if(content.isEmpty()) showTvEmptyList();
                else hideTvEmptyList();
            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        });
        assert folder != null;
        new ShareRepository().dispalySharePersonnes(Integer.parseInt(folder.getId()));

    }

    private void initComponents() {
        ivRefresh = findViewById(R.id.ivRefresh);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ShareActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        etSearch = findViewById(R.id.etSearch);
        cvAdd = findViewById(R.id.cvAdd);
        progressBar = findViewById(R.id.progress_circular);
        tvEmptyList = findViewById(R.id.tvEmptyList);
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
