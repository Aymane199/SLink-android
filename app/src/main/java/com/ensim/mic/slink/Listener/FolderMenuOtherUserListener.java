package com.ensim.mic.slink.Listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.ensim.mic.slink.Activities.FolderDetailsActivity;
import com.ensim.mic.slink.Component.FolderComponents;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.FolderOfUser;

/*
*   Clean
 */
public class FolderMenuOtherUserListener implements View.OnClickListener  {

    ImageView imageView;
    Context mContext;
    FolderOfUser folderOutput;


    public FolderMenuOtherUserListener(Context mContext, ImageView imageView, FolderOfUser folderOutput) {
        this.mContext = mContext;
        this.imageView = imageView;
        this.folderOutput = folderOutput;
    }

    @Override
    public void onClick(View view) {

        //creating a popup menu
        PopupMenu popup = new PopupMenu(mContext, imageView);
        //inflating menu from xml resource
        popup.inflate(R.menu.folder_menu_other_user);
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
        switch (item.getItemId()) {
            case R.id.menuDetails:
                Intent intent = new Intent(mContext, FolderDetailsActivity.class);
                intent.putExtra("idFolder",Integer.parseInt(folderOutput.getId()));
                Bundle bundle = new Bundle();
                bundle.putSerializable("folder", folderOutput);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
           case R.id.menuAdd_link:
                new FolderComponents().showLinkAddedDialog(mContext,folderOutput);
                break;
            case R.id.menuQuit:
                new FolderComponents().showQuitFolderDialog(mContext,folderOutput);
                break;
        }
    }

}


