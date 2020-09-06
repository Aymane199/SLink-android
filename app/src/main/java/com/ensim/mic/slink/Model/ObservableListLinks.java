package com.ensim.mic.slink.Model;

import com.ensim.mic.slink.Table.Link;
import com.ensim.mic.slink.utils.RequestState;

import java.util.List;

public class ObservableListLinks extends BaseObservableObject<List<Link>> {

    private int folderId;

    public ObservableListLinks(List<Link> links, RequestState state, int folderId) {
        super(links, state);
        this.folderId = folderId;
    }

    public ObservableListLinks(List<Link> links) {
        super(links);
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }
}
