package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class StateListComments {

    private List<Comment> comments;
    private RequestState state;

    public StateListComments() {
        comments = new ArrayList<>();
        state = RequestState.SUCCESSFUL;
    }

    public StateListComments(List<Comment> foldersOfUser, RequestState state) {
        this.comments = foldersOfUser;
        this.state = state;
    }

    public List<Comment> getListComments() {
        return comments;
    }

    public void setComments(List<Comment> commentsOfFolders) {
        this.comments = commentsOfFolders;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }
}


