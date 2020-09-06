package com.ensim.mic.slink.Model;

import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.Table.Folder;
import com.ensim.mic.slink.Table.FolderWithoutUser;
import com.ensim.mic.slink.Table.Link;
import com.ensim.mic.slink.Table.SharePersonne;
import com.ensim.mic.slink.Table.User;

import java.util.ArrayList;
import java.util.List;

public class Model {

    static private Model INSTATNCE = new Model();

    static public Model getInstance(){
        return INSTATNCE;
    }

    private BaseObservableObject<User> currentUser;
    private ObservableListFolders folders;
    private ObservableListLinks links;
    private ObservableListComments comments;
    private BaseObservableObject<List<Link>> savedLinks;
    private BaseObservableObject<Folder> folder;
    private BaseObservableObject<List<SharePersonne>> folderMembers;
    private BaseObservableObject<User> searchUser;



    private Model(){
        currentUser = new BaseObservableObject<>(new User());
        folders = new ObservableListFolders(new ArrayList<FolderWithoutUser>());
        links = new ObservableListLinks(new ArrayList<Link>());
        comments = new ObservableListComments(new ArrayList<Comment>());
        savedLinks = new BaseObservableObject<List<Link>>(new ArrayList<Link>());
        folder = new BaseObservableObject<>(new Folder());
        folderMembers = new BaseObservableObject<List<SharePersonne>>(new ArrayList<SharePersonne>());
        searchUser = new BaseObservableObject<>(new User());
    }

    public BaseObservableObject<User> getCurrentUser() {
        return currentUser;
    }

    public ObservableListFolders getFolders() {
        return folders;
    }

    public ObservableListLinks getLinks() {
        return links;
    }

    public ObservableListComments getComments() {
        return comments;
    }

    public BaseObservableObject<List<Link>> getSavedLinks() {
        return savedLinks;
    }

    public BaseObservableObject<Folder> getFolder() {
        return folder;
    }

    public BaseObservableObject<List<SharePersonne>> getFolderMembers() {
        return folderMembers;
    }

    public BaseObservableObject<User> getSearchUser() {
        return searchUser;
    }
}

