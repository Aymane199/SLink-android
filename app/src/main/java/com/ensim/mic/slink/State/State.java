package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.Table.User;

import java.util.ArrayList;
import java.util.List;

public class State {

    static private State INSTATNCE = new State();

    static public State getInstance(){
        return INSTATNCE;
    }

    private BaseObjectState<User> currentUser;
    private FoldersState folders;
    private LinksState links;
    private CommentsState comments;
    private BaseObjectState<List<LinkOfFolder>> savedLinks;
    private BaseObjectState<Folder> folder;
    private BaseObjectState<List<SharePersonne>> sharePeople;
    private BaseObjectState<User> searchUser;



    private State(){
        currentUser = new BaseObjectState<>(new User());
        folders = new FoldersState(new ArrayList<FolderOfUser>());
        links = new LinksState(new ArrayList<LinkOfFolder>());
        comments = new CommentsState(new ArrayList<Comment>());
        savedLinks = new BaseObjectState<List<LinkOfFolder>>(new ArrayList<LinkOfFolder>());
        folder = new BaseObjectState<>(new Folder());
        sharePeople = new BaseObjectState<List<SharePersonne>>(new ArrayList<SharePersonne>());
        searchUser = new BaseObjectState<>(new User());
    }

    public BaseObjectState<User> getCurrentUser() {
        return currentUser;
    }

    public FoldersState getFolders() {
        return folders;
    }

    public LinksState getLinks() {
        return links;
    }

    public CommentsState getComments() {
        return comments;
    }

    public BaseObjectState<List<LinkOfFolder>> getSavedLinks() {
        return savedLinks;
    }

    public BaseObjectState<Folder> getFolder() {
        return folder;
    }

    public BaseObjectState<List<SharePersonne>> getSharePeople() {
        return sharePeople;
    }

    public BaseObjectState<User> getSearchUser() {
        return searchUser;
    }
}

