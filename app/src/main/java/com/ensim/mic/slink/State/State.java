package com.ensim.mic.slink.State;

import com.ensim.mic.slink.Table.Comment;
import com.ensim.mic.slink.Table.FolderOfUser;
import com.ensim.mic.slink.Table.LinkOfFolder;
import com.ensim.mic.slink.utils.RequestState;

import java.util.ArrayList;
import java.util.List;

public class State {

    static public int idUser = 3;

    static private State INSTATNCE = new State();

    static public State getInstance(){
        return INSTATNCE;
    }

    private StateListFolders folders;
    private List<OnChangeObject> onChangeFoldersListner;

    private StateListLinks links;
    private List<OnChangeObject> onChangeLinksListner;

    private StateListComments comments;
    private List<OnChangeObject> onChangeCommentsListner;

    StateListSavedLinks savedLinks;
    private List<OnChangeObject> onChangeSavedLinksListner;

    private StateFolder folder;
    private List<OnChangeObject> onChangeFolderListner;


    private State(){
        folders = new StateListFolders();
        onChangeFoldersListner = new ArrayList<>();
        links = new StateListLinks();
        onChangeLinksListner = new ArrayList<>();
        comments = new StateListComments();
        onChangeCommentsListner = new ArrayList<>();
        savedLinks = new StateListSavedLinks();
        onChangeSavedLinksListner = new ArrayList<>();
        folder = new StateFolder();
        onChangeFolderListner =new ArrayList<>();
    }

    public interface
    OnChangeObject{
        void onChange();
    }

    /*
    * State of the list of the folders
     */
    public StateListFolders getFolders() {
        return folders;
    }

    public void setFolders(StateListFolders folders) {
        if(folders.getListFolder().isEmpty()) return;
        for (FolderOfUser folderOfUser :
             folders.getListFolder()) {
            System.out.println(folderOfUser.toString());
        }
        this.folders = folders;
        for (OnChangeObject listner : onChangeFoldersListner) {
            listner.onChange();
        }
    }

    public void setFoldersState(RequestState state) {
        folders.setState(state) ;
        for (OnChangeObject listner : onChangeFoldersListner) {
            listner.onChange();
        }
    }

    public void setFoldersList(List<FolderOfUser> foldersOfUser) {
        this.folders.setFolders(foldersOfUser); ;
        for (OnChangeObject listner : onChangeFoldersListner) {
            listner.onChange();
        }
    }

    public void setOnChangeFoldersListner(OnChangeObject onChangeFoldersListner) {
        this.onChangeFoldersListner.add(onChangeFoldersListner);
    }

    /*
    * State of the list of the comments
     */

    public StateListComments getComments() {
        return comments;
    }

    public void setComments(StateListComments comments) {
        for (Comment comment :
                comments.getListComments()) {
            System.out.println(comment.toString());
        }
        this.comments = comments;
        for (OnChangeObject listner : onChangeCommentsListner) {
            listner.onChange();
        }
    }

    public void setCommentsState(RequestState state) {
        folders.setState(state) ;
        for (OnChangeObject listner : onChangeCommentsListner) {
            listner.onChange();
        }
    }

    public void setCommentsList(List<Comment> comments) {
        this.comments.setComments(comments); ;
        for (OnChangeObject listner : onChangeCommentsListner) {
            listner.onChange();
        }
    }

    public void setOnChangeCommentsListner(OnChangeObject onChangeUserFoldersListner) {
        this.onChangeCommentsListner.add(onChangeUserFoldersListner);
    }

    /*
    * State of the list of the links
     */

    public StateListLinks getLinks() {
        return links;
    }

    public void setLinks(StateListLinks links) {
        for (LinkOfFolder link :
                links.getListLinks()) {
            System.out.println(link.toString());
        }
        this.links = links;
        for (OnChangeObject listner : onChangeLinksListner) {
            listner.onChange();
        }
    }

    public void setLinksState(RequestState state) {
        folders.setState(state) ;
        for (OnChangeObject listner : onChangeLinksListner) {
            listner.onChange();
        }
    }

    public void setLinksList(List<LinkOfFolder> links) {
        this.links.setLinks(links); ;
        for (OnChangeObject listner : onChangeLinksListner) {
            listner.onChange();
        }
    }

    public void setOnChangeLinksListner(OnChangeObject onChangeUserFoldersListner) {
        this.onChangeLinksListner.add(onChangeUserFoldersListner);
    }

    /*
    * State of the list of saved links
     */
    public StateListSavedLinks getSavedLinks() {
        return savedLinks;
    }

    public void setSavedLinks(StateListSavedLinks savedLinks) {
        for (LinkOfFolder link :
                savedLinks.getListLinks()) {
            System.out.println(link.toString());
        }
        this.savedLinks = savedLinks;
        for (OnChangeObject listner : onChangeSavedLinksListner) {
            listner.onChange();
        }
    }

    public void setSavedLinksState(RequestState state) {
        folders.setState(state) ;
        for (OnChangeObject listner : onChangeSavedLinksListner) {
            listner.onChange();
        }
    }

    public void setSavedLinksList(List<LinkOfFolder> savedLinks) {
        this.savedLinks.setLinks(savedLinks); ;
        for (OnChangeObject listner : onChangeSavedLinksListner) {
            listner.onChange();
        }
    }

    public void setOnChangeSavedLinksListner(OnChangeObject onChangeSavedLinksListner) {
        this.onChangeSavedLinksListner.add(onChangeSavedLinksListner);
    }
    /*
    *   State of folder details
     */

    public StateFolder getFolder() {
        return folder;
    }

    public void setFolder(StateFolder stateFolder) {

        this.folder = stateFolder;
        for (OnChangeObject listner : onChangeFolderListner) {
            listner.onChange();
        }
    }

    public void setFolderState(RequestState state) {
        folders.setState(state) ;
        for (OnChangeObject listner : onChangeFolderListner) {
            listner.onChange();
        }
    }

    public void setFolderList(List<LinkOfFolder> savedLinks) {
        this.savedLinks.setLinks(savedLinks);
        for (OnChangeObject listner : onChangeFolderListner) {
            listner.onChange();
        }
    }

    public void setOnChangeFolderListner(OnChangeObject onChangeSavedLinksListner) {
        this.onChangeFolderListner.add(onChangeSavedLinksListner);
    }

    /*
    * State of members of a folder
     */


}

