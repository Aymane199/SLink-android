package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class StateSearchPersonne {

    private User user;
    private RequestState state;

    public StateSearchPersonne() {
        user = new User();
        state = RequestState.SUCCESSFUL;
    }

    public StateSearchPersonne(User user, RequestState state) {
        this.user = user;
        this.state = state;
    }

    public User getListPersonnes() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

}


