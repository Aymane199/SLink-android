package com.ensim.mic.slink.Listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.ensim.mic.slink.Activities.ShareActivity;
import com.ensim.mic.slink.Component.FolderComponents;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Model.Model;
import com.ensim.mic.slink.Table.FolderWithoutUser;

/*
*   Clean
 */
public class FolderMenuOwnerListener implements View.OnClickListener  {

    ImageView imageView;
    Context mContext;
    FolderWithoutUser folderOutput;


    public FolderMenuOwnerListener(Context mContext, ImageView imageView, FolderWithoutUser folderOutput) {
        this.mContext = mContext;
        this.imageView = imageView;
        this.folderOutput = folderOutput;
    }

    @Override
    public void onClick(View view) {

        //creating a popup menu
        PopupMenu popup = new PopupMenu(mContext, imageView);

        //inflating menu from xml resource
        popup.inflate(R.menu.folder_menu_owner);
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
            /*case R.id.menuDetails:
                Intent intent = new Intent(mContext, FolderDetailsActivity.class);
                intent.putExtra("idFolder",Integer.parseInt(folderOutput.getId()));
                Bundle bundle = new Bundle();
                bundle.putSerializable("folder", folderOutput);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;*/
            case R.id.menuShare:
                Intent intent1 = new Intent(mContext, ShareActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("folder", folderOutput);
                intent1.putExtras(bundle1);
                mContext.startActivity(intent1);
                break;
            /*case R.id.menuGet_link:
                //Toast.makeText(mContext, "Link copied to clipboard", Toast.LENGTH_LONG).show();
                break;*/
            case R.id.menuRename:
                new FolderComponents().showRenameDialog(mContext,folderOutput);
                break;
            case R.id.menuDelete:
                new FolderComponents().showDeleteDialog(mContext,folderOutput);
                break;
            case R.id.menuAdd_link:
                new FolderComponents().showLinkAddedDialog(mContext,folderOutput, Model.getInstance().getCurrentUser().getContent().getId());
                break;
           /* case R.id.menuMake_public:
                new FolderComponents().showMakeItPublicDialog(mContext,folderOutput);
                break;*/
           /* case R.id.menuChange_picture:
                new FolderComponents().showChangePictureDialog(mContext,folderOutput);
                break;*/
        }
    }

}


