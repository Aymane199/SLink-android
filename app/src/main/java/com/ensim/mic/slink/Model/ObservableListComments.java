package com.ensim.mic.slink.Model;

import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.utils.RequestState;

import java.util.List;

public class ObservableListComments extends BaseObservableObject<List<Comment>> {

    private int linkId;

    public ObservableListComments(List<Comment> comments, RequestState state, int linkId) {
        super(comments, state);
        this.linkId = linkId;
    }

    public ObservableListComments(List<Comment> comments) {
        super(comments);
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }
}
