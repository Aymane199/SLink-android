package com.ensim.mic.slink.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ensim.mic.slink.Adapter.DataAdapter_folder;
import com.ensim.mic.slink.BottomSheet.BottomSheetComment;
import com.ensim.mic.slink.BottomSheet.BottomSheetFilter;
import com.ensim.mic.slink.BottomSheet.BottomSheetSort;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Folder;

import java.util.ArrayList;
import java.util.List;


public class FoldersFragment extends Fragment implements View.OnClickListener {

    View mview;
    ImageView img;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CardView cardViewFilter, cardViewAdd, cardViewSort;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_folders, container, false);
        return mview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardViewFilter = view.findViewById(R.id.card_view_filters);
        cardViewAdd = view.findViewById(R.id.card_view_add);
        cardViewSort = view.findViewById(R.id.card_view_sort);

        cardViewFilter.setOnClickListener(this);
        cardViewAdd.setOnClickListener(this);
        cardViewSort.setOnClickListener(this);


        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<Folder> folderList = new ArrayList<>();

        String str = "https://www.gstatic.com/webp/gallery/4.sm.jpg";
        System.out.println("str : " + str);

        folderList.add(new Folder(1, 1, 21, 5005, "Cuisine", "tajine twivjyat", str, "triwtza", "folder",true));
        folderList.add(new Folder(1, 1, 21, 5005, "Cuisine", "tajine twivjyat", str, "triwtza", "folder",false));
        folderList.add(new Folder(1, 1, 21, 5005, "Cuisine", "tajine twivjyat", str, "triwtza", "folder",true));
        folderList.add(new Folder(1, 1, 21, 5005, "Cuisine", "tajine twivjyat", str, "triwtza", "folder",false));
        folderList.add(new Folder(1, 1, 21, 5005, "Cuisine", "tajine twivjyat", str, "triwtza", "folder",false));

        mAdapter = new DataAdapter_folder(getActivity(), folderList);
        recyclerView.setAdapter(mAdapter);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.card_view_filters) {
            BottomSheetFilter bottomSheetFilter = new BottomSheetFilter();
            bottomSheetFilter.show(getActivity().getSupportFragmentManager(), "bottomSheetFilter");

        }
        if (v.getId() == R.id.card_view_sort) {
            BottomSheetSort bottomSheetSort = new BottomSheetSort();
            bottomSheetSort.show(getActivity().getSupportFragmentManager(), "bottomSheetSort");
        }
        if (v.getId() == R.id.card_view_add) {
            BottomSheetComment bottomSheetComment = new BottomSheetComment();
            bottomSheetComment.show(getActivity().getSupportFragmentManager(), "bottomSheetComment");
        }

    }


}