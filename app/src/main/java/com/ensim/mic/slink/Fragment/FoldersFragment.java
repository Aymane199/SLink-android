package com.ensim.mic.slink.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Adapter.DataAdapter_folder;
import com.ensim.mic.slink.Api.FolderApiServices;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.Api.UserApiServices;
import com.ensim.mic.slink.BottomSheet.BottomSheetFilter;
import com.ensim.mic.slink.BottomSheet.BottomSheetSort;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.UserFolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoldersFragment extends Fragment implements View.OnClickListener{

    static public int userId = 3;

    UserApiServices userApiServices;
    FolderApiServices folderApiService;

    List<UserFolder> folderOutputList;

    BottomSheetFilter bottomSheetFilter;
    BottomSheetSort bottomSheetSort;

    String searchText = "";

    View mview;
    ImageView img;
    EditText etSearch;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardViewFilter, cardViewAdd, cardViewSort;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_folders, container, false);
        return mview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get services
        userApiServices = RetrofitFactory.getINSTANCE().getRetrofit().create(UserApiServices.class);
        folderApiService = RetrofitFactory.getINSTANCE().getRetrofit().create(FolderApiServices.class);

        //init views
        bottomSheetFilter = new BottomSheetFilter();
        bottomSheetSort = new BottomSheetSort();

        progressBar = view.findViewById(R.id.progress_circular_album);
        hideProgress();

        cardViewFilter = view.findViewById(R.id.card_view_filters);
        cardViewAdd = view.findViewById(R.id.card_view_add);
        cardViewSort = view.findViewById(R.id.card_view_sort);
        etSearch = view.findViewById(R.id.etSearch);

        cardViewFilter.setOnClickListener(this);
        cardViewAdd.setOnClickListener(this);
        cardViewSort.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    searchText = etSearch.getText().toString();
                    displayFolders(bottomSheetFilter.getChoosen_filter(),bottomSheetSort.getChoosen_sort(),searchText);
                    return true;
                }
                return false;
            }
        });

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //display all folder
        displayFolders(bottomSheetFilter.getChoosen_filter(),bottomSheetSort.getChoosen_sort(), searchText);

        bottomSheetFilter.setListener(new BottomSheetFilter.ChangeListener() {
            @Override
            public void onChange() {
                displayFolders(bottomSheetFilter.getChoosen_filter(),bottomSheetSort.getChoosen_sort(), searchText);
            }
        });

        bottomSheetSort.setMlistener(new BottomSheetFilter.ChangeListener() {
            @Override
            public void onChange() {
                displayFolders(bottomSheetFilter.getChoosen_filter(),bottomSheetSort.getChoosen_sort(), searchText);
            }
        });
    }

    private void displayFolders(int filter, final int sort, String searchText) {
        showProgress();

        // etablish the request
        Call<List<UserFolder>> call;
        //set filter
        switch (filter){
            case BottomSheetFilter.FILTER_ANYONE :
                 call = userApiServices.getUserAllFolders(userId, searchText);
                break;
            case BottomSheetFilter.FILTER_OWNED_BY_ME :
                 call = userApiServices.getUserFolders(userId, searchText);
                break;
            case BottomSheetFilter.FILTER_SHARED_WITH_ME :
                 call = userApiServices.getUserShare(userId, searchText);
                break;
            case BottomSheetFilter.FILTER_SUBSCRIPTION :
                 call = userApiServices.getUserSubscribe(userId, searchText);
                break;
            default:
                 call = userApiServices.getUserAllFolders(userId, searchText);

        }
        //fill the folder list
        call.enqueue(new Callback<List<UserFolder>>() {

            @Override
            public void onResponse(Call<List<UserFolder>> call, Response<List<UserFolder>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    hideProgress();
                    return;
                }
                folderOutputList = response.body();
                //set sort
                if(!folderOutputList.isEmpty())
                switch(sort){
                    case BottomSheetSort.SORT_MOST_RECENT :
                        if(Integer.parseInt(folderOutputList.get(0).getId())<
                                Integer.parseInt(folderOutputList.get(folderOutputList.size()-1).getId()))
                        Collections.reverse(folderOutputList);
                        break;
                    case BottomSheetSort.SORT_OLDEST :
                        if(Integer.parseInt(folderOutputList.get(0).getId())>
                                Integer.parseInt(folderOutputList.get(folderOutputList.size()-1).getId()))
                            Collections.reverse(folderOutputList);
                        break;
                }
                for (UserFolder folder : folderOutputList) {
                    System.out.println(folder.toString());
                }
                mAdapter = new DataAdapter_folder(getActivity(), folderOutputList);
                recyclerView.setAdapter(mAdapter);
                hideProgress();
            }

            @Override
            public void onFailure(Call<List<UserFolder>> call, Throwable t) {
                System.out.println(t.getMessage());
                hideProgress();
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.card_view_filters) {
            bottomSheetFilter.show(getActivity().getSupportFragmentManager(), "bottomSheetFilter");

        }
        if (v.getId() == R.id.card_view_sort) {
            bottomSheetSort.show(getActivity().getSupportFragmentManager(), "bottomSheetSort");
        }
        if (v.getId() == R.id.card_view_add) {
            showRenameDialog();
            /*
            BottomSheetComment bottomSheetComment = new BottomSheetComment();
            bottomSheetComment.show(getActivity().getSupportFragmentManager(), "bottomSheetComment");
             */
        }

    }

    private void showRenameDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 0, 50, 0);
        input.setLayoutParams(layoutParams);
        input.setInputType(EditText.AUTOFILL_TYPE_TEXT);
        input.setSingleLine();

        LinearLayout layout = new LinearLayout(getActivity());
        layout.addView(input);

        TextView tvTitle = new TextView(getActivity());
        tvTitle.setText("Create folder");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Please entre the folder name ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true)
                .setView(layout)
                .setCancelable(true);

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity(), input.getText()+"created",
                        Toast.LENGTH_LONG).show();
                createFolder(input.getText().toString(),userId);
                displayFolders(bottomSheetFilter.getChoosen_filter(),bottomSheetSort.getChoosen_sort(), searchText);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity(), "create folder cancled ",
                        Toast.LENGTH_LONG).show();
            }

        });
        alertDialogBuilder.show();
    }

    public void createFolder(String name,int owner) {
        System.out.println("create folder ------------------------------------- ");

        HashMap<String, Object> body = new HashMap<>();
        body.put("name",name);
        body.put("owner",owner);
        Call<Folder> call = folderApiService.createFolder(body);
        call.enqueue(new Callback<Folder>() {
            @Override
            public void onResponse(Call<Folder> call, Response<Folder> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return ;
                }
                Folder folder1 = response.body();

                displayFolders(bottomSheetFilter.getChoosen_filter(),bottomSheetSort.getChoosen_sort(), searchText);
            }

            @Override
            public void onFailure(Call<Folder> call, Throwable t) {
                System.out.println(t.getMessage());
                return;
            }
        });
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}