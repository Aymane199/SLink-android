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
    private List<State.OnChangeUserFolders> onChangeFoldersListner;

    private StateListLinks links;
    private List<OnChangeLinks> onChangeLinksListner;

    private StateListComments comments;
    private List<OnChangeComments> onChangeCommentsListner;


    private State(){
        folders = new StateListFolders();
        onChangeFoldersListner = new ArrayList<>();
        links = new StateListLinks();
        onChangeLinksListner = new ArrayList<>();
        comments = new StateListComments();
        onChangeCommentsListner = new ArrayList<>();
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
        for (State.OnChangeUserFolders listner : onChangeFoldersListner) {
            listner.onChange();
        }
    }

    public void setFoldersState(RequestState state) {
        folders.setState(state) ;
        for (State.OnChangeUserFolders listner : onChangeFoldersListner) {
            listner.onChange();
        }
    }

    public void setFoldersList(List<FolderOfUser> foldersOfUser) {
        this.folders.setFolders(foldersOfUser); ;
        for (State.OnChangeUserFolders listner : onChangeFoldersListner) {
            listner.onChange();
        }
    }

    public void setOnChangeFoldersListner(OnChangeUserFolders onChangeFoldersListner) {
        this.onChangeFoldersListner.add(onChangeFoldersListner);
    }

    public interface OnChangeUserFolders{
        void onChange();
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
        for (OnChangeComments listner : onChangeCommentsListner) {
            listner.onChange();
        }
    }

    public void setCommentsState(RequestState state) {
        folders.setState(state) ;
        for (OnChangeComments listner : onChangeCommentsListner) {
            listner.onChange();
        }
    }

    public void setCommentsList(List<Comment> comments) {
        this.comments.setComments(comments); ;
        for (OnChangeComments listner : onChangeCommentsListner) {
            listner.onChange();
        }
    }

    public void setOnChangeCommentsListner(OnChangeComments onChangeUserFoldersListner) {
        this.onChangeCommentsListner.add(onChangeUserFoldersListner);
    }

    public interface OnChangeComments {
        void onChange();
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
        for (OnChangeLinks listner : onChangeLinksListner) {
            listner.onChange();
        }
    }

    public void setLinksState(RequestState state) {
        folders.setState(state) ;
        for (OnChangeLinks listner : onChangeLinksListner) {
            listner.onChange();
        }
    }

    public void setLinksList(List<LinkOfFolder> links) {
        this.links.setLinks(links); ;
        for (OnChangeLinks listner : onChangeLinksListner) {
            listner.onChange();
        }
    }

    public void setOnChangeLinksListner(OnChangeLinks onChangeUserFoldersListner) {
        this.onChangeLinksListner.add(onChangeUserFoldersListner);
    }

    public interface OnChangeLinks {
        void onChange();
    }


    /*
    * State of the list of saved links
     */

    /*
    * State of members of a folder
     */


}

