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

import com.ensim.mic.slink.Adapter.DataAdapterLink;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Model.OnChangeObject;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Repository.LinkRepository;
import com.ensim.mic.slink.Table.Link;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinksActivity extends AppCompatActivity {

    //incoming information -> folder selected & current user
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
    private DataAdapterLink mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private ImageView ivRefresh;
    private TextView tvEmptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);

        //get id folder and id user
        Intent intent = getIntent();
        idFolder = intent.getStringExtra("idFolder");
        nameFolder = intent.getStringExtra("nameFolder");
        idUser = Model.getInstance().getCurrentUser().getContent().getId()+"";

        initComponents();

        tvTitle.setText(nameFolder);

        hideTextViewEmptyList();

        etSearch.setOnEditorActionListener(showSearchButtonOnEditText());

        setListenerRefreshAndBack();

        Model.getInstance().getLinks().addOnChangeObjectListener(getOnChangeLinksListener());

        new LinkRepository().displayLinks(searchText, idFolder, idUser);

    }

    private void setListenerRefreshAndBack() {
        //add behavior to refresh
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LinkRepository().displayLinks(searchText, idFolder, idUser);
            }
        });

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initComponents() {
        //init views
        ivRefresh = findViewById(R.id.ivRefresh);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        etSearch = findViewById(R.id.etSearchLinks);
        progressBar = findViewById(R.id.progress_circular);
        tvTitle = findViewById(R.id.tvUserName);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private OnChangeObject getOnChangeLinksListener() {
        return new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                List<Link> content = Model.getInstance().getLinks().getContent();

                mAdapter = new DataAdapterLink(LinksActivity.this, content);
                recyclerView.setAdapter(mAdapter);

                if(mAdapter.mData.isEmpty()) showTextViewEmptyList();
                else hideTextViewEmptyList();

            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        };
    }

    private TextView.OnEditorActionListener showSearchButtonOnEditText() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    etSearch.clearFocus();

                    InputMethodManager in = (InputMethodManager) LinksActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

                    searchText = etSearch.getText().toString();
                    new LinkRepository().displayLinks(searchText, idFolder, idUser);

                    return true;
                }
                return false;
            }
        };
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