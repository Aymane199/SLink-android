package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class StateListSavedLinks {

    private List<LinkOfFolder> links;
    private RequestState state;

    public StateListSavedLinks() {
        links = new ArrayList<>();
        state = RequestState.SUCCESSFUL;
    }

    public StateListSavedLinks(List<LinkOfFolder> LinkOfFolder, RequestState state) {
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

}
