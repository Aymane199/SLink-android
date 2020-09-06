package com.ensim.mic.slink.Component;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ensim.mic.slink.Adapter.DataAdapterComment;
import com.ensim.mic.slink.Repository.CommentRepository;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.OnChangeObject;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.Comment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BottomSheetComment extends BottomSheetDialogFragment {

    private int idLink;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private int userId;
    private ImageView ivRefresh;
    private TextView tvEmptyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout_comment, container, false);

        userId = Model.getInstance().getCurrentUser().getContent().getId();

        //init views
        progressBar = v.findViewById(R.id.progress_circular);
        CardView btnComment = v.findViewById(R.id.cvComment);
        final EditText etText = v.findViewById(R.id.etText);
        recyclerView = v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ivRefresh = v.findViewById(R.id.ivRefresh);
        tvEmptyList = v.findViewById(R.id.tvEmptyList);

        hideTvEmptyList();


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etText.getText().toString();
                if (!text.isEmpty()) {
                    new CommentRepository().createComment(idLink, userId, text);
                    etText.setText("");
                }
            }
        });

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommentRepository().displayComments(idLink);
            }
        });

        Model.getInstance().getComments().addOnChangeObjectListener(new OnChangeObject() {

            @Override
            public void onLoading() {
                showProgress();
            }

            @Override
            public void onDataReady() {
                hideProgress();
                List<Comment> content = Model.getInstance().getComments().getContent();

                mAdapter = new DataAdapterComment(getActivity(), content, userId);
                recyclerView.setAdapter(mAdapter);

                if(content.isEmpty()) showTvEmptyList();
                else hideTvEmptyList();

            }

            @Override
            public void onFailed() {
                hideProgress();
                }
        });

        return v;
    }

    public int getIdLink() {
        return idLink;
    }

    public void setIdLink(int idLink) {
            this.idLink = idLink;
            new CommentRepository().displayComments(idLink);
    }

    @NonNull @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return  dialog;
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) requireContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
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
