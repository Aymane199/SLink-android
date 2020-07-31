package com.ensim.mic.slink.Fragment;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ensim.mic.slink.Adapter.DataAdapterFolder;
import com.ensim.mic.slink.Operations.OperationsOnFolder;
import com.ensim.mic.slink.Component.BottomSheetFilter;
import com.ensim.mic.slink.Component.BottomSheetSort;
import com.ensim.mic.slink.Component.FolderComponents;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.OnChangeObject;
import com.ensim.mic.slink.State.State;

/*
    clean
 */
public class FoldersFragment extends Fragment implements View.OnClickListener{

    //State of the user
    static public int userId = 3;
    static public String userName = "Aymanerzk";

    //BottomSheetView
    private BottomSheetFilter bottomSheetFilter;
    private BottomSheetSort bottomSheetSort;

    //search Text
    private String searchText = "";

    // components
    View mview;
    ImageView img;
    EditText etSearch;
    private RecyclerView recyclerView;
    private DataAdapterFolder mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardViewFilter, cardViewAdd, cardViewSort;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_folders, container, false);
        return mview;
    }

    /*
    *   init component (Views, bottomSheetViews...)
    *   set listener to search EditText
    *   set listener to State.onChangeListFolder
    *   set listener to bottomSheetView filter
    *   show ListFolders -> OpetationsOnFolder
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init views
        bottomSheetFilter = new BottomSheetFilter();
        bottomSheetSort = new BottomSheetSort();

        progressBar = view.findViewById(R.id.progress_circular);

        cardViewFilter = view.findViewById(R.id.card_view_filters);
        cardViewAdd = view.findViewById(R.id.card_view_add);
        cardViewSort = view.findViewById(R.id.card_view_sort);
        etSearch = view.findViewById(R.id.etSearch);

        cardViewFilter.setOnClickListener(this);
        cardViewAdd.setOnClickListener(this);
        cardViewSort.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new DataAdapterFolder(getActivity(), State.getInstance().getFoldersList().getObject());
        recyclerView.setAdapter(mAdapter);

        //add action on click search
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    searchText = etSearch.getText().toString();
                    new OperationsOnFolder().displayFolders(bottomSheetFilter.getChoosen_filter(),searchText);
                    return true;
                }
                return false;
            }
        });

        //add listner to filter bottomSheetFilter
        bottomSheetFilter.setListener(new BottomSheetFilter.ChangeListener() {
            @Override
            public void onChange() {
                new OperationsOnFolder().displayFolders(bottomSheetFilter.getChoosen_filter(),searchText);
            }
        });

        //add behavier when "List Folder State" changes
        State.getInstance().getFoldersList().setOnChangeObjectListeners(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                System.out.println("change list");
                mAdapter.mData = State.getInstance().getFoldersList().getObject();
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed() {
                hideProgress();
            }
        });
        new OperationsOnFolder().displayFolders(bottomSheetFilter.getChoosen_filter(),searchText);

    }

    @Override
    public void onResume() {
        super.onResume();
        new OperationsOnFolder().displayFolders(bottomSheetFilter.getChoosen_filter(),searchText);
    }

    /*
    *   Switch cases :
    *   open bottomSheetFilter
    *   open bottomSheetSort
    *   open add folder
     */
    public void onClick(View v) {
        if (v.getId() == R.id.card_view_filters) {
            bottomSheetFilter.show(getActivity().getSupportFragmentManager(), "bottomSheetFilter");

        }
        if (v.getId() == R.id.card_view_sort) {
            bottomSheetSort.show(getActivity().getSupportFragmentManager(), "bottomSheetSort");
        }
        if (v.getId() == R.id.card_view_add) {
            new FolderComponents().showAddFolderDialog(getActivity(),userId);
        }
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }


}