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
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Model.OnChangeObject;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Repository.LinkRepository;
import com.ensim.mic.slink.Table.Link;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedLinksActivity extends AppCompatActivity {

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
    private ImageView ivRefresh;
    private TextView tvEmptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        //get id user
        idUser = Model.getInstance().getCurrentUser().getContent().getId()+ "";

        initComponents();

        hideTextViewEmptyList();

        // set on action listener to search compoment
        etSearch.setOnEditorActionListener(showSearchButtonOnEditText());

        setListenerRefreshAndBack();

        //add behavior when "List Links State" changes
        Model.getInstance().getSavedLinks().addOnChangeObjectListener(getOnChangeSavedLinksListener());

        new LinkRepository().displaySavedLinks(searchText, idUser);
    }

    private void setListenerRefreshAndBack() {
        //set behavior to refresh
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LinkRepository().displaySavedLinks(searchText, idUser);
            }
        });

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private OnChangeObject getOnChangeSavedLinksListener() {
        return new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                List<Link> content = Model.getInstance().getSavedLinks().getContent();

                mAdapter = new DataAdapterLink(SavedLinksActivity.this, content);
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

    private TextView.OnEditorActionListener showSearchButtonOnEditText() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.clearFocus();

                    InputMethodManager in = (InputMethodManager) SavedLinksActivity.
                            this.getSystemService(INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

                    searchText = etSearch.getText().toString();
                    new LinkRepository().displaySavedLinks(searchText, idUser);
                    return true;
                }
                return false;
            }
        };
    }

    private void initComponents() {
        //init views
        ivRefresh = findViewById(R.id.ivRefresh);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        etSearch = findViewById(R.id.etSearchLinks);
        progressBar = findViewById(R.id.progress_circular);
        tvEmptyList =findViewById(R.id.tvEmptyList);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
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
