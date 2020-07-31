package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.Table.User;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class State {

    static public int idUser = 3;

    static private State INSTATNCE = new State();

    static public State getInstance(){
        return INSTATNCE;
    }


    private FoldersState foldersList;

    private LinksState linksList;

    private BaseObjectState<List<Comment>> commentsList;

    private BaseObjectState<List<LinkOfFolder>> savedLinksList;

    private BaseObjectState<Folder> folder1;

    private BaseObjectState<List<SharePersonne>> sharePersonnesList;

    private BaseObjectState<User> searchUser1;


    private State(){
        foldersList = new FoldersState(new ArrayList<FolderOfUser>());
        linksList = new LinksState(new ArrayList<LinkOfFolder>());
        commentsList = new BaseObjectState<List<Comment>>(new ArrayList<Comment>());
        savedLinksList = new BaseObjectState<List<LinkOfFolder>>(new ArrayList<LinkOfFolder>()) ;
        folder1 = new BaseObjectState<Folder>(new Folder());
        sharePersonnesList = new BaseObjectState<List<SharePersonne>>(new ArrayList<SharePersonne>()) ;
        searchUser1 = new BaseObjectState<User>(new User());
    }


    public FoldersState getFoldersList() {
        return foldersList;
    }

    public LinksState getLinksList() {
        return linksList;
    }

    public BaseObjectState<List<Comment>> getCommentsList() {
        return commentsList;
    }

    public BaseObjectState<List<LinkOfFolder>> getSavedLinksList() {
        return savedLinksList;
    }

    public BaseObjectState<Folder> getFolder1() {
        return folder1;
    }

    public BaseObjectState<List<SharePersonne>> getSharePersonnesList() {
        return sharePersonnesList;
    }

    public BaseObjectState<User> getSearchUser1() {
        return searchUser1;
    }

}

