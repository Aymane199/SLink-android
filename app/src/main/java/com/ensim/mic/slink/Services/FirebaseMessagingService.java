package com.ensim.mic.slink.Services;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.ensim.mic.slink.Operations.OperationsOnComment;
import com.ensim.mic.slink.Operations.OperationsOnFolder;
import com.ensim.mic.slink.Operations.OperationsOnLink;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.utils.FolderFilter;
import com.google.firebase.messaging.RemoteMessage;


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        System.out.println("message :" + remoteMessage.getData().get("behavior"));

        refreshAdequateObservableObject(remoteMessage);


    }

    private void refreshAdequateObservableObject(final RemoteMessage remoteMessage) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String behavior = remoteMessage.getData().get("behavior");
                if(behavior == null ) return;

                switch (behavior) {
                    case "share":
                        new OperationsOnFolder().displayFolders(FolderFilter.FILTER_ANYONE, "");
                        Toast.makeText(getApplicationContext(),remoteMessage.getNotification().getTitle(),Toast.LENGTH_LONG).show();
                        break;

                    case "link":
                        String idFolder = remoteMessage.getData().get("idFolder");
                        if(idFolder == null) return;

                        if(State.getInstance().getLinks().getFolderId() == Integer.parseInt(idFolder))
                        {
                            new OperationsOnLink().displayLinks("",idFolder,State.getInstance().getCurrentUser().getContent().getId()+"");
                        }else
                            Toast.makeText(getApplicationContext(),remoteMessage.getNotification().getTitle(),Toast.LENGTH_LONG).show();

                        break;

                    case "comment":
                        String idLink = remoteMessage.getData().get("idLink");
                        if(idLink == null) return;

                        if(State.getInstance().getComments().getLinkId() == Integer.parseInt(idLink))
                            new OperationsOnComment().displayComments(State.getInstance().getComments().getLinkId());
                        else
                            Toast.makeText(getApplicationContext(),remoteMessage.getNotification().getTitle(),Toast.LENGTH_LONG).show();

                        break;

                }

            }
        });
    }


}