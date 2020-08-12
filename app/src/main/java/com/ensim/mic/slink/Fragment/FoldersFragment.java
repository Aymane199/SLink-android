package com.ensim.mic.slink.Fragment;

import android.os.Bundle;
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
import com.ensim.mic.slink.Component.BottomSheetFilter;
import com.ensim.mic.slink.Component.BottomSheetSort;
import com.ensim.mic.slink.Component.FolderComponents;
import com.ensim.mic.slink.Operations.OperationsOnFolder;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.State.OnChangeObject;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.FolderOfUser;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
    clean
 */
public class FoldersFragment extends Fragment implements View.OnClickListener{

    //State of the user
    public int userId;

    static public String userName = State.getInstance().getCurrentUser().getContent().getUserName();

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
    private ImageView ivRefresh;
    private TextView tvEmptyList;

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

        userId = State.getInstance().getCurrentUser().getContent().getId();

        //init views
        ivRefresh = view.findViewById(R.id.ivRefresh);

        bottomSheetFilter = new BottomSheetFilter();
        bottomSheetSort = new BottomSheetSort();

        progressBar = view.findViewById(R.id.progress_circular);

        cardViewFilter = view.findViewById(R.id.card_view_filters);
        cardViewAdd = view.findViewById(R.id.card_view_add);
        cardViewSort = view.findViewById(R.id.card_view_sort);
        etSearch = view.findViewById(R.id.etSearch);
        tvEmptyList = view.findViewById(R.id.tvEmptyList);
        hideTvEmptyList();
        hideProgress();

        cardViewFilter.setOnClickListener(this);
        cardViewAdd.setOnClickListener(this);
        cardViewSort.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new DataAdapterFolder(getActivity(), State.getInstance().getFolders().getContent(), userId);
        recyclerView.setAdapter(mAdapter);

        //add action on click search
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    etSearch.clearFocus();
                    InputMethodManager in = (InputMethodManager) requireActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    searchText = etSearch.getText().toString();
                    new OperationsOnFolder().displayFolders(bottomSheetFilter.getChoosen_filter(),searchText);
                    return true;
                }
                return false;
            }
        });

        //add behavior to refresh button
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OperationsOnFolder().displayEditableFolders(searchText);
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
        State.getInstance().getFolders().addOnChangeObjectListener(new OnChangeObject() {
            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                List<FolderOfUser> content = State.getInstance().getFolders().getContent();

                mAdapter.mData = content;
                mAdapter.notifyDataSetChanged();

                if(content.isEmpty()) showTvEmptyList();
                else hideTvEmptyList();
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
            bottomSheetFilter.show(requireActivity().getSupportFragmentManager(), "bottomSheetFilter");

        }
        if (v.getId() == R.id.card_view_sort) {
            bottomSheetSort.show(requireActivity().getSupportFragmentManager(), "bottomSheetSort");
        }
        if (v.getId() == R.id.card_view_add) {
            new FolderComponents().showAddFolderDialog(getActivity(),userId);
        }
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