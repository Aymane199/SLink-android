package com.ensim.mic.slink.Component;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Operations.OperationsOnUser;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;


public class UserComponents {


    public void showDeleteDialog(final Context mContext, final int idUser) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Delete Account");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Are you sure you want to delete your account ? if you delete your account, you will permanently lose your profile, folders and links.")
                .setCustomTitle(tvTitle)
                .setCancelable(true);

        alertDialogBuilder.setPositiveButton("Delete Account", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = new User();
                user.setUserName("SLink user");
                user.setGmail("Slink@gmail.com");
                new OperationsOnUser().updateUser(idUser,user);

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                // Build a GoogleSignInClient with the options specified by gso.
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
                mGoogleSignInClient.revokeAccess()
                        .addOnCompleteListener((Activity) mContext, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ((Activity) mContext).moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);

                            }
                        });


            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        alertDialogBuilder.show();
    }


    public void showRenameUserName(final Context context, final User user) {


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
        tvTitle.setText("Change your username");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("new username :")
                .setCustomTitle(tvTitle)
                .setCancelable(true)
                .setView(layout);

        input.setText(user.getUserName());

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!input.getText().toString().isEmpty())
                {
                    User user1 = new User();
                    user1.setUserName(input.getText().toString());
                    new OperationsOnUser().updateUser(user.getId(), user1);
                    dialog.dismiss();

                }else{
                    Toast.makeText(context,"Cannot accept an empty username.",Toast.LENGTH_LONG).show();
                }
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