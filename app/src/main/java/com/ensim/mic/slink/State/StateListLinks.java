package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class StateListLinks {

    private int folderId;
    private List<LinkOfFolder> links;
    private RequestState state;

    public StateListLinks() {
        links = new ArrayList<>();
        state = RequestState.SUCCESSFUL;
    }

    public StateListLinks(int folderId, List<LinkOfFolder> LinkOfFolder, RequestState state) {
        this.folderId = folderId;
        this.links = LinkOfFolder;
        this.state = state;
    }

    public List<LinkOfFolder> getListLinks() {
        return links;
    }

    public void setLinks(List<LinkOfFolder> linksOfFolders) {
        this.links = linksOfFolders;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}


