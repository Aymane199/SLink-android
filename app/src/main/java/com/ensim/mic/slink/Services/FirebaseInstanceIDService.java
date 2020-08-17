package com.ensim.mic.slink.Services;

import com.ensim.mic.slink.Operations.OperationsOnUser;
import com.ensim.mic.slink.State.State;
import com.ensim.mic.slink.Table.User;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onCreate() {
        super.onCreate();
        String token = FirebaseInstanceId.getInstance().getToken();

        registerToken(token);
    }

    @Override
    public void onTokenRefresh() {


    }

    private void registerToken(String token) {

        User user = new User();
        user.setToken(token);
        Integer id = State.getInstance().getCurrentUser().getContent().getId();

        new OperationsOnUser().updateUser(id,user);

    }
}