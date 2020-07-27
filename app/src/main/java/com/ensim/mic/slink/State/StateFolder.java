package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.utils.RequestState;

public class StateFolder {

    private Folder folder;
    private RequestState state;

    public StateFolder() {
        state = RequestState.SUCCESSFUL;
    }

    public StateFolder(Folder folders, RequestState state) {
        this.folder = folders;
        this.state = state;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }
}


