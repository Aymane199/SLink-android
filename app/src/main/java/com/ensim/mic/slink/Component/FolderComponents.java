package com.ensim.mic.slink.Component;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Repository.FolderRepository;
import com.ensim.mic.slink.Repository.LinkRepository;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.Table.Link;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import io.github.ponnamkarthik.richlinkpreview.MetaData;
import io.github.ponnamkarthik.richlinkpreview.ResponseListener;
import io.github.ponnamkarthik.richlinkpreview.RichPreview;


public class FolderComponents {


    public void showAddFolderDialog(final Context context, final int userId) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);

        final EditText input = new EditText(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 0, 50, 0);
        input.setLayoutParams(layoutParams);
        input.setInputType(EditText.AUTOFILL_TYPE_TEXT);
        input.setSingleLine();

        LinearLayout layout = new LinearLayout(context);
        layout.addView(input);

        TextView tvTitle = new TextView(context);
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

                Toast.makeText(context, input.getText() + "created",
                        Toast.LENGTH_LONG).show();
                new FolderRepository().createFolder(input.getText().toString(), userId);
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialogBuilder.show();
    }

    public void showChangePictureDialog(final Context mContext, final FolderWithoutUser folderOutput) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);

        final ImageView input = new ImageView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //layoutParams.setMargins(20,  20, 20, 20);
        input.setLayoutParams(layoutParams);

        try {
            Picasso.get().load(Uri.parse(folderOutput.getPicture())).into(input);
        } catch (Exception e) {

        }

        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(input);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Picture");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder
                .setCustomTitle(tvTitle)
                .setCancelable(true)
                .setView(layout)
                .setCancelable(true);


        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //new OperationOnFolder().createFolder(input.getText().toString(), userId);
                Toast.makeText(mContext, folderOutput.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNegativeButton("Change pic", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Toast.makeText(mContext, "menuDetals no " + folderOutput.getName(),
                        Toast.LENGTH_LONG).show();
            }

        });
        alertDialogBuilder.show();
    }

    public void showMakeItPublicDialog(final Context mContext, final FolderWithoutUser folderOutput) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);


        final Switch aSwitch = new Switch(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 0, 50, 0);
        aSwitch.setLayoutParams(layoutParams);
        aSwitch.setChecked(Boolean.parseBoolean(folderOutput.getPublic()));

        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(aSwitch);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Make it public");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Do you want to make this folder public ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true)
                .setView(layout)
                .setCancelable(true);


        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(mContext, aSwitch.isChecked() + "",
                        Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.show();
    }

    public void showLinkAddedDialog(final Context mContext, final FolderWithoutUser folderOutput, final int idUser) {


        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        String linkUrl;
        try {
            linkUrl = Objects.requireNonNull(clipboard.getPrimaryClip()).getItemAt(0).getText().toString();
        }catch (Exception e){
            Toast.makeText(mContext,"Please copy an URL. ",Toast.LENGTH_LONG).show();
            return;
        }
        if(!URLUtil.isValidUrl(linkUrl)){
            Toast.makeText(mContext,"Please copy a valid URL. ",Toast.LENGTH_LONG).show();
            return;
        }



        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Link added successfully");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        final ImageView imageView = new ImageView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 0, 50, 0);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.ic_success);

        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.addView(imageView);

        alertDialogBuilder
                .setCustomTitle(tvTitle)
                .setView(layout)
                .setCancelable(true);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        RichPreview richPreview = new RichPreview(new ResponseListener() {
            @Override
            public void onData(MetaData metaData) {

                Link link =new Link();
                link.setName(metaData.getTitle());
                link.setPicture(metaData.getImageurl());
                link.setUrl(metaData.getUrl());

                new LinkRepository().addLinktoFolder(folderOutput,link, idUser);
                alertDialogBuilder.show();


            }

            @Override
            public void onError(Exception e) {
            }

        });

        richPreview.getPreview(linkUrl);


    }

    public void showDeleteDialog(final Context mContext, final FolderWithoutUser folderOutput) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Delete");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Are you sure you want to permanently remove this folder ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true);

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new FolderRepository().deleteFolder(folderOutput);

            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        alertDialogBuilder.show();
    }

    public void showQuitFolderDialog(final Context mContext, final FolderWithoutUser folderOutput) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Quit the folder");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Are you sure you want to leave this folder ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true);

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new FolderRepository().QuitFolder(folderOutput);

            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        alertDialogBuilder.show();
    }


    public void showRenameDialog(final Context mContext, final FolderWithoutUser folderOutput) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);


        final EditText input = new EditText(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 0, 50, 0);
        input.setLayoutParams(layoutParams);
        input.setInputType(EditText.AUTOFILL_TYPE_TEXT);
        input.setSingleLine();
        input.setText(folderOutput.getName());

        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(input);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Rename");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Please enter the new name ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true)
                .setView(layout)
                .setCancelable(true);


        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = input.getText().toString();
                if(!newName.isEmpty()) {
                    folderOutput.setName(newName);
                    new FolderRepository().updateFolder(folderOutput);
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(mContext, "menuDetals no " + folderOutput.getName(),
                        Toast.LENGTH_LONG).show();
            }

        });
        alertDialogBuilder.show();
    }

}