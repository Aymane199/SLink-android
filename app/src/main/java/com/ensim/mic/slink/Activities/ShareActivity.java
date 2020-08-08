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
    private String searchText;

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
                        new OperationsOnUser().getSearchUser(userName);
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
                new OperationsOnUser().getSearchUser(userName);
                etSearch.clearFocus();
                etSearch.setText("");

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
                //Toast.makeText(ShareActivity.this,"onLoading",Toast.LENGTH_SHORT).show();
                showProgress();
            }

            @Override
            public void onDataReady() {
                User user = State.getInstance().getSearchUser().getContent();
                assert user.getId()!=null;
                new OperationsOnShare().addPersonne(Integer.parseInt(folder.getId()),user.getId());
                System.out.println("add personne "+State.getInstance().getSearchUser().getContent());
                hideProgress();


            }

            @Override
            public void onFailed() {
                Toast.makeText(ShareActivity.this,"User do not exist, do u want to send him an invitation ?",Toast.LENGTH_LONG).show();
                hideProgress();

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
