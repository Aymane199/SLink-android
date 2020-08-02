package com.ensim.mic.slink.Listener;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.ensim.mic.slink.Component.ShareComponents;
import com.ensim.mic.slink.R;
import com.ensim.mic.slink.Table.SharePersonne;

/*
*   Clean
 */
public class ShareMenuListener implements View.OnClickListener {

    ImageView imageView;
    Context mContext;
    SharePersonne sharePersonne;


    public ShareMenuListener(Context mContext, ImageView imageView, SharePersonne sharePersonne) {
        this.mContext = mContext;
        this.imageView = imageView;
        this.sharePersonne = sharePersonne;
    }

    @Override
    public void onClick(View view) {

        //creating a popup menu
        PopupMenu popup = new PopupMenu(mContext, imageView);
        //inflating menu from xml resource
        popup.inflate(R.menu.link_menu);
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
            case R.id.menuDelete:
                new ShareComponents().showDeleteDialog(mContext, sharePersonne);
                break;

        }
    }

}


