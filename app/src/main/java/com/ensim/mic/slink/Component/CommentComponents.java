package com.ensim.mic.slink.Component;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.TextView;

import com.ensim.mic.slink.Repository.CommentRepository;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Comment;


public class CommentComponents {


    public void showDeleteDialog(final Context mContext, final Comment comment) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Delete");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Are you sure you want to permanently remove this comment ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true);

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new CommentRepository().deleteComment(comment);

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        alertDialogBuilder.show();
    }


}