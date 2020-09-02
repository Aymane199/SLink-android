package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.utils.RequestState;

import java.util.List;

public class CommentsState extends BaseObjectState<List<Comment>> {

    private int linkId;

    public CommentsState(List<Comment> comments, RequestState state, int linkId) {
        super(comments, state);
        this.linkId = linkId;
    }

    public CommentsState(List<Comment> comments) {
        super(comments);
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }
}
