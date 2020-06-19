package com.ensim.mic.slink.Listener;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ensim.mic.slink.Api.ApiUserImpl;
import com.ensim.mic.slink.Api.LinkApiServices;
import com.ensim.mic.slink.Api.RetrofitFactory;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderOutput;
import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.Table.UserFolder;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FolderMenuListener implements View.OnClickListener {

    ImageView imageView;
    Context mContext;
    UserFolder folderOutput;
    LinkApiServices linkApiServices;


    public FolderMenuListener(Context mContext, ImageView imageView, UserFolder folderOutput) {
        this.mContext = mContext;
        this.imageView = imageView;
        this.folderOutput = folderOutput;
        linkApiServices = RetrofitFactory.getINSTANCE().getRetrofit().create(LinkApiServices.class);
    }


    @Override
    public void onClick(View view) {

        //creating a popup menu
        PopupMenu popup = new PopupMenu(mContext, imageView);
        //inflating menu from xml resource
        popup.inflate(R.menu.folder_menu);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                manageMenu(item);
                return false;
            }
        });
        //displaying the popup
        popup.show();

    }

    private void manageMenu(MenuItem item) {
        ApiUserImpl apiUser = new ApiUserImpl();
        switch (item.getItemId()) {
            case R.id.menuDetails:
                //go to intent details
                break;
            case R.id.menuShare:
                break;
            case R.id.menuGet_link:
                //Toast.makeText(mContext, "Link copied to clipboard", Toast.LENGTH_LONG).show();
                break;
            case R.id.menuRename:
                showRenameDialog();
                break;
            case R.id.menuDelete:
                showDeleteDialog();
                break;
            case R.id.menuAdd_link:
                showLinkAddedDialog();
                break;
            case R.id.menuMake_public:
                showMakeItPublicDialog();
                break;
            case R.id.menuChange_picture:
                showChangePictureDialog();
                break;
        }
    }

    private void showChangePictureDialog() {
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

    private void showMakeItPublicDialog() {
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

    private void showLinkAddedDialog() {

        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        String link = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
        System.out.println("clipboard link "+link);
        HashMap<String, Object> body = new HashMap<>();
        body.put("name","twijnat lala na3ima");
        body.put("URL",link);
        body.put("picture"," ");
        body.put("folder",folderOutput.getId());

        Call<Object> call = linkApiServices.createLink(body);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    System.out.println("message: " + response.message());
                    System.out.println("error: " + response.errorBody());
                    return ;
                }
                Object links = response.body();
                System.out.println(links.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println(t.getMessage());
                return;
            }
        });

    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);

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

                Toast.makeText(mContext, "OnClick",
                        Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.show();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(mContext, "menuDetails " + folderOutput.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(mContext, "menuDetals no " + folderOutput.getName(),
                        Toast.LENGTH_LONG).show();
            }

        });
        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Delete");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Are you sure you want to permanently remove this folder ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true);

        alertDialogBuilder.show();
    }

    private void showRenameDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, R.style.CustomAlertDialog);


        final EditText input = new EditText(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 0, 50, 0);
        input.setLayoutParams(layoutParams);
        input.setInputType(EditText.AUTOFILL_TYPE_TEXT);
        input.setSingleLine();

        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(input);

        TextView tvTitle = new TextView(mContext);
        tvTitle.setText("Rename");
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage("Please entre the new folder name ?")
                .setCustomTitle(tvTitle)
                .setCancelable(true)
                .setView(layout)
                .setCancelable(true);


        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(mContext, input.getText() + folderOutput.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(mContext, "menuDetals no " + folderOutput.getName(),
                        Toast.LENGTH_LONG).show();
            }

        });
        alertDialogBuilder.show();
    }

}

