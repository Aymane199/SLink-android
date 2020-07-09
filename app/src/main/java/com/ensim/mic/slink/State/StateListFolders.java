package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class StateListFolders {

    private List<FolderOfUser> foldersOfUser;
    private RequestState state;

    public StateListFolders() {
        foldersOfUser = new ArrayList<>();
        state = RequestState.SUCCESSFUL;
    }

    public StateListFolders(List<FolderOfUser> foldersOfUser, RequestState state) {
        this.foldersOfUser = foldersOfUser;
        this.state = state;
    }

    public List<FolderOfUser> getListFolder() {
        return foldersOfUser;
    }

    public void setFoldersOfUser(List<FolderOfUser> foldersOfUser) {
        this.foldersOfUser = foldersOfUser;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }
}


